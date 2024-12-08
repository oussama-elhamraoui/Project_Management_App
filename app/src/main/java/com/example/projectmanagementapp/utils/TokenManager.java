package com.example.projectmanagementapp.util;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private static final String PREF_NAME = "app_prefs";
    private static final String TOKEN_KEY = "auth_token";

    private final SharedPreferences preferences;

    public TokenManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveToken(String token) {
        preferences.edit().putString(TOKEN_KEY, token).apply();
    }

    public String getToken() {
        return preferences.getString(TOKEN_KEY, null);
    }

    public void clearToken() {
        preferences.edit().remove(TOKEN_KEY).apply();
    }
}