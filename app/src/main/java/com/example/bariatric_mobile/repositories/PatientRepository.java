package com.example.bariatric_mobile.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bariatric_mobile.models.patient.Patient;
import com.example.bariatric_mobile.models.patient.PredictionResponse;
import com.example.bariatric_mobile.models.patient.SurveyData;
import com.example.bariatric_mobile.services.network.ApiClient;
import com.example.bariatric_mobile.services.network.PatientApiService;
import com.example.bariatric_mobile.services.network.PredictionApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientRepository {

    private final PatientApiService patientApiService;
    private final PredictionApiService predictionApiService;

    private final MutableLiveData<SurveyData> patientDetails = new MutableLiveData<>();
    private final MutableLiveData<PredictionResponse> prediction = new MutableLiveData<>();

    public PatientRepository() {
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
                // Optional: log or post error
            }
        });
    }

    private void updateSurveyStatus(String patientId) {
        patientApiService.updateSurveyStatus(patientId).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {}

            @Override
            public void onFailure(Call<Void> call, Throwable t) {}
        });
    }
    public interface PatientListCallback {
        void onSuccess(List<Patient> result);
        void onError(String message);
    }

    public void fetchAllPatients(PatientListCallback callback) {
        patientApiService.getPatients().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Błąd ładowania danych z serwera");
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                callback.onError("Błąd połączenia: " + t.getMessage());
            }
        });
    }
}
