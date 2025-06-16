package com.example.bariatric_mobile.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bariatric_mobile.models.auth.User;
import com.example.bariatric_mobile.models.patient.Patient;
import com.example.bariatric_mobile.repositories.AuthRepository;
import com.example.bariatric_mobile.repositories.PatientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardViewModel extends AndroidViewModel {

    private final PatientRepository patientRepository;
    private final AuthRepository authRepository;

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>(null);
    private final MutableLiveData<List<Patient>> patients = new MutableLiveData<>(new ArrayList<>());

    private List<Patient> fullList = new ArrayList<>();
    private List<Patient> filteredList = new ArrayList<>();

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        this.patientRepository = new PatientRepository();
        this.authRepository = new AuthRepository(application); // ✅ podajemy kontekst
    }

    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getError() { return error; }
    public LiveData<List<Patient>> getPatients() { return patients; }
    public LiveData<User> getCurrentUser() { return authRepository.getCurrentUser(); }
    public void logout() { authRepository.logout(); }

    public void loadPatients() {
        isLoading.setValue(true);
        patientRepository.fetchAllPatients(new PatientRepository.PatientListCallback() {
            @Override
            public void onSuccess(List<Patient> result) {
                isLoading.postValue(false);
                error.postValue(null);

                fullList = result != null ? result : new ArrayList<>();
                filteredList = new ArrayList<>(fullList);
                patients.postValue(filteredList);
            }

            @Override
            public void onError(String message) {
                isLoading.postValue(false);
                error.postValue(message);
            }
        });
    }

    public void filter(String query) {
        if (query == null || query.trim().isEmpty()) {
            filteredList = new ArrayList<>(fullList);
        } else {
            filteredList = fullList.stream()
                    .filter(p -> p.getCode() != null &&
                            p.getCode().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }
        patients.setValue(filteredList);
    }

    public int getTotalPatients() {
        return filteredList.size();
    }

    public List<Patient> getPatientsForPage(int page, int itemsPerPage) {
        int start = (page - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage, filteredList.size());
        if (start >= end) return new ArrayList<>();
        return filteredList.subList(start, end);
    }
}
