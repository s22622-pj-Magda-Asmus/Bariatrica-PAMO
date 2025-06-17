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

/**
 * Repository class for managing patient data operations and API interactions.
 *
 * Handles fetching patient details, predictions, and patient lists from the remote API.
 * Provides LiveData objects for observing data changes in the UI layer following
 * the MVVM architecture pattern.
 */
public class PatientRepository {

    private final PatientApiService patientApiService;
    private final PredictionApiService predictionApiService;

    private final MutableLiveData<SurveyData> patientDetails = new MutableLiveData<>();
    private final MutableLiveData<PredictionResponse> prediction = new MutableLiveData<>();

    /**
     * Creates a new PatientRepository instance.
     * Initializes API service instances from ApiClient.
     */
    public PatientRepository() {
        this.patientApiService = ApiClient.getPatientService();
        this.predictionApiService = ApiClient.getPredictionService();
    }

    /**
     * Returns LiveData for observing patient details changes.
     *
     * @return LiveData containing patient survey data
     */
    public LiveData<SurveyData> getPatientDetails() {
        return patientDetails;
    }

    /**
     * Returns LiveData for observing prediction data changes.
     *
     * @return LiveData containing patient weight loss predictions
     */
    public LiveData<PredictionResponse> getPrediction() {
        return prediction;
    }

    /**
     * Fetches detailed patient data from the API.
     * Automatically updates survey status and posts data to LiveData on success.
     *
     * @param patientId The unique identifier of the patient to fetch
     */
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

    /**
     * Fetches weight loss predictions for a patient from the API.
     * Posts prediction data to LiveData on successful response.
     *
     * @param patientId The unique identifier of the patient for predictions
     */
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

    /**
     * Updates the survey status for a patient to mark it as viewed.
     * Called automatically when patient details are fetched.
     *
     * @param patientId The unique identifier of the patient
     */
    private void updateSurveyStatus(String patientId) {
        patientApiService.updateSurveyStatus(patientId).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {}

            @Override
            public void onFailure(Call<Void> call, Throwable t) {}
        });
    }

    /**
     * Callback interface for handling patient list operations.
     * Provides success and error handling methods for asynchronous operations.
     */
    public interface PatientListCallback {
        /**
         * Called when patient list is successfully fetched.
         *
         * @param result List of patients retrieved from the API
         */
        void onSuccess(List<Patient> result);

        /**
         * Called when patient list fetch fails.
         *
         * @param message Error message describing the failure
         */
        void onError(String message);
    }

    /**
     * Fetches all patients from the API and returns results via callback.
     * Uses callback pattern instead of LiveData for one-time operations.
     *
     * @param callback Callback to handle success or error responses
     */
    public void fetchAllPatients(PatientListCallback callback) {
        patientApiService.getPatients().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
            }
        });
    }
}