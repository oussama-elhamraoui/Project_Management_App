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
import com.example.projectmanagementapp.state.UserState;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Random;

public class ProfileFragment extends Fragment {

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Find the ShareableImageView
        final ShapeableImageView profileImage = view.findViewById(R.id.profile_image);

        // Generate initials drawable
        final String userName = UserState.getInstance().getUsername(); // Replace with dynamic user name
        final int size = 96; // Example size in dp
        BitmapDrawable drawable = ProfileImageGenerator.generateInitialsDrawable(
            requireContext(), // Use the fragment's context
            userName,
            size
        );

        // Set drawable to the ShareableImageView
        profileImage.setImageDrawable(drawable);

        final TextView fullnameTextView = view.findViewById(R.id.UserFullName);
        final TextView emailTextView = view.findViewById(R.id.EmailName);
        final TextView mobileNumber = view.findViewById(R.id.mobile_numbre);
        final TextView usernameTextView = view.findViewById(R.id.username);

        //fetch the user name form Local state and display it here:
        fullnameTextView.setText(userName);
        usernameTextView.setText(userName);

        final String emailText = UserState.getInstance().getEmail();
        emailTextView.setText(emailText);
        //working random for mobile number
        mobileNumber.setText(RandomPhoneNumberGenerator());
        return view;
    }

    public static String RandomPhoneNumberGenerator() {
        String countryCode = "+212";
        String prefix = "609";

        // Generate random numbers for the last six digits
        Random random = new Random();
        @SuppressLint("DefaultLocale") String middlePart = String.format("%03d", random.nextInt(1000)); // Random 3-digit number
        @SuppressLint("DefaultLocale") String lastPart = String.format("%03d", random.nextInt(1000));   // Random 3-digit number
        // Concatenate the parts

        // Print the phone number
        return String.format("%s %s %s %s", countryCode, prefix, middlePart, lastPart);
    }
}
