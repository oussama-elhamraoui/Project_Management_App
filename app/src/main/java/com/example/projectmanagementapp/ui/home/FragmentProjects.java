package com.example.projectmanagementapp.ui.home;

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

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;

public class FragmentProjects extends Fragment {
    private RecyclerView recyclerView;
    private ProjectsAdapter projectsAdapter;
    private TextView projectCount;

    // Sample data
    private final ArrayList<Project> projects = new ArrayList<>(Arrays.asList(
            new Project("First Project", new Task[]{
                    new Task("Task 1", Duration.ZERO),
                    new Task("Task 2", Duration.ZERO),
                    new Task("Task 3", Duration.ZERO)
            }, ProjectTheme.RED),
            new Project("Second Project", new Task[]{
                    new Task("Task A", Duration.ZERO),
                    new Task("Task B", Duration.ZERO),
                    new Task("Task C", Duration.ZERO)
            },  ProjectTheme.BLUE),
            new Project("Third Project", new Task[]{
                    new Task("Task X", Duration.ZERO)
            },  ProjectTheme.ORANGE)
    ));

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        // Initialize RecyclerView and TextView
        recyclerView = view.findViewById(R.id.rv_project_card);
        projectCount = view.findViewById(R.id.tv_project_count);

        // Display the number of projects
        projectCount.setText(String.valueOf(projects.size()));

        // Set LayoutManager for RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Set up the custom adapter
        projectsAdapter = new ProjectsAdapter(requireContext(), projects);
        recyclerView.setAdapter(projectsAdapter);

        return view;
    }

}