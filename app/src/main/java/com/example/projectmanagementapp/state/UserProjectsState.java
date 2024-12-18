package com.example.projectmanagementapp.state;

import androidx.lifecycle.MutableLiveData;

import com.example.projectmanagementapp.models.Project;

import java.util.ArrayList;
import java.util.List;

public class UserProjectsState {
    private static UserProjectsState instance;

    public final MutableLiveData<List<Project>> projects;

    private UserProjectsState(){
        projects = new MutableLiveData<>(new ArrayList<Project>());
    }

    public static UserProjectsState getInstance(){
        if(instance == null){
            instance = new UserProjectsState();
        }
        return instance;
    }

    public List<Project> getProjects(){
        return projects.getValue();
    }

    public void addProject(Project project){
        final List<Project> updatedProjects = projects.getValue();
        assert updatedProjects != null;
        updatedProjects.add(project);
        projects.setValue(updatedProjects);
    }
    public void deleteProject(Project project){
        final List<Project> updatedProjects = projects.getValue();
        assert updatedProjects != null;
        updatedProjects.remove(project);
        projects.setValue(updatedProjects);
    }

    public void setProjects(List<Project> projects){
        this.projects.setValue(projects);
    }

}
