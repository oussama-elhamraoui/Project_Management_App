package com.example.projectmanagementapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.text.TextUtils;
import android.util.Log;

import com.example.projectmanagementapp.data.remote.model.LoginResponse;
import com.example.projectmanagementapp.data.remote.model.UserProfile;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class TokenManager {
    private static final String PREF_NAME = "app_prefs";
    private static final String TOKEN_KEY = "auth_token";
    private static final String USER_ID_KEY = "user_id";
    private static final String USER_EMAIL_KEY = "user_email";
    private static final String USER_FIRSTNAME_KEY = "user_first_name";
    private static final String USER_LASTNAME_KEY = "user_last_name";

    private static SharedPreferences preferences = null;

    public TokenManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Save the authentication token
     * @param token JWT token to be saved
     */
    public void saveToken(String token, LoginResponse loginResponse) {
        if (!TextUtils.isEmpty(token)) {
            preferences.edit().putString(TOKEN_KEY, token).apply();

            // Extract and save additional user info from token
            try {
                UserProfile userInfo = parseTokenPayload(token,loginResponse);
                Log.d("GetEmailFromString ",userInfo.getEmail()+token+"  "+loginResponse);
                saveUserInfo(userInfo);
            } catch (Exception e) {
                // Log the error or handle token parsing failure
            }
        }
    }

    /**
     * Get the saved authentication token
     * @return Stored token or null
     */
    public String getToken() {
        return preferences.getString(TOKEN_KEY, null);
    }

    /**
     * Clear the saved token and associated user info
     */
    public void clearToken() {
        preferences.edit()
                .remove(TOKEN_KEY)
                .remove(USER_ID_KEY)
                .remove(USER_EMAIL_KEY)
                .remove(USER_FIRSTNAME_KEY)
                .remove(USER_LASTNAME_KEY)
                .apply();
    }

    /**
     * Check if a token is currently saved
     * @return true if token exists, false otherwise
     */
    public boolean hasToken() {
        return !TextUtils.isEmpty(getToken());
    }

    /**
     * Check if the token is expired
     * @return true if token is expired, false otherwise
     */
    public boolean isTokenExpired() {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return true;
        }

        try {
            long expirationTime = getTokenExpirationTime(token);
            return expirationTime <= System.currentTimeMillis() / 1000;
        } catch (Exception e) {
            // If parsing fails, consider token invalid
            return true;
        }
    }

    /**
     * Save user information extracted from token
     * @param userInfo Parsed user information
     */
    public void saveUserInfo(UserProfile userInfo) {
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USER_ID_KEY, userInfo.getUserId());
        editor.putString(USER_EMAIL_KEY, userInfo.getEmail());
        editor.putString(USER_FIRSTNAME_KEY, userInfo.getFirstname());
        editor.putString(USER_LASTNAME_KEY, userInfo.getLastname());
    }

    /**
     * Retrieve saved user ID
     * @return User ID or null
     */
    public String getUserId() {
        return preferences.getString(USER_ID_KEY, null);
    }

    /**
     * Retrieve saved user email
     * @return User email or null
     */
    public String getUserEmail() {
        return preferences.getString(USER_EMAIL_KEY, null);
    }

    /**
     * Retrieve saved user name
     * @return User name or null
     */
    public String getUserName() {
        return preferences.getString(USER_FIRSTNAME_KEY+USER_LASTNAME_KEY, null);
    }

    /**
     * Parse JWT token payload
     * @param token JWT token
     * @return Parsed user information
     * @throws Exception if token parsing fails
     */
    public UserProfile parseTokenPayload(String token,LoginResponse loginResponse) throws Exception {
        // Parse the entire token response JSON
        JSONObject responseJson = new JSONObject(String.valueOf(loginResponse));
        String tokenN = responseJson.getString("token");

        // Split the token into parts
        String[] parts = tokenN.split("\\.");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid token format");
        }

        // Decode the payload (second part)
        // Use URL_SAFE decoding and add padding if necessary
        StringBuilder payloadBase64 = new StringBuilder(parts[1]);
        // Add padding if needed
        while (payloadBase64.length() % 4 != 0) {
            payloadBase64.append("=");
        }

        // Decode the payload
        byte[] payloadBytes = Base64.decode(payloadBase64.toString(), Base64.URL_SAFE);
        String payloadJson = new String(payloadBytes, StandardCharsets.UTF_8);

        // Parse the payload JSON
        JSONObject payload = new JSONObject(payloadJson);

        // Create and return UserProfile
        return new UserProfile(
                payload.optString("sub", ""),  // user ID
                responseJson.optString("username", ""),  // email from response JSON
                responseJson.optString("firstName", ""),  // first name from response JSON
                responseJson.optString("lastName", "")   // last name from response JSON
        );
    }

    /**
     * Extract token expiration time
     * @param token JWT token
     * @return Expiration time in seconds
     * @throws JSONException if token parsing fails
     */
    private long getTokenExpirationTime(String token) throws Exception {
        String[] parts = token.split("\\.");
        String payloadJson = new String(Base64.decode(parts[1], Base64.DEFAULT));
        JSONObject payload = new JSONObject(payloadJson);

        // JWT typically uses 'exp' claim for expiration
        return payload.optLong("exp", 0);
    }


    // Retrieve user profile from SharedPreferences
    public static UserProfile getUserProfileFromStorage() {
        String firstName = preferences.getString(USER_FIRSTNAME_KEY, "");
        String lastName = preferences.getString(USER_LASTNAME_KEY , "");
        String email = preferences.getString(USER_EMAIL_KEY , "");
        String userId = preferences.getString(USER_ID_KEY, "");

        // Only return a profile if at least some basic info exists
        if (!TextUtils.isEmpty(email)) {
            return new UserProfile(userId, email, firstName ,lastName);
        }
        return null;
    }
    
}