package com.example.bariatric_mobile.utils;

import android.content.Context;
import com.example.bariatric_mobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for translating survey data keys to localized human-readable strings.
 *
 * <p>This class provides translation services for various medical and survey-related
 * data that comes from the API in English keys but needs to be displayed to users
 * in their native language. It handles translation of diseases, contraindications,
 * family medical history, and treatment types.</p>
 *
 * <p>All translation methods are null-safe and will return empty lists for null input.
 * Unknown keys that don't have translations will fall back to returning the original
 * key string.</p>
 *
 */
public class SurveyMapperService {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private SurveyMapperService() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Translates a disease key to localized string.
     *
     * <p>Supported disease keys:</p>
     * <ul>
     *   <li>arthritis → Choroba zwyrodnieniowa stawów</li>
     *   <li>sleepApnea → Obturacyjny bezdech senny</li>
     *   <li>cardioDisease → Choroby układu sercowo-naczyniowego</li>
     *   <li>diabetes → Cukrzyca typu 2</li>
     *   <li>hypertension → Nadciśnienie tętnicze</li>
     *   <li>dyslipidemia → Dyslipidemia</li>
     * </ul>
     *
     * @param context The Android context for accessing string resources
     * @param disease The disease key to translate
     * @return Localized disease name or original key if not found
     */
    private static String getTranslatedDisease(Context context, String disease) {
        switch (disease) {
            case "arthritis":
                return context.getString(R.string.disease_arthritis);
            case "sleepApnea":
                return context.getString(R.string.disease_sleep_apnea);
            case "cardioDisease":
                return context.getString(R.string.disease_cardio_disease);
            case "diabetes":
                return context.getString(R.string.disease_diabetes);
            case "hypertension":
                return context.getString(R.string.disease_hypertension);
            case "dyslipidemia":
                return context.getString(R.string.disease_dyslipidemia);
            default:
                return disease;
        }
    }

    /**
     * Translates an additional disease key to localized string.
     *
     * <p>Supported additional disease keys:</p>
     * <ul>
     *   <li>prediabetes → Stan przedcukrzycowy</li>
     *   <li>fattyLiver → Stłuszczenie wątroby</li>
     *   <li>pcos → PCOS/zaburzenia płodności/obniżone libido</li>
     *   <li>asthma → Astma oskrzelowa</li>
     *   <li>copd → POCHP</li>
     *   <li>hypothyroidism → Niedoczynność tarczycy</li>
     *   <li>cancer → Choroba nowotworowa</li>
     * </ul>
     *
     * @param context The Android context for accessing string resources
     * @param disease The additional disease key to translate
     * @return Localized disease name or original key if not found
     */
    private static String getTranslatedAdditionalDisease(Context context, String disease) {
        switch (disease) {
            case "prediabetes":
                return context.getString(R.string.disease_prediabetes);
            case "fattyLiver":
                return context.getString(R.string.disease_fatty_liver);
            case "pcos":
                return context.getString(R.string.disease_pcos);
            case "asthma":
                return context.getString(R.string.disease_asthma);
            case "copd":
                return context.getString(R.string.disease_copd);
            case "hypothyroidism":
                return context.getString(R.string.disease_hypothyroidism);
            case "cancer":
                return context.getString(R.string.disease_cancer);
            default:
                return disease;
        }
    }

    /**
     * Translates a contraindication key to localized string.
     *
     * @param context The Android context for accessing string resources
     * @param contraindication The contraindication key to translate
     * @return Localized contraindication description or original key if not found
     */
    private static String getTranslatedContraindication(Context context, String contraindication) {
        switch (contraindication) {
            case "bloodClotting":
                return context.getString(R.string.contraindication_blood_clotting);
            case "addiction":
                return context.getString(R.string.contraindication_addiction);
            case "mentalDisability":
                return context.getString(R.string.contraindication_mental_disability);
            case "noFollowUp":
                return context.getString(R.string.contraindication_no_follow_up);
            case "pregnancy":
                return context.getString(R.string.contraindication_pregnancy);
            default:
                return contraindication;
        }
    }

    /**
     * Translates a family disease key to localized string.
     *
     * @param context The Android context for accessing string resources
     * @param disease The family disease key to translate
     * @return Localized family disease name or original key if not found
     */
    private static String getTranslatedFamilyDisease(Context context, String disease) {
        switch (disease) {
            case "heartDisease":
                return context.getString(R.string.family_disease_heart_disease);
            case "diabetes":
                return context.getString(R.string.family_disease_diabetes);
            case "hypertension":
                return context.getString(R.string.family_disease_hypertension);
            case "cancer":
                return context.getString(R.string.family_disease_cancer);
            default:
                return disease;
        }
    }

