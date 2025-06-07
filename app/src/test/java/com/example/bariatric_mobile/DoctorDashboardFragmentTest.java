package com.example.bariatric_mobile;

import static org.junit.Assert.assertEquals;

import com.example.bariatric_mobile.fragments.DoctorDashboardFragment;
import com.example.bariatric_mobile.models.patient.Patient;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DoctorDashboardFragmentTest {

    private DoctorDashboardFragment fragment;

    @Before
    public void setUp() {
        fragment = new DoctorDashboardFragment();
    }

    @Test
    public void testCalculateTotalPages_shouldReturnCorrectPageCount() {
        List<Patient> mockPatients = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            mockPatients.add(new Patient());
        }

        fragment.setFilteredPatientList(mockPatients);
        fragment.setItemsPerPage(10);
        fragment.calculateTotalPagesPublic();

        assertEquals(3, fragment.getTotalPages());
    }

    @Test
    public void testGetPatientsForPage_shouldReturnCorrectSublist() {
        List<Patient> mockPatients = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            mockPatients.add(new Patient());
        }

        fragment.setFilteredPatientList(mockPatients);
        fragment.setItemsPerPage(10);

        List<Patient> page1 = fragment.getPatientsForPage(1);
        List<Patient> page3 = fragment.getPatientsForPage(3);
        List<Patient> page4 = fragment.getPatientsForPage(4);

        assertEquals(10, page1.size());
        assertEquals(5, page3.size());
        assertEquals(0, page4.size());
    }
}

