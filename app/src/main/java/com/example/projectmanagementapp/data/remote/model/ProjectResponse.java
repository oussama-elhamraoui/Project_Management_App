package com.example.projectmanagementapp.data.remote.model;

import com.example.projectmanagementapp.models.Member;
import com.example.projectmanagementapp.models.Project;
import com.example.projectmanagementapp.models.ProjectTheme;
import com.example.projectmanagementapp.models.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectResponse {
    private int id;
    private String title;
    private String description;
    private List<Task> tasks;
    private int color;
    private List<MemberResponse> members;

    public Project getProject(){

        final List<Member> serializedMembers = members == null ? new ArrayList<>() : members.stream().map(MemberResponse::getMember).collect(Collectors.toList());

        return new Project(
                id,
                title,
                description,
                tasks,
                ProjectTheme.getColor(color),
                serializedMembers
        );
    }

    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public int getColor(){
        return color;
    }

    public String getDescription() {
        return description;
    }

    public void setMembers(List<MemberResponse> members) {
        this.members = members;
    }

}
