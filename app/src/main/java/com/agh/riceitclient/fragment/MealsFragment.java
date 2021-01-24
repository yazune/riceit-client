package com.agh.riceitclient.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorStateListDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agh.riceitclient.R;
import com.agh.riceitclient.dto.AddFoodDTO;
import com.agh.riceitclient.dto.DateDTO;
import com.agh.riceitclient.dto.AllMealsDTO;
import com.agh.riceitclient.dto.RemoveFoodDTO;
import com.agh.riceitclient.dto.RemoveMealDTO;
import com.agh.riceitclient.dto.UpdateFoodDTO;
import com.agh.riceitclient.util.MealsListener;
import com.agh.riceitclient.util.MealsAdapter;
import com.agh.riceitclient.model.Day;
import com.agh.riceitclient.model.Meal;
import com.agh.riceitclient.retrofit.ServiceGenerator;
import com.agh.riceitclient.service.DayService;
import com.agh.riceitclient.service.MealService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsFragment extends Fragment implements MealsListener{

    String authToken;
    MealService mealService = ServiceGenerator.createService(MealService.class);
    DayService dayService = ServiceGenerator.createService(DayService.class);

    TextView selectedDate;
    ProgressBar kcalBar, protBar, fatBar, carbBar;
    TextView kcalEaten, kcalBurnt, kcalTotal, kcalDaily;
    TextView protEaten, protBurnt, protTotal, protDaily;
    TextView fatEaten, fatBurnt, fatTotal, fatDaily;
    TextView carbEaten, carbBurnt, carbTotal, carbDaily;

    LinearLayout kcalMid, protMid, carbMid, fatMid;

    final LocalDate finalToday = LocalDate.now();
    LocalDate today;
    HashMap<LocalDate, ArrayList<Meal>> mealsMap;
    HashMap<LocalDate, Day> summaryMap;

    Button prevDayBtn, nextDayBtn;

    RecyclerView mealsRv;
    MealsAdapter mealsAdapter;

    boolean isMealsAdapterAssigned;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_meals, container, false);

        selectedDate = v.findViewById(R.id.datebar_selected_date);

        kcalBar = v.findViewById(R.id.kcal_bar);
        protBar = v.findViewById(R.id.prot_bar);
        fatBar = v.findViewById(R.id.fat_bar);
        carbBar = v.findViewById(R.id.carb_bar);

        kcalEaten = v.findViewById(R.id.kcal_eaten_amount);
        kcalBurnt = v.findViewById(R.id.kcal_burnt_amount);
        kcalTotal = v.findViewById(R.id.kcal_total_amount);
        kcalDaily = v.findViewById(R.id.kcal_daily_amount);

        protEaten = v.findViewById(R.id.prot_eaten_amount);
        protBurnt = v.findViewById(R.id.prot_burnt_amount);
        protTotal = v.findViewById(R.id.prot_total_amount);
        protDaily = v.findViewById(R.id.prot_daily_amount);

        fatEaten = v.findViewById(R.id.fat_eaten_amount);
        fatBurnt = v.findViewById(R.id.fat_burnt_amount);
        fatTotal = v.findViewById(R.id.fat_total_amount);
        fatDaily = v.findViewById(R.id.fat_daily_amount);

        carbEaten = v.findViewById(R.id.carb_eaten_amount);
        carbBurnt = v.findViewById(R.id.carb_burnt_amount);
        carbTotal = v.findViewById(R.id.carb_total_amount);
        carbDaily = v.findViewById(R.id.carb_daily_amount);

        kcalMid = v.findViewById(R.id.kcal_middle_linear);
        protMid = v.findViewById(R.id.prot_middle_linear);
        fatMid = v.findViewById(R.id.fat_middle_linear);
        carbMid = v.findViewById(R.id.carb_middle_linear);

        prevDayBtn = v.findViewById(R.id.btn_previous_day);
        nextDayBtn = v.findViewById(R.id.btn_next_day);

        prevDayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePreviousDay(view);
            }
        });

        nextDayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseNextDay(view);
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("RiceItClient", Context.MODE_PRIVATE);
        authToken = sharedPreferences.getString("TOKEN", null); //second parameter is default

        mealsRv = v.findViewById(R.id.meals_rv);
        mealsRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();

        mealsAdapter = new MealsAdapter(getActivity(), supportFragmentManager);
        isMealsAdapterAssigned = false;

        today = LocalDate.now();
        setDates();

        mealsMap = new HashMap<>();
        summaryMap = new HashMap<>();

        enqueueGetMeals(today);
        enqueueGetSummary(today);

        return v;
    }

    private void updateMealsLayout(ArrayList<Meal> mealsResponse){

        mealsAdapter.setMeals(mealsResponse);
        mealsMap.put(today, mealsResponse);

        if(!isMealsAdapterAssigned){
            mealsRv.setAdapter(mealsAdapter);
            isMealsAdapterAssigned = true;
        } else mealsAdapter.notifyDataSetChanged();
    }

    private void updateSummaryLayout(Day day){
        NumberFormat df = DecimalFormat.getInstance();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        df.setRoundingMode(RoundingMode.DOWN);

        double kcalE = day.getKcalConsumed();
        double kcalD = day.getKcalToEat();

        double protE = day.getProteinConsumed();
        double protD = day.getProteinToEat();

        double fatE = day.getFatConsumed();
        double fatD = day.getFatToEat();

        double carbE = day.getCarbohydrateConsumed();
        double carbD = day.getCarbohydrateToEat();

        kcalEaten.setText(df.format(kcalE));
        kcalDaily.setText(df.format(kcalD));

        protEaten.setText(df.format(protE));
        protDaily.setText(df.format(protD));

        fatEaten.setText(df.format(fatE));
        fatDaily.setText(df.format(fatD));

        carbEaten.setText(df.format(carbE));
        carbDaily.setText(df.format(carbD));

        int kcalProgress, protProgress, fatProgress, carbProgress;

        if(day.isUseK()) {
            kcalMid.setVisibility(View.GONE);
            protMid.setVisibility(View.GONE);
            fatMid.setVisibility(View.GONE);
            carbMid.setVisibility(View.GONE);

            kcalProgress = (int)(kcalE/kcalD*100);
            protProgress = (int)(protE/protD*100);
            fatProgress = (int)(fatE/fatD*100);
            carbProgress = (int)(carbE/carbD*100);
        } else {
            kcalMid.setVisibility(View.VISIBLE);
            protMid.setVisibility(View.VISIBLE);
            fatMid.setVisibility(View.VISIBLE);
            carbMid.setVisibility(View.VISIBLE);

            double kcalB = day.getKcalBurnt();
            double kcalT = kcalE - kcalB;
            kcalBurnt.setText(df.format(kcalB));
            kcalTotal.setText(df.format(kcalE-kcalB));

            double protB = day.getProteinBurnt();
            double protT = protE - protB;
            protBurnt.setText(df.format(protB));
            protTotal.setText(df.format(protT));

            double fatB = day.getFatBurnt();
            double fatT = fatE - fatB;
            fatBurnt.setText(df.format(fatB));
            fatTotal.setText(df.format(fatT));

            double carbB = day.getCarbohydrateBurnt();
            double carbT = carbE - carbB;
            carbBurnt.setText(df.format(carbB));
            carbTotal.setText(df.format(carbT));

            kcalProgress = (int)(kcalT/kcalD*100);
            protProgress = (int)(protT/protD*100);
            fatProgress = (int)(fatT/fatD*100);
            carbProgress = (int)(carbT/carbD*100);
        }

        if(kcalProgress < 95)
            kcalBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#303F9F")));
        else if ((kcalProgress >= 95) && (kcalProgress <= 100))
            kcalBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#5C9F60")));
        else if ((kcalProgress > 100) && (kcalProgress <= 105))
            kcalBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FFC107")));
        else
            kcalBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#D32F2F")));

        if(protProgress < 95)
            protBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#303F9F")));
        else if ((protProgress >= 95) && (protProgress <= 100))
            protBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#5C9F60")));
        else if ((protProgress > 100) && (protProgress <= 105))
            protBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FFC107")));
        else
            protBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#D32F2F")));

        if(fatProgress < 95)
            fatBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#303F9F")));
        else if ((fatProgress >= 95) && (fatProgress <= 100))
            fatBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#5C9F60")));
        else if ((fatProgress > 100) && (fatProgress <= 105))
            fatBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FFC107")));
        else
            fatBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#D32F2F")));

        if(carbProgress < 95)
            carbBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#303F9F")));
        else if ((carbProgress >= 95) && (carbProgress <= 100))
            carbBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#5C9F60")));
        else if ((carbProgress > 100) && (carbProgress <= 105))
            carbBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FFC107")));
        else
            carbBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#D32F2F")));

        kcalBar.setProgress(kcalProgress);
        protBar.setProgress(protProgress);
        fatBar.setProgress(fatProgress);
        carbBar.setProgress(carbProgress);
    }

    private void setDates() {

        if (today.equals(finalToday)){
            nextDayBtn.setClickable(false);
            nextDayBtn.setVisibility(View.INVISIBLE);
            nextDayBtn.setText("");
        } else{
            nextDayBtn.setClickable(true);
            nextDayBtn.setVisibility(View.VISIBLE);
            nextDayBtn.setText("Next\nDay");
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM");
        selectedDate.setText(today.format(dtf));
    }

    public void chooseNextDay(View view){
        today = today.plusDays(1);
        setDates();
        loadMealsWhenChangingDays(today);
        loadSummaryWhenChangingDays(today);
    }

    public void choosePreviousDay(View view){
        today = today.minusDays(1);
        setDates();
        loadMealsWhenChangingDays(today);
        loadSummaryWhenChangingDays(today);
    }

    private void loadMealsWhenChangingDays(LocalDate date){
        if (mealsMap.containsKey(date)){
            mealsAdapter.setMeals(mealsMap.get(date));
            mealsAdapter.notifyDataSetChanged();
        } else{
            enqueueGetMeals(date);
        }
    }

    private void loadSummaryWhenChangingDays(LocalDate date){
        if (summaryMap.containsKey(date)){
            updateSummaryLayout(summaryMap.get(date));
        } else{
            enqueueGetSummary(date);
        }
    }

    public void enqueueGetMeals(LocalDate date){
        DateDTO dateDTO = new DateDTO(date.toString());
        Call<AllMealsDTO> call = mealService.showAllMeals(authToken, dateDTO);

        call.enqueue(new Callback<AllMealsDTO>() {
            @Override
            public void onResponse(Call<AllMealsDTO> call, Response<AllMealsDTO> response) {
                if(response.isSuccessful()){
                    updateMealsLayout(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<AllMealsDTO> call, Throwable t) {

            }
        });
    }

    public void enqueueGetSummary(LocalDate date){
        DateDTO dateDTO = new DateDTO(date.toString());
        Call<Day> call = dayService.getDay(authToken, dateDTO);
        call.enqueue(new Callback<Day>() {
            @Override
            public void onResponse(Call<Day> call, Response<Day> response) {
                if(response.isSuccessful()){
                    summaryMap.put(today, response.body());
                    updateSummaryLayout(summaryMap.get(today));
                }
            }

            @Override
            public void onFailure(Call<Day> call, Throwable t) {

            }
        });
    }

    public void enqueueRemoveMeal(long mealId){
        RemoveMealDTO removeMealDTO = new RemoveMealDTO(mealId);
        Call<Void> call = mealService.removeMeal(authToken, removeMealDTO);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    enqueueGetMeals(today);
                    enqueueGetSummary(today);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void enqueueRemoveFood(long foodId){
        RemoveFoodDTO removeFoodDTO = new RemoveFoodDTO(foodId);
        Call<Void> call = mealService.removeFood(authToken, removeFoodDTO);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    enqueueGetMeals(today);
                    enqueueGetSummary(today);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @Override
    public void enqueueAddFood(AddFoodDTO addFoodDTO){
        Call<Void> call = mealService.addFood(authToken, addFoodDTO);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    enqueueGetMeals(today);
                    enqueueGetSummary(today);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @Override
    public void enqueueUpdateFood(UpdateFoodDTO updateFoodDTO){
        Call<Void> call = mealService.updateFood(authToken, updateFoodDTO);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    enqueueGetMeals(today);
                    enqueueGetSummary(today);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @Override
    public void enqueueCreateMeal(){
        DateDTO dateDTO = new DateDTO(today.toString());
        Call<Void> call = mealService.createMeal(authToken, dateDTO);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    enqueueGetMeals(today);
                    enqueueGetSummary(today);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    @Override
    public void removeMealOrFood(boolean isMeal, long dataToRemove) {
        if(isMeal){
            enqueueRemoveMeal(dataToRemove);
        } else
            enqueueRemoveFood(dataToRemove);
    }

    private static double round(double val) {
        BigDecimal bigDecimal = new BigDecimal(Double.toString(val));
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }





}
