package com.example.projectmanagementapp.data.remote.api;

import com.example.projectmanagementapp.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            // Configure OkHttpClient with custom timeouts
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS) // Set connection timeout
                    .readTimeout(30, TimeUnit.SECONDS)    // Set read timeout
                    .writeTimeout(30, TimeUnit.SECONDS)   // Set write timeout
                    .build();

            // Build Retrofit instance with the OkHttpClient
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(okHttpClient) // Attach the configured OkHttpClient
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
