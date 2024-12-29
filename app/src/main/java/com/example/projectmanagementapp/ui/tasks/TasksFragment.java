package com.example.projectmanagementapp.ui.tasks;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectmanagementapp.R;
import com.example.projectmanagementapp.data.remote.model.TaskResponse;
import com.example.projectmanagementapp.models.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class TasksFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Task> tasksList;
    private TasksAdapter tasksAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        // Inflate the layout for this fragment
        recyclerView = view.findViewById(R.id.task_recycler_view);
        tasksList = new ArrayList<>();
        int id = 0; // this is a temp solution plz delete it you need to get the id from the backend

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            Task task1 = new Task(id,"task 1", Duration.between(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
//            tasksList.add(task1);
        }
        id++;
        tasksAdapter = new TasksAdapter(tasksList,tasksList, (TaskCountsListener) this, (TasksAdapter.OnTaskStatusChangeListener) this, (TasksAdapter.OnTaskRemovedListener) this);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(tasksAdapter);
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }
}