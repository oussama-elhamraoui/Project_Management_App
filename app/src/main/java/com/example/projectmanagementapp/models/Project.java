package com.example.projectmanagementapp.models;

import kotlin.NotImplementedError;

public class Project {
    public int id;
    public String name;
    public Task[] tasks;
    public ProjectTheme theme;
    public String description;

    public Project(int id, String name, String description, Task[] tasks, ProjectTheme theme){
        this.id = id;
        this.tasks = tasks;
        this.name = name;
        this.description = description;
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

