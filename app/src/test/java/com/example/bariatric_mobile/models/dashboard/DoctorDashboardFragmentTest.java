package com.example.bariatric_mobile.models.dashboard;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

import com.example.bariatric_mobile.fragments.DoctorDashboardFragment;
import com.example.bariatric_mobile.models.patient.Patient;

public class DoctorDashboardFragmentTest {
    @Test
    void calculateTotalPages_shouldHandleVariousCases() {
        DoctorDashboardFragment fragment = new DoctorDashboardFragment();

        List<Patient> zero = new ArrayList<>();
        List<Patient> five = Arrays.asList(new Patient(), new Patient(), new Patient(), new Patient(), new Patient());
        List<Patient> fifteen = new ArrayList<>();
        for (int i = 0; i < 15; i++) fifteen.add(new Patient());

        fragment.calculateTotalPages(zero);
        assertEquals(1, fragment.totalPages);

        fragment.calculateTotalPages(five);
        assertEquals(1, fragment.totalPages);

        fragment.calculateTotalPages(fifteen);
        assertEquals(2, fragment.totalPages);
    }

}
