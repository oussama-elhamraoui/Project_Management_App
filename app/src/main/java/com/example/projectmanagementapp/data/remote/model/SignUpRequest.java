package com.example.projectmanagementapp.data.remote.model;

public class SignUpRequest {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private int color;
    public SignUpRequest(String email, String password, String firstname, String lastname, int color) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.color = color;
    }
}
