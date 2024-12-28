package com.example.projectmanagementapp.models;

public class Member {
    final public User user;
    public boolean isAdmin = false;

    public Member(User user, boolean isAdmin){
        this.user = user;
        this.isAdmin = isAdmin;
    }
}
