package com.example.projectmanagementapp.data.remote.model;

import com.example.projectmanagementapp.models.Member;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectRequest {
    final private String title;
    final private String description;
    final private int color;
    private final List<MemberResponse> members;
    public ProjectRequest(String title, String description, int color, List<Member> members) {
        final List<MemberResponse> memberResponses = members.stream().map(MemberResponse::new).collect(Collectors.toList());
        this.title = title;
        this.description = description;
        this.color = color;
        this.members = memberResponses;
    }
    public String getDescription() {
        return description;
    }
    public String getTitle() {
        return title;
    }
    public int getColor() { return color; }

}