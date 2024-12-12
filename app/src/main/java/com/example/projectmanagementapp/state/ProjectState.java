package com.example.projectmanagementapp.state;

import androidx.lifecycle.MutableLiveData;

import com.example.projectmanagementapp.models.Project;
import com.example.projectmanagementapp.models.User;

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

    public void addMember(final User member) {
        // Get the current list of members or initialize an empty list if null
        List<User> updatedMembers = members.getValue();
        if (updatedMembers == null) {
            updatedMembers = new ArrayList<>();
        }

        // Add the new member to the list
        updatedMembers.add(member);

        // Update the original members list
        members.setValue(updatedMembers);
    }


}
