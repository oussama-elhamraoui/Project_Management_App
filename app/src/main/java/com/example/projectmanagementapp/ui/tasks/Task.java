package com.example.projectmanagementapp.ui.tasks;

import java.time.Duration;

public class Task {
    String taskName;
    Duration timeLeft;

    public Task(String taskName, Duration timeLeft) {
        this.taskName = taskName;
        this.timeLeft = timeLeft;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Duration getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(Duration timeLeft) {
        this.timeLeft = timeLeft;
    }
}
