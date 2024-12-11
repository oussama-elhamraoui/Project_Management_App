package com.example.projectmanagementapp.data.remote.repository;

import com.example.projectmanagementapp.data.remote.api.ApiClient;
import com.example.projectmanagementapp.data.remote.api.ApiService;

public class TaskRepository {
    private ApiService taskApi;
    public TaskRepository() {
        taskApi = ApiClient.getInstance().create(ApiService.class);
    }
}
