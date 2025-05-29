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
        holder.dateTextView.setText(formatDate(patient.getSubmissionDate()));

        String status = patient.getStatus();
        if (status != null && status.equalsIgnoreCase("NOWA")) {
            holder.statusTextView.setVisibility(View.VISIBLE);
            holder.statusTextView.setText(status);
        } else {
            holder.statusTextView.setVisibility(View.GONE);
        }

        holder.detailsButton.setOnClickListener(v -> listener.onDetailsClick(patient));
    }

    private String formatDate(String isoDate) {
        if (isoDate == null || isoDate.isEmpty()) return "";
        try {
            java.text.SimpleDateFormat inputFormat = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            java.text.SimpleDateFormat outputFormat = new java.text.SimpleDateFormat("dd.MM.yyyy");
            java.util.Date date = inputFormat.parse(isoDate);
            return outputFormat.format(date);
        } catch (Exception e) {
            return isoDate;
        }
    }


    @Override
    public int getItemCount() {
        return patients.size();
    }
    public void updateData(List<Patient> newPatients) {
        this.patients = new ArrayList<>(newPatients);
        notifyDataSetChanged();
    }
}
