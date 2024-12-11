package com.example.projectmanagementapp.models;

import java.time.Duration;

public class Task {
    public String name;
    Duration timeLeft; // this is should be calculated by the task timestamp so in the future you will remove this attribute
    public Task(String taskName, Duration timeLeft) {
        this.name = taskName;
        this.timeLeft = timeLeft;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Duration getTimeLeft() {
        return timeLeft;
    } // need to calculated from timestamp


}
