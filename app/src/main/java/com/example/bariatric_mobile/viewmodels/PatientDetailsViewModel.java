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

/**
 * ViewModel for managing patient details screen data and operations.
 * <p>
 * Handles loading patient survey data and weight loss predictions,
 * provides user authentication state, and manages logout functionality.
 * Follows MVVM architecture pattern with LiveData for reactive UI updates.
 */
public class PatientDetailsViewModel extends AndroidViewModel {
    private final PatientRepository patientRepository;
    private final AuthRepository authRepository;

    /**
     * Creates a new PatientDetailsViewModel instance.
     *
     * @param application Application context for accessing repositories
     */
    public PatientDetailsViewModel(@NonNull Application application) {
        super(application);
        patientRepository = new PatientRepository();
        authRepository = new AuthRepository(application);
    }

    /**
     * Loads patient details and predictions from the API.
     * Triggers both survey data and prediction data fetching concurrently.
     *
     * @param patientId The unique identifier of the patient to load
     */
    public void loadPatientDetails(String patientId) {
        patientRepository.fetchPatientDetails(patientId);
        patientRepository.fetchPrediction(patientId);
    }

    /**
     * Logs out the current user and clears authentication data.
     */
    public void logout() {
        authRepository.logout();
    }

    /**
     * Returns LiveData for observing patient survey data changes.
     *
     * @return LiveData containing patient details or null if not loaded
     */
    public LiveData<SurveyData> getPatientDetails() {
        return patientRepository.getPatientDetails();
    }

    /**
     * Returns LiveData for observing prediction data changes.
     *
     * @return LiveData containing weight loss predictions or null if not loaded
     */
    public LiveData<PredictionResponse> getPrediction() {
        return patientRepository.getPrediction();
    }

    /**
     * Returns LiveData for observing current user authentication state.
     *
     * @return LiveData containing current user or null if not authenticated
     */
    public LiveData<User> getCurrentUser() {
        return authRepository.getCurrentUser();
    }

}