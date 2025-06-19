package com.example.bariatric_mobile.services.local;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

/**
 * Manages secure storage and retrieval of authentication tokens.
 *
 * Uses Android's EncryptedSharedPreferences for secure token storage
 * with AES256 encryption. Falls back to regular SharedPreferences
 * if encryption setup fails.
 */
public class TokenManager {
    private static final String PREF_NAME = "auth_preferences";
    private static final String PREF_TOKEN = "auth_token";

    private SharedPreferences preferences;

    /**
     * Creates a new TokenManager instance with encrypted storage.
     *
     * @param context Application context for accessing preferences
     */
    public TokenManager(Context context) {
        try {
            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            preferences = EncryptedSharedPreferences.create(
                    context,
                    PREF_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (Exception e) {
            preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
    }

    /**
     * Stores an authentication token securely.
     *
     * @param token The authentication token to store
     */
    public void setToken(String token) {
        preferences.edit().putString(PREF_TOKEN, token).apply();
    }

    /**
     * Retrieves the stored authentication token.
     *
     * @return The stored token or null if none exists
     */
    public String getToken() {
        return preferences.getString(PREF_TOKEN, null);
    }

    /**
     * Checks if a valid authentication token exists.
     *
     * @return True if token exists and is not empty, false otherwise
     */
    public boolean hasToken() {
        String token = getToken();
        return token != null && !token.trim().isEmpty();
    }

    /**
     * Removes the stored authentication token.
     */
    public void removeToken() {
        preferences.edit().remove(PREF_TOKEN).apply();
    }
}