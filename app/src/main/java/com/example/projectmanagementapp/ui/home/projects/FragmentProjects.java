package com.example.projectmanagementapp.ui.home.projects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmanagementapp.R;
import com.example.projectmanagementapp.models.Project;
import com.example.projectmanagementapp.models.ProjectTheme;
import com.example.projectmanagementapp.models.Task;
import com.example.projectmanagementapp.models.User;
import com.example.projectmanagementapp.state.UserProjectsState;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class FragmentProjects extends Fragment {
    private RecyclerView recyclerView;
    private ProjectsAdapter projectsAdapter;
    private TextView projectsCount;

    // Sample data

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        // Initialize RecyclerView and TextView
        recyclerView = view.findViewById(R.id.rv_project_card);
        projectsCount = view.findViewById(R.id.tv_project_count);

        // Set LayoutManager for RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Display the number of projects
        UserProjectsState.getInstance().projects.observe(getViewLifecycleOwner(), updatedProjects ->{
            projectsCount.setText(String.valueOf(updatedProjects.size()));
            projectsAdapter = new ProjectsAdapter(requireContext(), updatedProjects);
            recyclerView.setAdapter(projectsAdapter);
        });


        return view;
    }

}
