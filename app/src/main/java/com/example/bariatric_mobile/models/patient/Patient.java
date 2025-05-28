package com.example.bariatric_mobile.models.patient;

public class Patient {
    private final String code;
    private final String date;
    private final String status;

    public Patient(String code, String date, String status) {
        this.code = code;
        this.date = date;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}