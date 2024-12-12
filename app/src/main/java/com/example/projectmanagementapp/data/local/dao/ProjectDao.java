package com.example.projectmanagementapp.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.projectmanagementapp.data.local.entities.Project;

import java.util.List;

@Dao
public interface ProjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProject(Project project);

    @Query("SELECT * FROM project")
    LiveData<List<Project>> getAllProjects();

    @Query("DELETE FROM project WHERE id = :projectId")
    void deleteProject(int projectId);
}