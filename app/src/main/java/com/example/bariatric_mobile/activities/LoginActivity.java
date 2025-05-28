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

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;

    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;
    private Button loginButton;
    private ProgressBar progressBar;
    private LinearLayout errorAlertLayout;
    private TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViews();
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        setupObservers();
        setupListeners();
        /// TODO: 28.05.2025 uncomment when dashboard will be done
//            if (viewModel.isAlreadyAuthenticated()) {
//                navigateToDashboard();
//            }
    }

    private void initializeViews() {
        usernameEditText = findViewById(R.id.login_username);
        passwordEditText = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        progressBar = findViewById(R.id.login_progress);
        errorAlertLayout = findViewById(R.id.login_error_alert);
        errorTextView = findViewById(R.id.login_error_text);
    }

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

    private void navigateToDashboard() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showErrorAlert(String message) {
        if (errorTextView != null && errorAlertLayout != null) {
            errorTextView.setText(message);
            errorAlertLayout.setVisibility(View.VISIBLE);
        }
    }

    private void hideErrorAlert() {
        if (errorAlertLayout != null) {
            errorAlertLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}