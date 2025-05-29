package com.example.bariatric_mobile.services.network;

import com.example.bariatric_mobile.models.patient.Patient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PatientApiService {

    @GET("api/surveys")
    Call<List<Patient>> getPatients();
}
