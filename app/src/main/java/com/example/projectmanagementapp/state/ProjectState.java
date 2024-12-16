package com.example.projectmanagementapp.state;

import androidx.lifecycle.MutableLiveData;

import com.example.projectmanagementapp.models.Project;
import com.example.projectmanagementapp.models.ProjectTheme;
import com.example.projectmanagementapp.models.Task;
import com.example.projectmanagementapp.models.User;

import java.util.ArrayList;

public class ProjectState {
    private static ProjectState instance;
    private MutableLiveData<Project> project;

    private ProjectState() {
        project = new MutableLiveData<Project>(new Project(-1, "", "", new ArrayList<Task>(), ProjectTheme.BLUE, new ArrayList<User>()));
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
        final Project updatedProject= new Project(project.getValue());
        updatedProject.addMember(member);
        project.setValue(updatedProject);

    }
    public void deleteMember(final User member) {
        final Project updatedProject= new Project(project.getValue());
        updatedProject.deleteMember(member);
        project.setValue(updatedProject);
    }

    public void setTheme(ProjectTheme theme){
        project.getValue();
    }

    public ProjectTheme getTheme(){
        return project.getValue().getTheme();
    }

    public void addTask(Task task){
        final Project updatedProject= new Project(project.getValue());
        updatedProject.addTask(task);
        project.setValue(updatedProject);
    }
    public void deleteTask(Task task){
        final Project updatedProject= new Project(project.getValue());
        updatedProject.deleteTask(task);
        project.setValue(updatedProject);
    }
    public void updateTask(Task task) {
        final Project updatedProject= new Project(project.getValue());
        updatedProject.updateTask(task);
        project.setValue(updatedProject);
    }

}
