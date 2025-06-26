package com.example.bariatric_mobile.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bariatric_mobile.R;
import com.example.bariatric_mobile.activities.PatientDetailsActivity;
import com.example.bariatric_mobile.models.patient.Patient;
import com.example.bariatric_mobile.utils.DataFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView adapter used to display a list of patients.
 * Handles displaying patient information and navigation to patient details.
 */
public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {

    /**
     * Callback interface to handle patient detail button clicks.
     */
    public interface OnDetailsClickListener {
        /**
         * Called when the detail button is clicked for a specific patient.
         *
         * @param patient The selected patient.
         */
        void onDetailsClick(Patient patient);
    }

    private List<Patient> patients;
    private final OnDetailsClickListener listener;

    /**
     * Creates a new PatientAdapter.
     *
     * @param patients The list of patients to display.
     * @param listener The listener to handle detail button clicks.
     */
    public PatientAdapter(List<Patient> patients, OnDetailsClickListener listener) {
        this.patients = new ArrayList<>(patients);
        this.listener = listener;
    }

    /**
     * ViewHolder class representing each row in the patient list.
     */
    public static class PatientViewHolder extends RecyclerView.ViewHolder {
        final TextView codeTextView;
        final TextView dateTextView;
        final TextView statusTextView;
        final View detailsButton;

        /**
         * Creates a new PatientViewHolder.
         *
         * @param itemView The item view representing a patient.
         */
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
        holder.dateTextView.setText(DataFormatter.formatDate(patient.getSubmissionDate()));

        String status = patient.getStatus();
        if (status != null && status.equalsIgnoreCase("NOWA")) {
            holder.statusTextView.setVisibility(View.VISIBLE);
            holder.statusTextView.setText(status);
        } else {
            holder.statusTextView.setVisibility(View.GONE);
        }

        holder.detailsButton.setOnClickListener(v -> {
            Context context = v.getContext();

            if (patient.getStatus() != null && patient.getStatus().equalsIgnoreCase("NOWA")) {
                patient.setStatus("");
                notifyItemChanged(holder.getAdapterPosition());
            }

            Intent intent = new Intent(context, PatientDetailsActivity.class);
            intent.putExtra("patient_code", patient.getCode());
            intent.putExtra("submission_date", patient.getSubmissionDate());
            intent.putExtra("status", patient.getStatus());
            context.startActivity(intent);

            listener.onDetailsClick(patient);
        });
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    /**
     * Replaces the current patient list with a new one and refreshes the list.
     *
     * @param newPatients The new list of patients.
     */
    public void updateData(List<Patient> newPatients) {
        this.patients = new ArrayList<>(newPatients);
        notifyDataSetChanged();
    }
    /**
     * Sets the patient list without notifying the RecyclerView.
     * <p>
     * This method is intended for unit testing only. It bypasses the standard
     * {@link #updateData(List)} mechanism to avoid triggering UI updates that depend
     * on Android framework components (like RecyclerView observers).
     *
     * @param newPatients The list of patients to set internally in the adapter.
     */
    @VisibleForTesting
    public void setPatientListRaw(List<Patient> newPatients) {
        this.patients = new ArrayList<>(newPatients);
    }
}
