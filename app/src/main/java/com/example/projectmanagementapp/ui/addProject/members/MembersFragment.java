package com.example.projectmanagementapp.ui.addProject.members;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectmanagementapp.R;
import com.example.projectmanagementapp.models.User;
import com.example.projectmanagementapp.models.UserTheme;

import java.util.ArrayList;
import java.util.List;


public class MembersFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_members, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_members);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Example data
        List<User> members = new ArrayList<>();
        members.add(new User("Alice", "Project Manager", UserTheme.values[0]));
        members.add(new User("Bob", "Developer", UserTheme.values[1]));
        members.add(new User("Carol", "Designer", UserTheme.values[2]));

        MembersAdapter adapter = new MembersAdapter(members);
        recyclerView.setAdapter(adapter);

        return view;

    }


}