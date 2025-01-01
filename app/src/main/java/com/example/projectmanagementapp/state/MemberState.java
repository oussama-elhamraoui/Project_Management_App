package com.example.projectmanagementapp.state;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class MemberState {
    private static MemberState instance;


    @NonNull
    public MutableLiveData<Boolean> isAdmin = new MutableLiveData<Boolean>(false);

    public String userName = "";
    public String email = "";

    private MemberState(){

    }
    @NonNull
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
