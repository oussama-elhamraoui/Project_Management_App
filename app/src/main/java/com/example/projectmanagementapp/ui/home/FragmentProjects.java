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
import com.example.projectmanagementapp.models.User;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class FragmentProjects extends Fragment {
    private RecyclerView recyclerView;
    private ProjectsAdapter projectsAdapter;
    private TextView projectsCount;

    // Sample data
    private final ArrayList<Project> projects = new ArrayList<>(Arrays.asList(
            new Project(-1, "First Project", "", new ArrayList<>(Arrays.asList(
                    new Task(1,"","Task 1",  "", "", Date.from(Instant.now())),
                    new Task(2,"","Task 2",  "", "", Date.from(Instant.now())),
                    new Task(3,"","Task 3",  "", "", Date.from(Instant.now()))
            )), ProjectTheme.RED, new ArrayList<>()),
            new Project(-1,"Second Project", "",new ArrayList<>(Arrays.asList(
                    new Task(4,"","Task A", "", "", Date.from(Instant.now())),
                    new Task(5,"","Task B", "", "", Date.from(Instant.now())),
                    new Task(6,"","Task C", "", "", Date.from(Instant.now()))
            )),  ProjectTheme.BLUE, new ArrayList<User>()),
            new Project(-1,"Third Project", "",new ArrayList<>(Arrays.asList(
                    new Task(7,"","Task X", "", "", Date.from(Instant.now()) )
            )),  ProjectTheme.ORANGE, new ArrayList<User>())
    ));

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        // Initialize RecyclerView and TextView
        recyclerView = view.findViewById(R.id.rv_project_card);
        projectsCount = view.findViewById(R.id.tv_project_count);

        // Display the number of projects
        projectsCount.setText(String.valueOf(projects.size()));

        // Set LayoutManager for RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Set up the custom adapter
        projectsAdapter = new ProjectsAdapter(requireContext(), projects);
        recyclerView.setAdapter(projectsAdapter);

        return view;
    }

}
