package com.example.projectmanagementapp.models;

public class User {
    final String firstName;
    final String lastName;

    final UserTheme theme;

    private User(final String firstName, final String lastName, final UserTheme theme) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.theme = theme;
    }

    public final String getProfile() {
        if (this.firstName == null || this.lastName == null ||
                this.firstName.isEmpty() || this.lastName.isEmpty()) {
            throw new IllegalArgumentException("First name and last name must be non-empty and non-null.");
        }
        return String.valueOf(this.firstName.toUpperCase().charAt(0))
                + this.lastName.toUpperCase().charAt(0);

    }

}
