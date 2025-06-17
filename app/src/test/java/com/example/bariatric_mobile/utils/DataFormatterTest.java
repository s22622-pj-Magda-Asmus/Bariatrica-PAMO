package com.example.bariatric_mobile.utils;

import android.content.Context;

import com.example.bariatric_mobile.R;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DataFormatterTest {

    @Mock
    private Context mockContext;

    @BeforeEach
    public void setUp() {
        lenient().when(mockContext.getString(R.string.no)).thenReturn("Nie");
        lenient().when(mockContext.getString(R.string.yes)).thenReturn("Tak");
        lenient().when(mockContext.getString(R.string.dont_know)).thenReturn("Nie wiem");
        lenient().when(mockContext.getString(R.string.no_data)).thenReturn("Brak danych");
        lenient().when(mockContext.getString(R.string.none)).thenReturn("Brak");
        lenient().when(mockContext.getString(R.string.weight_format, 75)).thenReturn("75 kg");
        lenient().when(mockContext.getString(R.string.weight_format, 100)).thenReturn("100 kg");
        lenient().when(mockContext.getString(R.string.height_format, 180)).thenReturn("180 cm");
        lenient().when(mockContext.getString(R.string.height_format, 165)).thenReturn("165 cm");
    }

    @Test
    public void formatBoolean_shouldReturnCorrectStrings() {
        String resultTrue = DataFormatter.formatBoolean(mockContext, true);
        assertEquals("Tak", resultTrue);

        String resultFalse = DataFormatter.formatBoolean(mockContext, false);
        assertEquals("Nie", resultFalse);
    }

    @Test
    public void formatCompulsiveEating_shouldReturnCorrectValues() {
        assertEquals("Tak", DataFormatter.formatCompulsiveEating(mockContext, 0));

        assertEquals("Nie", DataFormatter.formatCompulsiveEating(mockContext, 1));

        assertEquals("Nie wiem", DataFormatter.formatCompulsiveEating(mockContext, 2));

        assertEquals("Brak danych", DataFormatter.formatCompulsiveEating(mockContext, 99));

        assertEquals("Brak danych", DataFormatter.formatCompulsiveEating(mockContext, -1));
    }

    @Test
    public void formatList_withNullList_shouldReturnNone() {
        String result = DataFormatter.formatList(mockContext, null);
        assertEquals("Brak", result);
    }

    @Test
    public void formatList_withEmptyList_shouldReturnNone() {
        List<String> emptyList = new ArrayList<>();
        String result = DataFormatter.formatList(mockContext, emptyList);
        assertEquals("Brak", result);
    }

    @Test
    public void formatList_withSingleItem_shouldReturnItem() {
        List<String> singleList = List.of("Cukrzyca");
        String result = DataFormatter.formatList(mockContext, singleList);
        assertEquals("Cukrzyca", result);
    }

    @Test
    public void formatList_withMultipleItems_shouldReturnJoinedString() {
        List<String> multipleList = List.of("Cukrzyca", "Nadciśnienie", "Astma");
        String result = DataFormatter.formatList(mockContext, multipleList);
        assertEquals("Cukrzyca, Nadciśnienie, Astma", result);
    }

    @Test
    public void formatWeight_shouldReturnFormattedWeight() {
        String result = DataFormatter.formatWeight(mockContext, 75);
        assertEquals("75 kg", result);

        String result2 = DataFormatter.formatWeight(mockContext, 100);
        assertEquals("100 kg", result2);
    }

    @Test
    public void formatHeight_shouldReturnFormattedHeight() {
        String result = DataFormatter.formatHeight(mockContext, 180);
        assertEquals("180 cm", result);

        String result2 = DataFormatter.formatHeight(mockContext, 165);
        assertEquals("165 cm", result2);
    }

    @Test
    public void formatNullableString_withValidString_shouldReturnString() {
        String result = DataFormatter.formatNullableString(mockContext, "Jakiś tekst");
        assertEquals("Jakiś tekst", result);

        String result2 = DataFormatter.formatNullableString(mockContext, "   Text with spaces   ");
        assertEquals("   Text with spaces   ", result2);
    }

    @Test
    public void formatNullableString_withNullString_shouldReturnNoData() {
        String result = DataFormatter.formatNullableString(mockContext, null);
        assertEquals("Brak danych", result);
    }

    @Test
    public void formatNullableString_withEmptyString_shouldReturnNoData() {
        String result = DataFormatter.formatNullableString(mockContext, "");
        assertEquals("Brak danych", result);

        String result2 = DataFormatter.formatNullableString(mockContext, "   ");
        assertEquals("Brak danych", result2);
    }

    @Test
    public void formatNullableInt_withValidInteger_shouldReturnString() {
        String result = DataFormatter.formatNullableInt(mockContext, 42);
        assertEquals("42", result);

        String result2 = DataFormatter.formatNullableInt(mockContext, 0);
        assertEquals("0", result2);

        String result3 = DataFormatter.formatNullableInt(mockContext, -5);
        assertEquals("-5", result3);
    }

    @Test
    public void formatNullableInt_withNullInteger_shouldReturnNoData() {
        String result = DataFormatter.formatNullableInt(mockContext, null);
        assertEquals("Brak danych", result);
    }

    @Test
    public void formatDate_withValidISODate_shouldReturnFormattedDate() {
        String result = DataFormatter.formatDate("2024-03-15T14:30:00");
        assertEquals("15.03.2024", result);

        String result2 = DataFormatter.formatDate("2023-12-31T23:59:59");
        assertEquals("31.12.2023", result2);
    }

    @Test
    public void formatDate_withNullDate_shouldReturnEmptyString() {
        String result = DataFormatter.formatDate(null);
        assertEquals("", result);
    }

    @Test
    public void formatDate_withEmptyDate_shouldReturnEmptyString() {
        String result = DataFormatter.formatDate("");
        assertEquals("", result);
    }
}