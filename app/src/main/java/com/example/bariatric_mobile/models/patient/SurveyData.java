package com.example.bariatric_mobile.models.patient;

import java.util.List;

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

    // Constructors
    public SurveyData() {}

    // Getters and Setters
    public String getPatientCode() { return patientCode; }
    public void setPatientCode(String patientCode) { this.patientCode = patientCode; }

    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getReferralType() { return referralType; }
    public void setReferralType(String referralType) { this.referralType = referralType; }

    public String getReferralPIN() { return referralPIN; }
    public void setReferralPIN(String referralPIN) { this.referralPIN = referralPIN; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public boolean isConsiderSurgery() { return considerSurgery; }
    public void setConsiderSurgery(boolean considerSurgery) { this.considerSurgery = considerSurgery; }

    public List<String> getDiseases() { return diseases; }
    public void setDiseases(List<String> diseases) { this.diseases = diseases; }

    public List<String> getAdditionalDiseases() { return additionalDiseases; }
    public void setAdditionalDiseases(List<String> additionalDiseases) { this.additionalDiseases = additionalDiseases; }

    public List<String> getContraindications() { return contraindications; }
    public void setContraindications(List<String> contraindications) { this.contraindications = contraindications; }

    public String getOtherDiseases() { return otherDiseases; }
    public void setOtherDiseases(String otherDiseases) { this.otherDiseases = otherDiseases; }

    public int getMaxWeight() { return maxWeight; }
    public void setMaxWeight(int maxWeight) { this.maxWeight = maxWeight; }

    public int getObesityYears() { return obesityYears; }
    public void setObesityYears(int obesityYears) { this.obesityYears = obesityYears; }

    public List<String> getPreviousTreatments() { return previousTreatments; }
    public void setPreviousTreatments(List<String> previousTreatments) { this.previousTreatments = previousTreatments; }

    public boolean isChronicMedication() { return chronicMedication; }
    public void setChronicMedication(boolean chronicMedication) { this.chronicMedication = chronicMedication; }

    public String getMedicationDetails() { return medicationDetails; }
    public void setMedicationDetails(String medicationDetails) { this.medicationDetails = medicationDetails; }

    public boolean isPhysicalActivity() { return physicalActivity; }
    public void setPhysicalActivity(boolean physicalActivity) { this.physicalActivity = physicalActivity; }

    public boolean isHealthyEating() { return healthyEating; }
    public void setHealthyEating(boolean healthyEating) { this.healthyEating = healthyEating; }

    public boolean isProcessedFood() { return processedFood; }
    public void setProcessedFood(boolean processedFood) { this.processedFood = processedFood; }

    public int getCompulsiveEating() { return compulsiveEating; }
    public void setCompulsiveEating(int compulsiveEating) { this.compulsiveEating = compulsiveEating; }

    public boolean isAlcoholConsumption() { return alcoholConsumption; }
    public void setAlcoholConsumption(boolean alcoholConsumption) { this.alcoholConsumption = alcoholConsumption; }

    public boolean isSmoking() { return smoking; }
    public void setSmoking(boolean smoking) { this.smoking = smoking; }

    public boolean isSuicidalThoughts() { return suicidalThoughts; }
    public void setSuicidalThoughts(boolean suicidalThoughts) { this.suicidalThoughts = suicidalThoughts; }

    public boolean isPsychiatristSupport() { return psychiatristSupport; }
    public void setPsychiatristSupport(boolean psychiatristSupport) { this.psychiatristSupport = psychiatristSupport; }

    public String getPsychiatricDiagnosis() { return psychiatricDiagnosis; }
    public void setPsychiatricDiagnosis(String psychiatricDiagnosis) { this.psychiatricDiagnosis = psychiatricDiagnosis; }

    public boolean isPsychologistSupport() { return psychologistSupport; }
    public void setPsychologistSupport(boolean psychologistSupport) { this.psychologistSupport = psychologistSupport; }

    public List<String> getFamilyDiseases() { return familyDiseases; }
    public void setFamilyDiseases(List<String> familyDiseases) { this.familyDiseases = familyDiseases; }

    public boolean isAbdomenSurgeries() { return abdomenSurgeries; }
    public void setAbdomenSurgeries(boolean abdomenSurgeries) { this.abdomenSurgeries = abdomenSurgeries; }

    public String getSurgeriesDetails() { return surgeriesDetails; }
    public void setSurgeriesDetails(String surgeriesDetails) { this.surgeriesDetails = surgeriesDetails; }

    public boolean isSpecialistClinic() { return specialistClinic; }
    public void setSpecialistClinic(boolean specialistClinic) { this.specialistClinic = specialistClinic; }

    public String getClinicType() { return clinicType; }
    public void setClinicType(String clinicType) { this.clinicType = clinicType; }

    public double getBmi() { return bmi; }
    public void setBmi(double bmi) { this.bmi = bmi; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(String submissionDate) { this.submissionDate = submissionDate; }
}