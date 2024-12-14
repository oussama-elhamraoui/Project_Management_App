package com.example.projectmanagementapp.state;

import android.util.Log;

import com.example.projectmanagementapp.models.User;

public class UserState {
    private static UserState instance;
    private User user;

    private UserState() {}

    public static UserState getInstance() {
        if (instance == null) {
            instance = new UserState();
        }
        return instance;
    }

    public void setUser(User user) {
        Log.d("UserState", user.toString());
        this.user = user;
    }

    public String getUsername(){
        return user.getUsername();
    }
    public int getColor(){
        return user.getColor();
    }

    public User getUser(){
        return user;
    }

}