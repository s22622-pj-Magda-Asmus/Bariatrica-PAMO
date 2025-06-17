package com.example.bariatric_mobile.services.local;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.example.bariatric_mobile.models.auth.User;
import com.google.gson.Gson;

/**
 * Manages secure storage and retrieval of user session data.
 *
 * Uses Android's EncryptedSharedPreferences for secure user data storage
 * with JSON serialization. Falls back to regular SharedPreferences
 * if encryption setup fails.
 */
public class SessionManager {
    private static final String PREF_NAME = "session_preferences";
    private static final String PREF_USER = "auth_user";

    private SharedPreferences preferences;
    private final Gson gson;

    /**
     * Creates a new SessionManager instance with encrypted storage.
     *
     * @param context Application context for accessing preferences
     */
    public SessionManager(Context context) {
        this.gson = new Gson();
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
     * Stores user data securely as JSON.
     *
     * @param user The user object to store, null values are ignored
     */
    public void storeUser(User user) {
        if (user == null) {
            return;
        }
        String userJson = gson.toJson(user);
        preferences.edit().putString(PREF_USER, userJson).apply();
    }

    /**
     * Retrieves the stored user data.
     *
     * @return The stored user object or null if none exists
     */
    public User getUser() {
        String userJson = preferences.getString(PREF_USER, null);
        if (userJson == null || userJson.isEmpty()) {
            return null;
        }
        return gson.fromJson(userJson, User.class);
    }

    /**
     * Checks if user session data exists.
     *
     * @return True if user data exists, false otherwise
     */
    public boolean hasUser() {
        return getUser() != null;
    }

    /**
     * Removes all stored user session data.
     */
    public void clearUser() {
        preferences.edit().remove(PREF_USER).apply();
    }
}