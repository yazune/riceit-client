package com.agh.riceitclient.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.agh.riceitclient.R;
import com.agh.riceitclient.dto.DateDTO;
import com.agh.riceitclient.dto.MealsDTO;
import com.agh.riceitclient.dto.RemoveFoodDTO;
import com.agh.riceitclient.dto.RemoveMealDTO;
import com.agh.riceitclient.model.Meal;
import com.agh.riceitclient.retrofit.AuthToken;
import com.agh.riceitclient.retrofit.ServiceGenerator;
import com.agh.riceitclient.service.MealService;
import com.agh.riceitclient.util.MealsAdapter;
import com.agh.riceitclient.util.RemoveObjectListener;

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

    ConstraintLayout yesterdayConstraint, todayConstraint, tomorrowConstraint;
    TextView yesterdayDate, todayDate, tomorrowDate;

    LocalDate today;
    HashMap<LocalDate, ArrayList<Meal>> daysWithData;

    RecyclerView mealsRv;
    MealsAdapter mealsAdapter;

    ArrayList<Meal> meals;

    boolean isMealsAdapterAssigned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);

        yesterdayConstraint = findViewById(R.id.datebar_yesterday_constraint);
        todayConstraint = findViewById(R.id.datebar_today_constraint);
        tomorrowConstraint = findViewById(R.id.datebar_tomorrow_constraint);

        yesterdayDate = findViewById(R.id.datebar_yesterday_date);
        todayDate = findViewById(R.id.datebar_today_date);
        tomorrowDate = findViewById(R.id.datebar_tomorrow_date);

        mealsRv = findViewById(R.id.meals_rv);
        mealsRv.setLayoutManager(new LinearLayoutManager(MealsActivity.this));
        //mealsRv.getItemAnimator().setChangeDuration(0);
        mealsAdapter = new MealsAdapter(this, MealsActivity.this);
        isMealsAdapterAssigned = false;

        today = LocalDate.now();
        setDates();
        daysWithData = new HashMap<>();

        showAllMealsEnqueue();
    }

    private void setNewMeals(ArrayList<Meal> mealsResponse){

        mealsAdapter.setMeals(mealsResponse);
        meals = mealsResponse;
        daysWithData.put(today, mealsResponse);

        if(!isMealsAdapterAssigned){
            mealsRv.setAdapter(mealsAdapter);
            isMealsAdapterAssigned = true;
        } else mealsAdapter.notifyDataSetChanged();
    }

    private void setDates() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM");

        yesterdayDate.setText(today.minusDays(1).format(dtf));
        todayDate.setText(today.format(dtf));
        tomorrowDate.setText(today.plusDays(1).format(dtf));
    }

    public void chooseNextDay(View view){
        today = today.plusDays(1);
        setDates();
        showMealsForDate(today);
    }

    public void choosePreviousDay(View view){
        today = today.minusDays(1);
        setDates();
        showMealsForDate(today);
    }

    public void showMealsForDate(LocalDate date){
        if (daysWithData.containsKey(date)){
            mealsAdapter.setMeals(daysWithData.get(date));
            mealsAdapter.notifyDataSetChanged();
        } else{
            DateDTO dateDTO = new DateDTO(date.toString());
            Call<MealsDTO> call = mealService.showAllMeals(authToken, dateDTO);

            call.enqueue(new Callback<MealsDTO>() {
                @Override
                public void onResponse(Call<MealsDTO> call, Response<MealsDTO> response) {
                    if(response.isSuccessful()){
                        setNewMeals(response.body().getMeals());
                    }
                }

                @Override
                public void onFailure(Call<MealsDTO> call, Throwable t) {

                }
            });
        }
    }

    public void callCreateMeal(View view){
        DateDTO dateDTO = new DateDTO(today.toString());
        Call<Void> call = mealService.createMeal(authToken, dateDTO);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    showAllMealsEnqueue();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void callCreateFood(View view){}

    public void showAllMealsEnqueue(){
        DateDTO dateDTO = new DateDTO(today.toString());
        Call<MealsDTO> call = mealService.showAllMeals(authToken, dateDTO);

        call.enqueue(new Callback<MealsDTO>() {
            @Override
            public void onResponse(Call<MealsDTO> call, Response<MealsDTO> response) {
                if(response.isSuccessful()){
                    setNewMeals(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealsDTO> call, Throwable t) {

            }
        });
    }

    public void callRemoveFood(View view){}

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
                    showAllMealsEnqueue();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}