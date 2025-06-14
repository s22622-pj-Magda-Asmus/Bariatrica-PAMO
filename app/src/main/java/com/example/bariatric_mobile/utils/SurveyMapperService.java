package com.example.bariatric_mobile.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SurveyMapperService {

    private static final Map<String, String> diseasesMap = new HashMap<String, String>() {{
        put("arthritis", "Choroba zwyrodnieniowa stawów");
        put("sleepApnea", "Obturacyjny bezdech senny");
        put("cardioDisease", "Choroby układu sercowo-naczyniowego");
        put("diabetes", "Cukrzyca typu 2");
        put("hypertension", "Nadciśnienie tętnicze");
        put("dyslipidemia", "Dyslipidemia");
    }};

    private static final Map<String, String> additionalDiseasesMap = new HashMap<String, String>() {{
        put("prediabetes", "Stan przedcukrzycowy");
        put("fattyLiver", "Stłuszczenie wątroby");
        put("pcos", "PCOS/zaburzenia płodności/obniżone libido");
        put("asthma", "Astma oskrzelowa");
        put("copd", "POCHP");
        put("hypothyroidism", "Niedoczynność tarczycy");
        put("cancer", "Choroba nowotworowa");
    }};

    private static final Map<String, String> contraindicationsMap = new HashMap<String, String>() {{
        put("bloodClotting", "Ciężkie zaburzenia krzepnięcia krwi");
        put("addiction", "Czynne uzależnienie od alkoholu lub narkotyków");
        put("mentalDisability", "Upośledzenie umysłowe ciężkiego stopnia");
        put("noFollowUp", "Brak możliwości udziału w stałej długoterminowej kontroli po leczeniu operacyjnym");
        put("pregnancy", "Okres 12 miesięcy poprzedzający planowaną ciążę oraz okres ciąży i karmienia piersią");
    }};

    private static final Map<String, String> familyDiseasesMap = new HashMap<String, String>() {{
        put("heartDisease", "Zawały serca i/lub choroba wieńcowa");
        put("diabetes", "Cukrzyca typu 2");
        put("hypertension", "Nadciśnienie tętnicze");
        put("cancer", "Choroby nowotworowe");
    }};

    private static final Map<String, String> previousTreatmentsMap = new HashMap<String, String>() {{
        put("diet", "niefarmakologiczne (dieta, aktywność fizyczna)");
        put("meds", "farmakologiczne");
        put("surgery", "chirurgiczne");
    }};

    public static List<String> mapArray(List<String> array, Map<String, String> translationMap) {
        if (array == null) {
            return new ArrayList<>();
        }

        List<String> translated = new ArrayList<>();
        for (String item : array) {
            String translation = translationMap.get(item);
            translated.add(translation != null ? translation : item);
        }
        return translated;
    }

    public static List<String> translateDiseases(List<String> diseases) {
        return mapArray(diseases, diseasesMap);
    }

    public static List<String> translateAdditionalDiseases(List<String> additionalDiseases) {
        return mapArray(additionalDiseases, additionalDiseasesMap);
    }

    public static List<String> translateContraindications(List<String> contraindications) {
        return mapArray(contraindications, contraindicationsMap);
    }

    public static List<String> translateFamilyDiseases(List<String> familyDiseases) {
        return mapArray(familyDiseases, familyDiseasesMap);
    }

    public static List<String> translatePreviousTreatments(List<String> previousTreatments) {
        return mapArray(previousTreatments, previousTreatmentsMap);
    }
}