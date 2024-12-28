package com.example.projectmanagementapp.ui.tasks;

import java.util.Map;

public interface TaskCountsListener {
    void onTaskCountsUpdated(Map<String, Integer> counts);
}
