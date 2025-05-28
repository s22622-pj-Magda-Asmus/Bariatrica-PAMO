package com.example.bariatric_mobile.services.local;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

public class TokenManager {
    private static final String PREF_NAME = "auth_preferences";
    private static final String PREF_TOKEN = "auth_token";

    private SharedPreferences preferences;

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

    public void setToken(String token) {
        preferences.edit().putString(PREF_TOKEN, token).apply();
    }

    public String getToken() {
        return preferences.getString(PREF_TOKEN, null);
    }

    public boolean hasToken() {
        String token = getToken();
        return token != null && !token.trim().isEmpty();
    }

    public void removeToken() {
        preferences.edit().remove(PREF_TOKEN).apply();
    }
}