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

/**
 * RecyclerView Adapter used to display a list of Patient objects
 * in the doctor's dashboard. Supports a click listener for patient details.
 */
public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {

    /**
     * Callback triggered when the "details" button is clicked.
     */
    public interface OnDetailsClickListener {
        void onDetailsClick(Patient patient);
    }

    private List<Patient> patients = new ArrayList<>();
    private final OnDetailsClickListener listener;

    public PatientAdapter(List<Patient> patients, OnDetailsClickListener listener) {
        this.patients = new ArrayList<>(patients);
        this.listener = listener;
    }


    /**
     * ViewHolder class for binding patient data to UI components.
     */
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

    /**
     * Inflates the layout for each list item.
     *
     * @param parent   The parent ViewGroup.
     * @param viewType The view type.
     * @return A new instance of {@link PatientViewHolder}.
     */
    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_patient, parent, false);
        return new PatientViewHolder(view);
    }

    /**
     * Binds data to a ViewHolder for a specific position.
     *
     * @param holder   The ViewHolder.
     * @param position The position of the item in the list.
     */
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

    /**
     * Formats the ISO 8601 date string to "dd.mm.yyyy".
     *
     * @param isoDate The date in ISO 8601 format.
     * @return The formatted date string.
     */
    public String formatDate(String isoDate) {
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

    /**
     * Returns the number of patients in the list.
     *
     * @return Total number of items in the adapter.
     */
    @Override
    public int getItemCount() {
        return patients.size();
    }

    /**
     * Updates the data in the adapter and refreshes the RecyclerView.
     *
     * @param newPatients The new list of patients to display.
     */
    public void updateData(List<Patient> newPatients) {
        this.patients = new ArrayList<>(newPatients);
        notifyDataSetChanged();
    }
}
