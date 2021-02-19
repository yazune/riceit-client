package com.agh.riceitclient.service;

import com.agh.riceitclient.dto.UserSettingsDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserSettingsService {

    @GET("/users/settings")
    Call<UserSettingsDTO> getUserSettings(@Header("Authorization") String authToken);

    @PUT("/users/settings")
    Call<Void> updateUserSettings(@Header("Authorization") String authToken, @Body UserSettingsDTO dto);
}
