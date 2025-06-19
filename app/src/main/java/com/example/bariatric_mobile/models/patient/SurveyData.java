package com.example.bariatric_mobile.models.patient;

import java.util.List;

/**
 * Data model representing comprehensive patient survey information.
 * <p>
 * Contains all patient data collected during the intake process including
 * personal information, medical history, lifestyle factors, mental health
 * status, and referral details. Used for displaying patient details and
 * generating weight loss predictions.
 */
public class SurveyData {
    private String patientCode;
    private String birthDate;
    private String gender;
    private String referralType;
    private String referralPIN;
    private int weight;
    private int height;
    private boolean considerSurgery;
    private List<String> diseases;
    private List<String> additionalDiseases;
    private List<String> contraindications;
    private String otherDiseases;
    private int maxWeight;
    private int obesityYears;
    private List<String> previousTreatments;
    private boolean chronicMedication;
    private String medicationDetails;
    private boolean physicalActivity;
    private boolean healthyEating;
    private boolean processedFood;
    private int compulsiveEating;
    private boolean alcoholConsumption;
    private boolean smoking;
    private boolean suicidalThoughts;
    private boolean psychiatristSupport;
    private String psychiatricDiagnosis;
    private boolean psychologistSupport;
    private List<String> familyDiseases;
    private boolean abdomenSurgeries;
    private String surgeriesDetails;
    private boolean specialistClinic;
    private String clinicType;
    private double bmi;
    private String status;
    private String date;
    private String submissionDate;

    /**
     * Default constructor creating an empty SurveyData object.
     */
    public SurveyData() {}

    /**
     * Returns the unique patient identifier code.
     *
     * @return Patient code string
     */
    public String getPatientCode() { return patientCode; }

    /**
     * Sets the unique patient identifier code.
     *
     * @param patientCode Patient code string
     */
    public void setPatientCode(String patientCode) { this.patientCode = patientCode; }

    /**
     * Returns the patient's birth date.
     *
     * @return Birth date in ISO format
     */
    public String getBirthDate() { return birthDate; }

    /**
     * Sets the patient's birth date.
     *
     * @param birthDate Birth date in ISO format
     */
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    /**
     * Returns the patient's gender.
     *
     * @return Gender string
     */
    public String getGender() { return gender; }

    /**
     * Sets the patient's gender.
     *
     * @param gender Gender string
     */
    public void setGender(String gender) { this.gender = gender; }

    /**
     * Returns the type of medical referral.
     *
     * @return Referral type string
     */
    public String getReferralType() { return referralType; }

    /**
     * Sets the type of medical referral.
     *
     * @param referralType Referral type string
     */
    public void setReferralType(String referralType) { this.referralType = referralType; }

    /**
     * Returns the referral PIN number.
     *
     * @return Referral PIN string
     */
    public String getReferralPIN() { return referralPIN; }

    /**
     * Sets the referral PIN number.
     *
     * @param referralPIN Referral PIN string
     */
    public void setReferralPIN(String referralPIN) { this.referralPIN = referralPIN; }

    /**
     * Returns the patient's current weight.
     *
     * @return Weight in kilograms
     */
    public int getWeight() { return weight; }

    /**
     * Sets the patient's current weight.
     *
     * @param weight Weight in kilograms
     */
    public void setWeight(int weight) { this.weight = weight; }

    /**
     * Returns the patient's height.
     *
     * @return Height in centimeters
     */
    public int getHeight() { return height; }

    /**
     * Sets the patient's height.
     *
     * @param height Height in centimeters
     */
    public void setHeight(int height) { this.height = height; }

    /**
     * Returns whether the patient is considering surgery.
     *
     * @return True if considering surgery, false otherwise
     */
    public boolean isConsiderSurgery() { return considerSurgery; }

    /**
     * Sets whether the patient is considering surgery.
     *
     * @param considerSurgery True if considering surgery, false otherwise
     */
    public void setConsiderSurgery(boolean considerSurgery) { this.considerSurgery = considerSurgery; }

    /**
     * Returns the list of patient's primary diseases.
     *
     * @return List of disease identifiers
     */
    public List<String> getDiseases() { return diseases; }

    /**
     * Sets the list of patient's primary diseases.
     *
     * @param diseases List of disease identifiers
     */
    public void setDiseases(List<String> diseases) { this.diseases = diseases; }

    /**
     * Returns the list of patient's additional diseases.
     *
     * @return List of additional disease identifiers
     */
    public List<String> getAdditionalDiseases() { return additionalDiseases; }

    /**
     * Sets the list of patient's additional diseases.
     *
     * @param additionalDiseases List of additional disease identifiers
     */
    public void setAdditionalDiseases(List<String> additionalDiseases) { this.additionalDiseases = additionalDiseases; }

    /**
     * Returns the list of surgery contraindications.
     *
     * @return List of contraindication identifiers
     */
    public List<String> getContraindications() { return contraindications; }

