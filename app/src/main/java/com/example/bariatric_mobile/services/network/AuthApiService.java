package com.example.bariatric_mobile.services.network;

import com.example.bariatric_mobile.models.auth.AuthResponse;
import com.example.bariatric_mobile.models.auth.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {
    @POST("auth/login")
    Call<AuthResponse> login(@Body LoginRequest loginRequest);

    @POST("auth/refresh-token")
    Call<AuthResponse> refreshToken();
}