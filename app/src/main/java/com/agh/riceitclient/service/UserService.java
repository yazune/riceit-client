package com.agh.riceitclient.service;

import com.agh.riceitclient.dto.BooleanDTO;
import com.agh.riceitclient.dto.ExistsEmailDTO;
import com.agh.riceitclient.dto.ExistsUsernameDTO;
import com.agh.riceitclient.dto.RegisterDTO;
import com.agh.riceitclient.dto.UsernameDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    
    @POST("/auth/existsByUsername")
    Call<BooleanDTO> existsByUsername(@Body ExistsUsernameDTO existsUsernameDTO);

    @POST("/auth/existsByEmail")
    Call<BooleanDTO> existsByEmail(@Body ExistsEmailDTO existsEmailDTO);

    @POST("/auth/register")
    Call<UsernameDTO> register(@Body RegisterDTO registerDTO);

}
