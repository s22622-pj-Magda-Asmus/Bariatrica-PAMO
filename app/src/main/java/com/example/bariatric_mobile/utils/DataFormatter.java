package com.example.bariatric_mobile.utils;

import android.content.Context;

import com.example.bariatric_mobile.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Utility class for formatting various data types for display in the UI.
 *
 * <p>This class provides static methods to format data such as booleans, lists,
 * weights, heights, and dates into user-friendly strings with proper internationalization
 * support through Android resources.</p>
 *
 * <p>All methods that require localized strings take a {@link Context} parameter
 * to access string resources. Methods return appropriate fallback values for null
 * or invalid input data.</p>
 *
 */
public class DataFormatter {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private DataFormatter() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Formats a boolean value to a localized string representation.
     *
     * @param context The Android context for accessing string resources
     * @param value The boolean value to format
     * @return "Tak" (yes) for true, "Nie" (no) for false (or localized equivalents)
     *
     * @throws NullPointerException if context is null
     *
     * @see R.string#yes
     * @see R.string#no
     */
    public static String formatBoolean(Context context, boolean value) {
        return context.getString(value ? R.string.yes : R.string.no);
    }

    /**
     * Formats a compulsive eating value to a localized string representation.
     *
     * <p>Maps integer values to human-readable strings:</p>
     * <ul>
     *   <li>0 → "Tak" (Yes)</li>
     *   <li>1 → "Nie" (No)</li>
     *   <li>2 → "Nie wiem" (Don't know)</li>
     *   <li>Other → "Brak danych" (No data)</li>
     * </ul>
     *
     * @param context The Android context for accessing string resources
     * @param value The compulsive eating value (0, 1, 2, or other)
     * @return Localized string representation of the value
     *
     * @throws NullPointerException if context is null
     */
    public static String formatCompulsiveEating(Context context, int value) {
        switch (value) {
            case 0:
                return context.getString(R.string.yes);
            case 1:
                return context.getString(R.string.no);
            case 2:
                return context.getString(R.string.dont_know);
            default:
                return context.getString(R.string.no_data);
        }
    }

    /**
     * Formats a list of strings into a comma-separated string.
     *
     * <p>If the list is null or empty, returns a localized "no data" string.
     * Otherwise, joins all elements with ", " separator.</p>
     *
     * @param context The Android context for accessing string resources
     * @param list The list of strings to format, may be null or empty
     * @return Comma-separated string or "Brak" if list is null/empty
     *
     * @throws NullPointerException if context is null
     *
     * @example
     * <pre>
     * List&lt;String&gt; diseases = Arrays.asList("Diabetes", "Hypertension");
     * String result = DataFormatter.formatList(context, diseases);
     * // Returns: "Diabetes, Hypertension"
     * </pre>
     */
    public static String formatList(Context context, List<String> list) {
        if (list == null || list.isEmpty()) {
            return context.getString(R.string.none);
        }
        return String.join(", ", list);
    }

    /**
     * Formats a weight value with unit suffix.
     *
     * @param context The Android context for accessing string resources
     * @param weight The weight value in kilograms
     * @return Formatted string with "kg" unit (e.g., "75 kg")
     *
     * @throws NullPointerException if context is null
     */
    public static String formatWeight(Context context, int weight) {
        return context.getString(R.string.weight_format, weight);
    }

    /**
     * Formats a height value with unit suffix.
     *
     * @param context The Android context for accessing string resources
     * @param height The height value in centimeters
     * @return Formatted string with "cm" unit (e.g., "180 cm")
     *
     * @throws NullPointerException if context is null
     */
    public static String formatHeight(Context context, int height) {
        return context.getString(R.string.height_format, height);
    }

    /**
     * Formats a nullable string, providing fallback for null or empty values.
     *
     * <p>Returns the original string if it's not null and not empty (after trimming).
     * Otherwise, returns a localized "no data" string.</p>
     *
     * @param context The Android context for accessing string resources
     * @param value The string value to format, may be null or empty
     * @return The original string or "Brak danych" if null/empty
     *
     * @throws NullPointerException if context is null
     */
    public static String formatNullableString(Context context, String value) {
        return value != null && !value.trim().isEmpty() ?
                value : context.getString(R.string.no_data);
    }

    /**
     * Formats a nullable integer, providing fallback for null values.
     *
     * @param context The Android context for accessing string resources
     * @param value The integer value to format, may be null
     * @return String representation of the integer or "Brak danych" if null
     *
     * @throws NullPointerException if context is null
     */
    public static String formatNullableInt(Context context, Integer value) {
        return value != null ?
                String.valueOf(value) : context.getString(R.string.no_data);
    }

    /**
     * Formats an ISO date string to a user-friendly format.
     *
     * <p>Converts ISO 8601 date format (yyyy-MM-dd'T'HH:mm:ss) to Polish
     * date format (dd.MM.yyyy). If parsing fails, returns the original string.</p>
     *
     * @param isoDate The ISO date string to format, may be null or invalid
     * @return Formatted date string (dd.MM.yyyy) or original string if parsing fails
     *
     * @example
     * <pre>
     * String result = DataFormatter.formatDate("2024-03-15T14:30:00");
     * // Returns: "15.03.2024"
     * </pre>
     */
    public static String formatDate(String isoDate) {
        if (isoDate == null || isoDate.isEmpty()) return "";
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            Date date = inputFormat.parse(isoDate);
            return outputFormat != null && date != null ? outputFormat.format(date) : isoDate;
        } catch (Exception e) {
            return isoDate;
        }
    }
}