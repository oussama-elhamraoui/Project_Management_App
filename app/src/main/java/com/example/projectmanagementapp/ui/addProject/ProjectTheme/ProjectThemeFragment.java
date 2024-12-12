package com.example.projectmanagementapp.ui.addProject.ProjectTheme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmanagementapp.R;
import com.example.projectmanagementapp.models.ProjectTheme;

public class ProjectThemeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProjectThemeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_project_themes, container, false);

        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.rv_themes);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Set up Adapter with ProjectTheme data
        int selectedIndex = 0;
        adapter = new ProjectThemeAdapter(ProjectTheme.values, selectedIndex);
        recyclerView.setAdapter(adapter);


        return view;
    }
}