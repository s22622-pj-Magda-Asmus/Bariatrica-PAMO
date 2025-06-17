package com.example.bariatric_mobile.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bariatric_mobile.models.auth.User;
import com.example.bariatric_mobile.models.patient.PredictionResponse;
import com.example.bariatric_mobile.models.patient.SurveyData;
import com.example.bariatric_mobile.repositories.AuthRepository;
import com.example.bariatric_mobile.repositories.PatientRepository;

public class PatientDetailsViewModel extends AndroidViewModel {
    private final PatientRepository patientRepository;
    private final AuthRepository authRepository;

    public PatientDetailsViewModel(@NonNull Application application) {
        super(application);
        patientRepository = new PatientRepository();
        authRepository = new AuthRepository(application);
    }

    public void loadPatientDetails(String patientId) {
        patientRepository.fetchPatientDetails(patientId);
        patientRepository.fetchPrediction(patientId);
    }

    public void logout() {
        authRepository.logout();
    }

    public LiveData<SurveyData> getPatientDetails() {
        return patientRepository.getPatientDetails();
    }

    public LiveData<PredictionResponse> getPrediction() {
        return patientRepository.getPrediction();
    }

    public LiveData<User> getCurrentUser() {
        return authRepository.getCurrentUser();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}