    /**
     * Translates a treatment key to localized string.
     *
     * @param context The Android context for accessing string resources
     * @param treatment The treatment key to translate
     * @return Localized treatment description or original key if not found
     */
    private static String getTranslatedTreatment(Context context, String treatment) {
        switch (treatment) {
            case "diet":
                return context.getString(R.string.treatment_diet);
            case "meds":
                return context.getString(R.string.treatment_meds);
            case "surgery":
                return context.getString(R.string.treatment_surgery);
            default:
                return treatment;
        }
    }

    /**
     * Translates a list of disease keys to localized strings.
     *
     * <p>This method processes the main diseases from survey data.</p>
     *
     * @param context The Android context for accessing string resources
     * @param diseases List of disease keys to translate, may be null
     * @return List of localized disease names, empty list if input is null
     *
     * @throws NullPointerException if context is null
     *
     * @example
     * <pre>
     * List&lt;String&gt; keys = Arrays.asList("diabetes", "hypertension");
     * List&lt;String&gt; translated = SurveyMapperService.translateDiseases(context, keys);
     * // Returns: ["Cukrzyca typu 2", "Nadciśnienie tętnicze"]
     * </pre>
     */
    public static List<String> translateDiseases(Context context, List<String> diseases) {
        if (diseases == null) return new ArrayList<>();

        List<String> translated = new ArrayList<>();
        for (String disease : diseases) {
            translated.add(getTranslatedDisease(context, disease));
        }
        return translated;
    }

    /**
     * Translates a list of additional disease keys to localized strings.
     *
     * <p>This method processes additional/secondary diseases from survey data.</p>
     *
     * @param context The Android context for accessing string resources
     * @param additionalDiseases List of additional disease keys to translate, may be null
     * @return List of localized disease names, empty list if input is null
     *
     * @throws NullPointerException if context is null
     */
    public static List<String> translateAdditionalDiseases(Context context, List<String> additionalDiseases) {
        if (additionalDiseases == null) return new ArrayList<>();

        List<String> translated = new ArrayList<>();
        for (String disease : additionalDiseases) {
            translated.add(getTranslatedAdditionalDisease(context, disease));
        }
        return translated;
    }

    /**
     * Translates a list of contraindication keys to localized strings.
     *
     * <p>Contraindications are medical conditions or circumstances that make
     * a particular treatment inadvisable.</p>
     *
     * @param context The Android context for accessing string resources
     * @param contraindications List of contraindication keys to translate, may be null
     * @return List of localized contraindication descriptions, empty list if input is null
     *
     * @throws NullPointerException if context is null
     */
    public static List<String> translateContraindications(Context context, List<String> contraindications) {
        if (contraindications == null) return new ArrayList<>();

        List<String> translated = new ArrayList<>();
        for (String contraindication : contraindications) {
            translated.add(getTranslatedContraindication(context, contraindication));
        }
        return translated;
    }

    /**
     * Translates a list of family disease keys to localized strings.
     *
     * <p>Family diseases are medical conditions present in the patient's
     * family medical history.</p>
     *
     * @param context The Android context for accessing string resources
     * @param familyDiseases List of family disease keys to translate, may be null
     * @return List of localized family disease names, empty list if input is null
     *
     * @throws NullPointerException if context is null
     */
    public static List<String> translateFamilyDiseases(Context context, List<String> familyDiseases) {
        if (familyDiseases == null) return new ArrayList<>();

        List<String> translated = new ArrayList<>();
        for (String disease : familyDiseases) {
            translated.add(getTranslatedFamilyDisease(context, disease));
        }
        return translated;
    }

    /**
     * Translates a list of previous treatment keys to localized strings.
     *
     * <p>Previous treatments are obesity treatment methods that the patient
     * has tried before considering bariatric surgery.</p>
     *
     * @param context The Android context for accessing string resources
     * @param previousTreatments List of treatment keys to translate, may be null
     * @return List of localized treatment descriptions, empty list if input is null
     *
     * @throws NullPointerException if context is null
     */
    public static List<String> translatePreviousTreatments(Context context, List<String> previousTreatments) {
        if (previousTreatments == null) return new ArrayList<>();

        List<String> translated = new ArrayList<>();
        for (String treatment : previousTreatments) {
            translated.add(getTranslatedTreatment(context, treatment));
        }
        return translated;
    }
}