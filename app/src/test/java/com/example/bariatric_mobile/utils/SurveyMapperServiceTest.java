package com.example.bariatric_mobile.utils;

import android.content.Context;

import com.example.bariatric_mobile.R;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SurveyMapperServiceTest {

    @Mock
    private Context mockContext;

    @BeforeEach
    public void setUp() {
        // Mock disease translations
        lenient().when(mockContext.getString(R.string.disease_arthritis)).thenReturn("Choroba zwyrodnieniowa stawów");
        lenient().when(mockContext.getString(R.string.disease_sleep_apnea)).thenReturn("Obturacyjny bezdech senny");
        lenient().when(mockContext.getString(R.string.disease_cardio_disease)).thenReturn("Choroby układu sercowo-naczyniowego");
        lenient().when(mockContext.getString(R.string.disease_diabetes)).thenReturn("Cukrzyca typu 2");
        lenient().when(mockContext.getString(R.string.disease_hypertension)).thenReturn("Nadciśnienie tętnicze");
        lenient().when(mockContext.getString(R.string.disease_dyslipidemia)).thenReturn("Dyslipidemia");

        // Mock additional disease translations
        lenient().when(mockContext.getString(R.string.disease_prediabetes)).thenReturn("Stan przedcukrzycowy");
        lenient().when(mockContext.getString(R.string.disease_fatty_liver)).thenReturn("Stłuszczenie wątroby");
        lenient().when(mockContext.getString(R.string.disease_pcos)).thenReturn("PCOS/zaburzenia płodności/obniżone libido");
        lenient().when(mockContext.getString(R.string.disease_asthma)).thenReturn("Astma oskrzelowa");
        lenient().when(mockContext.getString(R.string.disease_copd)).thenReturn("POCHP");
        lenient().when(mockContext.getString(R.string.disease_hypothyroidism)).thenReturn("Niedoczynność tarczycy");
        lenient().when(mockContext.getString(R.string.disease_cancer)).thenReturn("Choroba nowotworowa");

        // Mock contraindication translations
        lenient().when(mockContext.getString(R.string.contraindication_blood_clotting)).thenReturn("Ciężkie zaburzenia krzepnięcia krwi");
        lenient().when(mockContext.getString(R.string.contraindication_addiction)).thenReturn("Czynne uzależnienie od alkoholu lub narkotyków");
        lenient().when(mockContext.getString(R.string.contraindication_mental_disability)).thenReturn("Upośledzenie umysłowe ciężkiego stopnia");
        lenient().when(mockContext.getString(R.string.contraindication_no_follow_up)).thenReturn("Brak możliwości udziału w stałej długoterminowej kontroli po leczeniu operacyjnym");
        lenient().when(mockContext.getString(R.string.contraindication_pregnancy)).thenReturn("Okres 12 miesięcy poprzedzający planowaną ciążę oraz okres ciąży i karmienia piersią");

        // Mock family disease translations
        lenient().when(mockContext.getString(R.string.family_disease_heart_disease)).thenReturn("Zawały serca i/lub choroba wieńcowa");
        lenient().when(mockContext.getString(R.string.family_disease_diabetes)).thenReturn("Cukrzyca typu 2");
        lenient().when(mockContext.getString(R.string.family_disease_hypertension)).thenReturn("Nadciśnienie tętnicze");
        lenient().when(mockContext.getString(R.string.family_disease_cancer)).thenReturn("Choroby nowotworowe");

        // Mock treatment translations
        lenient().when(mockContext.getString(R.string.treatment_diet)).thenReturn("niefarmakologiczne (dieta, aktywność fizyczna)");
        lenient().when(mockContext.getString(R.string.treatment_meds)).thenReturn("farmakologiczne");
        lenient().when(mockContext.getString(R.string.treatment_surgery)).thenReturn("chirurgiczne");
    }

    // === DISEASES TESTS ===

    @Test
    public void translateDiseases_withNullList_shouldReturnEmptyList() {
        List<String> result = SurveyMapperService.translateDiseases(mockContext, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void translateDiseases_withEmptyList_shouldReturnEmptyList() {
        List<String> emptyList = new ArrayList<>();
        List<String> result = SurveyMapperService.translateDiseases(mockContext, emptyList);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void translateDiseases_withKnownDiseases_shouldReturnTranslatedList() {
        List<String> diseases = Arrays.asList("arthritis", "diabetes", "hypertension");
        List<String> result = SurveyMapperService.translateDiseases(mockContext, diseases);

        assertEquals(3, result.size());
        assertEquals("Choroba zwyrodnieniowa stawów", result.get(0));
        assertEquals("Cukrzyca typu 2", result.get(1));
        assertEquals("Nadciśnienie tętnicze", result.get(2));

        verify(mockContext).getString(R.string.disease_arthritis);
        verify(mockContext).getString(R.string.disease_diabetes);
        verify(mockContext).getString(R.string.disease_hypertension);
    }

    @Test
    public void translateDiseases_withUnknownDisease_shouldReturnOriginalValue() {
        List<String> diseases = Arrays.asList("unknownDisease", "diabetes");
        List<String> result = SurveyMapperService.translateDiseases(mockContext, diseases);

        assertEquals(2, result.size());
        assertEquals("unknownDisease", result.get(0)); // fallback to original
        assertEquals("Cukrzyca typu 2", result.get(1));
    }

    @Test
    public void translateDiseases_withAllKnownDiseases_shouldTranslateAll() {
        List<String> diseases = Arrays.asList("arthritis", "sleepApnea", "cardioDisease", "diabetes", "hypertension", "dyslipidemia");
        List<String> result = SurveyMapperService.translateDiseases(mockContext, diseases);

        assertEquals(6, result.size());
        assertEquals("Choroba zwyrodnieniowa stawów", result.get(0));
        assertEquals("Obturacyjny bezdech senny", result.get(1));
        assertEquals("Choroby układu sercowo-naczyniowego", result.get(2));
        assertEquals("Cukrzyca typu 2", result.get(3));
        assertEquals("Nadciśnienie tętnicze", result.get(4));
        assertEquals("Dyslipidemia", result.get(5));
    }

    // === ADDITIONAL DISEASES TESTS ===

    @Test
    public void translateAdditionalDiseases_withNullList_shouldReturnEmptyList() {
        List<String> result = SurveyMapperService.translateAdditionalDiseases(mockContext, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void translateAdditionalDiseases_withKnownDiseases_shouldReturnTranslatedList() {
        List<String> diseases = Arrays.asList("prediabetes", "fattyLiver", "asthma");
        List<String> result = SurveyMapperService.translateAdditionalDiseases(mockContext, diseases);

        assertEquals(3, result.size());
        assertEquals("Stan przedcukrzycowy", result.get(0));
        assertEquals("Stłuszczenie wątroby", result.get(1));
        assertEquals("Astma oskrzelowa", result.get(2));
    }

    @Test
    public void translateAdditionalDiseases_withAllKnownDiseases_shouldTranslateAll() {
        List<String> diseases = Arrays.asList("prediabetes", "fattyLiver", "pcos", "asthma", "copd", "hypothyroidism", "cancer");
        List<String> result = SurveyMapperService.translateAdditionalDiseases(mockContext, diseases);

        assertEquals(7, result.size());
        assertEquals("Stan przedcukrzycowy", result.get(0));
        assertEquals("Stłuszczenie wątroby", result.get(1));
        assertEquals("PCOS/zaburzenia płodności/obniżone libido", result.get(2));
        assertEquals("Astma oskrzelowa", result.get(3));
        assertEquals("POCHP", result.get(4));
        assertEquals("Niedoczynność tarczycy", result.get(5));
        assertEquals("Choroba nowotworowa", result.get(6));
    }

    // === CONTRAINDICATIONS TESTS ===

    @Test
    public void translateContraindications_withNullList_shouldReturnEmptyList() {
        List<String> result = SurveyMapperService.translateContraindications(mockContext, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void translateContraindications_withKnownContraindications_shouldReturnTranslatedList() {
        List<String> contraindications = Arrays.asList("bloodClotting", "addiction");
        List<String> result = SurveyMapperService.translateContraindications(mockContext, contraindications);

        assertEquals(2, result.size());
        assertEquals("Ciężkie zaburzenia krzepnięcia krwi", result.get(0));
        assertEquals("Czynne uzależnienie od alkoholu lub narkotyków", result.get(1));
    }

    @Test
    public void translateContraindications_withAllKnownContraindications_shouldTranslateAll() {
        List<String> contraindications = Arrays.asList("bloodClotting", "addiction", "mentalDisability", "noFollowUp", "pregnancy");
        List<String> result = SurveyMapperService.translateContraindications(mockContext, contraindications);

        assertEquals(5, result.size());
        assertEquals("Ciężkie zaburzenia krzepnięcia krwi", result.get(0));
        assertEquals("Czynne uzależnienie od alkoholu lub narkotyków", result.get(1));
        assertEquals("Upośledzenie umysłowe ciężkiego stopnia", result.get(2));
        assertEquals("Brak możliwości udziału w stałej długoterminowej kontroli po leczeniu operacyjnym", result.get(3));
        assertEquals("Okres 12 miesięcy poprzedzający planowaną ciążę oraz okres ciąży i karmienia piersią", result.get(4));
    }

    // === FAMILY DISEASES TESTS ===

    @Test
    public void translateFamilyDiseases_withNullList_shouldReturnEmptyList() {
        List<String> result = SurveyMapperService.translateFamilyDiseases(mockContext, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void translateFamilyDiseases_withKnownDiseases_shouldReturnTranslatedList() {
        List<String> diseases = Arrays.asList("heartDisease", "diabetes");
        List<String> result = SurveyMapperService.translateFamilyDiseases(mockContext, diseases);

        assertEquals(2, result.size());
        assertEquals("Zawały serca i/lub choroba wieńcowa", result.get(0));
        assertEquals("Cukrzyca typu 2", result.get(1));
    }

    @Test
    public void translateFamilyDiseases_withAllKnownDiseases_shouldTranslateAll() {
        List<String> diseases = Arrays.asList("heartDisease", "diabetes", "hypertension", "cancer");
        List<String> result = SurveyMapperService.translateFamilyDiseases(mockContext, diseases);

        assertEquals(4, result.size());
        assertEquals("Zawały serca i/lub choroba wieńcowa", result.get(0));
        assertEquals("Cukrzyca typu 2", result.get(1));
        assertEquals("Nadciśnienie tętnicze", result.get(2));
        assertEquals("Choroby nowotworowe", result.get(3));
    }

    // === PREVIOUS TREATMENTS TESTS ===

    @Test
    public void translatePreviousTreatments_withNullList_shouldReturnEmptyList() {
        List<String> result = SurveyMapperService.translatePreviousTreatments(mockContext, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void translatePreviousTreatments_withKnownTreatments_shouldReturnTranslatedList() {
        List<String> treatments = Arrays.asList("diet", "meds");
        List<String> result = SurveyMapperService.translatePreviousTreatments(mockContext, treatments);

        assertEquals(2, result.size());
        assertEquals("niefarmakologiczne (dieta, aktywność fizyczna)", result.get(0));
        assertEquals("farmakologiczne", result.get(1));
    }

    @Test
    public void translatePreviousTreatments_withAllKnownTreatments_shouldTranslateAll() {
        List<String> treatments = Arrays.asList("diet", "meds", "surgery");
        List<String> result = SurveyMapperService.translatePreviousTreatments(mockContext, treatments);

        assertEquals(3, result.size());
        assertEquals("niefarmakologiczne (dieta, aktywność fizyczna)", result.get(0));
        assertEquals("farmakologiczne", result.get(1));
        assertEquals("chirurgiczne", result.get(2));
    }

    @Test
    public void translatePreviousTreatments_withUnknownTreatment_shouldReturnOriginalValue() {
        List<String> treatments = Arrays.asList("unknownTreatment", "diet");
        List<String> result = SurveyMapperService.translatePreviousTreatments(mockContext, treatments);

        assertEquals(2, result.size());
        assertEquals("unknownTreatment", result.get(0)); // fallback to original
        assertEquals("niefarmakologiczne (dieta, aktywność fizyczna)", result.get(1));
    }

    // === MIXED TESTS ===

    @Test
    public void allMethods_withMixedKnownAndUnknownValues_shouldHandleCorrectly() {
        // Test that unknown values fall back to original strings
        List<String> diseases = Arrays.asList("diabetes", "unknownDisease");
        List<String> result = SurveyMapperService.translateDiseases(mockContext, diseases);

        assertEquals("Cukrzyca typu 2", result.get(0));
        assertEquals("unknownDisease", result.get(1));
    }
}