package com.example.bariatric_mobile.services.network;

import android.content.Context;
import android.content.Intent;

import com.example.bariatric_mobile.activities.LoginActivity;
import com.example.bariatric_mobile.repositories.AuthRepository;
import com.example.bariatric_mobile.services.local.TokenManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://192.168.0.229:8000/";

    private static Retrofit retrofit = null;
    private static TokenManager tokenManager;
    private static Context appContext;

    public static void initialize(Context context, TokenManager tokenMgr) {
        appContext = context.getApplicationContext();
        tokenManager = tokenMgr;
        retrofit = null;
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = createRetrofitClient();
        }
        return retrofit;
    }

    private static Retrofit createRetrofitClient() {

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);

        OkHttpClient httpClient = httpClientBuilder.build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static class AuthInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            Request.Builder requestBuilder = original.newBuilder();

            if (tokenManager != null && tokenManager.hasToken()) {
                String token = tokenManager.getToken();
                requestBuilder.header("Authorization", "Bearer " + token);
            }

            requestBuilder
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json");

            Request authenticatedRequest = requestBuilder.build();

            Response response = chain.proceed(authenticatedRequest);

            if (response.code() == 401) {
                String url = original.url().toString();

                if (url.contains("/api/auth/login")) {
                    return response;
                }

                if (tokenManager != null && appContext != null) {
                    AuthRepository authRepository = new AuthRepository(appContext);
                    authRepository.logout();

                    Intent intent = new Intent(appContext, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("session_expired", true);
                    appContext.startActivity(intent);
                }
            }

            return response;
        }
    }

    public static AuthApiService getAuthService() {
        return getClient().create(AuthApiService.class);
    }

    public static PatientApiService getPatientService() {
        return getClient().create(PatientApiService.class);
    }

    public static PredictionApiService getPredictionService() {
        return getClient().create(PredictionApiService.class);
    }
}