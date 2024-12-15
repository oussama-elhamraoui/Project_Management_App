package com.example.projectmanagementapp.ui.tasks;

import com.example.projectmanagementapp.data.remote.model.TaskResponse;

public interface TaskActionListener {
    void onEditTask(TaskResponse task);
    void onDeleteTask(TaskResponse task);
}