package com.example.bariatric_mobile.services.network;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

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

/**
 * Central HTTP client configuration and service factory for the application.
 *
 * <p>This class provides a singleton Retrofit instance configured with authentication,
 * timeouts, and automatic session management. It handles token injection, session
 * expiration, and provides factory methods for creating API service interfaces.</p>
 *
 * <p>The client automatically:</p>
 * <ul>
 *   <li>Adds authentication headers to all requests</li>
 *   <li>Handles 401 Unauthorized responses by logging out the user</li>
 *   <li>Redirects to login screen on session expiration</li>
 *   <li>Configures appropriate timeouts for network operations</li>
 * </ul>
 *
 * <p><strong>Usage:</strong></p>
 * <pre>
 * // Initialize once in Application class
 * ApiClient.initialize(context, tokenManager);
 *
 * // Get service instances
 * PatientApiService patientService = ApiClient.getPatientService();
 * AuthApiService authService = ApiClient.getAuthService();
 * </pre>
 *
 */
public class ApiClient {

    /** Base URL for the bariatric surgery API server */
    private static final String BASE_URL = "http://192.168.0.229:8000/";

    /** Singleton Retrofit instance */
    private static Retrofit retrofit = null;

    /** Token manager for authentication */
    private static TokenManager tokenManager;

    /** Application context for session management */
    private static Context appContext;

    /**
     * Initializes the API client with required dependencies.
     *
     * <p>This method must be called once before using any API services,
     * typically in the Application class onCreate() method.</p>
     *
     * @param context Application context for session management
     * @param tokenMgr Token manager instance for authentication
     *
     * @throws NullPointerException if context or tokenMgr is null
     */
    public static void initialize(Context context, TokenManager tokenMgr) {
        appContext = context.getApplicationContext();
        tokenManager = tokenMgr;
        retrofit = null; // Force recreation with new dependencies
    }

    /**
     * Gets the configured Retrofit client instance.
     *
     * <p>Creates a new instance if one doesn't exist. The client is configured
     * with authentication interceptor, timeouts, and JSON conversion.</p>
     *
     * @return Configured Retrofit instance
     *
     * @throws IllegalStateException if {@link #initialize(Context, TokenManager)}
     *                               hasn't been called
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = createRetrofitClient();
        }
        return retrofit;
    }

    /**
     * Creates and configures a new Retrofit client instance.
     *
     * <p>Configures the client with:</p>
     * <ul>
     *   <li>Authentication interceptor for token injection</li>
     *   <li>10-second timeouts for connect, read, and write operations</li>
     *   <li>Gson converter for JSON serialization/deserialization</li>
     * </ul>
     *
     * @return Configured Retrofit instance
     */
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

    /**
     * HTTP interceptor that handles authentication and session management.
     *
     * <p>This interceptor:</p>
     * <ul>
     *   <li>Adds Authorization header with Bearer token to all requests</li>
     *   <li>Adds standard Content-Type and Accept headers</li>
     *   <li>Handles 401 Unauthorized responses by logging out the user</li>
     *   <li>Redirects to login screen on session expiration</li>
     * </ul>
     *
     * <p>Login requests are exempted from automatic logout on 401 responses
     * to allow proper error handling for invalid credentials.</p>
     */
    private static class AuthInterceptor implements Interceptor {

        /**
         * Intercepts HTTP requests to add authentication and handle responses.
         *
         * @param chain The interceptor chain
         * @return The HTTP response
         * @throws IOException if the request execution fails
         */
        @NonNull
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

    /**
     * Creates an instance of the Authentication API service.
     *
     * <p>Provides endpoints for user authentication operations such as
     * login, logout, and token refresh.</p>
     *
     * @return AuthApiService instance for authentication operations
     */
    public static AuthApiService getAuthService() {
        return getClient().create(AuthApiService.class);
    }

    /**
     * Creates an instance of the Patient API service.
     *
     * <p>Provides endpoints for patient data operations such as
     * retrieving patient lists, patient details, and updating patient status.</p>
     *
     * @return PatientApiService instance for patient operations
     */
    public static PatientApiService getPatientService() {
        return getClient().create(PatientApiService.class);
    }

    /**
     * Creates an instance of the Prediction API service.
     *
     * <p>Provides endpoints for weight loss prediction operations,
     * retrieving AI-generated predictions for patient outcomes.</p>
     *
     * @return PredictionApiService instance for prediction operations
     */
    public static PredictionApiService getPredictionService() {
        return getClient().create(PredictionApiService.class);
    }
}