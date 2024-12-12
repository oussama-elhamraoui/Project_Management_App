package com.example.projectmanagementapp.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.projectmanagementapp.data.local.entities.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task task);

    @Query("SELECT * FROM task WHERE projectId = :projectId")
    LiveData<List<Task>> getTasksForProject(int projectId);

    @Query("DELETE FROM task WHERE id = :taskId")
    void deleteTask(int taskId);
}