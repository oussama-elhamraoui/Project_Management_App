package com.example.projectmanagementapp.data.api;

import com.example.projectmanagementapp.data.model.LoginRequest;
import com.example.projectmanagementapp.data.model.LoginResponse;
import com.example.projectmanagementapp.data.model.SignUpRequest;
import com.example.projectmanagementapp.data.model.SignUpResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    //we are using a json body when login
    @POST("api/v1/auth/login")
    public Call<LoginResponse> login(@Body LoginRequest loginRequest);

    //we are using Query Params when signing up
    @POST("api/v1/auth/register")
    public Call<SignUpResponse> signUp(@Query("first_name") String firstname ,
                                       @Query("last_name") String lastname,
                                       @Query("email") String email,
                                       @Query("password") String password
    );
}
