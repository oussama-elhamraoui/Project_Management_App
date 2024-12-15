package com.example.projectmanagementapp.data.remote.model;

import com.example.projectmanagementapp.models.Project;

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
}
