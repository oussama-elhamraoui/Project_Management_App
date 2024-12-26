package com.example.projectmanagementapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.projectmanagementapp.R;
import com.example.projectmanagementapp.databinding.ActivityMainBinding;
import com.example.projectmanagementapp.ui.addProject.AddProjectActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NavigationActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_activity_nav_host);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        // Get reference to the BottomNavigationView from the layout
        BottomNavigationView navView = binding.bottomNavigationView;

        // Set item icon tint list to null (optional, to disable default icon tinting)
        navView.setItemIconTintList(null);

        // Set up the BottomNavigationView with the NavController for navigation
        androidx.navigation.ui.NavigationUI.setupWithNavController(navView, navController);


        final FloatingActionButton addButton = findViewById(R.id.bt_add_project);

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(NavigationActivity.this, AddProjectActivity.class);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        if (intent != null && "home_fragment".equals(intent.getStringExtra("navigate_to"))) {
            // Correct navigation based on current fragment
            navController.popBackStack();  // Pop the back stack to ensure we're at the start
            navController.navigate(R.id.home); // Navigate to HomeFragment
        }

    }

}
