package com.agh.riceitclient.service;

import com.agh.riceitclient.dto.DateDTO;
import com.agh.riceitclient.dto.MealsDTO;
import com.agh.riceitclient.dto.RemoveFoodDTO;
import com.agh.riceitclient.dto.RemoveMealDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MealService {

    @POST("/meals/showAll")
    Call<MealsDTO> showAllMeals(@Header("Authorization") String authToken, @Body DateDTO dateDTO);

    @POST("/meals/create")
    Call<Void> createMeal(@Header("Authorization") String authToken, @Body DateDTO dateDTO);

    @POST("/meals/remove")
    Call<Void> removeMeal(@Header("Authorization") String authToken, @Body RemoveMealDTO removeMealDTO);

    @POST("/meals/removeFood")
    Call<Void> removeFood(@Header("Authorization") String authToken, @Body RemoveFoodDTO removeFoodDTO);
}
