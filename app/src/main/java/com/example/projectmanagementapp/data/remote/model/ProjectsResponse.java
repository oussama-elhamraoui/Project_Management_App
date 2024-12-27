package com.example.projectmanagementapp.data.remote.model;

import com.example.projectmanagementapp.models.Project;
import com.example.projectmanagementapp.models.ProjectTheme;
import com.example.projectmanagementapp.models.Task;
import com.example.projectmanagementapp.models.User;

import java.util.List;

public class ProjectsResponse {
    private int id;
    private String title;
    private String description;

    private List<Task> tasks;
    private int color;




    private Integer creatorId; // Nullable Integer for creatorId
    private String startDate;  // Nullable startDate as a String (consider using LocalDateTime if needed)
    private String endDate;    // Nullable endDate as a String (consider using LocalDateTime if needed)
    private String createdAt;  // ISO-8601 timestamp format
    private String updatedAt;  // ISO-8601 timestamp format
    private String status;

    private List<User> members;

    public Project getProject(){
        return new Project(
                id,
                title,
                description,
                tasks,
                ProjectTheme.getColor(color),
                members
        );
    }

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
