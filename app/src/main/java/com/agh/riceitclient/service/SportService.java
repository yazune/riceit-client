package com.agh.riceitclient.service;

import com.agh.riceitclient.dto.SportAddDTO;
import com.agh.riceitclient.dto.DateDTO;
import com.agh.riceitclient.dto.SportsDTO;
import com.agh.riceitclient.dto.SportUpdateDTO;
import com.agh.riceitclient.model.Sport;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SportService {

    @GET("/sports/{sportId}")
    Call<Sport> getSport(@Header("Authorization") String authToken, @Path("sportId") long sportId);

    @POST("/sports")
    Call<Void> addSport(@Header("Authorization") String authToken, @Body SportAddDTO sportAddDTO);

    @DELETE("/sports/{sportId}")
    Call<Void> removeSport(@Header("Authorization") String authToken, @Path("sportId") long sportId);

    @PUT("/sports/{sportId}")
    Call<Void> updateSport(@Header("Authorization") String authToken, @Path("sportId") long sportId, @Body SportUpdateDTO sportUpdateDTO);

    @POST("/sports/all")
    Call<SportsDTO> getSports(@Header("Authorization") String authToken, @Body DateDTO dateDTO);
}
