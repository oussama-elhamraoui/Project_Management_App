package com.example.projectmanagementapp.data.remote.repository;



import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.projectmanagementapp.data.local.AppDatabase;
import com.example.projectmanagementapp.data.local.entities.Project;
import com.example.projectmanagementapp.data.remote.api.ApiClient;
import com.example.projectmanagementapp.data.remote.api.ApiService;
import com.example.projectmanagementapp.data.remote.model.ProjectRequest;
import com.example.projectmanagementapp.data.remote.model.ProjectsResponse;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import com.example.projectmanagementapp.utils.TokenManager;

public class ProjectRepository {
    private static final String TAG = "ProjectRepository";
    private final ApiService apiService;
    private final AppDatabase appDatabase;
    private final ExecutorService executor;


    private final String TOKEN = TokenManager.getPreferences().toString();

    public ProjectRepository(Context context, String token) {
        this.apiService = ApiClient.getInstance().create(ApiService.class);
        this.appDatabase = AppDatabase.getInstance(context);
        this.executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Project>> getAllProjects() {
        return appDatabase.projectDao().getAllProjects();
    }

// TODO : add the Project state !!!!!
    public void syncProjects(String token, SyncProjectsCallback callback) {
        executor.execute(() -> {
            try {
                List<ProjectsResponse> remoteProjects = apiService.getAllProjects("Bearer " + token).execute().body();
                if (remoteProjects != null) {
                    for (ProjectsResponse project : remoteProjects) {
                        Project localProject = new Project();
                        localProject.setId(project.getId());
                        localProject.setDescription(project.getDescription());
                        localProject.setName(project.getTitle());
                        appDatabase.projectDao().insertProject(localProject); // Save project to local DB
                    }
                    // Run callback on main thread
                    new Handler(Looper.getMainLooper()).post(() -> {
                        if (callback != null) {
                            callback.onSyncCompleted(remoteProjects);
                        }
                    });
                }
            } catch (Exception e) {
                Log.e(TAG, "Error syncing projects: ", e);
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (callback != null) {
                        callback.onSyncFailed(e);
                    }
                });
            }
        });
    }

    // Callback interface for project sync
    public interface SyncProjectsCallback {
        void onSyncCompleted(List<ProjectsResponse> projects);
        void onSyncFailed(Exception e);
    }


    // Add a new project through the API and save it locally
    public void addProject(String token, ProjectRequest projectRequest, Callback<ProjectsResponse> callback) {
        apiService.createProject("Bearer " + token, projectRequest).enqueue(new Callback<ProjectsResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProjectsResponse> call, @NonNull Response<ProjectsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProjectsResponse remoteProject = response.body();
                    Project localProject = new Project();
                    localProject.setId(remoteProject.getId());
                    localProject.setName(remoteProject.getTitle());
                    localProject.setDescription(remoteProject.getDescription());

                    executor.execute(() -> {
                        appDatabase.projectDao().insertProject(localProject);
                        Log.d(TAG, "Project saved locally: " + remoteProject.getTitle());
                    });

                    // Notify the callback
                    callback.onResponse(call, response);
                } else {
                    Log.e(TAG, "Failed to add project: " + response.code());
                    callback.onFailure(call, new Throwable("Failed to add project"));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProjectsResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "Error adding project: ", t);
                callback.onFailure(call, t);
            }
        });
    }
}
