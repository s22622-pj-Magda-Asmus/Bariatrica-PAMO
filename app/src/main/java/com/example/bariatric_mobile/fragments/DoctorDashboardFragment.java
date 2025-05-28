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
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DoctorDashboardFragment extends Fragment {

    private RecyclerView recyclerView;
    private PatientAdapter adapter;
    private List<Patient> fullPatientList;
    private List<Patient> filteredPatientList;

    private TextView paginationInfo;
    private Button nextPageButton, prevPageButton;

    private int currentPage = 1;
    private int itemsPerPage = 10;
    private int totalPages = 1;

    public DoctorDashboardFragment() {
        super(R.layout.fragment_doctor_dashboard);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.patient_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        paginationInfo = view.findViewById(R.id.pagination_info);
        nextPageButton = view.findViewById(R.id.next_page);
        prevPageButton = view.findViewById(R.id.prev_page);

        // Dane testowe
        fullPatientList = Arrays.asList(
                new Patient("1670931", "25.05.2025", "NOWA"),
                new Patient("1670932", "24.05.2025", "NOWA"),
                new Patient("1670933", "23.05.2025", "STARA"),
                new Patient("1670934", "22.05.2025", "NOWA"),
                new Patient("1670935", "21.05.2025", "STARA"),
                new Patient("1670936", "20.05.2025", "NOWA"),
                new Patient("1670937", "19.05.2025", "NOWA"),
                new Patient("1670938", "18.05.2025", "STARA"),
                new Patient("1670939", "17.05.2025", "NOWA"),
                new Patient("1670940", "16.05.2025", "STARA"),
                new Patient("1670941", "15.05.2025", "NOWA"),
                new Patient("1670942", "14.05.2025", "NOWA")
        );

        filteredPatientList = new ArrayList<>(fullPatientList);
        calculateTotalPages();

        adapter = new PatientAdapter(getPatientsForPage(currentPage), patient ->
                Toast.makeText(requireContext(), "Szczegóły: " + patient.getCode(), Toast.LENGTH_SHORT).show()
        );
        recyclerView.setAdapter(adapter);
        updatePaginationInfo();

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
                        .filter(p -> p.getCode().toLowerCase().contains(query))
                        .collect(Collectors.toList());

                currentPage = 1;
                calculateTotalPages();
                adapter.updateData(getPatientsForPage(currentPage));
                updatePaginationInfo();
            }
        });
    }

    private void calculateTotalPages() {
        totalPages = (int) Math.ceil((double) filteredPatientList.size() / itemsPerPage);
        if (totalPages == 0) totalPages = 1;
    }

    private List<Patient> getPatientsForPage(int page) {
        int start = (page - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage, filteredPatientList.size());
        if (start >= end) return new ArrayList<>();
        return filteredPatientList.subList(start, end);
    }

    private void updatePaginationInfo() {
        String pageText = getString(R.string.page);
        String fromText = getString(R.string.from);
        paginationInfo.setText(pageText + " " + currentPage + " " + fromText + " " + totalPages);

    }
}
