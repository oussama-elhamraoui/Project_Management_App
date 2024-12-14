package com.example.projectmanagementapp.data.remote.model;

import com.example.projectmanagementapp.models.User;

import java.util.List;

public class ProjectRequest {

    final private String title;
    final private String description;
    final private int color;
    private final List<User> members;

    public ProjectRequest(String title, String description, int color, List<User> members) {
        this.title = title;
        this.description = description;
        this.color = color;
        this.members = members;
    }

    public String getDescription() {
        return description;
    }
    public String getTitle() {
        return title;
    }
    public int getColor() { return color; }



}