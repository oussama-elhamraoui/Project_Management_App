package com.example.projectmanagementapp.data.remote.model;

import androidx.annotation.NonNull;

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
    private int color;
    private List<TaskResponse> tasks;
    private List<MemberResponse> members;


    public Project getProject(){
        final List<Member> serializedMembers = members == null ? new ArrayList<>() : members.stream().map(MemberResponse::getMember).collect(Collectors.toList());
        final List<Task> serializedTasks = tasks == null ? new ArrayList<>() : tasks.stream().map(TaskResponse::getTask).collect(Collectors.toList());
        System.out.println("Inside getProject");
        if(tasks != null){
            System.out.println(tasks);
        } else{
            System.out.println("Tasks are null");
        }

        return new Project(
                id,
                title,
                description,
                serializedTasks,
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

    @NonNull
    @Override
    public String toString() {
        return "ClassName{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", color=" + color +
                ", tasks=" + tasks +
                ", members=" + members +
                '}';
    }


}
