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

/**
 * ViewModel for managing login screen data and user authentication logic.
 * <p>
 * Handles user input validation, login state management, and coordinates
 * with AuthRepository for authentication operations. Provides combined
 * error handling and automatic error clearing during user input.
 */
public class LoginViewModel extends AndroidViewModel {
    private final AuthRepository authRepository;
    private final MutableLiveData<String> info = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>(false);
    private final MutableLiveData<String> username = new MutableLiveData<>();
    private final MutableLiveData<String> password = new MutableLiveData<>();
    private final MediatorLiveData<String> combinedError = new MediatorLiveData<>();
    private final MutableLiveData<String> validationError = new MutableLiveData<>();

    /**
     * Creates a new LoginViewModel instance.
     * Initializes AuthRepository and sets up error handling and success observers.
     *
     * @param application Application context for repository initialization
     */
    public LoginViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);

        setupErrorMediator();
        setupSuccessObserver();
    }

    /**
     * Attempts to log in user with current username and password values.
     * Clears existing errors and info messages before making login request.
     */
    public void login() {
        String usernameValue = username.getValue();
        String passwordValue = password.getValue();

        info.setValue(null);
        validationError.setValue(null);
        authRepository.clearLoginError();

        LoginRequest loginRequest = new LoginRequest(usernameValue, passwordValue);
        authRepository.login(loginRequest);
    }

    /**
     * Logs out the current user and resets login form.
     * Clears all user data and resets UI state.
     */
    public void logout() {
        authRepository.logout();
        loginSuccess.setValue(false);
        clearForm();
    }

    /**
     * Checks if user is already authenticated.
     *
     * @return True if user has valid authentication, false otherwise
     */
    public boolean isAlreadyAuthenticated() {
        return authRepository.isAuthenticated();
    }

    /**
     * Clears username and password input fields.
     */
    public void clearForm() {
        username.setValue("");
        password.setValue("");
    }

    /**
     * Sets up MediatorLiveData to combine different error sources.
     * Combines authentication errors and validation errors into single stream.
     */
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

    /**
     * Sets up observer for successful authentication responses.
     * Updates login success state and clears form on successful login.
     */
    private void setupSuccessObserver() {
        authRepository.getLoginSuccess().observeForever(authResponse -> {
            if (authResponse != null) {
                loginSuccess.setValue(true);
                clearForm();
            }
        });
    }

    /**
     * Clears all error states when user starts typing.
     * Provides better user experience by removing errors during input.
     */
    private void clearErrorsOnTyping() {
        if (combinedError.getValue() != null) {
            authRepository.clearLoginError();
            validationError.setValue(null);
        }
    }

    /**
     * Returns LiveData for observing loading state during authentication.
     *
     * @return LiveData indicating if authentication request is in progress
     */
    public LiveData<Boolean> getIsLoading() {
        return authRepository.getIsLoading();
    }

    /**
     * Returns LiveData for observing combined error messages.
     *
     * @return LiveData containing error messages from all sources
     */
    public LiveData<String> getError() {
        return combinedError;
    }

    /**
     * Returns LiveData for observing informational messages.
     *
     * @return LiveData containing info messages for user feedback
     */
    public LiveData<String> getInfo() {
        return info;
    }

    /**
     * Returns LiveData for observing current authenticated user.
     *
     * @return LiveData containing user data or null if not authenticated
     */
    public LiveData<User> getUser() {
        return authRepository.getCurrentUser();
    }

    /**
     * Returns LiveData for observing login success state.
     *
     * @return LiveData indicating successful login completion
     */
    public LiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }

    /**
     * Returns LiveData for observing username input.
     *
     * @return LiveData containing current username value
     */
    public LiveData<String> getUsername() {
        return username;
    }

    /**
     * Returns LiveData for observing password input.
     *
     * @return LiveData containing current password value
     */
    public LiveData<String> getPassword() {
        return password;
    }

    /**
     * Sets username value and clears existing errors.
     *
     * @param username The username entered by user
     */
    public void setUsername(String username) {
        this.username.setValue(username);
        clearErrorsOnTyping();
    }

    /**
     * Sets password value and clears existing errors.
     *
     * @param password The password entered by user
     */
    public void setPassword(String password) {
        this.password.setValue(password);
        clearErrorsOnTyping();
    }

}