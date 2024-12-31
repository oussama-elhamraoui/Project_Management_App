package com.example.projectmanagementapp.ui.tasks;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.example.projectmanagementapp.models.Task;
import com.example.projectmanagementapp.models.User;
import com.example.projectmanagementapp.state.ProjectState;
import com.example.projectmanagementapp.state.UserState;
import com.example.projectmanagementapp.ui.NavigationActivity;
import com.example.projectmanagementapp.ui.home.HomeFragment;
import com.example.projectmanagementapp.utils.TaskUtils;
import com.example.projectmanagementapp.utils.TokenManager;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TasksActivity extends AppCompatActivity implements TaskCountsListener, TasksAdapter.OnTaskStatusChangeListener, TasksAdapter.OnTaskRemovedListener {
    private RecyclerView recyclerView;
    private List<Task> tasksList = ProjectState.getInstance().getProject().tasks ;
    private List<Task> filteredTasks = new ArrayList<>();
    private TasksAdapter tasksAdapter;
    private Dialog addTaskDialog;
    private Button addTaskButton;
    private ImageButton datePickerButton;
    private TextView datePickerTextView;
    private Button addTaskButtonDialog;


    private static final String STATUS_TODO = "TO_DO";
    private static final String PRIORITY_HIGH = "HIGH";
    private  TextView tasksNumberTextView;
    private LinearLayout pendingButton, finishedButton, yourTasksButton;
    private TextView pendingTextView, finishedTextView, yourTasksTextView;
    private TextView countPendingTextView, countFinishedTextView, countYourTasksTextView;
    private LinearLayout saveButton;
    final int primaryColor = ProjectState.getInstance().getTheme().primaryColor;
    final int secondaryColor = ProjectState.getInstance().getTheme().secondaryColor;

    private static int taskIdCounter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tasks);

        // Adding task Pop Up
        addTaskDialog = new Dialog(TasksActivity.this);
        addTaskDialog.setContentView(R.layout.dialog_add_task);



        final CardView projectNameBackground = findViewById(R.id.cv_project_name_bg);
        projectNameBackground.setCardBackgroundColor(primaryColor);
        TextView project_name_text_view = findViewById(R.id.project_name_text_view);
        project_name_text_view.setText(ProjectState.getInstance().getProject().name);

        final Button addTaskButton =  findViewById(R.id.add_task_button);
        addTaskButton.setTextColor(primaryColor);

        final CardView taskCountBackground = findViewById(R.id.cv_tasks_count_bg);
        taskCountBackground.setCardBackgroundColor(secondaryColor);

        tasksNumberTextView = findViewById(R.id.tasks_number_text_view);
        tasksNumberTextView.setTextColor(primaryColor);

        pendingButton = findViewById(R.id.pending_button);
        finishedButton = findViewById(R.id.finished_button);
        yourTasksButton = findViewById(R.id.your_tasks_button);

//        save Button intent to home fragment
        saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),NavigationActivity.class);
                intent.putExtra("navigate_to", "home_fragment");
                startActivity(intent);
            }
        });


        pendingTextView = findViewById(R.id.pending_text_view);
        finishedTextView = findViewById(R.id.finished_text_view);
        yourTasksTextView = findViewById(R.id.your_tasks_text_view);
        countPendingTextView = findViewById(R.id.count_pending_text_view);
        countFinishedTextView = findViewById(R.id.count_finished_text_view);
        countYourTasksTextView = findViewById(R.id.count_your_tasks_text_view);
        updateTaskCounts();
        // setting up the filter buttons
        activateButton(pendingButton, pendingTextView, countPendingTextView, primaryColor,"#FFFFFF" , "#FFFFFF");
        resetButton(finishedButton, finishedTextView , countFinishedTextView, "#FFFFFF", "#000000" ,primaryColor); // White background, black text
        resetButton(yourTasksButton, yourTasksTextView, countYourTasksTextView, "#FFFFFF", "#000000", primaryColor);

        // Set click listeners
        pendingButton.setOnClickListener(v -> updateButtonColors("pending"));
        finishedButton.setOnClickListener(v -> updateButtonColors("finished"));
        yourTasksButton.setOnClickListener(v -> updateButtonColors("your_tasks"));




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
        filteredTasks = filterTasks(tasksList, "Pending");
        tasksAdapter = new TasksAdapter(tasksList,filteredTasks,this,this,this); // Initialize adapter with empty list
