package com.example.projectmanagementapp.ui.addProject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectmanagementapp.R;
import com.example.projectmanagementapp.ui.tasks.TasksActivity;

public class AddProjectActivity  extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_project);

        final LinearLayout btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event here
                final Intent homeIntent = new Intent(AddProjectActivity.this, TasksActivity.class);
                startActivity(homeIntent);
            }
        });
    }
}
