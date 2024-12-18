package com.example.projectmanagementapp.state;

import androidx.lifecycle.MutableLiveData;

import com.example.projectmanagementapp.models.Project;
import com.example.projectmanagementapp.models.ProjectTheme;
import com.example.projectmanagementapp.models.Task;
import com.example.projectmanagementapp.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProjectState {
    private static ProjectState instance;
    private final MutableLiveData<Project> project;

    private ProjectState() {
        project = new MutableLiveData<Project>(getDefaultProject());
    }

    private Project getDefaultProject(){
        return new Project(-1, null, null, new ArrayList<Task>(), ProjectTheme.BLUE, new ArrayList<User>());
    }

    public static ProjectState getInstance(){
        if(instance == null){
            instance = new ProjectState();

        }
        return instance;
    }

    public void setProject(Project project){
        this.project.setValue(project);
    }

    public Project getProject(){
        return project.getValue();
    }

    public void addMember(final User member) {
        final Project updatedProject= new Project(Objects.requireNonNull(project.getValue()));
        updatedProject.addMember(member);
        project.setValue(updatedProject);

    }
    public void deleteMember(final User member) {
        final Project updatedProject= new Project(Objects.requireNonNull(project.getValue()));
        updatedProject.deleteMember(member);
        project.setValue(updatedProject);
    }

    public void setTheme(ProjectTheme theme){
        final Project newProject = new Project(Objects.requireNonNull(project.getValue()));
        newProject.setTheme(theme);
        project.setValue(newProject);
    }

    public ProjectTheme getTheme(){
        return Objects.requireNonNull(project.getValue()).getTheme();
    }

    public void addTask(Task task){
        final Project updatedProject= new Project(Objects.requireNonNull(project.getValue()));
        updatedProject.addTask(task);
        project.setValue(updatedProject);
    }
    public void deleteTask(Task task){
        final Project updatedProject= new Project(Objects.requireNonNull(project.getValue()));
        updatedProject.deleteTask(task);
        project.setValue(updatedProject);
    }
    public void updateTask(Task task) {
        final Project updatedProject= new Project(Objects.requireNonNull(project.getValue()));
        updatedProject.updateTask(task);
        project.setValue(updatedProject);
    }

    public List<Task> getPendingTasks() {
        List<Task> pendingTasks = new ArrayList<>();
        List<Task> tasks = Objects.requireNonNull(project.getValue()).tasks;
        for (Task t : tasks) {
            if (t.status.equals("In Progress") || t.status.equals("To Do")) {
                pendingTasks.add(t);
            }
        }
        return pendingTasks;
    }

    public List<Task> getFinishedTasks() {
        List<Task> pendingTasks = new ArrayList<>();
        List<Task> tasks = Objects.requireNonNull(project.getValue()).tasks;
        for (Task t : tasks) {
            if (t.status.equals("Completed")) {
                pendingTasks.add(t);
            }
        }
        return pendingTasks;
    }
    public List<Task> getYourTasks(){
        return Objects.requireNonNull(project.getValue()).tasks;
    }

}
