package com.example.projectmanagementapp.ui.home;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.projectmanagementapp.R;
import com.example.projectmanagementapp.state.UserState;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        final TextView usernameTextView = view.findViewById(R.id.tv_username);
        final CardView memberCardView = view.findViewById(R.id.bg_member);
        final TextView memberTextView = view.findViewById(R.id.tv_member);

        memberCardView.setCardBackgroundColor(UserState.getInstance().getColor());
        memberTextView.setText(UserState.getInstance().getUser().getProfile());

        usernameTextView.setText(UserState.getInstance().getUsername());

        return view;
    }

}