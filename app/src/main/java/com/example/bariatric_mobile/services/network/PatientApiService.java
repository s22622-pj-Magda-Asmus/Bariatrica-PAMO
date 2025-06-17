package com.example.bariatric_mobile.services.network;

import com.example.bariatric_mobile.models.patient.Patient;
import com.example.bariatric_mobile.models.patient.SurveyData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

/**
 * Retrofit API service interface for patient-related operations.
 * This interface defines the HTTP endpoints for accessing patient data
 * in the bariatric surgery management system. It provides methods to retrieve
 * patient lists, detailed patient survey data, and update patient status.
 * All endpoints require authentication via Bearer token, which is automatically
 * handled by the {@link ApiClient} authentication interceptor.
 *
 * @see ApiClient
 * @see Patient
 * @see SurveyData
 */
public interface PatientApiService {

    /**
     * Retrieves a list of all patients in the system.
     *
     * <p>This endpoint returns basic patient information including patient codes,
     * submission dates, and status. Use this for displaying patient lists and
     * dashboards.</p>
     *
     * <p><strong>HTTP Method:</strong> GET<br>
     * <strong>Endpoint:</strong> {@code /api/surveys}<br>
     * <strong>Authentication:</strong> Required (Bearer token)</p>
     *
     * @return Retrofit Call object containing a list of Patient objects
     *
     */
    @GET("api/surveys")
    Call<List<Patient>> getPatients();

    /**
     * Retrieves detailed survey data for a specific patient.
     *This endpoint returns comprehensive patient information including
     * personal data, medical history, survey responses, and all data collected
     * during the patient intake process.
     *
     * @param patientId The unique identifier of the patient (patient code)
     * @return Retrofit Call object containing detailed SurveyData
     *
     * @throws IllegalArgumentException if patientId is null or empty
     *
     */
    @GET("api/survey/{patientId}")
    Call<SurveyData> getPatientDetails(@Path("patientId") String patientId);

    /**
     * Updates the survey status for a specific patient.
     * This endpoint is used to mark a patient's survey as "viewed" or update
     * other status flags. It's typically called automatically when a healthcare
     * provider views patient details.
     *
     * @param patientId The unique identifier of the patient (patient code)
     * @return Retrofit Call object with Void response (no response body expected)
     *
     * @throws IllegalArgumentException if patientId is null or empty
     *
     */
    @PATCH("api/survey/{patientId}")
    Call<Void> updateSurveyStatus(@Path("patientId") String patientId);
}