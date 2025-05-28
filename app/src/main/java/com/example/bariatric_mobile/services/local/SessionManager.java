package com.example.bariatric_mobile.services.local;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.example.bariatric_mobile.models.auth.User;
import com.google.gson.Gson;

public class SessionManager {
    private static final String PREF_NAME = "session_preferences";
    private static final String PREF_USER = "auth_user";

    private SharedPreferences preferences;
    private final Gson gson;

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

    public void storeUser(User user) {
        if (user == null) {
            return;
        }
        String userJson = gson.toJson(user);
        preferences.edit().putString(PREF_USER, userJson).apply();
    }

    public User getUser() {
            String userJson = preferences.getString(PREF_USER, null);
            if (userJson == null || userJson.isEmpty()) {
                return null;
            }
            return gson.fromJson(userJson, User.class);
    }

    public boolean hasUser() {
        return getUser() != null;
    }

    public void clearUser() {
        preferences.edit().remove(PREF_USER).apply();
    }
}