package com.example.projectmanagementapp.state;

import androidx.lifecycle.MutableLiveData;

public class MemberState {
    private static MemberState instance;


    public MutableLiveData<Boolean> isAdmin = new MutableLiveData<Boolean>(false);

    public String userName = "";
    public String email = "";

    private MemberState(){

    }
    public static MemberState getInstance(){
        if(instance == null){
            instance = new MemberState();
        }
        return instance;
    }

    public void toggle(){
        isAdmin.setValue(Boolean.FALSE.equals(isAdmin.getValue())); // isAdmin.setValue(!(isAdmin.getValue()))
    }

}
