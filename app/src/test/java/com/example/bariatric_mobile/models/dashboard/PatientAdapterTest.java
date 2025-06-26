package com.example.bariatric_mobile.models.dashboard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.bariatric_mobile.adapters.PatientAdapter;
import com.example.bariatric_mobile.models.patient.Patient;

import org.junit.jupiter.api.Test;

import java.util.List;

public class PatientAdapterTest {

    @Test
    public void updateData_shouldReplaceList() {
        Patient patient1 = new Patient();
        List<Patient> originalList = List.of(patient1);
        PatientAdapter.OnDetailsClickListener listener = patient -> {};
        PatientAdapter adapter = new PatientAdapter(originalList, listener);

        assertEquals(1, adapter.getItemCount());

        Patient patient2 = new Patient();
        Patient patient3 = new Patient();
        List<Patient> newList = List.of(patient2, patient3);
        adapter.setPatientListRaw(newList);


        assertEquals(2, adapter.getItemCount());
    }
}
