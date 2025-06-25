package com.example.bariatric_mobile.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
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

/**
 * ViewModel responsible for managing patient data and user session in the doctor's dashboard.
 * Provides support for data loading, searching, pagination, and logout actions.
 */
public class DashboardViewModel extends AndroidViewModel {

    private final PatientRepository patientRepository;
    private final AuthRepository authRepository;

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>(null);
    private final MutableLiveData<List<Patient>> patients = new MutableLiveData<>(new ArrayList<>());

    private List<Patient> fullList = new ArrayList<>();
    private List<Patient> filteredList = new ArrayList<>();

    /**
     * Creates a new instance of DashboardViewModel.
     *
     * @param application The application context used for repository access.
     */
    public DashboardViewModel(@NonNull Application application) {
        super(application);
        this.patientRepository = new PatientRepository();
        this.authRepository = new AuthRepository(application);
    }

    /**
     * @return LiveData indicating whether the data is currently loading.
     */
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    /**
     * @return LiveData with an error message in case of load failure.
     */
    public LiveData<String> getError() {
        return error;
    }

    /**
     * @return LiveData with the current (filtered) list of patients.
     */
    public LiveData<List<Patient>> getPatients() {
        return patients;
    }

    /**
     * @return LiveData with the currently logged-in user.
     */
    public LiveData<User> getCurrentUser() {
        return authRepository.getCurrentUser();
    }

    /**
     * Logs out the current user via the authentication repository.
     */
    public void logout() {
        authRepository.logout();
    }

    /**
     * Loads all patients from the repository and populates the LiveData.
     * Also handles loading and error state.
     */
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

    /**
     * Filters the full list of patients by query (matches patient code).
     *
     * @param query The search text entered by the user.
     */
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

    /**
     * @return The number of patients in the filtered list.
     */
    public int getTotalPatients() {
        return filteredList.size();
    }

    /**
     * Returns a sublist of patients for a specific page.
     *
     * @param page         The page number (1-based).
     * @param itemsPerPage The number of items per page.
     * @return A list of patients to be shown on the given page.
     */
    public List<Patient> getPatientsForPage(int page, int itemsPerPage) {
        int start = (page - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage, filteredList.size());
        if (start >= end) return new ArrayList<>();
        return filteredList.subList(start, end);
    }

    /**
     * Initializes the internal full and filtered patient lists with the provided data.
     * This method is intended for testing purposes and bypasses loading from repositories.
     *
     * @param patients The list of patients to initialize both full and filtered lists.
     */
    @VisibleForTesting
    public void setupPatientLists(List<Patient> patients) {
        this.fullList = new ArrayList<>(patients);
        this.filteredList = new ArrayList<>(patients);
    }

    /**
     * Constructor for testing that allows injecting a mock AuthRepository.
     * This avoids instantiating real repository instances during unit tests.
     *
     * @param application The application context (usually mocked).
     * @param authRepository The authentication repository to inject (usually mocked).
     */
    public DashboardViewModel(@NonNull Application application, @NonNull AuthRepository authRepository) {
        super(application);
        this.authRepository = authRepository;
        this.patientRepository = new PatientRepository();
    }

    /**
     * Filters the internal patient list using the provided query, without updating LiveData.
     * This method is intended for unit testing to avoid Android dependencies such as Looper.
     *
     * @param query The search text to filter patients by their code. If null or empty, all patients are included.
     */
    @VisibleForTesting
    public void filterWithoutLiveData(String query) {
        if (query == null || query.trim().isEmpty()) {
            filteredList = new ArrayList<>(fullList);
        } else {
            filteredList = fullList.stream()
                    .filter(p -> p.getCode() != null && p.getCode().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }
    }
}
