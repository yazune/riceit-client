package com.agh.riceitclient.service;

import com.agh.riceitclient.dto.UserDetailsGetDTO;
import com.agh.riceitclient.dto.UserDetailsUpdateDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface UserDetailsService {

    @GET("/users/details")
    Call<UserDetailsGetDTO> getUserDetails(@Header("Authorization") String authToken);

    @PUT("/users/details")
    Call<Void> updateUserDetails(@Header("Authorization") String authToken, @Body UserDetailsUpdateDTO userDetailsUpdateDTO);
}
