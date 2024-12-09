package com.example.projectmanagementapp.ui.tasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmanagementapp.R;
import com.example.projectmanagementapp.models.Task;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {

    private List<Task> tasksList;

    public TasksAdapter(List<Task> tasksList) {
        this.tasksList = tasksList;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_card,parent,false);
        return new TasksViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        Task task= tasksList.get(position);
        holder.taskNameTextView.setText(task.taskName);
        holder.durationLeftTextView.setText(task.timeLeft+" Left");
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }


    public static class TasksViewHolder extends RecyclerView.ViewHolder{
        TextView taskNameTextView;
        TextView durationLeftTextView;

        public TasksViewHolder(@NonNull View itemView) {
            super(itemView);
            taskNameTextView = itemView.findViewById(R.id.task_name_text_view);
            durationLeftTextView = itemView.findViewById(R.id.duration_left_text_view);
        }
    }
}
