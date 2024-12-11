package com.example.projectmanagementapp.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmanagementapp.R;
import com.example.projectmanagementapp.models.Project;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ProjectViewHolder> {

    private final Context context;
    private final ArrayList<Project> projects;

    public ProjectsAdapter(Context context, ArrayList<Project> projects) {
        this.context = context;
        this.projects = projects;
    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_project_card, parent, false);
        return new ProjectViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        // Get the current project
        final Project project = projects.get(position);

        // Bind data to the layout
        holder.tvProjectName.setText(project.name);
        holder.tvTasksLeft.setText(project.getTasksLeft() + " tasks left");

        holder.progressBar.setProgress(project.getProgress());
        holder.progressBar.setIndicatorColor(project.theme.primaryColor);

        holder.linearLayout.setBackgroundColor(project.theme.secondaryColor);
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    static class ProjectViewHolder extends RecyclerView.ViewHolder {

        final TextView tvProjectName;
        final TextView tvTasksLeft;
        final LinearProgressIndicator progressBar;
        final LinearLayout linearLayout;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProjectName = itemView.findViewById(R.id.tv_project_name);
            tvTasksLeft = itemView.findViewById(R.id.tv_tasks_left);
            progressBar = itemView.findViewById(R.id.progress_bar_project);
            linearLayout = itemView.findViewById(R.id.layout_project_card);
        }
    }
}
