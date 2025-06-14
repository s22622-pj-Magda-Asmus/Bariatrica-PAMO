package com.example.bariatric_mobile.services.network;

import com.example.bariatric_mobile.models.patient.Patient;
import com.example.bariatric_mobile.models.patient.SurveyData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface PatientApiService {

    @GET("api/surveys")
    Call<List<Patient>> getPatients();

    @GET("api/survey/{patientId}")
    Call<SurveyData> getPatientDetails(@Path("patientId") String patientId);

    @PATCH("api/survey/{patientId}")
    Call<Void> updateSurveyStatus(@Path("patientId") String patientId);
}
