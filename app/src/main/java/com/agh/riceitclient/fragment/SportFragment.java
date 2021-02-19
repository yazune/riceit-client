package com.agh.riceitclient.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agh.riceitclient.R;
import com.agh.riceitclient.dto.SportAddDTO;
import com.agh.riceitclient.dto.DateDTO;
import com.agh.riceitclient.dto.SportsDTO;
import com.agh.riceitclient.dto.SportUpdateDTO;
import com.agh.riceitclient.model.Sport;
import com.agh.riceitclient.retrofit.ServiceGenerator;
import com.agh.riceitclient.service.SportService;
import com.agh.riceitclient.util.IdTransfer;
import com.agh.riceitclient.util.SportUpdateTransfer;
import com.agh.riceitclient.util.SportAdapter;
import com.agh.riceitclient.listener.SportListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SportFragment extends Fragment implements SportListener {

    String authToken;
    SportService sportService = ServiceGenerator.createService(SportService.class);

    TextView selectedDate;
    Button btnPreviousDay, btnNextDay;
    Button btnAddSport;

    final LocalDate finalToday = LocalDate.now();
    LocalDate today;
    HashMap<LocalDate, ArrayList<Sport>> sportMap;

    RecyclerView sportRv;
    SportAdapter sportAdapter;

    boolean isSportAdapterAssigned;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_sports, container, false);

        selectedDate = v.findViewById(R.id.datebar_selected_date);
        btnPreviousDay = v.findViewById(R.id.btn_previous_day);
        btnNextDay = v.findViewById(R.id.btn_next_day);

        btnAddSport = v.findViewById(R.id.btn_add_sport_manually);

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
                openSportAddFragment();
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("RiceItClient", Context.MODE_PRIVATE);
        authToken = sharedPreferences.getString("TOKEN", null); //second parameter is default

        sportRv = v.findViewById(R.id.sports_rv);
        sportRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        sportAdapter = new SportAdapter((SportListener) getActivity(), getActivity().getSupportFragmentManager());
        isSportAdapterAssigned = false;

        today = LocalDate.now();
        setDates();

        sportMap = new HashMap<>();

        enqueueGetSports(today);

        return v;
    }

    public void openSportAddFragment(){
        Fragment sportAddFragment = new SportAddFragment((SportListener) getActivity());
        SportAddDTO sportAddDTO = new SportAddDTO();
        sportAddDTO.setDate(today.toString());

        Bundle args = new Bundle();

        args.putSerializable("sportAddDTO", sportAddDTO);
        sportAddFragment.setArguments(args);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_container, sportAddFragment, "sportAddFragment")
                .addToBackStack(null)
                .commit();
    }

    private void updateSportsLayout(ArrayList<Sport> sportsResponse){
        sportAdapter.setSports(sportsResponse);
        sportMap.put(today, sportsResponse);

        if(!isSportAdapterAssigned){
            sportRv.setAdapter(sportAdapter);
            isSportAdapterAssigned = true;
        } else sportAdapter.notifyDataSetChanged();
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
        if (sportMap.containsKey(date)){
            sportAdapter.setSports(sportMap.get(date));
            sportAdapter.notifyDataSetChanged();
        } else{
            enqueueGetSports(date);
        }
    }

    public void enqueueGetSports(LocalDate date){
        DateDTO dateDTO = new DateDTO(date.toString());

        Call<SportsDTO> call = sportService.getSports(authToken, dateDTO);
        call.enqueue(new Callback<SportsDTO>() {
            @Override
            public void onResponse(Call<SportsDTO> call, Response<SportsDTO> response) {
                if (response.isSuccessful()){
                    updateSportsLayout(response.body().getSports());
                }
            }

            @Override
            public void onFailure(Call<SportsDTO> call, Throwable t) {

            }
        });
    }

    @Override
    public void enqueueAddSport(SportAddDTO sportAddDTO) {
        Call<Void> call = sportService.addSport(authToken, sportAddDTO);
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
    public void enqueueRemoveSport(IdTransfer idTransfer) {
        Call<Void> call = sportService.removeSport(authToken, idTransfer.getId());
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
    public void enqueueUpdateSport(SportUpdateTransfer sportUpdateTransfer) {

        SportUpdateDTO dto = new SportUpdateDTO();
        dto.setName(sportUpdateTransfer.getName());
        dto.setDuration(sportUpdateTransfer.getDuration());
        dto.setKcalBurnt(sportUpdateTransfer.getKcalBurnt());
        dto.setSportType(sportUpdateTransfer.getSportType());

        Call<Void> call = sportService.updateSport(authToken, sportUpdateTransfer.getSportId(), dto);
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