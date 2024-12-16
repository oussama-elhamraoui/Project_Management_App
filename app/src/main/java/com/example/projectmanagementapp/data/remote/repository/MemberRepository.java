package com.example.projectmanagementapp.data.remote.repository;

import com.example.projectmanagementapp.data.remote.api.ApiClient;
import com.example.projectmanagementapp.data.remote.api.ApiService;
import com.example.projectmanagementapp.data.remote.model.AuthResponse;
import com.example.projectmanagementapp.data.remote.model.ContributorResponse;
import com.example.projectmanagementapp.data.remote.model.LoginRequest;
import com.example.projectmanagementapp.utils.TokenManager;

import retrofit2.Call;

public class MemberRepository {
    final private String token = TokenManager.getToken();
    private final ApiService apiService;
    public MemberRepository() {
        this.apiService = ApiClient.getInstance().create(ApiService.class);
    }


    public Call<ContributorResponse> addMember(int projectId, String email) {
        return apiService.addContributorByEmail(token, projectId, email);
    }

}
