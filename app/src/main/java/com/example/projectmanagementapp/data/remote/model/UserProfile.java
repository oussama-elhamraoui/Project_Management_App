package com.example.projectmanagementapp.data.remote.model;

public class UserProfile {
    private final String userId;
    private final String email;
    private final String firstname;
    private final String lastname;
    public UserProfile(String userId, String email, String firstname,String lastname) {
        this.userId = userId;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }
    public String getUserId() {
        return userId;
    }
    public String getEmail() {
        return email;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
}