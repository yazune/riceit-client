package com.agh.riceitclient.service;

import com.agh.riceitclient.dto.ManualParametersDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ManualParametersService {

    @GET("/manual")
    Call<ManualParametersDTO> getManualParameters(@Header("Authorization") String authToken);

    @PUT("/manual")
    Call<Void> updateManualParameters(@Header("Authorization") String authToken, @Body ManualParametersDTO dto);
}
