package com.example.bariatric_mobile.fragments;

import android.content.Intent;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bariatric_mobile.R;
import com.example.bariatric_mobile.activities.LoginActivity;
import com.example.bariatric_mobile.adapters.PatientAdapter;
import com.example.bariatric_mobile.models.patient.Patient;
import com.example.bariatric_mobile.viewmodels.DashboardViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment used as the main dashboard for doctors.
 * Displays a paginated list of patients, supports filtering, and handles logout.
 */
public class DoctorDashboardFragment extends Fragment {

    private PatientAdapter adapter;
    private DashboardViewModel viewModel;

    private TextView paginationInfo;
    private TextView userNameTextView;
    private Button nextPageButton, prevPageButton, logoutButton;
    private int currentPage = 1;
    private final int itemsPerPage = 10;
    public int totalPages = 1;

    /**
     * Default constructor which inflates the fragment_doctor_dashboard layout.
     */
    public DoctorDashboardFragment() {
        super(R.layout.fragment_doctor_dashboard);
    }

    /**
     * Called when the fragment's view has been created.
     * Initializes RecyclerView, ViewModel, observers, and listeners.
     *
     * @param view               The root view of the fragment.
     * @param savedInstanceState The saved instance state (if any).
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.patient_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        paginationInfo = view.findViewById(R.id.pagination_info);
        nextPageButton = view.findViewById(R.id.next_page);
        prevPageButton = view.findViewById(R.id.prev_page);
        logoutButton = view.findViewById(R.id.logout_button);
        userNameTextView = view.findViewById(R.id.user_name);

        adapter = new PatientAdapter(new ArrayList<>(), patient ->
                Toast.makeText(requireContext(), "Wybrano pacjenta: " + patient.getCode(), Toast.LENGTH_SHORT).show()
        );

        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        setupObservers();
        setupListeners(view);
        viewModel.loadPatients();
    }

    /**
     * Sets up observers for patients, error messages, and the current user.
     * Updates the UI when LiveData changes.
     */
    private void setupObservers() {
        viewModel.getPatients().observe(getViewLifecycleOwner(), patients -> {
            calculateTotalPages(patients);
            updatePage();
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show();
            }
        });

        viewModel.getCurrentUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null && userNameTextView != null) {
                userNameTextView.setText(user.getFullName());
            }
        });
    }

    /**
     * Attaches click listeners to UI buttons (pagination, logout) and search input field.
     *
     * @param view The root view of the fragment.
     */
    private void setupListeners(View view) {
        nextPageButton.setOnClickListener(v -> {
            if (currentPage < totalPages) {
                currentPage++;
                updatePage();
            }
        });

        prevPageButton.setOnClickListener(v -> {
            if (currentPage > 1) {
                currentPage--;
                updatePage();
            }
        });

        logoutButton.setOnClickListener(v -> {
            viewModel.logout();
            requireActivity().startActivity(new Intent(requireContext(), LoginActivity.class));
            requireActivity().finish();
        });

        TextInputEditText searchInput = view.findViewById(R.id.search_input);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentPage = 1;
                viewModel.filter(s.toString());
            }
        });
    }

    /**
     * Calculates how many pages are needed to display the full list of patients.
     *
     * @param list The list of patients to paginate.
     */
    public void calculateTotalPages(List<Patient> list) {
        int totalItems = list.size();
        totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
        if (totalPages == 0) totalPages = 1;
    }

    /**
     * Updates the displayed patient list for the current page and refreshes the pagination info.
     */
    private void updatePage() {
        List<Patient> pageData = viewModel.getPatientsForPage(currentPage, itemsPerPage);
        adapter.updateData(pageData);
        updatePaginationInfo();
    }

    /**
     * Updates the UI with the current page number and total number of pages.
     */
    private void updatePaginationInfo() {
        String pageText = getString(R.string.page);
        String fromText = getString(R.string.from);
        paginationInfo.setText(pageText + " " + currentPage + " " + fromText + " " + totalPages);
    }
}
