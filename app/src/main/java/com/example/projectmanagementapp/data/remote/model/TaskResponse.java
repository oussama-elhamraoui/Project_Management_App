package com.example.projectmanagementapp.data.remote.model;

import androidx.annotation.NonNull;

import com.example.projectmanagementapp.models.Project;
import com.example.projectmanagementapp.models.Task;

import java.time.LocalDateTime;
import java.util.Date;

public class TaskResponse {
    private final int id;
    private final String name;
    private final String description;
    private final  String status;
    private final String priority;
    private final Date dueDate;

    public TaskResponse(int id, Project project, String name, String description, String status, String priority, Date dueDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
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
    @NonNull
    @Override
    public String toString() {
        return "TaskResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", dueDate=" + dueDate +
                '}';
    }
    public int getId() {
        return id;
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
