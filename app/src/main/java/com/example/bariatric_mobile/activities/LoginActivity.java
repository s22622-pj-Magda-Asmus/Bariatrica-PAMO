package com.example.bariatric_mobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.bariatric_mobile.R;
import com.example.bariatric_mobile.viewmodels.LoginViewModel;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Activity for user authentication and login functionality.
 *
 * Provides login form with username/email and password fields,
 * handles user input validation, displays authentication progress,
 * and manages navigation to dashboard on successful login.
 */
public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;
    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;
    private Button loginButton;
    private ProgressBar progressBar;
    private LinearLayout errorAlertLayout;
    private TextView errorTextView;

    /**
     * Called when the activity is first created.
     * Initializes views, ViewModel, observers, and checks for existing authentication.
     *
     * @param savedInstanceState Bundle containing saved state data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViews();
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        setupObservers();
        setupListeners();
        if (viewModel.isAlreadyAuthenticated()) {
            navigateToDashboard();
        }
    }

    /**
     * Initializes all UI components and finds views by ID.
     */
    private void initializeViews() {
        usernameEditText = findViewById(R.id.login_username);
        passwordEditText = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        progressBar = findViewById(R.id.login_progress);
        errorAlertLayout = findViewById(R.id.login_error_alert);
        errorTextView = findViewById(R.id.login_error_text);
    }

    /**
     * Sets up LiveData observers for authentication state changes.
     * Observes loading state, error messages, and login success.
     */
    private void setupObservers() {
        viewModel.getIsLoading().observe(this, isLoading -> {
            updateLoadingState(isLoading != null ? isLoading : false);
        });

        viewModel.getError().observe(this, error -> {
            if (error != null && !error.isEmpty()) {
                showErrorAlert(error);
            } else {
                hideErrorAlert();
            }
        });

        viewModel.getLoginSuccess().observe(this, success -> {
            if (success != null && success) {
                navigateToDashboard();
            }
        });
    }

    /**
     * Sets up click listeners and text change watchers for user input.
     * Connects UI interactions with ViewModel methods.
     */
    private void setupListeners() {
        loginButton.setOnClickListener(v -> {
            viewModel.login();
        });

        if (viewModel != null) {
            usernameEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    viewModel.setUsername(s.toString());
                }
            });

            passwordEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    viewModel.setPassword(s.toString());
                }
            });
        }
    }

    /**
     * Updates UI elements based on loading state.
     * Shows/hides progress indicator and disables/enables input fields.
     *
     * @param isLoading True if authentication request is in progress
     */
    private void updateLoadingState(boolean isLoading) {
        if (loginButton != null) {
            loginButton.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        }
        if (progressBar != null) {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        }
        if (usernameEditText != null) {
            usernameEditText.setEnabled(!isLoading);
        }
        if (passwordEditText != null) {
            passwordEditText.setEnabled(!isLoading);
        }
    }

    /**
     * Navigates to the main dashboard activity after successful authentication.
     * Clears activity stack to prevent returning to login screen.
     */
    private void navigateToDashboard() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Displays error message in the error alert layout.
     *
     * @param message Error message to display to user
     */
    private void showErrorAlert(String message) {
        if (errorTextView != null && errorAlertLayout != null) {
            errorTextView.setText(message);
            errorAlertLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Hides the error alert layout.
     */
    private void hideErrorAlert() {
        if (errorAlertLayout != null) {
            errorAlertLayout.setVisibility(View.GONE);
        }
    }

    /**
     * Called when the activity is being destroyed.
     * Performs cleanup operations.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}