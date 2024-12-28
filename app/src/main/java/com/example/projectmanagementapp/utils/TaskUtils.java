package com.example.projectmanagementapp.utils;

import com.example.projectmanagementapp.models.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskUtils {
    public static Map<String, Integer> countTaskStatus(List<Task> tasksList) {
        int pendingCount = 0;
        int finishedCount = 0;
        int yourTasksCount = 0;

        for (Task task : tasksList) {
            switch (task.getStatus()) {
                case "TO_DO":
                case "IN_PROGRESS":
                    pendingCount++;
                    break;
                case "COMPLETED":
                    finishedCount++;
                    break;
            }
        }

        Map<String, Integer> counts = new HashMap<>();
        counts.put("pending", pendingCount);
        counts.put("finished", finishedCount);
        counts.put("yourTasks", yourTasksCount);

        return counts;
    }
}
