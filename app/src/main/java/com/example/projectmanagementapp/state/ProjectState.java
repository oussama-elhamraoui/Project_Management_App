package com.example.projectmanagementapp.state;

import androidx.lifecycle.MutableLiveData;

import com.example.projectmanagementapp.models.Project;
import com.example.projectmanagementapp.models.ProjectTheme;
import com.example.projectmanagementapp.models.User;
import com.example.projectmanagementapp.models.UserTheme;

import java.util.ArrayList;
import java.util.List;

public class ProjectState {
    private static ProjectState instance;
    private Project project;
    private final MutableLiveData<List<User>> members;

    private ProjectState() {
        this.members = new MutableLiveData<>();
        this.members.setValue(new ArrayList<>()); // Initialize with an empty list
    }

    public static ProjectState getInstance(){
        if(instance == null){
            instance = new ProjectState();
        }
        return instance;
    }

    public Project getProject(){
        return project;
    }

    public void addMember(final User member) {
        List<User> updatedMembers = members.getValue();

        assert updatedMembers != null;
        updatedMembers.add(member);

        members.setValue(updatedMembers);
    }

    public void setTheme(ProjectTheme theme){
        project.theme = theme;
    }

    public ProjectTheme getTheme(){
        return project.theme;
    }
    public void deleteMember(final User member){
        List<User> updatedMembers = members.getValue();

        assert updatedMembers != null;
        updatedMembers.remove(member);

        members.setValue(updatedMembers);
    }


}