    /**
     * Sets the list of surgery contraindications.
     *
     * @param contraindications List of contraindication identifiers
     */
    public void setContraindications(List<String> contraindications) { this.contraindications = contraindications; }

    /**
     * Returns free-text description of other diseases.
     *
     * @return Other diseases description
     */
    public String getOtherDiseases() { return otherDiseases; }

    /**
     * Sets free-text description of other diseases.
     *
     * @param otherDiseases Other diseases description
     */
    public void setOtherDiseases(String otherDiseases) { this.otherDiseases = otherDiseases; }

    /**
     * Returns the patient's maximum historical weight.
     *
     * @return Maximum weight in kilograms
     */
    public int getMaxWeight() { return maxWeight; }

    /**
     * Sets the patient's maximum historical weight.
     *
     * @param maxWeight Maximum weight in kilograms
     */
    public void setMaxWeight(int maxWeight) { this.maxWeight = maxWeight; }

    /**
     * Returns the number of years the patient has been obese.
     *
     * @return Years of obesity
     */
    public int getObesityYears() { return obesityYears; }

    /**
     * Sets the number of years the patient has been obese.
     *
     * @param obesityYears Years of obesity
     */
    public void setObesityYears(int obesityYears) { this.obesityYears = obesityYears; }

    /**
     * Returns the list of previous obesity treatments attempted.
     *
     * @return List of treatment identifiers
     */
    public List<String> getPreviousTreatments() { return previousTreatments; }

    /**
     * Sets the list of previous obesity treatments attempted.
     *
     * @param previousTreatments List of treatment identifiers
     */
    public void setPreviousTreatments(List<String> previousTreatments) { this.previousTreatments = previousTreatments; }

    /**
     * Returns whether the patient takes chronic medications.
     *
     * @return True if taking chronic medications, false otherwise
     */
    public boolean isChronicMedication() { return chronicMedication; }

    /**
     * Sets whether the patient takes chronic medications.
     *
     * @param chronicMedication True if taking chronic medications, false otherwise
     */
    public void setChronicMedication(boolean chronicMedication) { this.chronicMedication = chronicMedication; }

    /**
     * Returns details about chronic medications.
     *
     * @return Medication details description
     */
    public String getMedicationDetails() { return medicationDetails; }

    /**
     * Sets details about chronic medications.
     *
     * @param medicationDetails Medication details description
     */
    public void setMedicationDetails(String medicationDetails) { this.medicationDetails = medicationDetails; }

    /**
     * Returns whether the patient engages in physical activity.
     *
     * @return True if physically active, false otherwise
     */
    public boolean isPhysicalActivity() { return physicalActivity; }

    /**
     * Sets whether the patient engages in physical activity.
     *
     * @param physicalActivity True if physically active, false otherwise
     */
    public void setPhysicalActivity(boolean physicalActivity) { this.physicalActivity = physicalActivity; }

    /**
     * Returns whether the patient follows healthy eating habits.
     *
     * @return True if eating healthy, false otherwise
     */
    public boolean isHealthyEating() { return healthyEating; }

    /**
     * Sets whether the patient follows healthy eating habits.
     *
     * @param healthyEating True if eating healthy, false otherwise
     */
    public void setHealthyEating(boolean healthyEating) { this.healthyEating = healthyEating; }

    /**
     * Returns whether the patient consumes processed foods.
     *
     * @return True if consuming processed foods, false otherwise
     */
    public boolean isProcessedFood() { return processedFood; }

    /**
     * Sets whether the patient consumes processed foods.
     *
     * @param processedFood True if consuming processed foods, false otherwise
     */
    public void setProcessedFood(boolean processedFood) { this.processedFood = processedFood; }

    /**
     * Returns the compulsive eating indicator value.
     * Values: 0 = Yes, 1 = No, 2 = Don't know
     *
     * @return Compulsive eating indicator
     */
    public int getCompulsiveEating() { return compulsiveEating; }

    /**
     * Sets the compulsive eating indicator value.
     * Values: 0 = Yes, 1 = No, 2 = Don't know
     *
     * @param compulsiveEating Compulsive eating indicator
     */
    public void setCompulsiveEating(int compulsiveEating) { this.compulsiveEating = compulsiveEating; }

    /**
     * Returns whether the patient consumes alcohol.
     *
     * @return True if consuming alcohol, false otherwise
     */
    public boolean isAlcoholConsumption() { return alcoholConsumption; }

    /**
     * Sets whether the patient consumes alcohol.
     *
     * @param alcoholConsumption True if consuming alcohol, false otherwise
     */
    public void setAlcoholConsumption(boolean alcoholConsumption) { this.alcoholConsumption = alcoholConsumption; }

    /**
     * Returns whether the patient smokes.
     *
     * @return True if smoking, false otherwise
     */
    public boolean isSmoking() { return smoking; }

    /**
     * Sets whether the patient smokes.
     *
     * @param smoking True if smoking, false otherwise
     */
    public void setSmoking(boolean smoking) { this.smoking = smoking; }

