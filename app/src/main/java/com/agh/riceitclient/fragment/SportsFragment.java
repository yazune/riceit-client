package com.agh.riceitclient.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agh.riceitclient.R;
import com.agh.riceitclient.activity.MainActivity;
import com.agh.riceitclient.dto.AddSportManDTO;
import com.agh.riceitclient.dto.DateDTO;
import com.agh.riceitclient.dto.AllSportsDTO;
import com.agh.riceitclient.dto.RemoveSportDTO;
import com.agh.riceitclient.dto.UpdateSportDTO;
import com.agh.riceitclient.dto.UpdateUserDetailsDTO;
import com.agh.riceitclient.model.Sport;
import com.agh.riceitclient.retrofit.ServiceGenerator;
import com.agh.riceitclient.service.SportService;
import com.agh.riceitclient.util.DetailsListener;
import com.agh.riceitclient.util.SportConstants;
import com.agh.riceitclient.util.SportsAdapter;
import com.agh.riceitclient.util.SportsListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SportsFragment extends Fragment implements SportsListener {

    String authToken;
    SportService sportService = ServiceGenerator.createService(SportService.class);

    TextView selectedDate;
    Button btnPreviousDay, btnNextDay;
    Button btnAddSport, btnTrackSport;

    final LocalDate finalToday = LocalDate.now();
    LocalDate today;
    HashMap<LocalDate, ArrayList<Sport>> sportsMap;

    RecyclerView sportsRv;
    SportsAdapter sportsAdapter;

    boolean isSportsAdapterAssigned;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_sports, container, false);

        selectedDate = v.findViewById(R.id.datebar_selected_date);
        btnPreviousDay = v.findViewById(R.id.btn_previous_day);
        btnNextDay = v.findViewById(R.id.btn_next_day);

        btnAddSport = v.findViewById(R.id.btn_add_sport_manually);
        btnTrackSport = v.findViewById(R.id.btn_add_sport_automatically);

        btnPreviousDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePreviousDay(view);
            }
        });

        btnNextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseNextDay(view);
            }
        });

        btnAddSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSportsAddFragment();
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("RiceItClient", Context.MODE_PRIVATE);
        authToken = sharedPreferences.getString("TOKEN", null); //second parameter is default

        sportsRv = v.findViewById(R.id.sports_rv);
        sportsRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        sportsAdapter = new SportsAdapter((SportsListener) getActivity(), getActivity().getSupportFragmentManager());
        isSportsAdapterAssigned = false;

        today = LocalDate.now();
        setDates();

        sportsMap = new HashMap<>();

        enqueueGetSports(today);

        return v;
    }

    public void openSportsAddFragment(){
        Fragment sportsAddFragment = new SportsAddFragment((SportsListener) getActivity());
        AddSportManDTO addSportManDTO = new AddSportManDTO();
        addSportManDTO.setDate(today.toString());

        Bundle args = new Bundle();

        args.putSerializable("addSportManDTO", addSportManDTO);
        sportsAddFragment.setArguments(args);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_container, sportsAddFragment, "sportsAddFragment")
                .addToBackStack(null)
                .commit();
    }

    private void updateSportsLayout(ArrayList<Sport> sportsResponse){
        sportsAdapter.setSports(sportsResponse);
        sportsMap.put(today, sportsResponse);

        if(!isSportsAdapterAssigned){
            sportsRv.setAdapter(sportsAdapter);
            isSportsAdapterAssigned = true;
        } else sportsAdapter.notifyDataSetChanged();
    }

    private void setDates() {

        if (today.equals(finalToday)){
            btnNextDay.setVisibility(View.INVISIBLE);
        } else{
            btnNextDay.setVisibility(View.VISIBLE);
        }


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM");
        selectedDate.setText(today.format(dtf));
    }

    public void choosePreviousDay(View view){
        today = today.minusDays(1);
        setDates();
        loadSportsWhenChangingDays(today);
    }

    public void chooseNextDay(View view){
        today = today.plusDays(1);
        setDates();
        loadSportsWhenChangingDays(today);
    }

    private void loadSportsWhenChangingDays(LocalDate date){
        if (sportsMap.containsKey(date)){
            sportsAdapter.setSports(sportsMap.get(date));
            sportsAdapter.notifyDataSetChanged();
        } else{
            enqueueGetSports(date);
        }
    }

    public void enqueueGetSports(LocalDate date){
        DateDTO dateDTO = new DateDTO(date.toString());

        Call<AllSportsDTO> call = sportService.showAllSports(authToken, dateDTO);
        call.enqueue(new Callback<AllSportsDTO>() {
            @Override
            public void onResponse(Call<AllSportsDTO> call, Response<AllSportsDTO> response) {
                if (response.isSuccessful()){
                    updateSportsLayout(response.body().getSports());
                }
            }

            @Override
            public void onFailure(Call<AllSportsDTO> call, Throwable t) {

            }
        });
    }

    @Override
    public void enqueueAddSportManually(AddSportManDTO addSportManDTO) {
        Call<Void> call = sportService.addSportMan(authToken, addSportManDTO);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    enqueueGetSports(today);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @Override
    public void enqueueRemoveSport(RemoveSportDTO removeSportDTO) {
        Call<Void> call = sportService.removeSport(authToken, removeSportDTO);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    enqueueGetSports(today);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @Override
    public void enqueueUpdateSport(UpdateSportDTO updateSportDTO) {
        Call<Void> call = sportService.updateSport(authToken, updateSportDTO);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    enqueueGetSports(today);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}