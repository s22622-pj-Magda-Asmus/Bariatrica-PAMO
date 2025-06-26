package com.example.bariatric_mobile;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.bariatric_mobile.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DashboardInstrumentedTest {

    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void fr4_patientTableIsDisplayedWithColumns() {
        onView(withId(R.id.doctor_dashboard)).check(matches(isDisplayed()));
        onView(withText("KOD PACJENTA")).check(matches(isDisplayed()));
        onView(withText("DATA WYPEŁNIENIA")).check(matches(isDisplayed()));
        onView(withText("STATUS")).check(matches(isDisplayed()));
        onView(withText("AKCJE")).check(matches(isDisplayed()));
    }

    @Test
    public void fr5_searchFiltersList() {
        onView(withId(R.id.search_input)).perform(click(), replaceText("167"), closeSoftKeyboard());
        onView(withId(R.id.patient_code)).check(matches(withText("1670933")));
    }

    @Test
    public void fr7_clickPatientOpensDetails() {
        onView(withId(R.id.search_input)).perform(click(), replaceText("171"), closeSoftKeyboard());
        onView(withId(R.id.button_details)).perform(click());
        onView(withId(R.id.details_view)).check(matches(isDisplayed()));
    }
}

