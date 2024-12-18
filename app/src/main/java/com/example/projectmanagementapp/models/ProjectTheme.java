package com.example.projectmanagementapp.models;

public class ProjectTheme {
    public final int primaryColor;
    public final int secondaryColor;

    // Just for debugging;
    public final String name;
    ProjectTheme(int primaryColor, int secondaryColor, String name) {
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.name = name;
    }

    public static final ProjectTheme BLUE = new ProjectTheme(0xFF0087FF, 0xFFE7F3FF, "Blue");
    public static final ProjectTheme RED = new ProjectTheme(0xFFFF5353, 0xFFFFE3E3, "Red");
    public static final ProjectTheme ORANGE = new ProjectTheme(0xFFFF7D53, 0xFFFFE9E1, "Orange");

    public static final ProjectTheme PINK = new ProjectTheme(0xFFF478B8, 0xFFFFE2F1, "Pink");

    public static final ProjectTheme[] values = {
            BLUE,
            RED,
            ORANGE,
            PINK,
    };

    public static ProjectTheme getColor(int color) {
        for (ProjectTheme theme : values) {
            if (theme.primaryColor == color) {
                return theme;
            }
        }
        return values[0];
    }

}
