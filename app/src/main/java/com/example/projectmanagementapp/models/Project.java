package com.example.projectmanagementapp.models;

import kotlin.NotImplementedError;

public class Project {
    public String getName() {
        return name;
    }

    public String name;
    public Task[] tasks;
    public ProjectTheme theme;

    public Project(String name, Task[] tasks, ProjectTheme theme){
        this.tasks = tasks;
        this.name = name;
        this.theme = theme;
    }

    public int getTasksLeft() {
        return tasks.length - 1; // getTasksByStatus(Status.inProgress).length
    }

    public Task[] getTasksByStatus() {
        throw new NotImplementedError("Implement Tasks");
        //return Arrays.stream(this.tasks).filter((task) -> {return task.status == status;});
    }

    //
    public int getProgress() {
        return 80; // (int)(getTasksByStatus(Status.Done).length / tasks.length * 100);
    }
}

