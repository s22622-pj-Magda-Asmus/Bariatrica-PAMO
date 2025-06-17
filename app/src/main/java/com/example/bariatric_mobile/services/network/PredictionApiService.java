package com.example.bariatric_mobile.services.network;

import com.example.bariatric_mobile.models.patient.PredictionResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Retrofit API service interface for AI-powered weight loss predictions.
 * This interface defines HTTP endpoints for accessing machine learning-generated
 * predictions about patient weight loss outcomes following bariatric surgery.
 * The predictions are generated based on patient survey data, medical history,
 * and statistical models trained on historical surgery outcomes.
 * Predictions provide estimated weight values at specific time intervals
 * post-surgery (1 month, 3 months, 6 months) to help healthcare providers
 * set realistic expectations and monitor patient progress.
 * All endpoints require authentication via Bearer token, which is automatically
 * handled by the {@link ApiClient} authentication interceptor.
 *
 * @see ApiClient
 * @see PredictionResponse
 */
public interface PredictionApiService {

    /**
     * Retrieves AI-generated weight loss predictions for a specific patient.
     *
     * <p>This endpoint returns machine learning predictions for patient weight
     * at 1 month, 3 months, and 6 months following bariatric surgery.
     *
     * @param patientId The unique identifier of the patient (patient code)
     *                  for whom predictions should be generated
     * @return Retrofit Call object containing PredictionResponse with
     *         weight predictions at 1, 3, and 6 months post-surgery
     *
     * @throws IllegalArgumentException if patientId is null or empty
     *
     */
    @GET("api/results/{patientId}")
    Call<PredictionResponse> getPrediction(@Path("patientId") String patientId);
}