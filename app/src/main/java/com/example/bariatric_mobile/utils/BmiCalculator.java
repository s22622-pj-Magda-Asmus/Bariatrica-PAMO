package com.example.bariatric_mobile.utils;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.bariatric_mobile.R;

/**
 * Utility class for BMI (Body Mass Index) calculations and categorization.
 *
 * <p>This class provides methods to calculate BMI from weight and height,
 * format BMI values for display, categorize BMI values according to medical
 * standards, and provide appropriate colors for different BMI categories.</p>
 *
 * <p>BMI Categories used:</p>
 * <ul>
 *   <li>&lt; 18.5: Underweight</li>
 *   <li>18.5 - 24.9: Normal weight</li>
 *   <li>25.0 - 29.9: Overweight</li>
 *   <li>30.0 - 34.9: Obesity Class I</li>
 *   <li>35.0 - 39.9: Obesity Class II</li>
 *   <li>≥ 40.0: Obesity Class III</li>
 * </ul>
 *
 */
public class BmiCalculator {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private BmiCalculator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Calculates BMI (Body Mass Index) from weight and height.
     *
     * <p>BMI is calculated using the formula: BMI = weight(kg) / height(m)²</p>
     *
     * <p>If height is zero or negative, returns 0 to avoid division by zero
     * and invalid calculations.</p>
     *
     * @param weight The weight in kilograms
     * @param height The height in centimeters
     * @return The calculated BMI value, or 0 if height is invalid (≤ 0)
     *
     * @example
     * <pre>
     * double bmi = BmiCalculator.calculateBMI(70, 175);
     * // Returns: 22.86 (70 kg, 175 cm → BMI 22.86)
     * </pre>
     */
    public static double calculateBMI(int weight, int height) {
        if (height <= 0) return 0;
        double heightInMeters = height / 100.0;
        return weight / (heightInMeters * heightInMeters);
    }

    /**
     * Formats a BMI value to a string with one decimal place.
     *
     * @param bmi The BMI value to format
     * @return Formatted BMI string with one decimal place (e.g., "22.9")
     *
     * @example
     * <pre>
     * String formatted = BmiCalculator.getFormattedBMI(22.857);
     * // Returns: "22.9"
     * </pre>
     */
    public static String getFormattedBMI(double bmi) {
        return String.format("%.1f", bmi);
    }

    /**
     * Gets the localized BMI category string based on BMI value.
     *
     * <p>Categories are determined according to standard medical BMI ranges:</p>
     * <ul>
     *   <li>&lt; 18.5: Underweight (Niedowaga)</li>
     *   <li>18.5 - 24.9: Normal weight (Prawidłowa)</li>
     *   <li>25.0 - 29.9: Overweight (Nadwaga)</li>
     *   <li>30.0 - 34.9: Obesity Class I (Otyłość I stopnia)</li>
     *   <li>35.0 - 39.9: Obesity Class II (Otyłość II stopnia)</li>
     *   <li>≥ 40.0: Obesity Class III (Otyłość III stopnia)</li>
     * </ul>
     *
     * @param context The Android context for accessing string resources
     * @param bmi The BMI value to categorize
     * @return Localized string representing the BMI category
     *
     * @throws NullPointerException if context is null
     *
     * @example
     * <pre>
     * String category = BmiCalculator.getBMICategory(context, 22.5);
     * // Returns: "Prawidłowa" (Normal weight)
     * </pre>
     */
    public static String getBMICategory(Context context, double bmi) {
        if (bmi < 18.5) {
            return context.getString(R.string.bmi_underweight);
        } else if (bmi < 25) {
            return context.getString(R.string.bmi_normal);
        } else if (bmi < 30) {
            return context.getString(R.string.bmi_overweight);
        } else if (bmi < 35) {
            return context.getString(R.string.bmi_obese_1);
        } else if (bmi < 40) {
            return context.getString(R.string.bmi_obese_2);
        } else {
            return context.getString(R.string.bmi_obese_3);
        }
    }

    /**
     * Gets the appropriate color resource for a BMI value.
     *
     * <p>Colors are assigned based on BMI categories to provide visual
     * indication of health status:</p>
     * <ul>
     *   <li>Underweight (&lt; 18.5): Blue</li>
     *   <li>Normal weight (18.5 - 24.9): Green</li>
     *   <li>Overweight (25.0 - 29.9): Orange</li>
     *   <li>Obesity (≥ 30.0): Red</li>
     * </ul>
     *
     * @param context The Android context for accessing color resources
     * @param bmi The BMI value to determine color for
     * @return Color integer value from resources
     *
     * @throws NullPointerException if context is null
     *
     * @example
     * <pre>
     * int color = BmiCalculator.getBMIColor(context, 35.0);
     * // Returns: Red color for obesity
     * textView.setTextColor(color);
     * </pre>
     */
    public static int getBMIColor(Context context, double bmi) {
        if (bmi < 18.5) {
            return ContextCompat.getColor(context, R.color.blue);
        } else if (bmi < 25) {
            return ContextCompat.getColor(context, R.color.green);
        } else if (bmi < 30) {
            return ContextCompat.getColor(context, R.color.orange);
        } else {
            return ContextCompat.getColor(context, R.color.red);
        }
    }
}