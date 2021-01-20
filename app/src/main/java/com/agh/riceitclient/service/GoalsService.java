package com.agh.riceitclient.service;

import com.agh.riceitclient.dto.BooleanDTO;
import com.agh.riceitclient.dto.DietTypeDTO;
import com.agh.riceitclient.dto.GetGoalDTO;
import com.agh.riceitclient.dto.GetUserDetailsDTO;
import com.agh.riceitclient.dto.UpdateGoalsDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface GoalsService {

    @GET("/user/getGoal")
    Call<GetGoalDTO> getGoal(@Header("Authorization") String authToken);

    @POST("/user/updateGoal")
    Call<Void> updateGoal(@Header("Authorization") String authToken, @Body UpdateGoalsDTO updateGoalsDTO);

    @POST("/user/manParams")
    Call<Void> chooseManualOptions(@Header("Authorization") String authToken, @Body BooleanDTO booleanDTO);

    @POST("/user/changeDietType")
    Call<Void> chooseDietType(@Header("Authorization") String authToken, @Body DietTypeDTO dietTypeDTO);
}
