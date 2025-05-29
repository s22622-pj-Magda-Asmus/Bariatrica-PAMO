package com.example.bariatric_mobile.models.patient;

import com.google.gson.annotations.SerializedName;

public class Patient {
    @SerializedName("patient_number")
    private String patientNumber;

    @SerializedName("submission_date")
    private String submissionDate;

    @SerializedName("status")
    private String status;

    public String getCode() {
        return patientNumber;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public String getStatus() {
        return status;
    }
}
