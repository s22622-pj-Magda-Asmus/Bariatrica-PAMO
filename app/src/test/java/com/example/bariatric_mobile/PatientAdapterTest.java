package com.example.bariatric_mobile;

import static org.junit.Assert.assertEquals;

import com.example.bariatric_mobile.adapters.PatientAdapter;

import org.junit.Test;

import java.util.ArrayList;

public class PatientAdapterTest {

    private final PatientAdapter adapter = new PatientAdapter(new ArrayList<>(), patient -> {});

    @Test
    public void testFormatDate_validIso_shouldReturnFormattedDate() {
        String isoDate = "2024-06-07T10:15:00";
        String expected = "07.06.2024";

        String result = adapter.formatDate(isoDate);

        assertEquals(expected, result);
    }

    @Test
    public void testFormatDate_nullOrEmpty_shouldReturnEmptyString() {
        assertEquals("", adapter.formatDate(null));
        assertEquals("", adapter.formatDate(""));
    }

    @Test
    public void testFormatDate_invalidFormat_shouldReturnOriginal() {
        String invalid = "07/06/2024";
        assertEquals(invalid, adapter.formatDate(invalid));
    }
}
