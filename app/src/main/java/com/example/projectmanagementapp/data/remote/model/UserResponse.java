package com.example.projectmanagementapp.data.remote.model;

import com.example.projectmanagementapp.models.User;
import com.example.projectmanagementapp.models.UserTheme;

public class UserResponse {
    private final int id;
    private final String email;

    private final String firstname;
    private final String lastname;

    private final int color;

    public UserResponse(final int userId, final String email,
                        final String firstname, final String lastname,
                        int color
    ) {
        this.id = userId;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.color = color;
    }

    public User getUser() {
        return new User(id, firstname, lastname, email, UserTheme.getColor(color));
    }


    public String getEmail() {
        return email;
    }

}