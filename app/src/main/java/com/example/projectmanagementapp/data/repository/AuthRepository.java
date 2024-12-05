package com.example.projectmanagementapp.data.repository;

import com.example.projectmanagementapp.data.api.ApiClient;
import com.example.projectmanagementapp.data.api.ApiService;
import com.example.projectmanagementapp.data.model.LoginRequest;
import com.example.projectmanagementapp.data.model.LoginResponse;
import com.example.projectmanagementapp.data.model.SignUpRequest;
import com.example.projectmanagementapp.data.model.SignUpResponse;

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
