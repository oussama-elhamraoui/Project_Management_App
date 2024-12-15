package com.example.projectmanagementapp.ui.tasks;

import static com.example.projectmanagementapp.utils.TokenManager.getToken;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmanagementapp.R;
import com.example.projectmanagementapp.data.remote.api.ApiClient;
import com.example.projectmanagementapp.data.remote.api.ApiService;
import com.example.projectmanagementapp.data.remote.model.TaskRequest;
import com.example.projectmanagementapp.data.remote.model.TaskResponse;
import com.example.projectmanagementapp.models.Project;
import com.example.projectmanagementapp.models.Task;
import com.example.projectmanagementapp.models.User;
import com.example.projectmanagementapp.state.ProjectState;
import com.example.projectmanagementapp.state.UserState;
import com.example.projectmanagementapp.utils.TokenManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TasksActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Task> tasksList;
    private TasksAdapter tasksAdapter;
    private Dialog addTaskDialog;
    private Button addTaskButton;
    private ImageButton datePickerButton;
    private TextView datePickerTextView;
    private Button addTaskButtonDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tasks);
//        Adding task Pop Up
        addTaskDialog = new Dialog(TasksActivity.this);
        addTaskDialog.setContentView(R.layout.dialog_add_task);
        Window window = addTaskDialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        addTaskDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_box_background));
//        addTaskDialog.setCancelable(false);

        addTaskButton = findViewById(R.id.add_task_button);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskDialog.show();
            }
        });
        datePickerButton = addTaskDialog.findViewById(R.id.date_picker_image_button);
        datePickerTextView = addTaskDialog.findViewById(R.id.date_picker_text_view);
        addTaskButtonDialog=addTaskDialog.findViewById(R.id.add_task_button);

        datePickerButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    TasksActivity.this,
                    (view, year1, month1, dayOfMonth1) -> {
                        // Format and display the selected date in the TextView
                        String formattedDate = year1 + "/" + (month1 + 1) + "/" + dayOfMonth1;
                        datePickerTextView.setText(formattedDate);
                    },
                    year, month, dayOfMonth
            );
            datePickerDialog.show();
        });
        addTaskButtonDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addTaskDialog.dismiss();
            }
        });




        int id = 0; // this is a temp solution plz delete it you need to get the id from the backend
        recyclerView = findViewById(R.id.task_recycler_view);
        tasksList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Task task1 = new Task(id, "task 1", Duration.between(LocalDateTime.now(), LocalDateTime.now().plusDays(1)));
            id++;
            tasksList.add(task1);
        }
        tasksAdapter = new TasksAdapter(tasksList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(tasksAdapter);


    }
    private void addTaskToProject(TaskRequest task) {
        ApiService taskApi = ApiClient.getInstance().create(ApiService.class);
        Project project =ProjectState.getInstance().getProject();
        User user= UserState.getInstance().getUser();
        Integer projectId = project.id;
        Integer userId = user.getUserId();
        String token = TokenManager.getToken();
        Call<TaskResponse> call = taskApi.createTask(token,projectId, userId, task);

        call.enqueue(new Callback<TaskResponse>() {
            @Override
            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TasksActivity.this, "Task added successfully", Toast.LENGTH_SHORT).show();
                    tasksList.add(response.body()); // Add the new task to the list
                    tasksAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(TasksActivity.this, "Failed to add task", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TaskResponse> call, Throwable t) {
                Toast.makeText(TasksActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
}