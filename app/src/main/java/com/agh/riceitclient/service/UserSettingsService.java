package com.agh.riceitclient.service;

import com.agh.riceitclient.dto.UserSettingsDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserSettingsService {

    @GET("/user/settings")
    Call<UserSettingsDTO> getUserSettings(@Header("Authorization") String authToken);

    @POST("/user/settings")
    Call<Void> updateUserSettings(@Header("Authorization") String authToken, @Body UserSettingsDTO dto);
}
