package com.example.projectmanagementapp.data.remote.model;

import com.example.projectmanagementapp.models.Member;
import com.example.projectmanagementapp.models.User;
import com.example.projectmanagementapp.models.UserTheme;

public class MemberResponse {
    private int userId;
    private String firstName;
    private String lastName;
    private String role;
    private String email;

    private int color;

    public int getUserId() {
        return userId;
    }

    public MemberResponse(Member member) {
        User user = member.user;
        this.userId = user.getId();
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.email = user.email;
        this.color = user.theme.color;
        this.role = member.isAdmin ? "Owner" : "Contributor";
    }
    public Member getMember(){
        return new Member(
                new User(userId, firstName, lastName, email, UserTheme.getColor(color)),
                role.equals("Owner")
        );
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
