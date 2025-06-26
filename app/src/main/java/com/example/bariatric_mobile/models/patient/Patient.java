package com.example.bariatric_mobile.models.patient;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a patient in the application.
 * This model is used for displaying and transferring patient data,
 * and is typically deserialized from a JSON response.
 */
public class Patient {

    /**
     * The unique patient number used for identification.
     */
    @SerializedName("patient_number")
    private String patientNumber;

    /**
     * The date the form was submitted by the patient (e.g. registration date).
     */
    @SerializedName("submission_date")
    private String submissionDate;

    /**
     * The current status of the patient (e.g. "NOWA", "ARCHIWALNA").
     */
    @SerializedName("status")
    private String status;

    /**
     * Returns the patient code (ID).
     *
     * @return The patient number as a string.
     */
    public String getCode() {
        return patientNumber;
    }

    /**
     * Updates the patient code (patient number).
     *
     * @param patientNumber The new patient code to assign.
     */
    public void setCode(String patientNumber) {
        this.patientNumber = patientNumber;
    }
    /**
     * Returns the date when the patient submitted the form.
     *
     * @return The submission date as a string (format depends on backend).
     */
    public String getSubmissionDate() {
        return submissionDate;
    }

    /**
     * Returns the current status of the patient.
     *
     * @return The status string (can be null).
     */
    public String getStatus() {
        return status;
    }

    /**
     * Updates the status of the patient.
     *
     * @param status The new status (e.g. "NOWA", "ARCHIWALNA").
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
