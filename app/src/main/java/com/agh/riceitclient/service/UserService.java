package com.agh.riceitclient.service;

import com.agh.riceitclient.dto.BooleanDTO;
import com.agh.riceitclient.dto.ExistsEmailDTO;
import com.agh.riceitclient.dto.ExistsUsernameDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    
    @POST("/existsByUsername")
    Call<BooleanDTO> existsByUsername(@Body ExistsUsernameDTO existsUsernameDTO);

    @POST("/existsByEmail")
    Call<BooleanDTO> existsByEmail(@Body ExistsEmailDTO existsEmailDTO);

}
