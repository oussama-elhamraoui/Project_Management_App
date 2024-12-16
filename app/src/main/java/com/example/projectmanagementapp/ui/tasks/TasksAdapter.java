package com.example.projectmanagementapp.ui.tasks;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmanagementapp.R;
import com.example.projectmanagementapp.data.remote.api.ApiClient;
import com.example.projectmanagementapp.data.remote.api.ApiService;
import com.example.projectmanagementapp.data.remote.model.TaskRequest;
import com.example.projectmanagementapp.data.remote.model.TaskResponse;
import com.example.projectmanagementapp.state.ProjectState;
import com.example.projectmanagementapp.utils.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {

    private List<TaskResponse> tasksList;
    private Context context;
    private ApiService apiService;



    public TasksAdapter(List<TaskResponse> tasksList) {
        this.tasksList = tasksList;
    }
    public TasksAdapter(List<TaskResponse> tasksList, Context context) {
        this.tasksList = tasksList;
        this.context = context;
        this.apiService = ApiClient.getInstance().create(ApiService.class); // Initialize Retrofit
    }
    public void addTask(TaskResponse task) {
        this.tasksList.add(task);
        notifyItemInserted(tasksList.size() - 1); // Notify only the new item
    }

    public void updateTasks(List<TaskResponse> updatedTasks) {
        this.tasksList = updatedTasks;
        notifyDataSetChanged(); // Refresh the RecyclerView
    }
    public void updateTaskInList(TaskResponse updatedTask, int position) {
        tasksList.set(position, updatedTask);
        notifyItemChanged(position);
    }
    public void removeTask(int position) {
        tasksList.remove(position);
        notifyItemRemoved(position);
    }


    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_card,parent,false);
        return new TasksViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        TaskResponse task= tasksList.get(position);
        holder.taskNameTextView.setText(task.getName());
//        holder.durationLeftTextView.setText(task.getTimeLeft()+" Left");

        // Popup menu for options
        holder.moreOptionsButton.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(context, holder.moreOptionsButton);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.task_manipulation_menu, popupMenu.getMenu());

            // Handle menu item clicks
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.edit_task_menu) {
                    showEditTaskDialog(task, position);
                    return true;
                } else if (item.getItemId() == R.id.delete_task_menu) {
                    deleteTask(task, position);
                    return true;
                }
                return false;
            });

            popupMenu.show();
        });
    }
    private void deleteTask(TaskResponse task, int position) {
        String token = TokenManager.getToken(); // Replace with your actual token retrieval logic

        apiService.deleteTask(token, task.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    removeTask(position); // Update RecyclerView
                    Toast.makeText(context, "Task deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to delete task", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showEditTaskDialog(TaskResponse task, int position) {
        // Create and configure dialog
        Dialog editDialog = new Dialog(context);
        editDialog.setContentView(R.layout.dialog_edit_task);
        Objects.requireNonNull(editDialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Initialize dialog fields
        EditText taskNameEditText = editDialog.findViewById(R.id.task_name_edit_text);
        EditText taskDescriptionEditText = editDialog.findViewById(R.id.task_description_edit_text);
        TextView dueDateTextView = editDialog.findViewById(R.id.date_picker_text_view);
        Button editTaskButton = editDialog.findViewById(R.id.edit_task_button_dialog);

        // Pre-fill existing task data
        taskNameEditText.setText(task.getName());
        taskDescriptionEditText.setText(task.getDescription());
        dueDateTextView.setText(String.valueOf(task.getDueDate())); // Format this if needed

        // Handle date selection
        dueDateTextView.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                    (view, year, month, dayOfMonth) -> dueDateTextView.setText(year + "/" + (month + 1) + "/" + dayOfMonth),
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });

        // Handle update button click
        editTaskButton.setOnClickListener(v -> {
            String updatedName = taskNameEditText.getText().toString().trim();
            String updatedDescription = taskDescriptionEditText.getText().toString().trim();
            String updatedDueDate = dueDateTextView.getText().toString().trim();


            if (updatedName.isEmpty() || updatedDescription.isEmpty() || updatedDueDate.isEmpty()) {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Send update to backend
            updateTaskOnBackend(task, updatedName, updatedDescription, updatedDueDate, position);
            editDialog.dismiss();
        });

        editDialog.show();
    }
    private void updateTaskOnBackend(TaskResponse task, String updatedName, String updatedDescription, String updatedDueDate, int position) {
        // Retrieve token for authentication
        String token = TokenManager.getToken();

        // Format the due date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date parsedDueDate = null;

        try {
            parsedDueDate = dateFormat.parse(updatedDueDate);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(context, "Invalid date format", Toast.LENGTH_SHORT).show();
            return; // Exit the method if the date format is invalid
        }

        // Create a new TaskRequest with updated values
        TaskRequest updatedTaskRequest = new TaskRequest(
                updatedName,
                updatedDescription,
                task.getPriority(),
                task.getStatus(),
                parsedDueDate
        );
            // Proceed with sending updatedTaskRequest to your backend

        apiService.updateTask(token, task.getId(), updatedTaskRequest).enqueue(new Callback<TaskResponse>() {
            @Override
            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TaskResponse updatedTask = response.body();
                    updateTaskInList(updatedTask, position); // Update the RecyclerView
                    Toast.makeText(context, "Task updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to update task", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TaskResponse> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }


    public static class TasksViewHolder extends RecyclerView.ViewHolder{
        TextView taskNameTextView;
        TextView durationLeftTextView;
        ImageButton moreOptionsButton;

        public TasksViewHolder(@NonNull View itemView) {
            super(itemView);
            taskNameTextView = itemView.findViewById(R.id.task_name_text_view);
            durationLeftTextView = itemView.findViewById(R.id.duration_left_text_view);
            moreOptionsButton = itemView.findViewById(R.id.more_vert_image_button);
            durationLeftTextView.setTextColor(ProjectState.getInstance().getTheme().primaryColor);
        }
        public void showPopupMenu(View view, TaskResponse task) {
            // Create a PopupMenu
            PopupMenu popupMenu = new PopupMenu(itemView.getContext(), view);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.task_manipulation_menu, popupMenu.getMenu());

            // Set a listener for menu item clicks
            popupMenu.setOnMenuItemClickListener(item -> {
                if(item.getItemId()==R.id.edit_task_menu){
                    Toast.makeText(itemView.getContext(), "Edit Task Selected", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (item.getItemId()==R.id.delete_task_menu) {
                    Toast.makeText(itemView.getContext(), "Delete Task Selected", Toast.LENGTH_SHORT).show();
                    return true;
                }else {
                    return false;
                }
            });

            // Show the popup menu
            popupMenu.show();
        }
    }


}
