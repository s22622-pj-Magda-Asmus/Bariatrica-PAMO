package com.example.bariatric_mobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bariatric_mobile.R;

/**
 * Splash screen activity displayed when the application starts.
 *
 * Shows the app logo and branding for a brief period before automatically
 * navigating to the login screen. Provides a smooth app launch experience.
 */
public class SplashActivity extends AppCompatActivity {

    /** Duration to display splash screen in milliseconds */
    private static final int SPLASH_DISPLAY_TIME = 2000;

    /**
     * Called when the activity is first created.
     * Sets up the splash screen layout and schedules navigation to login.
     *
     * @param savedInstanceState Bundle containing saved state data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }, SPLASH_DISPLAY_TIME);
    }
}