package com.example.bariatric_mobile.utils;

/**
 * FormatUtils
 */

import java.util.List;

public class DataFormatter {

    public static String formatBoolean(boolean value) {
        return value ? "Tak" : "Nie";
    }

    public static String formatCompulsiveEating(int value) {
        switch (value) {
            case 0:
                return "Tak";
            case 1:
                return "Nie";
            case 2:
                return "Nie wiem";
            default:
                return "Brak danych";
        }
    }

    public static String formatList(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "Brak";
        }
        return String.join(", ", list);
    }

    public static String formatDate(String date) {
        if (date == null || date.isEmpty()) {
            return "Brak danych";
        }
        return date;
    }

    public static String formatWeight(int weight) {
        return weight + " kg";
    }

    public static String formatHeight(int height) {
        return height + " cm";
    }

    public static String formatPredictionDifference(double currentWeight, double predictedWeight) {
        double difference = currentWeight - predictedWeight;
        return String.format("- %.1f kg", difference);
    }

    public static String formatPredictionPercentage(double currentWeight, double predictedWeight) {
        double percentage = ((currentWeight - predictedWeight) / currentWeight) * 100;
        return String.format("%.0f%%", percentage);
    }

    public static String formatNullableString(String value) {
        return value != null && !value.trim().isEmpty() ? value : "Brak danych";
    }

    public static String formatNullableInt(Integer value) {
        return value != null ? String.valueOf(value) : "Brak danych";
    }
}
