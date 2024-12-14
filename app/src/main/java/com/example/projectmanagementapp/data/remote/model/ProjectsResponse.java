package com.example.projectmanagementapp.data.remote.model;

import com.example.projectmanagementapp.models.ProjectTheme;

public class ProjectsResponse {
    private int id;
    private String title;
    private String description;

    private int color;

    public int getId() {
        return id;
    }

//    public void setId(int id) {
//        this.id = id;
//    } // why ???????

    public String getTitle() {
        return title;
    }

    public int getColor(){
        return color;
    }
//    public void setTitle(String title) {
//        this.title = title;
//    }

    public String getDescription() {
        return description;
    }

//    public void setDescription(String description) {
//        this.description = description;
//    } // WHy you need setters in request
}
