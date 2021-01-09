package com.agh.riceitclient.service;

import com.agh.riceitclient.dto.LoginDTO;
import com.agh.riceitclient.dto.TokenDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("/auth/login")
    Call<TokenDTO> login(@Body LoginDTO loginDTO);
}
