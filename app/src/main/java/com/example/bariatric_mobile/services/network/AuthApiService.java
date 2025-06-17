package com.example.bariatric_mobile.services.network;

import com.example.bariatric_mobile.models.auth.AuthResponse;
import com.example.bariatric_mobile.models.auth.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Retrofit API service interface for user authentication operations.
 *
 * Defines HTTP endpoints for user login and token refresh operations.
 * All endpoints require valid request bodies and return authentication
 * responses containing user data and access tokens.
 */
public interface AuthApiService {

    /**
     * Authenticates user with email and password credentials.
     *
     * @param loginRequest Contains user email and password for authentication
     * @return Retrofit Call containing AuthResponse with user data and access token
     */
    @POST("api/auth/login")
    Call<AuthResponse> login(@Body LoginRequest loginRequest);

    /**
     * Refreshes expired access token using stored refresh token.
     *
     * @return Retrofit Call containing AuthResponse with new access token
     */
    @POST("api/auth/refresh-token")
    Call<AuthResponse> refreshToken();
}