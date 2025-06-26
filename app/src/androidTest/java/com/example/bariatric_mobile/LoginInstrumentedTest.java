package com.example.bariatric_mobile;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.bariatric_mobile.activities.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginInstrumentedTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> rule = new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void fr1_loginScreenIsDisplayed() {
        onView(withId(R.id.login_button)).check(matches(isDisplayed()));
        onView(withId(R.id.login_username)).check(matches(isDisplayed()));
        onView(withId(R.id.login_password)).check(matches(isDisplayed()));
    }

    @Test
    public void fr2_successfulLoginNavigatesToDashboard() throws InterruptedException {
        onView(withId(R.id.login_username)).perform(typeText("anna@szpital.pl"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText("HasloAnna123"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());
        Thread.sleep(1000);
        onView(withId(R.id.doctor_dashboard)).check(matches(isDisplayed()));
        onView(withId(R.id.logout_button)).perform(click());
    }

    @Test
    public void fr3_invalidLoginShowsError() {
        onView(withId(R.id.login_username)).perform(typeText("wrong"), closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText("wrong"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        onView(withText("401")).check(matches(isDisplayed()));
    }
}

