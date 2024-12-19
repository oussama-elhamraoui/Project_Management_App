package com.example.projectmanagementapp.models;

import androidx.annotation.NonNull;

public class User {
    private final int userId;
    String firstName;
    String lastName;

    final public String email;
    final UserTheme theme;

    public User(final int id, final String firstName, final String lastName, final String email, final UserTheme theme) {
        this.userId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.theme = theme;
        this.email = email;
    }

    public final int getColor() {
        return theme.color;
    }

    public final String getProfile() {
        if (this.firstName == null || this.lastName == null ||
                this.firstName.isEmpty() || this.lastName.isEmpty()) {
            throw new IllegalArgumentException("First name and last name must be non-empty and non-null.");
        }
        return String.valueOf(this.firstName.toUpperCase().charAt(0))
                + this.lastName.toUpperCase().charAt(0);
    }

    public int getUserId() {
        return userId;
    }

    public final String getUsername(){
        return this.firstName + " "+  this.lastName;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", theme=" + theme +
                '}';
    }
}
