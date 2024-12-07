package com.example.projectmanagementapp.data.remote.repository;

import com.example.projectmanagementapp.data.remote.api.ApiClient;
import com.example.projectmanagementapp.data.remote.api.ApiService;
import com.example.projectmanagementapp.data.remote.model.ProjectRequest;
import com.example.projectmanagementapp.data.remote.model.ProjectsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectRepository {
    private final ApiService apiService;

    public ProjectRepository(String token) {
        apiService = ApiClient.getInstance().create(ApiService.class);
    }

    public void addProject(String token , ProjectRequest projectRequest, Callback<ProjectsResponse> callback) {
        Call<ProjectsResponse> call = apiService.addProject("Bearer" + token , projectRequest);
        call.enqueue(callback);
    }
}
