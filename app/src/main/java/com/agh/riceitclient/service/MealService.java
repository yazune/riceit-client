package com.agh.riceitclient.service;

import com.agh.riceitclient.dto.FoodAddDTO;
import com.agh.riceitclient.dto.DateDTO;
import com.agh.riceitclient.dto.MealsDTO;
import com.agh.riceitclient.dto.FoodUpdateDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MealService {

    @POST("/meals/all")
    Call<MealsDTO> getMeals(@Header("Authorization") String authToken, @Body DateDTO dateDTO);

    @POST("/meals")
    Call<Void> createMeal(@Header("Authorization") String authToken, @Body DateDTO dateDTO);

    @DELETE("/meals/{mealId}")
    Call<Void> removeMeal(@Header("Authorization") String authToken, @Path("mealId") long mealId);

    @DELETE("/foods/{foodId}")
    Call<Void> removeFood(@Header("Authorization") String authToken, @Path("foodId") long foodId);

    @POST("/foods")
    Call<Void> addFood(@Header("Authorization") String authToken, @Body FoodAddDTO foodAddDTO);

    @PUT("/foods/{foodId}")
    Call<Void> updateFood(@Header("Authorization") String authToken, @Path("foodId") long foodId, @Body FoodUpdateDTO foodUpdateDTO);
}
