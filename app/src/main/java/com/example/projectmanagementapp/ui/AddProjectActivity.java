package com.example.projectmanagementapp.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectmanagementapp.R;
import com.example.projectmanagementapp.data.remote.repository.*;
import com.example.projectmanagementapp.data.remote.model.ProjectRequest;
import com.example.projectmanagementapp.data.remote.model.ProjectsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProjectActivity extends AppCompatActivity {

    private static final String TAG = "AddProjectActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        String token = "your-jwt-token";  // Replace with actual token retrieval logic
        ProjectRepository repository = new ProjectRepository(token);

        ProjectRequest projectRequest = new ProjectRequest("New Project", "Project Description");

        repository.addProject(token, projectRequest, new Callback<ProjectsResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProjectsResponse> call, @NonNull Response<ProjectsResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    Log.d(TAG, "Project added: " + response.body().getTitle());
                } else {
                    Log.e(TAG, "Failed to add project: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProjectsResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "Error adding project", t);
            }
        });
    }
}