package com.example.projectmanagementapp.data.remote.model;

import com.example.projectmanagementapp.models.Project;
import com.example.projectmanagementapp.models.Task;

import java.time.LocalDateTime;
import java.util.Date;

public class TaskResponse {
    private int id;


    private Project project;


    private String name;


    private String description;


    private String status;


    private String priority;


    private Date dueDate;


    public TaskResponse(int id, Project project, String name, String description, String status, String priority, Date dueDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.project = project;
        this.name = name;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public TaskResponse(int id, String name, String description, Date dueDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = "TO_DO";
        this.priority = "HIGH";
        this.dueDate = dueDate;
    }

    public Task getTask(){
        return new Task(
                id,
                name,
                description,
                dueDate
        );
    }
    public int getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getPriority() {
        return priority;
    }

    public Date getDueDate() {
        return dueDate;
    }
}
