package com.example.bariatric_mobile.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bariatric_mobile.models.auth.AuthResponse;
import com.example.bariatric_mobile.models.auth.LoginRequest;
import com.example.bariatric_mobile.models.auth.User;
import com.example.bariatric_mobile.services.local.SessionManager;
import com.example.bariatric_mobile.services.local.TokenManager;
import com.example.bariatric_mobile.services.network.ApiClient;
import com.example.bariatric_mobile.services.network.AuthApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repository for managing user authentication operations and state.
 *
 * Handles login, logout, and authentication state management using secure
 * local storage for tokens and user data. Provides LiveData objects for
 * observing authentication state changes in the UI layer.
 */
public class AuthRepository {
    private final TokenManager tokenManager;
    private final SessionManager sessionManager;
    private final AuthApiService authApiService;

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> loginError = new MutableLiveData<>();
    private final MutableLiveData<AuthResponse> loginSuccess = new MutableLiveData<>();
    private final MutableLiveData<User> currentUser = new MutableLiveData<>();

    /**
     * Creates a new AuthRepository instance with required dependencies.
     * Initializes secure storage managers and API client, then loads
     * current user session if available.
     *
     * @param context Application context for accessing storage and API services
     */
    public AuthRepository(Context context) {
        this.tokenManager = new TokenManager(context);
        this.sessionManager = new SessionManager(context);
        ApiClient.initialize(context, tokenManager);
        this.authApiService = ApiClient.getAuthService();

        loadCurrentUser();
    }

    /**
     * Returns LiveData for observing loading state during authentication operations.
     *
     * @return LiveData containing loading state boolean
     */
    public LiveData<Boolean> getIsLoading() { return isLoading; }

    /**
     * Returns LiveData for observing login error messages.
     *
     * @return LiveData containing error messages or null if no error
     */
    public LiveData<String> getLoginError() { return loginError; }

    /**
     * Returns LiveData for observing successful login responses.
     *
     * @return LiveData containing AuthResponse on successful login
     */
    public LiveData<AuthResponse> getLoginSuccess() { return loginSuccess; }

    /**
     * Returns LiveData for observing current authenticated user.
     *
     * @return LiveData containing current User or null if not authenticated
     */
    public LiveData<User> getCurrentUser() { return currentUser; }

    /**
     * Performs user login with provided credentials.
     * Updates loading state and handles API response, storing authentication
     * data on success or setting error message on failure.
     *
     * @param loginRequest Contains user email and password for authentication
     */
    public void login(LoginRequest loginRequest) {
        isLoading.setValue(true);
        loginError.setValue(null);

        authApiService.login(loginRequest).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                isLoading.postValue(false);

                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse authResponse = response.body();

                    tokenManager.setToken(authResponse.getToken());
                    sessionManager.storeUser(authResponse.getUser());

                    currentUser.postValue(authResponse.getUser());
                    loginSuccess.postValue(authResponse);

                } else {
                    loginError.postValue(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                isLoading.postValue(false);
            }
        });
    }

    /**
     * Logs out the current user by clearing all stored authentication data.
     * Removes tokens, user session data, and resets all LiveData states.
     */
    public void logout() {
        tokenManager.removeToken();
        sessionManager.clearUser();
        currentUser.postValue(null);

        loginSuccess.postValue(null);
        loginError.setValue(null);
    }

    /**
     * Checks if user is currently authenticated.
     * Verifies both token and user session data exist.
     *
     * @return True if user has valid authentication data, false otherwise
     */
    public boolean isAuthenticated() {
        boolean hasToken = tokenManager.hasToken();
        boolean hasUser = sessionManager.hasUser();

        return hasToken && hasUser;
    }

    /**
     * Loads current user from session storage if authenticated.
     * Called during repository initialization to restore user state.
     */
    private void loadCurrentUser() {
        if (isAuthenticated()) {
            User user = sessionManager.getUser();
            currentUser.postValue(user);
        }
    }

    /**
     * Clears any existing login error message.
     * Useful for resetting error state when user starts typing new credentials.
     */
    public void clearLoginError() {
        loginError.setValue(null);
    }
}