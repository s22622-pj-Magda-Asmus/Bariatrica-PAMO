package com.example.bariatric_mobile.utils;

import android.content.Context;

import com.example.bariatric_mobile.R;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BmiCalculatorTest {

    @Mock
    private Context mockContext;

    @BeforeEach
    public void setUp() {
        lenient().when(mockContext.getString(R.string.bmi_underweight)).thenReturn("Niedowaga");
        lenient().when(mockContext.getString(R.string.bmi_normal)).thenReturn("Prawidłowa");
        lenient().when(mockContext.getString(R.string.bmi_overweight)).thenReturn("Nadwaga");
        lenient().when(mockContext.getString(R.string.bmi_obese_1)).thenReturn("Otyłość I stopnia");
        lenient().when(mockContext.getString(R.string.bmi_obese_2)).thenReturn("Otyłość II stopnia");
        lenient().when(mockContext.getString(R.string.bmi_obese_3)).thenReturn("Otyłość III stopnia");
    }

    @Test
    public void calculateBMI_withValidValues_shouldReturnCorrectBMI() {
        double result = BmiCalculator.calculateBMI(70, 175);
        assertEquals(22.86, result, 0.01);

        double result2 = BmiCalculator.calculateBMI(80, 180);
        assertEquals(24.69, result2, 0.01);

        double result3 = BmiCalculator.calculateBMI(100, 170);
        assertEquals(34.60, result3, 0.01);
    }

    @Test
    public void calculateBMI_withZeroHeight_shouldReturnZero() {
        double result = BmiCalculator.calculateBMI(70, 0);
        assertEquals(0.0, result);
    }

    @Test
    public void calculateBMI_withNegativeHeight_shouldReturnZero() {
        double result = BmiCalculator.calculateBMI(70, -175);
        assertEquals(0.0, result);
    }

    @Test
    public void calculateBMI_withZeroWeight_shouldReturnZero() {
        double result = BmiCalculator.calculateBMI(0, 175);
        assertEquals(0.0, result);
    }

    @Test
    public void getFormattedBMI_shouldFormatToOneDecimalPlace() {
        assertEquals("22,9", BmiCalculator.getFormattedBMI(22.857));
        assertEquals("24,7", BmiCalculator.getFormattedBMI(24.691));
        assertEquals("18,5", BmiCalculator.getFormattedBMI(18.5));
    }

    @Test
    public void getBMICategory_withUnderweight_shouldReturnUnderweightString() {
        String result = BmiCalculator.getBMICategory(mockContext, 18.4);
        assertEquals("Niedowaga", result);

        String result2 = BmiCalculator.getBMICategory(mockContext, 18.0);
        assertEquals("Niedowaga", result2);

        String result3 = BmiCalculator.getBMICategory(mockContext, 15.0);
        assertEquals("Niedowaga", result3);
    }

    @Test
    public void getBMICategory_withNormal_shouldReturnNormalString() {
        String result = BmiCalculator.getBMICategory(mockContext, 22.0);
        assertEquals("Prawidłowa", result);

        String result2 = BmiCalculator.getBMICategory(mockContext, 18.5);
        assertEquals("Prawidłowa", result2);

        String result3 = BmiCalculator.getBMICategory(mockContext, 24.9);
        assertEquals("Prawidłowa", result3);
    }

    @Test
    public void getBMICategory_withOverweight_shouldReturnOverweightString() {
        String result = BmiCalculator.getBMICategory(mockContext, 27.0);
        assertEquals("Nadwaga", result);

        String result2 = BmiCalculator.getBMICategory(mockContext, 25.0);
        assertEquals("Nadwaga", result2);

        String result3 = BmiCalculator.getBMICategory(mockContext, 29.9);
        assertEquals("Nadwaga", result3);
    }

    @Test
    public void getBMICategory_withObese1_shouldReturnObese1String() {
        String result = BmiCalculator.getBMICategory(mockContext, 32.0);
        assertEquals("Otyłość I stopnia", result);
        verify(mockContext).getString(R.string.bmi_obese_1);

        String result2 = BmiCalculator.getBMICategory(mockContext, 30.0);
        assertEquals("Otyłość I stopnia", result2);

        String result3 = BmiCalculator.getBMICategory(mockContext, 34.9);
        assertEquals("Otyłość I stopnia", result3);
    }

    @Test
    public void getBMICategory_withObese2_shouldReturnObese2String() {
        String result = BmiCalculator.getBMICategory(mockContext, 37.0);
        assertEquals("Otyłość II stopnia", result);
        verify(mockContext).getString(R.string.bmi_obese_2);

        String result2 = BmiCalculator.getBMICategory(mockContext, 35.0);
        assertEquals("Otyłość II stopnia", result2);

        String result3 = BmiCalculator.getBMICategory(mockContext, 39.9);
        assertEquals("Otyłość II stopnia", result3);
    }

    @Test
    public void getBMICategory_withObese3_shouldReturnObese3String() {
        String result = BmiCalculator.getBMICategory(mockContext, 45.0);
        assertEquals("Otyłość III stopnia", result);
        verify(mockContext).getString(R.string.bmi_obese_3);

        String result2 = BmiCalculator.getBMICategory(mockContext, 40.0);
        assertEquals("Otyłość III stopnia", result2);

        String result3 = BmiCalculator.getBMICategory(mockContext, 60.0);
        assertEquals("Otyłość III stopnia", result3);
    }

    @Test
    public void calculateBMI_withRealWorldValues_shouldMatchExpectedCategories() {
        double bmi1 = BmiCalculator.calculateBMI(65, 170);
        assertEquals(22.49, bmi1, 0.1);
        String category1 = BmiCalculator.getBMICategory(mockContext, bmi1);
        assertEquals("Prawidłowa", category1);

        double bmi2 = BmiCalculator.calculateBMI(85, 175);
        assertEquals(27.76, bmi2, 0.1);
        String category2 = BmiCalculator.getBMICategory(mockContext, bmi2);
        assertEquals("Nadwaga", category2);

        double bmi3 = BmiCalculator.calculateBMI(110, 180);
        assertEquals(34.0, bmi3, 0.1);
        String category3 = BmiCalculator.getBMICategory(mockContext, bmi3);
        assertEquals("Otyłość I stopnia", category3);
    }

    @Test
    public void getBMICategory_boundaryValues_shouldReturnCorrectCategories() {
        assertEquals("Prawidłowa", BmiCalculator.getBMICategory(mockContext, 18.5));
        assertEquals("Nadwaga", BmiCalculator.getBMICategory(mockContext, 25.0));
        assertEquals("Otyłość I stopnia", BmiCalculator.getBMICategory(mockContext, 30.0));
        assertEquals("Otyłość II stopnia", BmiCalculator.getBMICategory(mockContext, 35.0));
        assertEquals("Otyłość III stopnia", BmiCalculator.getBMICategory(mockContext, 40.0));
    }
}