package com.example.projectmanagementapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.projectmanagementapp.databinding.ActivityMainBinding;

public class NavigationActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Initialize view binding
//        getLayoutInflater();
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
//
//        // Set the default fragment
//        if (savedInstanceState == null) {
//            replaceFragment(new HomeFragment());
//        }
//
//        // Set up bottom navigation listener
//        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
//            Fragment selectedFragment = null;
//
//            if (item.getItemId() == R.id.home) {
//                selectedFragment = new HomeFragment();
//            } else if (item.getItemId() == R.id.tasks) {
//                selectedFragment = new TasksFragment();
//            } else if (item.getItemId() == R.id.profile) {
//                selectedFragment = new ProfileFragment();
//            } else {
//                return false;
//            }
//
//
//            replaceFragment(selectedFragment);
//            return true;
//        });
    }

//    private void replaceFragment(@NonNull Fragment fragment) {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.frame_layout, fragment)
//                .commit();
//    }
}
