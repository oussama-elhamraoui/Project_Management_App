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
import com.example.projectmanagementapp.data.remote.model.ProjectResponse;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import com.example.projectmanagementapp.utils.TokenManager;

public class ProjectRepository {
    private static ProjectRepository instance;
    private static final String TAG = "ProjectRepository";
    private final ApiService apiService;
    private final AppDatabase appDatabase;
    private final ExecutorService executor;


    private final String TOKEN = TokenManager.getToken();

    private ProjectRepository(Context context) {
        this.apiService = ApiClient.getInstance().create(ApiService.class);
        this.appDatabase = AppDatabase.getInstance(context);
        this.executor = Executors.newSingleThreadExecutor();
    }

    public static ProjectRepository getInstance(Context context){
        if(instance == null){
            instance = new ProjectRepository(context);
        }
        return instance;
    }
    public LiveData<List<Project>> getAllProjects() {
        return appDatabase.projectDao().getAllProjects();
    }

    public void syncProjects(String token, SyncProjectsCallback callback) {
        executor.execute(() -> {
            try {
                List<ProjectResponse> remoteProjects = apiService.getAllProjects("Bearer " + token).execute().body();
                if (remoteProjects != null) {
                    for (ProjectResponse project : remoteProjects) {
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
        void onSyncCompleted(List<ProjectResponse> projects);
        void onSyncFailed(Exception e);
    }
    public void getAllProjects(Callback<List<ProjectResponse>> callback){
        apiService.getAllProjects("Bearer " + TOKEN).enqueue(new Callback<List<ProjectResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ProjectResponse>> call, @NonNull Response<List<ProjectResponse>> response) {
                callback.onResponse(call, response);
            }

            @Override
            public void onFailure(@NonNull Call<List<ProjectResponse>> call, @NonNull Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }
    // Add a new project through the API and save it locally
    public void addProject(ProjectRequest projectRequest, Callback<ProjectResponse> callback) {

        apiService.createProject("Bearer " + TOKEN, projectRequest).enqueue(new Callback<ProjectResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProjectResponse> call, @NonNull Response<ProjectResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProjectResponse remoteProject = response.body();
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
            public void onFailure(@NonNull Call<ProjectResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "Error adding project: ", t);
                callback.onFailure(call, t);
            }
        });
    }
}
