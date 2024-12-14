package com.example.projectmanagementapp.models;

import java.util.Random;

public class UserTheme {
    final int color;

    private UserTheme(final int color) {
        this.color = color;
    }

    public static final UserTheme[] values = {
            new UserTheme(0xFFFF6666),
            new UserTheme(0xFF66BFFF),
            new UserTheme(0xFF724CE4),
            new UserTheme(0xFF4C56E4),
            new UserTheme(0xFFCD4CE4),
    };

    // Method to return a random color from the values array
    public static int randomColor() {
        Random random = new Random();
        int index = random.nextInt(values.length);
        return values[index].color;
    }

    // Method to get a color if it exists, or throw an exception
    public static UserTheme getColor(int color) {
        for (UserTheme theme : values) {
            if (theme.color == color) {
                return theme;
            }
        }
        return values[0];
    }
}
