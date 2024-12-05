package com.example.projectmanagementapp.data.model;

public class SignUpRequest {
    private String email;
    private String password;
    private String firstname;
    private String lastname;

    public SignUpRequest(String email , String password , String firstname , String lastname) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }
}
