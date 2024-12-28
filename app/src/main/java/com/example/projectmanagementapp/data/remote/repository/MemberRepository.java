package com.example.projectmanagementapp.data.remote.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.projectmanagementapp.data.remote.api.ApiClient;
import com.example.projectmanagementapp.data.remote.api.ApiService;
import com.example.projectmanagementapp.data.remote.model.MemberResponse;
import com.example.projectmanagementapp.state.ProjectState;
import com.example.projectmanagementapp.utils.TokenManager;

import retrofit2.Call;

public class MemberRepository {
    public static final String SEPERATOR = ";";
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

    public Call<MemberResponse> addMember(String email, Context context) {
        final SharedPreferences preferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String members = preferences.getString("members", "");
        final String userValue = ProjectState.getInstance().getId()+":" +"user"; // add logic later
        members += members + SEPERATOR + userValue;

        // Set the value later
        return apiService.addContributorByEmail(token, ProjectState.getInstance().getProject().id, email);
    }

}
