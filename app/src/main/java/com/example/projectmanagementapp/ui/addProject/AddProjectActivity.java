package com.example.projectmanagementapp.ui.addProject;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectmanagementapp.MainActivity;
import com.example.projectmanagementapp.R;
import com.example.projectmanagementapp.data.remote.model.ProjectRequest;
import com.example.projectmanagementapp.data.remote.model.ProjectsResponse;
import com.example.projectmanagementapp.data.remote.repository.ProjectRepository;
import com.example.projectmanagementapp.models.ProjectTheme;
import com.example.projectmanagementapp.models.User;
import com.example.projectmanagementapp.state.ProjectState;
import com.example.projectmanagementapp.state.UserProjectsState;
import com.example.projectmanagementapp.ui.tasks.TasksActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProjectActivity  extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_project);

        final ProjectRepository projectRepository = new ProjectRepository(this);


        final LinearLayout btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText projectName = findViewById(R.id.et_project_name);
                final EditText projectDescription = findViewById(R.id.et_project_description);

                final String name = projectName.getText().toString().trim();
                final String description = projectDescription.getText().toString().trim();
                final ProjectTheme theme = ProjectState.getInstance().getTheme();
                final List<User> members = ProjectState.getInstance().getProject().getMembers();

                final ProjectRequest projectRequest = new ProjectRequest(name, description, theme.primaryColor, members);
                final Intent homeIntent = new Intent(AddProjectActivity.this, TasksActivity.class);
                startActivity(homeIntent);

                projectRepository.addProject(projectRequest, new Callback<ProjectsResponse>() {
                    @Override
                    public void onResponse(Call<ProjectsResponse> call, Response<ProjectsResponse> response) {
                        UserProjectsState.getInstance().addProject(response.body().getProject());
                        final Intent homeIntent = new Intent(AddProjectActivity.this, TasksActivity.class);
                        startActivity(homeIntent);
                    }

                    @Override
                    public void onFailure(Call<ProjectsResponse> call, Throwable t) {
                        Log.d("AddProjectActivity onFailure", "Something went wrong"+t.toString());
                        Toast.makeText(AddProjectActivity.this, "Unexpected server response", Toast.LENGTH_SHORT).show();

                    }
                });




            }
        });
        final View rootView = findViewById(android.R.id.content);
        //* this code is for the ui to not touch the battery connection
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (view, windowInsets) -> {
            // Get insets for system bars
            final Insets systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Adjust view padding based on system bar insets to
            view.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom
            );

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
    }

    // Utility method to get safe area insets
    private WindowInsetsCompat getSafeAreaInsets() {
        final View rootView = findViewById(android.R.id.content);
        return ViewCompat.getRootWindowInsets(rootView);
    }

    // Example of getting specific inset values
    private void demonstrateInsetUsage() {
        final WindowInsetsCompat insets = getSafeAreaInsets();
        if (insets != null) {
            final int topInset = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;
            final int bottomInset = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom;

            // Use these values to adjust your layout
            Log.d("SafeArea", "Top Inset: " + topInset);
            Log.d("SafeArea", "Bottom Inset: " + bottomInset);
        }
    }
}
