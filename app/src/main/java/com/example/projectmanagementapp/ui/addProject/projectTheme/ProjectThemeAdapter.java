package com.example.projectmanagementapp.ui.addProject.projectTheme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmanagementapp.R;
import com.example.projectmanagementapp.models.ProjectTheme;
public class ProjectThemeAdapter extends RecyclerView.Adapter<ProjectThemeAdapter.ProjectThemeViewHolder> {

    private final ProjectTheme[] projectThemes;
    private int selectedIndex;

    public ProjectThemeAdapter(ProjectTheme[] projectThemes, int selectedIndex) {
        this.projectThemes = projectThemes;
        this.selectedIndex = selectedIndex;
    }

    @NonNull
    @Override
    public ProjectThemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_theme, parent, false);
        return new ProjectThemeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectThemeViewHolder holder, int position) {
        ProjectTheme theme = projectThemes[position];
        final int index = position; //* I have to do that I can not used directly
        holder.cardView.setCardBackgroundColor(theme.primaryColor);
        holder.iconView.setVisibility(position == selectedIndex ? View.VISIBLE : View.INVISIBLE);

        // Set click listener to update the selected index
        holder.itemView.setOnClickListener(v -> {
            // Only update if the clicked position is not the currently selected one
            if (selectedIndex != index) {
                int previousIndex = selectedIndex;
                selectedIndex = index;

                // Notify the adapter that the previously selected and newly selected items should be updated
                notifyItemChanged(previousIndex);
                notifyItemChanged(selectedIndex);
            }
        });
    }


    @Override
    public int getItemCount() {
        return projectThemes.length;
    }

    public static class ProjectThemeViewHolder extends RecyclerView.ViewHolder {

        final CardView cardView;
        final View iconView;

        public ProjectThemeViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv_theme);
            iconView = itemView.findViewById(R.id.icon_view);
        }
    }
}
