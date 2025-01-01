package com.example.projectmanagementapp.data.remote.repository;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.projectmanagementapp.data.remote.api.ApiClient;
import com.example.projectmanagementapp.data.remote.api.ApiService;
import com.example.projectmanagementapp.data.remote.model.ProjectResponse;
import com.example.projectmanagementapp.data.remote.model.UserRequest;
import com.example.projectmanagementapp.data.remote.model.UserResponse;
import com.example.projectmanagementapp.utils.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public void addMember(String email, String username, Callback<UserResponse> callback) {

        apiService.getUser(token, new UserRequest(email = email, username = username)).enqueue(new Callback<UserResponse>() {
            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                callback.onFailure(call, t);
            }

            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                callback.onResponse(call, response);

            }
        });
        // Set the value later
    }

}
