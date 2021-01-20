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

import com.agh.riceitclient.R;
import com.agh.riceitclient.dto.BooleanDTO;
import com.agh.riceitclient.dto.DietTypeDTO;
import com.agh.riceitclient.dto.GetGoalDTO;
import com.agh.riceitclient.dto.UpdateGoalsDTO;
import com.agh.riceitclient.dto.UpdateUserDetailsDTO;
import com.agh.riceitclient.model.Goal;
import com.agh.riceitclient.retrofit.ServiceGenerator;
import com.agh.riceitclient.service.GoalsService;
import com.agh.riceitclient.service.UserDetailsService;
import com.agh.riceitclient.util.DetailsListener;
import com.agh.riceitclient.util.GoalsListener;

import java.math.BigDecimal;
import java.math.RoundingMode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoalsFragment extends Fragment implements GoalsListener {

    String authToken;
    GoalsService goalsService = ServiceGenerator.createService(GoalsService.class);

    Button btnAutoSet, btnManSet, btnManUpdate, btnGainSet, btnMaintainingSet, btnReductionSet;
    TextView autoKcal, autoProt, autoFat, autoCarb;
    TextView manKcal, manProt, manFat, manCarb;
    TextView dietType;

    Goal goal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_goals, container, false);

        autoKcal = v.findViewById(R.id.goals_auto_kcal_amount);
        autoProt = v.findViewById(R.id.goals_auto_prot_amount);
        autoFat = v.findViewById(R.id.goals_auto_fat_amount);
        autoCarb = v.findViewById(R.id.goals_auto_carb_amount);

        manKcal = v.findViewById(R.id.goals_man_kcal_amount);
        manProt = v.findViewById(R.id.goals_man_prot_amount);
        manFat = v.findViewById(R.id.goals_man_fat_amount);
        manCarb = v.findViewById(R.id.goals_man_carb_amount);

        dietType = v.findViewById(R.id.goals_man_selected_type);

        btnAutoSet = v.findViewById(R.id.btn_goals_auto_set);
        btnManSet = v.findViewById(R.id.btn_goals_man_set);
        btnManUpdate = v.findViewById(R.id.btn_goals_man_update);

        btnGainSet = v.findViewById(R.id.btn_goals_diet_gain);
        btnMaintainingSet = v.findViewById(R.id.btn_goals_diet_maintaining);
        btnReductionSet = v.findViewById(R.id.btn_goals_diet_reduction);


        btnAutoSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enqueueChooseManualOptions(false);
            }
        });

        btnManSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enqueueChooseManualOptions(true);
            }
        });

        btnManUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGoalsUpdateFragment();
            }
        });

        btnGainSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enqueueChooseDietType("GAIN");
            }
        });

        btnMaintainingSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enqueueChooseDietType("MAINTAINING");
            }
        });

        btnReductionSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enqueueChooseDietType("REDUCTION");
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("RiceItClient", Context.MODE_PRIVATE);
        authToken = sharedPreferences.getString("TOKEN", null); //second parameter is default

        goal = new Goal();

        enqueueGetGoals();

        return v;
    }

    public void openGoalsUpdateFragment(){
        Fragment goalsUpdateFragment = new GoalsUpdateFragment((GoalsListener) getActivity());
        UpdateGoalsDTO updateGoalsDTO = new UpdateGoalsDTO();
        updateGoalsDTO.fillWithData(goal);

        Bundle args = new Bundle();

        args.putSerializable("updateGoalsDTO", updateGoalsDTO);
        goalsUpdateFragment.setArguments(args);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_container, goalsUpdateFragment, "goalsUpdateFragment")
                .addToBackStack(null)
                .commit();
    }

    public void enqueueGetGoals(){
        Call<GetGoalDTO> call = goalsService.getGoal(authToken);
        call.enqueue(new Callback<GetGoalDTO>() {
            @Override
            public void onResponse(Call<GetGoalDTO> call, Response<GetGoalDTO> response) {
                if (response.isSuccessful()){
                    goal.fillWithData(response.body());
                    updateLayout();
                }
            }

            @Override
            public void onFailure(Call<GetGoalDTO> call, Throwable t) {

            }
        });
    }

    public void updateLayout(){
        if(goal.areManParamsInUse()){
            btnAutoSet.setVisibility(View.VISIBLE);
            btnManSet.setVisibility(View.GONE);
        } else {
            btnAutoSet.setVisibility(View.GONE);
            btnManSet.setVisibility(View.VISIBLE);
        }

        if(goal.getDietType().equals("GAIN")){
            btnGainSet.setClickable(false);
            btnGainSet.setText("");
            btnMaintainingSet.setClickable(true);
            btnMaintainingSet.setText("MAINTAINING");
            btnReductionSet.setClickable(true);
            btnReductionSet.setText("REDUCTION");
        } else if(goal.getDietType().equals("MAINTAINING")){
            btnGainSet.setClickable(true);
            btnGainSet.setText("GAIN");
            btnMaintainingSet.setClickable(false);
            btnMaintainingSet.setText("");
            btnReductionSet.setClickable(true);
            btnReductionSet.setText("REDUCTION");
        } else {
            btnGainSet.setClickable(true);
            btnGainSet.setText("GAIN");
            btnMaintainingSet.setClickable(true);
            btnMaintainingSet.setText("MAINTAINING");
            btnReductionSet.setClickable(false);
            btnReductionSet.setText("");
        }

        autoKcal.setText(String.valueOf(round(goal.getAutoKcal())));
        autoProt.setText(String.valueOf(round(goal.getAutoProtein())));
        autoFat.setText(String.valueOf(round(goal.getAutoFat())));
        autoCarb.setText(String.valueOf(round(goal.getAutoCarbohydrate())));

        manKcal.setText(String.valueOf(round(goal.getManKcal())));
        manProt.setText(String.valueOf(round(goal.getManProtein())));
        manFat.setText(String.valueOf(round(goal.getManFat())));
        manCarb.setText(String.valueOf(round(goal.getManCarbohydrate())));


    }

    public void inverseButtons(){
        if(btnAutoSet.getVisibility() == View.VISIBLE){
            btnAutoSet.setVisibility(View.GONE);
            btnManSet.setVisibility(View.VISIBLE);
        } else {
            btnAutoSet.setVisibility(View.VISIBLE);
            btnManSet.setVisibility(View.GONE);
        }
    }

    @Override
    public void enqueueUpdateGoals(UpdateGoalsDTO updateGoalsDTO) {
        Call<Void> call = goalsService.updateGoal(authToken, updateGoalsDTO);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    enqueueGetGoals();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void enqueueChooseManualOptions(boolean option){
        BooleanDTO booleanDTO = new BooleanDTO(option);
        Call<Void> call = goalsService.chooseManualOptions(authToken, booleanDTO);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    inverseButtons();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void enqueueChooseDietType(String dietType){
        DietTypeDTO dietTypeDTO = new DietTypeDTO(dietType);
        Call<Void> call = goalsService.chooseDietType(authToken, dietTypeDTO);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    enqueueGetGoals();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private static double round(double val) {
        BigDecimal bigDecimal = new BigDecimal(Double.toString(val));
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
