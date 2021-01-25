package com.agh.riceitclient.service;

import com.agh.riceitclient.dto.AddSportAutoDTO;
import com.agh.riceitclient.dto.AddSportManDTO;
import com.agh.riceitclient.dto.DateDTO;
import com.agh.riceitclient.dto.GetSportDTO;
import com.agh.riceitclient.dto.RemoveSportDTO;
import com.agh.riceitclient.dto.AllSportsDTO;
import com.agh.riceitclient.dto.UpdateSportDTO;
import com.agh.riceitclient.model.Sport;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SportService {

    @POST("/sports/get")
    Call<Sport> getSport(@Header("Authorization") String authToken, @Body GetSportDTO getSportDTO);

    @POST("/sports/addMan")
    Call<Void> addSportMan(@Header("Authorization") String authToken, @Body AddSportManDTO addSportManDTO);

    @POST("/sports/addAuto")
    Call<Void> addSportAuto(@Header("Authorization") String authToken, @Body AddSportAutoDTO addSportAutoDTO);

    @POST("/sports/remove")
    Call<Void> removeSport(@Header("Authorization") String authToken, @Body RemoveSportDTO removeSportDTO);

    @POST("/sports/update")
    Call<Void> updateSport(@Header("Authorization") String authToken, @Body UpdateSportDTO updateSportDTO);

    @POST("/sports/showAll")
    Call<AllSportsDTO> showAllSports(@Header("Authorization") String authToken, @Body DateDTO dateDTO);
}
