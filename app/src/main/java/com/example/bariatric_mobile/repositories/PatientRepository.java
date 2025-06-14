package com.example.bariatric_mobile.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bariatric_mobile.models.patient.PredictionResponse;
import com.example.bariatric_mobile.models.patient.SurveyData;
import com.example.bariatric_mobile.services.network.ApiClient;
import com.example.bariatric_mobile.services.network.PatientApiService;
import com.example.bariatric_mobile.services.network.PredictionApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientRepository {
    private final PatientApiService patientApiService;
    private final PredictionApiService predictionApiService;

    private final MutableLiveData<SurveyData> patientDetails = new MutableLiveData<>();
    private final MutableLiveData<PredictionResponse> prediction = new MutableLiveData<>();

    public PatientRepository(Context context) {
        this.patientApiService = ApiClient.getPatientService();
        this.predictionApiService = ApiClient.getPredictionService();
    }
    public LiveData<SurveyData> getPatientDetails() { return patientDetails; }
    public LiveData<PredictionResponse> getPrediction() { return prediction; }

    public void fetchPatientDetails(String patientId) {
        patientApiService.getPatientDetails(patientId).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<SurveyData> call, Response<SurveyData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SurveyData surveyData = response.body();
                    updateSurveyStatus(patientId);
                    patientDetails.postValue(surveyData);
                }
            }

            @Override
            public void onFailure(Call<SurveyData> call, Throwable t) {
            }
        });
    }

    public void fetchPrediction(String patientId) {
        predictionApiService.getPrediction(patientId).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<PredictionResponse> call, Response<PredictionResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    prediction.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PredictionResponse> call, Throwable t) {
            }
        });
    }

    private void updateSurveyStatus(String patientId) {
        patientApiService.updateSurveyStatus(patientId).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }
}