package com.example.projectmanagementapp.utils;

public class Constants {
    // Base URL for your API
    public static final String BASE_URL = "https://backend-springboot-xx6o.onrender.com/";

    // API Endpoints (Optional, for better readability)
    public static final String LOGIN_ENDPOINT = "api/v1/auth/login";
    public static final String SIGNUP_ENDPOINT = "auth/signup";

    // Shared Preferences Keys
    public static final String PREF_NAME = "app_prefs";
    public static final String TOKEN_KEY = "auth_token";

    // Error Messages
    public static final String ERROR_MESSAGE_NETWORK = "Network error. Please try again.";
    public static final String ERROR_MESSAGE_SERVER = "Server error. Please try again later.";
    public static final String ERROR_MESSAGE_UNAUTHORIZED = "Unauthorized access. Please log in again.";

    // Other Constants
    public static final int TIMEOUT_DURATION = 30; // in seconds for network calls
}
