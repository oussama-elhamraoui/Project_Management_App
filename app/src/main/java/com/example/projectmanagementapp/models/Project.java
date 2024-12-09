package com.example.projectmanagementapp.models;

import kotlin.NotImplementedError;

public class Project {
    String name;
    Task[] tasks;

    public Project(String name, Task[] tasks){
        this.tasks = tasks;
        this.name = name;
    }

    int getTasksLeft(){
        return tasks.length - 2; // later I will replace the 2 by the
    }

    Task[] getTasksByStatus(){
        throw new NotImplementedError("Implement Tasks");
    }
    double getProgress(){
        return 0.3; // getTasksByStatus(Status.Done).length / tasks.length;
    }
}
