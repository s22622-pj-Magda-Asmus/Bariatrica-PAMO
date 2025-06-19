package com.example.bariatric_mobile.models.patient;

import com.google.gson.annotations.SerializedName;

/**
 * Data model representing AI-generated weight loss predictions for a patient.
 * <p>
 * Contains predicted weight values at specific time intervals following
 * bariatric surgery (1 month, 3 months, and 6 months post-operation).
 * All weight values are in kilograms.
 */
public class PredictionResponse {
    @SerializedName("one_month")
    private double oneMonth;

    @SerializedName("three_months")
    private double threeMonths;

    @SerializedName("six_months")
    private double sixMonths;

    /**
     * Creates a new PredictionResponse with specified weight predictions.
     *
     * @param oneMonth Predicted weight after 1 month in kg
     * @param threeMonths Predicted weight after 3 months in kg
     * @param sixMonths Predicted weight after 6 months in kg
     */
    public PredictionResponse(double oneMonth, double threeMonths, double sixMonths) {
        this.oneMonth = oneMonth;
        this.threeMonths = threeMonths;
        this.sixMonths = sixMonths;
    }

    /**
     * Returns the predicted weight after 1 month post-surgery.
     *
     * @return Weight prediction in kilograms
     */
    public double getOneMonth() { return oneMonth; }

    /**
     * Sets the predicted weight after 1 month post-surgery.
     *
     * @param oneMonth Weight prediction in kilograms
     */
    public void setOneMonth(double oneMonth) { this.oneMonth = oneMonth; }

    /**
     * Returns the predicted weight after 3 months post-surgery.
     *
     * @return Weight prediction in kilograms
     */
    public double getThreeMonths() { return threeMonths; }

    /**
     * Sets the predicted weight after 3 months post-surgery.
     *
     * @param threeMonths Weight prediction in kilograms
     */
    public void setThreeMonths(double threeMonths) { this.threeMonths = threeMonths; }

    /**
     * Returns the predicted weight after 6 months post-surgery.
     *
     * @return Weight prediction in kilograms
     */
    public double getSixMonths() { return sixMonths; }

    /**
     * Sets the predicted weight after 6 months post-surgery.
     *
     * @param sixMonths Weight prediction in kilograms
     */
    public void setSixMonths(double sixMonths) { this.sixMonths = sixMonths; }
}