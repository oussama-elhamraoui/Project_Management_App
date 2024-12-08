package com.example.projectmanagementapp.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.projectmanagementapp.R;
import com.example.projectmanagementapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

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


    }

}