//        fetchTasksFromBackend();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(tasksAdapter);


    }
    //this function to handle the dialog
    private void handleDialog(int primaryColor) {
        datePickerButton = addTaskDialog.findViewById(R.id.date_picker_image_button);
        Drawable drawable = datePickerButton.getDrawable();
        // set the date picker color
        if (drawable != null) {
            drawable.setColorFilter(primaryColor, PorterDuff.Mode.SRC_IN);
        }
        //initialize the datePicker to today's date
        datePickerTextView = addTaskDialog.findViewById(R.id.date_picker_text_view);
        Calendar defaultCalendar = Calendar.getInstance();
        int defaultYear = defaultCalendar.get(Calendar.YEAR);
        int defaultMonth = defaultCalendar.get(Calendar.MONTH);
        int defaultDayOfMonth = defaultCalendar.get(Calendar.DAY_OF_MONTH);
        String defaultDate = defaultYear + "/" + (defaultMonth + 1) + "/" +  defaultDayOfMonth;
        datePickerTextView.setText(defaultDate);
        addTaskButtonDialog = addTaskDialog.findViewById(R.id.add_task_button_dialog);
        addTaskButtonDialog.setBackgroundColor(primaryColor);


        final CardView addButtonBackground = addTaskDialog.findViewById(R.id.cv_add_button_bg);
        addButtonBackground.setCardBackgroundColor(primaryColor);


        // clickListener on the datePicker for the datePicker Pop up
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
                Task task = createTaskRequest();
                updateTaskCounts();
                if (task != null) {
                    addTaskToProject(task);
                    TaskRequest taskRequest = new TaskRequest(task.name,task.description,task.priority,task.status,task.dueDate);
                    String token = TokenManager.getToken();
                    Log.d("Token", token);
                    int projectId=ProjectState.getInstance().getProject().getId();
                    Log.d("Project Id", ""+projectId);
                    int userId = UserState.getInstance().getUser().getId();
                    Log.d("User Id", ""+userId);
                    saveTaskInDB(token,projectId,userId,taskRequest);
                    addTaskDialog.dismiss();
                }
            }
        });
    }
    // adding task to project function
    private void addTaskToProject(Task newTask){

        // Add the task to the project
        ProjectState.getInstance().addTask(newTask);



        // Update UI (e.g., refresh adapter, close dialog)
        tasksList.add(newTask);
        String currentFilter = getCurrentFilter();
        Log.d("TasksActivity", "Current filter: "+currentFilter);// Get the current filter type
        filteredTasks.clear();
        filteredTasks = filterTasks(tasksList, currentFilter);
        updateRecyclerView(filteredTasks);
        updateTaskCounts();
        updateTaskCount();
    }
    //this function to update the task count
    private void updateTaskCount() {
        // Assuming ProjectState holds the list of tasks, you can count them and set the value
        int taskCount = ProjectState.getInstance().getProject().getTasks().size();
        tasksNumberTextView.setText(String.valueOf(taskCount));
    }

    private Task createTaskRequest() {
        EditText taskNameEditText = addTaskDialog.findViewById(R.id.task_name_edit_text);
        EditText taskDescriptionEditText = addTaskDialog.findViewById(R.id.task_description_edit_text);

        String title = taskNameEditText.getText().toString().trim();
        taskNameEditText.setText("");
        String description = taskDescriptionEditText.getText().toString().trim();
        taskDescriptionEditText.setText("");
        String dateString = datePickerTextView.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty() || dateString.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return null;
        }

        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date dueDate = dateFormat.parse(dateString);
            int uniqueId = taskIdCounter++;
            return new Task(uniqueId,title, description, STATUS_TODO, PRIORITY_HIGH,  dueDate);
        } catch (ParseException e) {
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
//    private void addTaskToProject(TaskRequest task) {
//        ApiService taskApi = ApiClient.getInstance().create(ApiService.class);
//        Project project = ProjectState.getInstance().getProject();
//        User user= UserState.getInstance().getUser();
//        if (project == null || user == null) {
//            Toast.makeText(this, "Project or User not available", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        int projectId = project.id;
//        int userId = user.getUserId();
//        String token = TokenManager.getToken();
//        Call<TaskResponse> call = taskApi.createTask(token,projectId, userId, task);
//
//        call.enqueue(new Callback<TaskResponse>() {
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            public void onResponse(@NonNull Call<TaskResponse> call, @NonNull Response<TaskResponse> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(TasksActivity.this, "Task added successfully", Toast.LENGTH_SHORT).show();
////                    taskList.add(response.body()); // Add the new task to the list
//                    tasksAdapter.addTask(response.body());
//                    tasksAdapter.notifyDataSetChanged();
//                } else {
//                    Toast.makeText(TasksActivity.this, "Failed to add task", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<TaskResponse> call, @NonNull Throwable t) {
//                Toast.makeText(TasksActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//    private void fetchTasksFromBackend() {
//        ApiService taskApi = ApiClient.getInstance().create(ApiService.class);
//        Project project = ProjectState.getInstance().getProject();
//        int projectId = project.id;
//        String token = TokenManager.getToken();
//        Call<List<TaskResponse>> call = taskApi.getTasksByProject(token, projectId);
//
//        call.enqueue(new Callback<List<TaskResponse>>() {
//            @Override
//            public void onResponse(Call<List<TaskResponse>> call, Response<List<TaskResponse>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    taskList = response.body();
//                    updateTasksAdapter(taskList);
//                } else {
//                    Toast.makeText(TasksActivity.this, "Failed to fetch tasks", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<TaskResponse>> call, Throwable t) {
//                Toast.makeText(TasksActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
private void saveTaskInDB(String token, int projectId, int userId, TaskRequest taskRequest) {
    ApiService taskApiService = ApiClient.getInstance().create(ApiService.class);
    taskApiService.createTask(token, projectId, userId, taskRequest)
            .enqueue(new Callback<TaskResponse>() {
                @Override
                public void onResponse(@NonNull Call<TaskResponse> call, @NonNull Response<TaskResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        TaskResponse taskResponse = response.body();
                        Toast.makeText(TasksActivity.this, "Task created successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(TasksActivity.this, "Failed to create task", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TaskResponse> call, @NonNull Throwable t) {
                    Log.e("AddTaskActivity", "Error: " + t.getMessage(), t);
                    Toast.makeText(TasksActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                }
            });
}

    private void updateTasksAdapter(List<Task> tasks) {
        List<Task> taskModels = new ArrayList<>();
        for (Task task : tasks) {
            Task updatedTask = new Task(task.getId(), task.getName(), task.getDescription(), task.getDueDate());
            taskModels.add(task);
        }
        tasksAdapter.updateTasks(taskModels); // Use a method in adapter to update data
    }
    //this is a function to handle the filter buttons
    private void updateButtonColors(String selectedButton) {
        // Reset all buttons to the inactive state
        resetButton(pendingButton, pendingTextView, countPendingTextView, "#FFFFFF", "#000000",primaryColor); // Red background, white text
        resetButton(finishedButton, finishedTextView , countFinishedTextView, "#FFFFFF", "#000000" ,primaryColor); // White background, black text
        resetButton(yourTasksButton, yourTasksTextView, countYourTasksTextView, "#FFFFFF", "#000000", primaryColor); // White background, black text
        filteredTasks.clear();
        // Activate the selected button
        switch (selectedButton) {
            case "pending":
                filteredTasks = filterTasks(tasksList, "pending");
                activateButton(pendingButton, pendingTextView, countPendingTextView, primaryColor,"#FFFFFF" , "#FFFFFF"); // White background, red text
                break;
            case "finished":
                filteredTasks = filterTasks(tasksList, "finished");
                activateButton(finishedButton, finishedTextView, countFinishedTextView, primaryColor,"#FFFFFF" , "#FFFFFF"); // Red background, white text
                break;
            case "your_tasks":
                filteredTasks = filterTasks(tasksList, "your_tasks");
                activateButton(yourTasksButton, yourTasksTextView, countYourTasksTextView, primaryColor,"#FFFFFF" , "#FFFFFF"); // Red background, white text
                break;
        }
        updateRecyclerView(filteredTasks);
    }
    // Method to filter tasks based on status
    public List<Task> filterTasks(List<Task> allTasks,String status) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : allTasks) {
            switch (status) {
                case "pending":
                    if (task.getStatus().equals("TO_DO") || task.getStatus().equals("IN_PROGRESS")) {
                        filteredTasks.add(task);
                    }
                    break;
                case "finished":
                    if (task.getStatus().equals("COMPLETED") ) {
                        filteredTasks.add(task);
                    }
                    break;
                case "your_tasks":
//                    if (task.getAssignedTo().equals(currentUser)) {
//                        filteredTasks.add(task);
//                    }
                    break;
            }
        }
        Log.d("TasksActivity", "Filtered tasks size: " + filteredTasks.size());
        return filteredTasks;
    }
    private void updateRecyclerView(List<Task> tasks) {
        tasksAdapter.setTasksList(tasks);
        tasksAdapter.notifyDataSetChanged();
    }
//    this method gets the current active filter
    private String getCurrentFilter() {
        if (pendingButton.getBackgroundTintList().getDefaultColor() == primaryColor) {
            Log.d("TasksActivity", ""+pendingButton.getBackgroundTintList().getDefaultColor()+" "+primaryColor);
            return "pending";
        } else if (finishedButton.getBackgroundTintList().getDefaultColor() == primaryColor) {
            return "finished";
        } else if (yourTasksButton.getBackgroundTintList().getDefaultColor() == primaryColor) {
            return "your_tasks";
        }
        return "all"; // Default filter if no button is selected
    }
//    this method update filter counts
    private void updateTaskCounts() {
        Map<String, Integer> counts = TaskUtils.countTaskStatus(tasksList);

        countPendingTextView.setText(String.valueOf(counts.get("pending")));
        countFinishedTextView.setText(String.valueOf(counts.get("finished")));
        countYourTasksTextView.setText(String.valueOf(counts.get("yourTasks")));
    }
//    this method overrides the the interface taskCountsListener
    @Override
    public void onTaskCountsUpdated(Map<String, Integer> counts) {
        countPendingTextView.setText(String.valueOf(counts.get("pending")));
        countFinishedTextView.setText(String.valueOf(counts.get("finished")));
        countYourTasksTextView.setText(String.valueOf(counts.get("yourTasks")));
    }
//    this method updates the tasks according to filter selected
    @Override
    public void onTaskStatusChanged(Task task) {
        updateFilteredTasks();
        updateRecyclerView(filteredTasks);
    }

    @Override
    public void onTaskRemoved(Task task) {
        tasksList.remove(task);
        updateTaskCount();
        updateRecyclerView(filteredTasks);
    }
    private void updateFilteredTasks() {
        filteredTasks.clear();
        // Apply the filter based on the current filter type
        filteredTasks.addAll(filterTasks(tasksList, getCurrentFilter()));
    }

    private void resetButton(LinearLayout button,TextView textView, TextView countTextView, String bgColor, String textColor,int textBgColor) {
        button.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor(bgColor)));
        textView.setTextColor(android.graphics.Color.parseColor(textColor));
        countTextView.setBackgroundTintList(ColorStateList.valueOf(textBgColor));
        countTextView.setTextColor(android.graphics.Color.parseColor(bgColor));
    }

    private void activateButton(LinearLayout button, TextView textView, TextView countTextView,int bgColor, String textColor ,String textBgColor) {
        button.setBackgroundTintList(ColorStateList.valueOf(bgColor));
        textView.setTextColor(android.graphics.Color.parseColor(textColor));
        countTextView.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor(textBgColor)));
        countTextView.setTextColor(bgColor);
    }

}
