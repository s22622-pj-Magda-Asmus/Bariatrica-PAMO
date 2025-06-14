package com.example.bariatric_mobile.services.network;

import com.example.bariatric_mobile.models.patient.PredictionResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * PredictionApiService
 */
public interface PredictionApiService {
    @GET("api/results/{patientId}")
    Call<PredictionResponse> getPrediction(@Path("patientId") String patientId);}
