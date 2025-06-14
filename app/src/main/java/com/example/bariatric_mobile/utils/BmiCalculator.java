package com.example.bariatric_mobile.utils;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.bariatric_mobile.R;

public class BmiCalculator {

    public static double calculateBMI(int weight, int height) {
        if (height <= 0) return 0;
        double heightInMeters = height / 100.0;
        return weight / (heightInMeters * heightInMeters);
    }

    public static String getFormattedBMI(double bmi) {
        return String.format("%.1f", bmi);
    }

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