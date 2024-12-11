package com.example.projectmanagementapp.ui.addProject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
        final View rootView = findViewById(android.R.id.content);
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (view, windowInsets) -> {
            // Get insets for system bars
            final Insets systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());

            // Adjust view padding based on system bar insets
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
