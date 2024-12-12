package com.example.projectmanagementapp.models;

public class UserTheme {
    final int color;
    private  UserTheme(final int color){
        this.color = color;
    }
    public static final UserTheme[] values = {
            new UserTheme(0xFFFF6666),
            new UserTheme(0xFF66BFFF),
            new UserTheme(0xFF724CE4),
            new UserTheme(0xFF4C56E4),
            new UserTheme(0xFFCD4CE4),
    };
}
