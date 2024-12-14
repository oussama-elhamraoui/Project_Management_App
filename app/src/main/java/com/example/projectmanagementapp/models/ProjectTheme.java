package com.example.projectmanagementapp.models;

public class ProjectTheme {
    public final int primaryColor;
    public final int secondaryColor;

    ProjectTheme(int primaryColor, int secondaryColor) {
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    public static final ProjectTheme BLUE = new ProjectTheme(0xFF0087FF, 0xFFE7F3FF);
    public static final ProjectTheme RED = new ProjectTheme(0xFFFF5353, 0xFFFFE3E3);
    public static final ProjectTheme ORANGE = new ProjectTheme(0xFFFF7D53, 0xFFFFE9E1);

    public static final ProjectTheme PINK = new ProjectTheme(0xFFF478B8, 0xFFFFE2F1);

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
