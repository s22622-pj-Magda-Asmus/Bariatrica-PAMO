//package com.example.bariatric_mobile.adapters;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.bariatric_mobile.R;
//import com.example.bariatric_mobile.models.patient.Patient;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {
//
//    public interface OnDetailsClickListener {
//        void onDetailsClick(Patient patient);
//    }
//
//    private final List<Patient> originalPatients;  // pełna lista
//    private List<Patient> filteredPatients;        // lista filtrowana
//    private final OnDetailsClickListener listener;
//
//    public PatientAdapter(List<Patient> patients, OnDetailsClickListener listener) {
//        this.originalPatients = new ArrayList<>(patients);
//        this.filteredPatients = new ArrayList<>(patients);
//        this.listener = listener;
//    }
//
//    public static class PatientViewHolder extends RecyclerView.ViewHolder {
//        TextView codeTextView, dateTextView, statusTextView;
//        View detailsButton;
//
//        public PatientViewHolder(@NonNull View itemView) {
//            super(itemView);
//            codeTextView = itemView.findViewById(R.id.patient_code);
//            dateTextView = itemView.findViewById(R.id.fill_date);
//            statusTextView = itemView.findViewById(R.id.status);
//            detailsButton = itemView.findViewById(R.id.details_button);
//        }
//    }
//
//    @NonNull
//    @Override
//    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_patient, parent, false);
//        return new PatientViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
//        Patient patient = filteredPatients.get(position);
//
//        holder.codeTextView.setText(patient.getCode());
//        holder.dateTextView.setText(patient.getDate());
//
//        String status = patient.getStatus();
//        if (status != null && status.equalsIgnoreCase("NOWA")) {
//            holder.statusTextView.setVisibility(View.VISIBLE);
//            holder.statusTextView.setText(status);
//        } else {
//            holder.statusTextView.setVisibility(View.GONE);
//        }
//
//        holder.detailsButton.setOnClickListener(v -> listener.onDetailsClick(patient));
//    }
//
//    @Override
//    public int getItemCount() {
//        return filteredPatients.size();
//    }
//
//    // Wyszukiwanie po kodzie pacjenta
//    public void filterByCode(String query) {
//        if (query == null || query.trim().isEmpty()) {
//            filteredPatients = new ArrayList<>(originalPatients);
//        } else {
//            String lowerQuery = query.toLowerCase();
//            List<Patient> filteredList = new ArrayList<>();
//            for (Patient patient : originalPatients) {
//                if (patient.getCode().toLowerCase().contains(lowerQuery)) {
//                    filteredList.add(patient);
//                }
//            }
//            filteredPatients = filteredList;
//        }
//        notifyDataSetChanged();
//    }
//}
//
package com.example.bariatric_mobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bariatric_mobile.R;
import com.example.bariatric_mobile.models.patient.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {

    public interface OnDetailsClickListener {
        void onDetailsClick(Patient patient);
    }

    private List<Patient> patients = new ArrayList<>();
    private final OnDetailsClickListener listener;

    public PatientAdapter(List<Patient> patients, OnDetailsClickListener listener) {
        this.patients = new ArrayList<>(patients);
        this.listener = listener;
    }

    public static class PatientViewHolder extends RecyclerView.ViewHolder {
        TextView codeTextView, dateTextView, statusTextView;
        View detailsButton;

        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);
            codeTextView = itemView.findViewById(R.id.patient_code);
            dateTextView = itemView.findViewById(R.id.fill_date);
            statusTextView = itemView.findViewById(R.id.status);
            detailsButton = itemView.findViewById(R.id.details_button);
        }
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_patient, parent, false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        Patient patient = patients.get(position);

        holder.codeTextView.setText(patient.getCode());
        holder.dateTextView.setText(patient.getDate());

        String status = patient.getStatus();
        if (status != null && status.equalsIgnoreCase("NOWA")) {
            holder.statusTextView.setVisibility(View.VISIBLE);
            holder.statusTextView.setText(status);
        } else {
            holder.statusTextView.setVisibility(View.GONE);
        }

        holder.detailsButton.setOnClickListener(v -> listener.onDetailsClick(patient));
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    // 🔄 Metoda do odświeżania danych (np. przy zmianie strony)
    public void updateData(List<Patient> newPatients) {
        this.patients = new ArrayList<>(newPatients);
        notifyDataSetChanged();
    }
}
