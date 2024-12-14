package com.example.projectmanagementapp.models;

import java.util.ArrayList;
import java.util.List;

import kotlin.NotImplementedError;

public class Project {


    public int id;
    public String name;
    public List<Task> tasks;
    public ProjectTheme theme;
    public String description;
    public List<User> members;

    public Project(int id, String name, String description, List<Task> tasks, ProjectTheme theme,List<User> members){
        this.id = id;
        this.tasks = tasks;
        this.name = name;
        this.description = description;
        this.theme = theme;
        this.members = members;

    }

    public int getTasksLeft() {
        return tasks.size() - 1; // getTasksByStatus(Status.inProgress).length
    }

    public Task[] getTasksByStatus() {
        throw new NotImplementedError("Implement Tasks");
        //return Arrays.stream(this.tasks).filter((task) -> {return task.status == status;});
    }
    public String getName() {
        return name;
    }

    public List<User> getMembers(){
        return members;
    }


    public int getProgress() {
        return 80; // (int)(getTasksByStatus(Status.Done).length / tasks.length * 100);
    }
}