    /**
     * Returns whether the patient has suicidal thoughts.
     *
     * @return True if having suicidal thoughts, false otherwise
     */
    public boolean isSuicidalThoughts() { return suicidalThoughts; }

    /**
     * Sets whether the patient has suicidal thoughts.
     *
     * @param suicidalThoughts True if having suicidal thoughts, false otherwise
     */
    public void setSuicidalThoughts(boolean suicidalThoughts) { this.suicidalThoughts = suicidalThoughts; }

    /**
     * Returns whether the patient receives psychiatric support.
     *
     * @return True if receiving psychiatric support, false otherwise
     */
    public boolean isPsychiatristSupport() { return psychiatristSupport; }

    /**
     * Sets whether the patient receives psychiatric support.
     *
     * @param psychiatristSupport True if receiving psychiatric support, false otherwise
     */
    public void setPsychiatristSupport(boolean psychiatristSupport) { this.psychiatristSupport = psychiatristSupport; }

    /**
     * Returns the psychiatric diagnosis details.
     *
     * @return Psychiatric diagnosis description
     */
    public String getPsychiatricDiagnosis() { return psychiatricDiagnosis; }

    /**
     * Sets the psychiatric diagnosis details.
     *
     * @param psychiatricDiagnosis Psychiatric diagnosis description
     */
    public void setPsychiatricDiagnosis(String psychiatricDiagnosis) { this.psychiatricDiagnosis = psychiatricDiagnosis; }

    /**
     * Returns whether the patient receives psychological support.
     *
     * @return True if receiving psychological support, false otherwise
     */
    public boolean isPsychologistSupport() { return psychologistSupport; }

    /**
     * Sets whether the patient receives psychological support.
     *
     * @param psychologistSupport True if receiving psychological support, false otherwise
     */
    public void setPsychologistSupport(boolean psychologistSupport) { this.psychologistSupport = psychologistSupport; }

    /**
     * Returns the list of diseases in patient's family history.
     *
     * @return List of family disease identifiers
     */
    public List<String> getFamilyDiseases() { return familyDiseases; }

    /**
     * Sets the list of diseases in patient's family history.
     *
     * @param familyDiseases List of family disease identifiers
     */
    public void setFamilyDiseases(List<String> familyDiseases) { this.familyDiseases = familyDiseases; }

    /**
     * Returns whether the patient has had abdominal surgeries.
     *
     * @return True if had abdominal surgeries, false otherwise
     */
    public boolean isAbdomenSurgeries() { return abdomenSurgeries; }

    /**
     * Sets whether the patient has had abdominal surgeries.
     *
     * @param abdomenSurgeries True if had abdominal surgeries, false otherwise
     */
    public void setAbdomenSurgeries(boolean abdomenSurgeries) { this.abdomenSurgeries = abdomenSurgeries; }

    /**
     * Returns details about previous surgeries.
     *
     * @return Surgery details description
     */
    public String getSurgeriesDetails() { return surgeriesDetails; }

    /**
     * Sets details about previous surgeries.
     *
     * @param surgeriesDetails Surgery details description
     */
    public void setSurgeriesDetails(String surgeriesDetails) { this.surgeriesDetails = surgeriesDetails; }

    /**
     * Returns whether the patient is under specialist clinic care.
     *
     * @return True if under specialist care, false otherwise
     */
    public boolean isSpecialistClinic() { return specialistClinic; }

    /**
     * Sets whether the patient is under specialist clinic care.
     *
     * @param specialistClinic True if under specialist care, false otherwise
     */
    public void setSpecialistClinic(boolean specialistClinic) { this.specialistClinic = specialistClinic; }

    /**
     * Returns the type of specialist clinic.
     *
     * @return Clinic type description
     */
    public String getClinicType() { return clinicType; }

    /**
     * Sets the type of specialist clinic.
     *
     * @param clinicType Clinic type description
     */
    public void setClinicType(String clinicType) { this.clinicType = clinicType; }

    /**
     * Returns the calculated BMI value.
     *
     * @return BMI value
     */
    public double getBmi() { return bmi; }

    /**
     * Sets the calculated BMI value.
     *
     * @param bmi BMI value
     */
    public void setBmi(double bmi) { this.bmi = bmi; }

    /**
     * Returns the current survey status.
     *
     * @return Status string
     */
    public String getStatus() { return status; }

    /**
     * Sets the current survey status.
     *
     * @param status Status string
     */
    public void setStatus(String status) { this.status = status; }

    /**
     * Returns the survey date.
     *
     * @return Date in ISO format
     */
    public String getDate() { return date; }

    /**
     * Sets the survey date.
     *
     * @param date Date in ISO format
     */
    public void setDate(String date) { this.date = date; }

    /**
     * Returns the survey submission date.
     *
     * @return Submission date in ISO format
     */
    public String getSubmissionDate() { return submissionDate; }

    /**
     * Sets the survey submission date.
     *
     * @param submissionDate Submission date in ISO format
     */
    public void setSubmissionDate(String submissionDate) { this.submissionDate = submissionDate; }
}