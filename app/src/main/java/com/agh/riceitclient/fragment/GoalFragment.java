package com.agh.riceitclient.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.agh.riceitclient.R;
import com.agh.riceitclient.dto.ManualParametersDTO;
import com.agh.riceitclient.dto.UserSettingsDTO;
import com.agh.riceitclient.model.Day;
import com.agh.riceitclient.model.UserSettings;
import com.agh.riceitclient.retrofit.ServiceGenerator;
import com.agh.riceitclient.service.DayService;
import com.agh.riceitclient.service.ManualParametersService;
import com.agh.riceitclient.service.UserSettingsService;
import com.agh.riceitclient.listener.GoalListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoalFragment extends Fragment implements GoalListener {

    String authToken;
    ManualParametersService manualParametersService = ServiceGenerator.createService(ManualParametersService.class);
    UserSettingsService userSettingsService = ServiceGenerator.createService(UserSettingsService.class);
    DayService dayService = ServiceGenerator.createService(DayService.class);

    RelativeLayout autoContainer, manContainer;
    TextView autoKcal, autoProt, autoFat, autoCarb;
    TextView manKcal, manProt, manFat, manCarb;

    Button btnUpdateMan, btnUpdateSettings;
    RadioButton gainRadio, mainRadio, reductionRadio;
    CheckBox palCheck, manCheck;

    Day day = new Day();
    UserSettings userSettings = new UserSettings();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_goals, container, false);

        autoContainer = v.findViewById(R.id.goals_auto_container);
        autoKcal = v.findViewById(R.id.goals_auto_kcal_amount);
        autoProt = v.findViewById(R.id.goals_auto_prot_amount);
        autoFat = v.findViewById(R.id.goals_auto_fat_amount);
        autoCarb = v.findViewById(R.id.goals_auto_carb_amount);

        manContainer = v.findViewById(R.id.goals_man_container);
        manKcal = v.findViewById(R.id.goals_man_kcal_amount);
        manProt = v.findViewById(R.id.goals_man_prot_amount);
        manFat = v.findViewById(R.id.goals_man_fat_amount);
        manCarb = v.findViewById(R.id.goals_man_carb_amount);
        btnUpdateMan = v.findViewById(R.id.btn_goals_man_update);

        gainRadio = v.findViewById(R.id.radio_gain);
        mainRadio = v.findViewById(R.id.radio_main);
        reductionRadio = v.findViewById(R.id.radio_reduction);

        palCheck = v.findViewById(R.id.check_use_k);
        manCheck = v.findViewById(R.id.check_use_man);
        btnUpdateSettings = v.findViewById(R.id.btn_settings_update);

        btnUpdateSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enqueueUpdateUserSettings();
            }
        });

        btnUpdateMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openManualParametersUpdateFragment();
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("RiceItClient", Context.MODE_PRIVATE);
        authToken = sharedPreferences.getString("TOKEN", null); //second parameter is default

        enqueueGetLastDay();

        return v;
    }

    public void openManualParametersUpdateFragment(){
        Fragment manualParametersUpdateFragment = new ManualParametersUpdateFragment((GoalListener) getActivity());
        ManualParametersDTO manualParametersDTO = new ManualParametersDTO(
                day.getKcalToEat(),
                day.getProteinToEat(),
                day.getFatToEat(),
                day.getCarbohydrateToEat()
        );

        Bundle args = new Bundle();
        args.putSerializable("manualParametersDTO", manualParametersDTO);
        manualParametersUpdateFragment.setArguments(args);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_container, manualParametersUpdateFragment, "manualParametersUpdateFragment")
                .addToBackStack(null)
                .commit();
    }

    public void enqueueUpdateUserSettings(){

        String dietType;

        if(gainRadio.isChecked()){
            dietType = "GAIN";
        } else if(mainRadio.isChecked()){
            dietType = "MAINTAINING";
        } else if(reductionRadio.isChecked()){
            dietType = "REDUCTION";
        } else {
            dietType = userSettings.getDietType();
        }

        UserSettingsDTO userSettingsDTO = new UserSettingsDTO(
                palCheck.isChecked(),
                manCheck.isChecked(),
                dietType
        );

        Call<Void> call = userSettingsService.updateUserSettings(authToken, userSettingsDTO);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    enqueueGetLastDay();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void enqueueGetLastDay(){
        Call<Day> call = dayService.getLastDay(authToken);
        call.enqueue(new Callback<Day>() {
            @Override
            public void onResponse(Call<Day> call, Response<Day> response) {
                if (response.isSuccessful()){
                    day = response.body();
                    enqueueGetSettings();
                }
            }

            @Override
            public void onFailure(Call<Day> call, Throwable t) {

            }
        });
    }

    public void enqueueGetSettings(){
        Call<UserSettingsDTO> call = userSettingsService.getUserSettings(authToken);
        call.enqueue(new Callback<UserSettingsDTO>() {
            @Override
            public void onResponse(Call<UserSettingsDTO> call, Response<UserSettingsDTO> response) {
                if (response.isSuccessful()){
                    UserSettingsDTO dto = response.body();
                    userSettings.setUsePal(dto.isUsePal());
                    userSettings.setUseMan(dto.isUseMan());
                    userSettings.setDietType(dto.getDietType());

                    updateSettingsLayout();

                    if(dto.isUseMan()){
                        updateDayLayout(true);
                    } else{
                        updateDayLayout(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserSettingsDTO> call, Throwable t) {

            }
        });
    }

    public void updateDayLayout(boolean useManParameters){

        NumberFormat df = DecimalFormat.getInstance();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        df.setRoundingMode(RoundingMode.DOWN);

        if(useManParameters){
            manContainer.setVisibility(View.VISIBLE);
            autoContainer.setVisibility(View.GONE);

            manKcal.setText(df.format(day.getKcalToEat()));
            manProt.setText(df.format(day.getProteinToEat()));
            manFat.setText(df.format(day.getFatToEat()));
            manCarb.setText(df.format(day.getCarbohydrateToEat()));
        } else{
            manContainer.setVisibility(View.GONE);
            autoContainer.setVisibility(View.VISIBLE);

            autoKcal.setText(df.format(day.getKcalToEat()));
            autoProt.setText(df.format(day.getProteinToEat()));
            autoFat.setText(df.format(day.getFatToEat()));
            autoCarb.setText(df.format(day.getCarbohydrateToEat()));
        }
    }

    public void updateSettingsLayout(){
        palCheck.setChecked(userSettings.isUsePal());
        manCheck.setChecked(userSettings.isUseMan());

        if(userSettings.getDietType().equals("GAIN")){
            gainRadio.setChecked(true);
            mainRadio.setChecked(false);
            reductionRadio.setChecked(false);
        } else if(userSettings.getDietType().equals("MAINTAINING")){
            gainRadio.setChecked(false);
            mainRadio.setChecked(true);
            reductionRadio.setChecked(false);
        } else {
            gainRadio.setChecked(false);
            mainRadio.setChecked(false);
            reductionRadio.setChecked(true);
        }
    }

    @Override
    public void enqueueUpdateManualParameters(ManualParametersDTO manualParametersDTO) {

        Call<Void> call = manualParametersService.updateManualParameters(authToken, manualParametersDTO);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    enqueueGetLastDay();
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
