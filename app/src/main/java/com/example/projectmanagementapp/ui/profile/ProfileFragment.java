package com.example.projectmanagementapp.ui.profile;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.projectmanagementapp.R;
import com.example.projectmanagementapp.utils.TokenManager;
import com.google.android.material.imageview.ShapeableImageView;
import com.example.projectmanagementapp.data.remote.model.UserProfile;

public class ProfileFragment extends Fragment {
    private TextView fullnameTextView;
    private TextView emailTextView;

    @SuppressLint("MissingInflatedId")
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


        fullnameTextView = view.findViewById(R.id.UserFullName);
        emailTextView = view.findViewById(R.id.EmailName);

        loadProfileData();

        return view;
    }

    private void loadProfileData() {
        // Retrieve saved profile from local storage or shared preferences
        UserProfile profile = TokenManager.getUserProfileFromStorage(); //we have a problem here cause the profile is null !!!!!!!

        if (profile != null) {
            String fullName = (profile.getFirstname() + " " + profile.getLastname()).trim();
            fullnameTextView.setText(TextUtils.isEmpty(fullName) ? "No Name" : fullName);
            emailTextView.setText(TextUtils.isEmpty(profile.getEmail()) ? "No Email" : profile.getEmail());
        }
        else {
            // Handle case when no profile is found
            fullnameTextView.setText("No Profile");
            emailTextView.setText("Please log in");
        }
    }
}
