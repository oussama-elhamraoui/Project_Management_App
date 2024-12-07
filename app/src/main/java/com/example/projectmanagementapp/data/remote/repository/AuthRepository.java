package com.example.projectmanagementapp.data.remote.repository;

import com.example.projectmanagementapp.data.remote.api.ApiClient;
import com.example.projectmanagementapp.data.remote.api.ApiService;
import com.example.projectmanagementapp.data.remote.model.LoginRequest;
import com.example.projectmanagementapp.data.remote.model.LoginResponse;
import com.example.projectmanagementapp.data.remote.model.SignUpResponse;

import retrofit2.Call;

public class AuthRepository {
    private final ApiService apiService;

    public AuthRepository() {
        this.apiService = ApiClient.getInstance().create(ApiService.class);
    }

    public Call<LoginResponse> login(String email , String password) {
        return apiService.login(new LoginRequest(email, password));
    }
    // firstname, lastname , email , password
    public Call<SignUpResponse> signUp(String email , String password , String firstname , String lastname  ) {
        return apiService.signUp(email ,password , firstname , lastname);
    }
}