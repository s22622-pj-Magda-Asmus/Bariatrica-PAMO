package com.example.bariatric_mobile.models.patient;

import com.google.gson.annotations.SerializedName;

public class PredictionResponse {
    @SerializedName("one_month")
    private double oneMonth;

    @SerializedName("three_months")
    private double threeMonths;

    @SerializedName("six_months")
    private double sixMonths;

    public PredictionResponse(double oneMonth, double threeMonths, double sixMonths) {
        this.oneMonth = oneMonth;
        this.threeMonths = threeMonths;
        this.sixMonths = sixMonths;
    }

    public double getOneMonth() { return oneMonth; }
    public void setOneMonth(double oneMonth) { this.oneMonth = oneMonth; }

    public double getThreeMonths() { return threeMonths; }
    public void setThreeMonths(double threeMonths) { this.threeMonths = threeMonths; }

    public double getSixMonths() { return sixMonths; }
    public void setSixMonths(double sixMonths) { this.sixMonths = sixMonths; }
}