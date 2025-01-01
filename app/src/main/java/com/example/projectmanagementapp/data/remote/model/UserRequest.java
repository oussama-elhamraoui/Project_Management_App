package com.example.projectmanagementapp.data.remote.model;

public class UserRequest {
    private String email;
    private String username;
    public UserRequest(final String username, final String email) {
        this.username = username;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    public String getUsername() {
        return username;
    }

}
