package com.example.bariatric_mobile.services.network;

import com.example.bariatric_mobile.models.patient.Patient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Retrofit API service interface for accessing patient data from the backend.
 * <p>
 * Defines HTTP requests to retrieve patient-related resources.
 */
public interface PatientApiService {
    /**
     * Sends a GET request to retrieve a list of patients from the server.
     */
    @GET("api/surveys")
    Call<List<Patient>> getPatients();
}

