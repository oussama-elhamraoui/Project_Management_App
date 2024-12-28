package com.example.projectmanagementapp.ui.tasks;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmanagementapp.R;
import com.example.projectmanagementapp.models.Task;
import com.example.projectmanagementapp.state.ProjectState;
import com.example.projectmanagementapp.utils.TaskUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {
    static final int primaryColor = ProjectState.getInstance().getTheme().primaryColor;

    private List<Task> tasksList;
    private TaskCountsListener taskCountsListener;
    private OnTaskStatusChangeListener onTaskStatusChangeListener;


    public int getNumberOfTasks() {
        return tasksList.size();
    }

    public TasksAdapter(List<Task> tasksList, TaskCountsListener countslistener, OnTaskStatusChangeListener statuslistener) {
        this.taskCountsListener = countslistener;
        this.tasksList = tasksList;
        this.onTaskStatusChangeListener = statuslistener;
    }
    //this interface is  used to update the taskList according to filters
    public interface OnTaskStatusChangeListener {
        void onTaskStatusChanged(Task task);
    }
    public void setTasksList(List<Task> tasksList) {
        this.tasksList = tasksList;
    }

    public void addTask(Task task) {
        this.tasksList.add(task);
        notifyItemInserted(tasksList.size() - 1); // Notify only the new item
    }

    public void updateTasks(List<Task> updatedTasks) {
        this.tasksList = updatedTasks;
        notifyDataSetChanged(); // Refresh the RecyclerView
    }


    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_card,parent,false);
            return new TasksViewHolder(itemView,this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        Task task= tasksList.get(position);
        holder.taskNameTextView.setText(task.getName());
//        holder.durationLeftTextView.setText(task.getTimeLeft()+" Left");

        holder.moreOptionsButton.setOnClickListener(view -> holder.showPopupMenu(view, task,position));
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }



    public static class TasksViewHolder extends RecyclerView.ViewHolder{
        private final TasksAdapter adapter;
        TextView taskNameTextView;
        TextView durationLeftTextView;
        ImageButton moreOptionsButton;
        Button editButton;

        public TasksViewHolder(@NonNull View itemView, TasksAdapter tasksAdapter) {
            super(itemView);
            this.adapter = tasksAdapter;
            taskNameTextView = itemView.findViewById(R.id.task_name_text_view);
            durationLeftTextView = itemView.findViewById(R.id.duration_left_text_view);
            moreOptionsButton = itemView.findViewById(R.id.more_vert_image_button);
            durationLeftTextView.setTextColor(ProjectState.getInstance().getTheme().primaryColor);

        }
        public void showPopupMenu(View view, Task task, int position) {
            // Create a PopupMenu
            PopupMenu popupMenu = new PopupMenu(itemView.getContext(), view);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.task_manipulation_menu, popupMenu.getMenu());

            // Set a listener for menu item clicks
            popupMenu.setOnMenuItemClickListener(item -> {
                if(item.getItemId()==R.id.finished_task_menu){
                    task.setStatus("COMPLETED");
                    adapter.tasksList.set(position, task);
                    ProjectState.getInstance().updateTask(task);
                    adapter.onTaskStatusChangeListener.onTaskStatusChanged(task);
                    adapter.updateTaskCounts();
                    adapter.notifyItemChanged(position);
                    return true;
                } else if(item.getItemId()==R.id.edit_task_menu){

                    showEditTaskDialog(view,task, position);

                    return true;
                } else if (item.getItemId()==R.id.delete_task_menu) {
                    adapter.tasksList.remove(position);
                    ProjectState.getInstance().deleteTask(task);
                    adapter.updateTaskCounts();
                    adapter.notifyItemRemoved(position);
                    Toast.makeText(view.getContext(), "Task Deleted", Toast.LENGTH_SHORT).show();
                    return true;
                }else {
                    return false;
                }
            });

            // Show the popup menu
            popupMenu.show();
        }

        public void showEditTaskDialog(View view, Task task, int position) {
            Dialog editTaskDialog = new Dialog(view.getContext());
            editTaskDialog.setContentView(R.layout.dialog_edit_task);
            Window window = editTaskDialog.getWindow();
            if (window != null) {
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }// Make sure this layout contains the button
            editTaskDialog.getWindow().setBackgroundDrawable(
                    ContextCompat.getDrawable(view.getContext(), R.drawable.dialog_box_background)
            );

            EditText taskNameEditText=editTaskDialog.findViewById(R.id.task_name_edit_text);
            EditText taskDescriptionEditText=editTaskDialog.findViewById(R.id.task_description_edit_text);
            TextView datePickerTextView = editTaskDialog.findViewById(R.id.date_picker_text_view);
            taskNameEditText.setText(task.getName());
            taskDescriptionEditText.setText(task.getDescription());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

// Format the due date and set it in the TextView
            String datePickerformattedDate = dateFormat.format(task.getDueDate());
            datePickerTextView.setText(datePickerformattedDate);

            ImageButton datePickerButton = editTaskDialog.findViewById(R.id.date_picker_image_button);
            Drawable drawable = datePickerButton.getDrawable();
            // set the date picker color
            if (drawable != null) {
                drawable.setColorFilter(primaryColor, PorterDuff.Mode.SRC_IN);
            }

            datePickerButton.setOnClickListener(v -> {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        view.getContext(),
                        (view1, year1, month1, dayOfMonth1) -> {
                            // Format and display the selected date in the TextView
                            String formattedDate = year1 + "/" + (month1 + 1) + "/" + dayOfMonth1;
                            datePickerTextView.setText(formattedDate);
                        },
                        year, month, dayOfMonth
                );
                datePickerDialog.show();
            });
            final CardView editButtonBackground = editTaskDialog.findViewById(R.id.cv_add_button_bg);
            editButtonBackground.setCardBackgroundColor(primaryColor);
            Button editTaskButtonDialog = editTaskDialog.findViewById(R.id.edit_task_button_dialog);
            editTaskButtonDialog.setBackgroundColor(primaryColor);// Ensure the correct ID

            if (editTaskButtonDialog != null) {
                editTaskButtonDialog.setOnClickListener(v -> {
                    String updatedName = taskNameEditText.getText().toString().trim();
                    String updatedDescription = taskDescriptionEditText.getText().toString().trim();
                    String updatedDueDateString = datePickerTextView.getText().toString().trim(); // Renamed variable

                    task.setName(updatedName);
                    task.setDescription(updatedDescription);

                    SimpleDateFormat updatedDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                    try {
                        Date updatedDueDate = updatedDateFormat.parse(updatedDueDateString);
                        if (updatedDueDate != null) {
                            task.setDueDate(updatedDueDate); // Set the Date in the Task object
                        } else {
                            Toast.makeText(editTaskDialog.getContext(), "Invalid date format", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(view.getContext(), "Error parsing date", Toast.LENGTH_SHORT).show();
                    }

                    adapter.tasksList.set(position, task);
                    ProjectState.getInstance().updateTask(task);
                    adapter.notifyItemChanged(position);
                    editTaskDialog.dismiss();
                    Toast.makeText(view.getContext(), "Task Edited", Toast.LENGTH_SHORT).show();
                });
                editTaskDialog.show();
            } else {
                Log.e("TasksAdapter", "Save button not found in layout");
            }
        }

    }
    //        this method updates filter counts
    private void updateTaskCounts() {
        if (taskCountsListener != null) {
            Map<String, Integer> counts = TaskUtils.countTaskStatus(tasksList);
            taskCountsListener.onTaskCountsUpdated(counts);
        }
    }




}
