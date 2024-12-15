package com.example.projectmanagementapp.models;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

public class Task {
    public int id;
    public String name;
    public String description;
    public String status;
    public String priority;
    public Date dueDate;

    public Task(int id, String name, String description, String status, String priority, Date dueDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public Task(int id, String name, String description, Date dueDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = "TO_DO";
        this.priority = "HIGH";
        this.dueDate = dueDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
    //    public Duration getTimeLeft() {
//
//
//        // Calculate the duration
//        Duration duration = Duration.between(LocalDateTime.now(), targetInstant);
//    } // need to calculated from timestamp


}
