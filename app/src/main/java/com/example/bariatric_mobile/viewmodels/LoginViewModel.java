package com.example.bariatric_mobile.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bariatric_mobile.models.auth.LoginRequest;
import com.example.bariatric_mobile.models.auth.User;
import com.example.bariatric_mobile.repositories.AuthRepository;

public class LoginViewModel extends AndroidViewModel {
    private final AuthRepository authRepository;
    private final MutableLiveData<String> info = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>(false);
    private final MutableLiveData<String> username = new MutableLiveData<>();
    private final MutableLiveData<String> password = new MutableLiveData<>();
    private final MediatorLiveData<String> combinedError = new MediatorLiveData<>();
    private final MutableLiveData<String> validationError = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);

        setupErrorMediator();
        setupSuccessObserver();
    }

    public void login() {
        String usernameValue = username.getValue();
        String passwordValue = password.getValue();

        info.setValue(null);
        validationError.setValue(null);
        authRepository.clearLoginError();

        LoginRequest loginRequest = new LoginRequest(usernameValue, passwordValue);
        authRepository.login(loginRequest);
    }

    public void logout() {
        authRepository.logout();
        loginSuccess.setValue(false);
        clearForm();
    }

    public boolean isAlreadyAuthenticated() {
        return authRepository.isAuthenticated();
    }

    public void clearForm() {
        username.setValue("");
        password.setValue("");
    }

    private void setupErrorMediator() {
        combinedError.addSource(authRepository.getLoginError(), error -> {
            if (error != null) {
                combinedError.setValue(error);
            }
        });

        combinedError.addSource(validationError, error -> {
            if (error != null) {
                combinedError.setValue(error);
            }
        });
    }

    private void setupSuccessObserver() {
        authRepository.getLoginSuccess().observeForever(authResponse -> {
            if (authResponse != null) {
                loginSuccess.setValue(true);
                clearForm();
            }
        });
    }

    private void clearErrorsOnTyping() {
        if (combinedError.getValue() != null) {
            authRepository.clearLoginError();
            validationError.setValue(null);
        }
    }

    public LiveData<Boolean> getIsLoading() {
        return authRepository.getIsLoading();
    }

    public LiveData<String> getError() {
        return combinedError;
    }

    public LiveData<String> getInfo() {
        return info;
    }

    public LiveData<User> getUser() {
        return authRepository.getCurrentUser();
    }

    public LiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }

    public LiveData<String> getUsername() {
        return username;
    }

    public LiveData<String> getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username.setValue(username);
        clearErrorsOnTyping();
    }

    public void setPassword(String password) {
        this.password.setValue(password);
        clearErrorsOnTyping();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}