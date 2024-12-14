package com.example.projectmanagementapp.data.remote.model;

public class ProjectRequest {

    final private String title;
    final private String description;
    final private int color;

    public ProjectRequest(String title, String description, int color) {
        this.title = title;
        this.description = description;
        this.color = color;
    }

    public String getDescription() {
        return description;
    }
    public String getTitle() {
        return title;
    }
    public int getColor() { return color; }


}