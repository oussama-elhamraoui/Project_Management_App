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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    public String getDurationLeft() {
        // Calculate the time left
        Instant dueDateInstant = dueDate.toInstant();
        Instant now = Instant.now();

        if (now.isAfter(dueDateInstant)) {
            return "Task is overdue";
        }

        Duration duration = Duration.between(now, dueDateInstant);

        long minutesLeft = duration.toMinutes();

        if (minutesLeft == 1) {
            return "1 minute left";
        } else if (minutesLeft < 60) {
            return minutesLeft + " minutes left";
        } else if (minutesLeft < 1440) { // 1440 minutes in a day
            long hoursLeft = duration.toHours();
            if (hoursLeft == 1) {
                return "1 hour left";
            } else {
                return hoursLeft + " hours left";
            }
        } else {
            long daysLeft = duration.toDays();
            if (daysLeft == 1) {
                return "1 day left";
            } else {
                return daysLeft + " days left";
            }
        }
    }


    public int getId() {
        return this.id;
    }
}
