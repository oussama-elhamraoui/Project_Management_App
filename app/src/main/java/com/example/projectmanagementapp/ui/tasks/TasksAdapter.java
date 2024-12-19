package com.example.projectmanagementapp.ui.tasks;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmanagementapp.R;
import com.example.projectmanagementapp.models.Task;
import com.example.projectmanagementapp.state.ProjectState;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {

    private List<Task> tasksList;


    public int getNumberOfTasks() {
        return tasksList.size();
    }

    public TasksAdapter(List<Task> tasksList) {
        this.tasksList = tasksList;
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
        return new TasksViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        Task task= tasksList.get(position);
        holder.taskNameTextView.setText(task.getName());
//        holder.durationLeftTextView.setText(task.getTimeLeft()+" Left");

        holder.moreOptionsButton.setOnClickListener(view -> holder.showPopupMenu(view, task));
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
        public void showPopupMenu(View view, Task task) {
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
