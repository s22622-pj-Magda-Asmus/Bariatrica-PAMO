package com.example.bariatric_mobile;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.os.SystemClock;
import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.bariatric_mobile.activities.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PatientDetailsInstrumentedTest {

    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

    private void openPatientDetails(String code) {
        onView(withId(R.id.search_input)).perform(click(), replaceText(code), closeSoftKeyboard());
        waitUntilDisplayed(withId(R.id.details_button), 5000);
        onView(withId(R.id.details_button)).perform(scrollTo(), click());
        waitUntilDisplayed(withId(R.id.details_view), 10000);
    }

    public static void waitUntilDisplayed(Matcher<View> matcher, long timeoutMillis) {
        long start = System.currentTimeMillis();
        long end = start + timeoutMillis;
        while (System.currentTimeMillis() < end) {
            try {
                onView(matcher).check(matches(isDisplayed()));
                return;
            } catch (Exception ignored) {
                SystemClock.sleep(100);
            }
        }
        throw new AssertionError("View " + matcher.toString() + " not displayed within timeout");
    }

    @Test
    public void fr8_patientDetailsShowCompleteSurveyData() {
        openPatientDetails("171");

        onView(withId(R.id.details_gender)).check(matches(isDisplayed()));
        onView(withId(R.id.details_birthdate)).check(matches(isDisplayed()));
        onView(withId(R.id.details_height)).check(matches(isDisplayed()));
        onView(withId(R.id.details_max_weight)).check(matches(isDisplayed()));
        onView(withId(R.id.details_obesity_years)).check(matches(isDisplayed()));
        onView(withId(R.id.details_bmi)).check(matches(isDisplayed()));
    }

    @Test
    public void fr10_patientDetailsWeightLossChartDisplayed() {
        openPatientDetails("171");

        onView(withId(R.id.chart_webview)).perform(scrollTo());
        waitUntilDisplayed(withId(R.id.chart_webview), 5000);
        onView(withId(R.id.chart_webview)).check(matches(isDisplayed()));
    }

    @Test
    public void fr11_returnToPatientListFromDetails() {
        openPatientDetails("171");

        onView(withId(R.id.back_to_list)).perform(click());
        waitUntilDisplayed(withId(R.id.patient_recycler_view), 5000);
        onView(withId(R.id.patient_recycler_view)).check(matches(isDisplayed()));
    }
}
