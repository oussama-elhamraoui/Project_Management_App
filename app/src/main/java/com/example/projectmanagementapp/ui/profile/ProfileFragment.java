package com.example.projectmanagementapp.ui.profile;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.projectmanagementapp.R;
import com.google.android.material.imageview.ShapeableImageView;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Find the ShareableImageView
        ShapeableImageView profileImage = view.findViewById(R.id.profile_image);

        // Generate initials drawable
        String userName = "YJohn"; // Replace with dynamic user name
        int size = 96; // Example size in dp
        BitmapDrawable drawable = ProfileImageGenerator.generateInitialsDrawable(
                requireContext(), // Use the fragment's context
                userName,
                size
        );

        // Set drawable to the ShareableImageView
        profileImage.setImageDrawable(drawable);

        return view;
    }
}
