package com.example.projectmanagementapp.data.remote.model;

import com.example.projectmanagementapp.models.User;
import com.example.projectmanagementapp.models.UserTheme;

public class AuthResponse {
    private String token;
    private int userId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;

    private int color;

    public String getToken() {
        return token;
    }

    public User getUser() {
        return new User(
                userId,
                firstName,
                lastName,
                email,
                UserTheme.getColor(color)
        );
    }
}
