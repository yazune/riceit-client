package com.agh.riceitclient.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.agh.riceitclient.R;
import com.agh.riceitclient.dto.DateDTO;
import com.agh.riceitclient.dto.MealsDTO;
import com.agh.riceitclient.dto.RemoveFoodDTO;
import com.agh.riceitclient.dto.RemoveMealDTO;
import com.agh.riceitclient.model.Day;
import com.agh.riceitclient.model.Meal;
import com.agh.riceitclient.retrofit.AuthToken;
import com.agh.riceitclient.retrofit.ServiceGenerator;
import com.agh.riceitclient.service.DayService;
import com.agh.riceitclient.service.MealService;
import com.agh.riceitclient.util.MealsAdapter;
import com.agh.riceitclient.util.RemoveObjectListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsActivity extends AppCompatActivity implements RemoveObjectListener {

    String authToken = AuthToken.getType() +" " + AuthToken.getToken();
    MealService mealService = ServiceGenerator.createService(MealService.class);
    DayService dayService = ServiceGenerator.createService(DayService.class);

    TextView todayDate;
    ProgressBar kcalBar, protBar, fatBar, carbBar;
    TextView kcalEaten, kcalBurnt, kcalTotal, kcalDaily;
    TextView protEaten, protDaily, fatEaten, fatDaily, carbEaten, carbDaily;

    LocalDate today;
    HashMap<LocalDate, ArrayList<Meal>> mealsMap;
    HashMap<LocalDate, Day> summaryMap;

    RecyclerView mealsRv;
    MealsAdapter mealsAdapter;

    boolean isMealsAdapterAssigned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);

        todayDate = findViewById(R.id.datebar_today_date);

        kcalBar = findViewById(R.id.kcal_bar);
        protBar = findViewById(R.id.prot_bar);
        fatBar = findViewById(R.id.fat_bar);
        carbBar = findViewById(R.id.carb_bar);

        kcalEaten = findViewById(R.id.kcal_eaten_amount);
        kcalBurnt = findViewById(R.id.kcal_burnt_amount);
        kcalTotal = findViewById(R.id.kcal_total_amount);
        kcalDaily = findViewById(R.id.kcal_daily_amount);

        protEaten = findViewById(R.id.prot_amount);
        protDaily = findViewById(R.id.prot_daily);
        fatEaten = findViewById(R.id.fat_amount);
        fatDaily = findViewById(R.id.fat_daily);
        carbEaten = findViewById(R.id.carb_amount);
        carbDaily = findViewById(R.id.carb_daily);

        mealsRv = findViewById(R.id.meals_rv);
        mealsRv.setLayoutManager(new LinearLayoutManager(MealsActivity.this));

        mealsAdapter = new MealsAdapter(this, MealsActivity.this);
        isMealsAdapterAssigned = false;

        today = LocalDate.now();
        setDates();

        mealsMap = new HashMap<>();
        summaryMap = new HashMap<>();

        enqueueGetMeals(today);
        enqueueGetSummary(today);
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
        double kcalE = round(day.getKcalConsumed());
        double kcalB = round(day.getKcalBurnt());
        double kcalT = round(kcalE - kcalB);
        double kcalD = round(day.getKcalToEat());

        double protE = round(day.getProteinConsumed());
        double protD = round(day.getProteinToEat());

        double fatE = round(day.getFatConsumed());
        double fatD = round(day.getFatToEat());

        double carbE = round(day.getCarbohydrateConsumed());
        double carbD = round(day.getCarbohydrateToEat());


        int kcalProgress = (int)(kcalT/kcalD*100);
        int protProgress = (int)(protE/protD*100);
        int fatProgress = (int)(fatE/fatD*100);
        int carbProgress = (int)(carbE/carbD*100);

        kcalEaten.setText(String.valueOf(kcalE));
        kcalBurnt.setText(String.valueOf(kcalB));
        kcalTotal.setText(String.valueOf(kcalE-kcalB));
        kcalDaily.setText(String.valueOf(kcalD));

        protEaten.setText(String.valueOf(protE));
        protDaily.setText(String.valueOf(protD));

        fatEaten.setText(String.valueOf(fatE));
        fatDaily.setText(String.valueOf(fatD));

        carbEaten.setText(String.valueOf(carbE));
        carbDaily.setText(String.valueOf(carbD));

        kcalBar.setProgress(kcalProgress);
        protBar.setProgress(protProgress);
        fatBar.setProgress(fatProgress);
        carbBar.setProgress(carbProgress);
    }

    private void setDates() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM");
        todayDate.setText(today.format(dtf));
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
        Call<MealsDTO> call = mealService.showAllMeals(authToken, dateDTO);

        call.enqueue(new Callback<MealsDTO>() {
            @Override
            public void onResponse(Call<MealsDTO> call, Response<MealsDTO> response) {
                if(response.isSuccessful()){
                    updateMealsLayout(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealsDTO> call, Throwable t) {

            }
        });
    }

    public void enqueueGetSummary(LocalDate date){
        DateDTO dateDTO = new DateDTO(date.toString());
        Call<Day> call = dayService.getDay(authToken, dateDTO);
        call.enqueue(new Callback<Day>() {
            @Override
            public void onResponse(Call<Day> call, Response<Day> response) {
                summaryMap.put(today, response.body());
                updateSummaryLayout(summaryMap.get(today));
            }

            @Override
            public void onFailure(Call<Day> call, Throwable t) {

            }
        });
    }

    public void callCreateMeal(View view){
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

    public void callCreateFood(View view){}

    @Override
    public void callRemoveMealOrFood(boolean removeMeal, long receivedData) {
        Call<Void> call;

        if(removeMeal){
            RemoveMealDTO removeMealDTO = new RemoveMealDTO(receivedData);
            call = mealService.removeMeal(authToken, removeMealDTO);
        } else {
            RemoveFoodDTO removeFoodDTO = new RemoveFoodDTO(receivedData);
            call = mealService.removeFood(authToken, removeFoodDTO);
        }
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

    private static double round(double val){
        BigDecimal bigDecimal = new BigDecimal(Double.toString(val));
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}