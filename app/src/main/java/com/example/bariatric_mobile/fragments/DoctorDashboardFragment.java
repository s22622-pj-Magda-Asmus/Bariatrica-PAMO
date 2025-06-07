package com.example.bariatric_mobile.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bariatric_mobile.R;
import com.example.bariatric_mobile.adapters.PatientAdapter;
import com.example.bariatric_mobile.models.patient.Patient;
import com.example.bariatric_mobile.services.network.PatientApiService;
import com.example.bariatric_mobile.services.network.ApiClient;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Fragment representing the doctor's dashboard, responsible for displaying
 * a list of patients with pagination and search functionality.
 *
 * Uses {@link PatientAdapter} to present patient data in a RecyclerView and
 * {@link PatientApiService} to fetch data from a remote API.
 */
public class DoctorDashboardFragment extends Fragment {

    private RecyclerView recyclerView;
    private PatientAdapter adapter;
    private List<Patient> fullPatientList = new ArrayList<>();
    private List<Patient> filteredPatientList = new ArrayList<>();

    private PatientApiService patientApiService;

    private TextView paginationInfo;
    private Button nextPageButton, prevPageButton;

    private int currentPage = 1;
    private int itemsPerPage = 10;
    private int totalPages = 1;

    /**
     * Default constructor. Sets the layout resource for this fragment.
     */
    public DoctorDashboardFragment() {
        super(R.layout.fragment_doctor_dashboard);
    }

    /**
     * Called when the view has been created. Initializes UI components,
     * sets up listeners, and starts fetching patient data from the API.
     *
     * @param view               The fragment's root view.
     * @param savedInstanceState A Bundle containing saved state (if any).
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.patient_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        paginationInfo = view.findViewById(R.id.pagination_info);
        nextPageButton = view.findViewById(R.id.next_page);
        prevPageButton = view.findViewById(R.id.prev_page);

        adapter = new PatientAdapter(new ArrayList<>(), patient ->
                Toast.makeText(requireContext(), "Szczegóły: " + patient.getCode(), Toast.LENGTH_SHORT).show()
        );
        recyclerView.setAdapter(adapter);

        patientApiService = ApiClient.getPatientService();
        fetchPatientsFromApi();

        nextPageButton.setOnClickListener(v -> {
            if (currentPage < totalPages) {
                currentPage++;
                adapter.updateData(getPatientsForPage(currentPage));
                updatePaginationInfo();
            }
        });

        prevPageButton.setOnClickListener(v -> {
            if (currentPage > 1) {
                currentPage--;
                adapter.updateData(getPatientsForPage(currentPage));
                updatePaginationInfo();
            }
        });

        TextInputEditText searchInput = view.findViewById(R.id.search_input);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim().toLowerCase();
                filteredPatientList = fullPatientList.stream()
                        .filter(p -> p.getCode() != null && p.getCode().toLowerCase().contains(query))
                        .collect(Collectors.toList());

                currentPage = 1;
                calculateTotalPages();
                adapter.updateData(getPatientsForPage(currentPage));
                updatePaginationInfo();
            }
        });
    }

    /**
     * Fetches the list of patients from the backend API and updates the UI accordingly.
     * In case of failure, displays an error message.
     */
    private void fetchPatientsFromApi() {
        patientApiService.getPatients().enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(@NonNull Call<List<Patient>> call, @NonNull Response<List<Patient>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fullPatientList = response.body();
                    if (fullPatientList == null) fullPatientList = new ArrayList<>();
                    filteredPatientList = new ArrayList<>(fullPatientList);

                    calculateTotalPages();
                    adapter.updateData(getPatientsForPage(currentPage));
                    updatePaginationInfo();
                } else {
                    Toast.makeText(requireContext(), "Błąd ładowania danych z serwera", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Patient>> call, @NonNull Throwable t) {
                Toast.makeText(requireContext(), "Błąd połączenia: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Calculates the total number of pages based on the size of the filtered patient list.
     * Ensures at least one page is available.
     */
    private void calculateTotalPages() {
        totalPages = (int) Math.ceil((double) filteredPatientList.size() / itemsPerPage);
        if (totalPages == 0) totalPages = 1;
    }

    /**
     * Returns a sublist of patients to be displayed on the specified page.
     *
     * @param page The page number to fetch.
     * @return A list of patients corresponding to that page.
     */
    private List<Patient> getPatientsForPage(int page) {
        int start = (page - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage, filteredPatientList.size());
        if (start >= end) return new ArrayList<>();
        return filteredPatientList.subList(start, end);
    }

    /**
     * Updates the pagination info text to reflect the current page and total pages.
     */
    private void updatePaginationInfo() {
        String pageText = getString(R.string.page);
        String fromText = getString(R.string.from);
        paginationInfo.setText(pageText + " " + currentPage + " " + fromText + " " + totalPages);
    }
}
