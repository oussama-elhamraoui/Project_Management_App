package com.example.projectmanagementapp.models;

import androidx.annotation.NonNull;

public class User {
    public static final String SEPARATOR = ",";
    private final int id;
    public String firstName;
    public String lastName;

    final public String email;
    public final UserTheme theme;

    public User(final int id, final String firstName, final String lastName, final String email, final UserTheme theme) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public final String getUsername(){
        return this.firstName + " "+  this.lastName;
    }


    public String serialiaze() {
        return id + SEPARATOR +
                firstName + SEPARATOR +
                lastName + SEPARATOR +
                email + SEPARATOR +
                theme.color;
    }

    public User deserialize(String value) {
        final String[] values = value.split(SEPARATOR);
        final int id = Integer.parseInt(values[0]);
        final String firstName = values[1];
        final String lastName = values[2];
        final String email = values[3];
        final UserTheme theme = UserTheme.getColor(Integer.parseInt(values[4]));
        return new User(
                id,
                firstName,
                lastName,
                email,
                theme
        );
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "userId=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", theme=" + theme +
                '}';
    }
}
