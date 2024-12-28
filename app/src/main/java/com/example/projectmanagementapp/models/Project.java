package com.example.projectmanagementapp.models;

import java.util.ArrayList;
import java.util.List;


public class Project {
    public int id;
    public String name;
    public List<Task> tasks;
    public ProjectTheme theme;
    public String description;
    public List<Member> members;

    public Project(
            int id,
            String name,
            String description,
            List<Task> tasks,
            ProjectTheme theme,
            List<Member> members
        ) {
        this.id = id;
        this.tasks = (tasks != null) ? new ArrayList<>(tasks) : new ArrayList<>();
        this.name = name;
        this.description = description;
        this.theme = theme;
        this.members = members;
    }
    public Project(Project existingProject) {
        this.id = existingProject.id;
        this.name = existingProject.name;
        this.description = existingProject.description;
        this.tasks = new ArrayList<>(existingProject.tasks);
        this.theme = existingProject.theme;
        this.members = new ArrayList<>(existingProject.members);
    }
    public void addMember(Member member){
        members.add(member);
    }
    public void deleteMember(Member member){
        members.remove(member);
    }
    public int getTasksLeft() {
        return getTasksByStatus("To Do").size() + getTasksByStatus("In Progress").size();
    }
    public List<Task> getTasksByStatus(String status) {
         ArrayList<Task> newTasks = new ArrayList<>();
         for(final Task task : this.tasks){
             if(task.status.equals(status)){
                 newTasks.add(task);
             }
         }
         return newTasks;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public List<Member> getMembers() {
        return members;
    }
    public void setMembers(List<Member> members) {
        this.members = members;
    }
    public int getProgress() {
        if (tasks.isEmpty()) {
            return 0; // Default progress when there are no tasks
        }
        return (int)(getTasksByStatus("COMPLETED").size() / tasks.size() * 100);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public ProjectTheme getTheme() {
        return theme;
    }

    public void setTheme(ProjectTheme theme) {
        this.theme = theme;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addTask(Task task){
        tasks.add(task);
    }
    public void deleteTask(Task task){
        tasks.remove(task);
    }
    public void updateTask(Task task) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).id == task.id) {
                tasks.set(i, task);
                return;
            }
        }
    }
}
