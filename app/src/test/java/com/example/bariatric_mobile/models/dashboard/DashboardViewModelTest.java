package com.example.bariatric_mobile.models.dashboard;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import android.app.Application;

import com.example.bariatric_mobile.models.patient.Patient;
import com.example.bariatric_mobile.repositories.AuthRepository;
import com.example.bariatric_mobile.viewmodels.DashboardViewModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class DashboardViewModelTest {

    DashboardViewModel viewModel;

    @BeforeEach
    void setUp() {
        Application mockApp = mock(Application.class);
        AuthRepository mockAuthRepo = mock(AuthRepository.class);

        viewModel = new DashboardViewModel(mockApp, mockAuthRepo);

        List<Patient> mockPatients = Arrays.asList(
                createPatient("abc123"),
                createPatient("xyz789"),
                createPatient("test456")
        );

        viewModel.setupPatientLists(mockPatients);
    }

    private Patient createPatient(String code) {
        Patient patient = new Patient();
        patient.setCode(code);
        return patient;
    }

    @Test
    void filter_shouldReturnOneResult() {
        viewModel.filterWithoutLiveData("xyz");
        assertEquals(1, viewModel.getTotalPatients());
    }

    @Test
    void filter_withEmptyQuery_shouldReturnAll() {
        viewModel.filterWithoutLiveData("");
        assertEquals(3, viewModel.getTotalPatients());
    }

    @Test
    void getPatientsForPage_shouldReturnCorrectPatients() {
        List<Patient> page = viewModel.getPatientsForPage(1, 2);
        assertEquals(2, page.size());
        assertEquals("abc123", page.get(0).getCode());
    }

    @Test
    void getPatientsForPage_outOfBounds_shouldReturnEmpty() {
        List<Patient> page = viewModel.getPatientsForPage(5, 2);
        assertTrue(page.isEmpty());
    }
}

