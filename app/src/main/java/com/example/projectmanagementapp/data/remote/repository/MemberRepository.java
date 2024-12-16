package com.example.projectmanagementapp.data.remote.repository;

import com.example.projectmanagementapp.data.remote.api.ApiClient;
import com.example.projectmanagementapp.data.remote.api.ApiService;
import com.example.projectmanagementapp.data.remote.model.AuthResponse;
import com.example.projectmanagementapp.data.remote.model.ContributorResponse;
import com.example.projectmanagementapp.data.remote.model.LoginRequest;
import com.example.projectmanagementapp.state.ProjectState;
import com.example.projectmanagementapp.utils.TokenManager;

import retrofit2.Call;

public class MemberRepository {
    private static MemberRepository instance;
    final static private String token = TokenManager.getToken();
    private static ApiService apiService;
    private MemberRepository() {
        apiService = ApiClient.getInstance().create(ApiService.class);
    }

    public static MemberRepository getInstance(){
        if(instance == null){
            instance = new MemberRepository();
        }
        return instance;
    }

    public Call<ContributorResponse> addMember(String email) {
        return apiService.addContributorByEmail(token, ProjectState.getInstance().getProject().id, email);
    }

}
