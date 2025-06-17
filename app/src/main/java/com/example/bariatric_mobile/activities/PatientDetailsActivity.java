package com.example.bariatric_mobile.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.bariatric_mobile.R;
import com.example.bariatric_mobile.models.auth.User;
import com.example.bariatric_mobile.models.patient.PredictionResponse;
import com.example.bariatric_mobile.models.patient.SurveyData;
import com.example.bariatric_mobile.utils.BmiCalculator;
import com.example.bariatric_mobile.utils.DataFormatter;
import com.example.bariatric_mobile.utils.SurveyMapperService;
import com.example.bariatric_mobile.viewmodels.PatientDetailsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PatientDetailsActivity extends AppCompatActivity {

    private PatientDetailsViewModel viewModel;

    private Button logoutButton;
    private TextView detailsCodeText;
    private TextView detailsDateText;
    private LinearLayout backToList;

    private WebView chartWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        initializeViews();
        initializeViewModel();
        setupObservers();
        setupListeners();
        loadPatientData();
    }

    private void initializeViews() {
        logoutButton = findViewById(R.id.logout_button);
        detailsCodeText = findViewById(R.id.details_code);
        detailsDateText = findViewById(R.id.details_date);
        backToList = findViewById(R.id.back_to_list);
        chartWebView = findViewById(R.id.chart_webview);
        setupWebView();
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(PatientDetailsViewModel.class);
    }

    private void setupObservers() {
        viewModel.getPatientDetails().observe(this, surveyData -> {
            if (surveyData != null) {
                populatePatientData(surveyData);
                tryPopulatePredictionTexts();
                tryLoadChart();

            }
        });

        viewModel.getPrediction().observe(this, prediction -> {
            if (prediction != null) {
                tryPopulatePredictionTexts();
                tryLoadChart();
            }
        });

        viewModel.getCurrentUser().observe(this, user -> {
            if (user != null) {
                populateUserData(user);
            }
        });
    }

    private void setupListeners() {
        backToList.setOnClickListener(v -> finish());

        logoutButton.setOnClickListener(v -> {
            viewModel.logout();
            navigateToLogin();
        });

    }

    private void loadPatientData() {
        String patientCode = getIntent().getStringExtra("patient_code");
        if (patientCode != null) {
            viewModel.loadPatientDetails(patientCode);
        }
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView() {
        if (chartWebView == null) return;

        chartWebView.getSettings().setJavaScriptEnabled(true);
        chartWebView.getSettings().setDomStorageEnabled(true);
        chartWebView.getSettings().setAllowFileAccess(true);

        chartWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                tryLoadChart();
            }
        });

        chartWebView.loadUrl("file:///android_asset/chart_simple.html");
    }

    private void populatePatientData(SurveyData surveyData) {
        detailsCodeText.setText(surveyData.getPatientCode());
        detailsDateText.setText(DataFormatter.formatDate(surveyData.getDate()));

        populatePersonalDataSection(surveyData);
        populateReferralDataSection(surveyData);
        populateDiseasesSection(surveyData);
        populateBariatricSection(surveyData);
        populateLifestyleSection(surveyData);
        populateMentalHealthSection(surveyData);
    }

    private void populatePersonalDataSection(SurveyData surveyData) {
        setLabeledTextView(R.id.details_gender, R.string.gender, surveyData.getGender());
        setLabeledTextView(R.id.details_birthdate, R.string.birth_date, DataFormatter.formatDate(surveyData.getBirthDate()));
        setLabeledTextView(R.id.details_height, R.string.height, DataFormatter.formatHeight(surveyData.getHeight()));
        setLabeledTextView(R.id.details_max_weight, R.string.max_weight, DataFormatter.formatWeight(surveyData.getWeight()));
        setLabeledTextView(R.id.details_obesity_years, R.string.obesity_years, DataFormatter.formatNullableInt(surveyData.getObesityYears()));

        double bmi = BmiCalculator.calculateBMI(surveyData.getWeight(), surveyData.getHeight());
        setLabeledTextView(R.id.details_bmi, R.string.bmi, BmiCalculator.getFormattedBMI(bmi));
        setLabeledTextView(R.id.details_bmi_desc, R.string.bmi_category, BmiCalculator.getBMICategory(this, bmi));
        TextView bmiTextView = findViewById(R.id.details_bmi);
        bmiTextView.setTextColor(BmiCalculator.getBMIColor(this, bmi));
    }

    private void populateReferralDataSection(SurveyData surveyData) {
        setLabeledTextView(R.id.details_ref_code, R.string.patient_code2, surveyData.getPatientCode());
        setLabeledTextView(R.id.details_ref_type, R.string.refferal_type, surveyData.getReferralType());
        setLabeledTextView(R.id.details_ref_pin, R.string.refferal_pin, surveyData.getReferralPIN());
        setLabeledTextView(R.id.details_ref_consider_op, R.string.things_about_surgery, DataFormatter.formatBoolean(surveyData.isConsiderSurgery()));
    }

    private void populateDiseasesSection(SurveyData surveyData) {
        setLabeledTextView(R.id.details_diseases, R.string.diseases2,
                DataFormatter.formatList(new ArrayList<>() {{
                    addAll(SurveyMapperService.translateDiseases(surveyData.getDiseases()));
                    addAll(SurveyMapperService.translateAdditionalDiseases(surveyData.getAdditionalDiseases()));
                }}));

        setLabeledTextView(R.id.details_additional_diseases, R.string.additional_diseases,
                DataFormatter.formatNullableString(surveyData.getOtherDiseases()));

        setLabeledTextView(R.id.details_abdomen_surgeries, R.string.abdominal_surgery,
                DataFormatter.formatBoolean(surveyData.isAbdomenSurgeries()));

        setLabeledTextView(R.id.details_family_diseases, R.string.diseases_in_family,
                DataFormatter.formatList(SurveyMapperService.translateFamilyDiseases(surveyData.getFamilyDiseases())));

        List<String> translatedContraindications = SurveyMapperService.translateContraindications(surveyData.getContraindications());
        setLabeledTextView(R.id.details_contraindications, R.string.contraindications,
                DataFormatter.formatList(translatedContraindications));
    }

    private void populateBariatricSection(SurveyData surveyData) {
        List<String> translatedTreatments = SurveyMapperService.translatePreviousTreatments(surveyData.getPreviousTreatments());
        setLabeledTextView(R.id.details_treatment_attempts, R.string.treatment_trials,
                DataFormatter.formatList(translatedTreatments));

        setLabeledTextView(R.id.details_chronic_meds, R.string.medication,
                DataFormatter.formatBoolean(surveyData.isChronicMedication()));
        setLabeledTextView(R.id.details_meds_details, R.string.medication_details,
                DataFormatter.formatNullableString(surveyData.getMedicationDetails()));
        setLabeledTextView(R.id.details_specialist_care, R.string.specialist_care,
                DataFormatter.formatBoolean(surveyData.isSpecialistClinic()));
        setLabeledTextView(R.id.details_specialist_type, R.string.clinic_type,
                DataFormatter.formatNullableString(surveyData.getClinicType()));
    }

    private void populateLifestyleSection(SurveyData surveyData) {
        setLabeledTextView(R.id.details_physical_activity, R.string.physical_activity, DataFormatter.formatBoolean(surveyData.isPhysicalActivity()));
        setLabeledTextView(R.id.details_healthy_eating, R.string.healthy_diet, DataFormatter.formatBoolean(surveyData.isHealthyEating()));
        setLabeledTextView(R.id.details_processed_food, R.string.processed_food, DataFormatter.formatBoolean(surveyData.isProcessedFood()));
        setLabeledTextView(R.id.details_compulsive_eating, R.string.compulsive_eating, DataFormatter.formatCompulsiveEating(surveyData.getCompulsiveEating()));
        setLabeledTextView(R.id.details_alcohol, R.string.alcohol, DataFormatter.formatBoolean(surveyData.isAlcoholConsumption()));
        setLabeledTextView(R.id.details_smoking, R.string.cigarets, DataFormatter.formatBoolean(surveyData.isSmoking()));
    }

    private void populateMentalHealthSection(SurveyData surveyData) {
        setLabeledTextView(R.id.details_psychiatric_support, R.string.mental_support, DataFormatter.formatBoolean(surveyData.isPsychiatristSupport()));
        setLabeledTextView(R.id.details_psychological_support, R.string.psychological_support, DataFormatter.formatBoolean(surveyData.isPsychologistSupport()));
        setLabeledTextView(R.id.details_suicidal_thoughts, R.string.suicial_thoughts, DataFormatter.formatBoolean(surveyData.isSuicidalThoughts()));
    }

    private void populateUserData(User user) {
        TextView userNameTextView = findViewById(R.id.user_name);
        if (userNameTextView != null) {
            userNameTextView.setText(user.getFullName());
        }
    }

    private void setLabeledTextView(int textViewId, int labelStringId, String value) {
        TextView textView = findViewById(textViewId);
        if (textView != null) {
            String label = getString(labelStringId);
            SpannableStringBuilder builder = new SpannableStringBuilder();
            SpannableString boldLabel = new SpannableString(label + " ");
            boldLabel.setSpan(new StyleSpan(Typeface.BOLD), 0, boldLabel.length(), 0);
            builder.append(boldLabel);
            builder.append(value != null ? value : "");
            textView.setText(builder);
        }
    }

    private void tryLoadChart() {
        if (chartWebView == null) return;

        SurveyData survey = viewModel.getPatientDetails().getValue();
        PredictionResponse prediction = viewModel.getPrediction().getValue();

        if (survey != null && prediction != null) {
            loadChart(survey, prediction);
        }
    }

    private void tryPopulatePredictionTexts() {
        SurveyData survey = viewModel.getPatientDetails().getValue();
        PredictionResponse prediction = viewModel.getPrediction().getValue();

        if (survey != null && prediction != null) {
            populatePredictionTexts(prediction, survey);
        }
    }

    private void populatePredictionTexts(PredictionResponse prediction, SurveyData survey) {
        double currentWeight = survey.getWeight();

        setLabeledTextView(R.id.prediction_1_month_text, R.string.prediction_1_month,
                String.format(Locale.US, "%.1f kg (- %.1f kg)",
                        prediction.getOneMonth(),
                        currentWeight - prediction.getOneMonth()));

        setLabeledTextView(R.id.prediction_3_months_text, R.string.prediction_3_months,
                String.format(Locale.US, "%.1f kg (- %.1f kg)",
                        prediction.getThreeMonths(),
                        currentWeight - prediction.getThreeMonths()));

        setLabeledTextView(R.id.prediction_6_months_text, R.string.prediction_6_months,
                String.format(Locale.US, "%.1f kg (- %.1f kg)",
                        prediction.getSixMonths(),
                        currentWeight - prediction.getSixMonths()));
    }

    private void loadChart(SurveyData survey, PredictionResponse prediction) {
        if (chartWebView == null) return;

        String chartStrings = String.format(Locale.US,
                "{" +
                        "\"loading\": \"%s\", " +
                        "\"noData\": \"%s\", " +
                        "\"noWeight\": \"%s\", " +
                        "\"chartError\": \"%s\", " +
                        "\"patientWeight\": \"%s\", " +
                        "\"noDataPoint\": \"%s\", " +
                        "\"before\": \"%s\", " +
                        "\"oneMonth\": \"%s\", " +
                        "\"threeMonths\": \"%s\", " +
                        "\"sixMonths\": \"%s\", " +
                        "\"weightUnit\": \"%s\"" +
                        "}",
                getString(R.string.loading_prediction),
                getString(R.string.no_data_to_display),
                getString(R.string.no_patient_weight_data),
                getString(R.string.chart_creation_error),
                getString(R.string.patient_weight),
                getString(R.string.no_data),
                getString(R.string.before_surgery),
                getString(R.string.one_month_after),
                getString(R.string.three_months_after),
                getString(R.string.six_months_after),
                getString(R.string.kg_unit)
        );

        String jsCode = String.format(Locale.US,
                "if (typeof loadChartData === 'function') { " +
                        "loadChartData({weight: %.1f}, {one_month: %.1f, three_months: %.1f, six_months: %.1f}, %s); " +
                        "}",
                (double) survey.getWeight(),
                prediction.getOneMonth(),
                prediction.getThreeMonths(),
                prediction.getSixMonths(),
                chartStrings);

        chartWebView.evaluateJavascript(jsCode, null);
    }
}