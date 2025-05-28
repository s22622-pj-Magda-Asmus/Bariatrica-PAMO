package com.example.bariatric_mobile.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bariatric_mobile.R;
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

public class AuthRepository {
    private final TokenManager tokenManager;
    private final SessionManager sessionManager;
    private final AuthApiService authApiService;

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> loginError = new MutableLiveData<>();
    private final MutableLiveData<AuthResponse> loginSuccess = new MutableLiveData<>();
    private final MutableLiveData<User> currentUser = new MutableLiveData<>();

    public AuthRepository(Context context) {
        this.tokenManager = new TokenManager(context);
        this.sessionManager = new SessionManager(context);
        ApiClient.initialize(context, tokenManager);
        this.authApiService = ApiClient.getAuthService();

        loadCurrentUser();
    }

    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getLoginError() { return loginError; }
    public LiveData<AuthResponse> getLoginSuccess() { return loginSuccess; }
    public LiveData<User> getCurrentUser() { return currentUser; }

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

    public void logout() {

        tokenManager.removeToken();
        sessionManager.clearUser();
        currentUser.setValue(null);

        loginSuccess.setValue(null);
        loginError.setValue(null);
    }

    public boolean isAuthenticated() {
        boolean hasToken = tokenManager.hasToken();
        boolean hasUser = sessionManager.hasUser();

        return hasToken && hasUser;
    }

    private void loadCurrentUser() {
        if (isAuthenticated()) {
            User user = sessionManager.getUser();
            currentUser.setValue(user);
        }
    }

    public void clearLoginError() {
        loginError.setValue(null);
    }
}