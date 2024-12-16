package com.example.projectmanagementapp.ui.tasks;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmanagementapp.R;
import com.example.projectmanagementapp.data.remote.api.ApiClient;
import com.example.projectmanagementapp.data.remote.api.ApiService;
import com.example.projectmanagementapp.data.remote.model.TaskRequest;
import com.example.projectmanagementapp.data.remote.model.TaskResponse;
import com.example.projectmanagementapp.models.Project;
import com.example.projectmanagementapp.models.User;
import com.example.projectmanagementapp.state.ProjectState;
import com.example.projectmanagementapp.state.UserState;
import com.example.projectmanagementapp.utils.TokenManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TasksActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<TaskResponse> taskList;
    private TasksAdapter tasksAdapter;
    private Dialog addTaskDialog;
    private Button addTaskButton;
    private ImageButton datePickerButton;
    private TextView datePickerTextView;
    private Button addTaskButtonDialog;

    private static final String STATUS_TODO = "TO_DO";
    private static final String PRIORITY_HIGH = "HIGH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tasks);

        // Adding task Pop Up
        addTaskDialog = new Dialog(TasksActivity.this);
        addTaskDialog.setContentView(R.layout.dialog_add_task);

        final int primaryColor = ProjectState.getInstance().getTheme().primaryColor;
        final int secondaryColor = ProjectState.getInstance().getTheme().secondaryColor;

        final CardView projectNameBackground = findViewById(R.id.cv_project_name_bg);
        projectNameBackground.setCardBackgroundColor(primaryColor);

        final Button addTaskButton =  findViewById(R.id.add_task_button);
        addTaskButton.setTextColor(primaryColor);

        final CardView taskCountBackground = findViewById(R.id.cv_tasks_count_bg);
        taskCountBackground.setCardBackgroundColor(secondaryColor);

        final TextView textView = findViewById(R.id.tasks_number_text_view);
        textView.setTextColor(primaryColor);




        Window window = addTaskDialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        final View rootView = findViewById(android.R.id.content);

        //* this code is for the ui to not touch the battery connection
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (view, windowInsets) -> {
            // Get insets for system bars
            final Insets systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Adjust view padding based on system bar insets to
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return WindowInsetsCompat.CONSUMED;
        });
        // Alternative Method 2: Edge-to-edge configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false);

            // Optional: Customize system bar appearance
            final WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.show(WindowInsets.Type.systemBars());
            }
        }


        Objects.requireNonNull(addTaskDialog.getWindow()).setBackgroundDrawable(getDrawable(R.drawable.dialog_box_background));
//        addTaskDialog.setCancelable(false);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskDialog.show();
                handleDialog(primaryColor);

            }
        });



        int id = 0; // this is a temp solution plz delete it you need to get the id from the backend
        recyclerView = findViewById(R.id.task_recycler_view);
        tasksAdapter = new TasksAdapter(new ArrayList<>()); // Initialize adapter with empty list
        fetchTasksFromBackend();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(tasksAdapter);


    }

    private void handleDialog(int primaryColor) {
        datePickerButton = addTaskDialog.findViewById(R.id.date_picker_image_button);
        Drawable drawable = datePickerButton.getDrawable();
        // set the date picker color
        if (drawable != null) {
            drawable.setColorFilter(primaryColor, PorterDuff.Mode.SRC_IN);
        }
        datePickerTextView = addTaskDialog.findViewById(R.id.date_picker_text_view);
        addTaskButtonDialog = addTaskDialog.findViewById(R.id.add_task_button_dialog);
        addTaskButtonDialog.setBackgroundColor(primaryColor);


        final CardView addButtonBackground = addTaskDialog.findViewById(R.id.cv_add_button_bg);
        addButtonBackground.setCardBackgroundColor(primaryColor);



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
                TaskRequest taskRequest = createTaskRequest();
                if (taskRequest != null) {
                    addTaskToProject(taskRequest);
                    addTaskDialog.dismiss();
                }
            }
        });
    }

    private TaskRequest createTaskRequest() {
        EditText taskNameEditText = addTaskDialog.findViewById(R.id.task_name_edit_text);
        EditText taskDescriptionEditText = addTaskDialog.findViewById(R.id.task_description_edit_text);

        String title = taskNameEditText.getText().toString().trim();
        String description = taskDescriptionEditText.getText().toString().trim();
        String dateString = datePickerTextView.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty() || dateString.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return null;
        }

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date dueDate = dateFormat.parse(dateString);
            return new TaskRequest(title, description, PRIORITY_HIGH, STATUS_TODO, dueDate);
        } catch (ParseException e) {
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    private void addTaskToProject(TaskRequest task) {
        ApiService taskApi = ApiClient.getInstance().create(ApiService.class);
        Project project = ProjectState.getInstance().getProject();
        User user= UserState.getInstance().getUser();
        if (project == null || user == null) {
            Toast.makeText(this, "Project or User not available", Toast.LENGTH_SHORT).show();
            return;
        }
        int projectId = project.id;
        int userId = user.getUserId();
        String token = TokenManager.getToken();
        Call<TaskResponse> call = taskApi.createTask(token,projectId, userId, task);

        call.enqueue(new Callback<TaskResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<TaskResponse> call, @NonNull Response<TaskResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TasksActivity.this, "Task added successfully", Toast.LENGTH_SHORT).show();
//                    taskList.add(response.body()); // Add the new task to the list
                    tasksAdapter.addTask(response.body());
                    tasksAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(TasksActivity.this, "Failed to add task", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TaskResponse> call, @NonNull Throwable t) {
                Toast.makeText(TasksActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void fetchTasksFromBackend() {
        ApiService taskApi = ApiClient.getInstance().create(ApiService.class);
        Project project = ProjectState.getInstance().getProject();
        int projectId = project.id;
        String token = TokenManager.getToken();
        Call<List<TaskResponse>> call = taskApi.getTasksByProject(token,projectId);

        call.enqueue(new Callback<List<TaskResponse>>() {
            @Override
            public void onResponse(Call<List<TaskResponse>> call, Response<List<TaskResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    taskList = response.body();
                    updateTasksAdapter(taskList);
                } else {
                    Toast.makeText(TasksActivity.this, "Failed to fetch tasks", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TaskResponse>> call, Throwable t) {
                Toast.makeText(TasksActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateTasksAdapter(List<TaskResponse> tasks) {
        List<TaskResponse> taskModels = new ArrayList<>();
        for (TaskResponse response : tasks) {
            TaskResponse task = new TaskResponse(response.getId(), response.getName(), response.getDescription(), response.getDueDate());
            taskModels.add(task);
        }
        tasksAdapter.updateTasks(taskModels); // Use a method in adapter to update data
    }
}
