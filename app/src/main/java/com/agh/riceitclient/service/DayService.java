package com.agh.riceitclient.service;

import com.agh.riceitclient.dto.DateDTO;
import com.agh.riceitclient.model.Day;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface DayService {

    @POST("/day/getDay")
    Call<Day> getDay(@Header("Authorization") String authToken, @Body DateDTO dateDTO);
}
