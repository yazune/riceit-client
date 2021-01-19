package com.agh.riceitclient.service;

import com.agh.riceitclient.dto.GetUserDetailsDTO;
import com.agh.riceitclient.dto.UpdateUserDetailsDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserDetailsService {

    @GET("/user/getDetails")
    Call<GetUserDetailsDTO> getUserDetails(@Header("Authorization") String authToken);

    @POST("/user/updateDetails")
    Call<Void> updateUserDetails(@Header("Authorization") String authToken, @Body UpdateUserDetailsDTO updateUserDetailsDTO);
}
