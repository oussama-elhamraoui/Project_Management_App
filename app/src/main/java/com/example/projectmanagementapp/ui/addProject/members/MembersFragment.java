package com.example.projectmanagementapp.ui.addProject.members;



import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmanagementapp.R;
import com.example.projectmanagementapp.data.remote.repository.MemberRepository;
import com.example.projectmanagementapp.models.User;
import com.example.projectmanagementapp.models.UserTheme;
import com.example.projectmanagementapp.state.MemberState;
import com.example.projectmanagementapp.state.ProjectState;

import java.util.ArrayList;
import java.util.List;


public class MembersFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_members, container, false);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        final CardView addMemberButton = view.findViewById(R.id.cv_add_member);

        addMemberButton.setOnClickListener( v -> {
            handleDialog();
        });


        final RecyclerView recyclerView = view.findViewById(R.id.rv_members);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Example data
        final List<User> members = new ArrayList<>();
        members.add(new User(1, "Alice", "Project Manager","", UserTheme.values[0]));
        members.add(new User(1, "Bob", "Developer","", UserTheme.values[1]));
        members.add(new User(1, "Carol", "Designer","", UserTheme.values[2]));

        final MembersAdapter adapter = new MembersAdapter(members);
        recyclerView.setAdapter(adapter);

        return view;

    }

    private void handleDialog() {
        final Dialog dialogView = new Dialog(getContext());
        dialogView.setContentView(R.layout.dialog_add_member);
        final Window window = dialogView.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        dialogView.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.dialog_box_background));
        // Build the dialog
        final LinearLayout checkBox = dialogView.findViewById(R.id.cb_add_as_admin);
        checkBox.setOnClickListener(v1 -> {
            MemberState.getInstance().toggle();
        });
        final CardView cardViewContent = dialogView.findViewById(R.id.cv_checkbox_on);
        MemberState.getInstance().isAdmin.observe(getViewLifecycleOwner(), isAdmin -> {
            cardViewContent.setVisibility(isAdmin ? View.VISIBLE : View.INVISIBLE);
        });

        final CardView addMemberButton = dialogView.findViewById(R.id.cv_add_member);
        addMemberButton.setOnClickListener(v -> {
            final EditText emailEditView = dialogView.findViewById(R.id.et_member_email);
            final String email = emailEditView.getText().toString().trim();
            // need new api to get the member id by email

//            MemberRepository.getInstance().addMember(email); only on edit

        });
        // Show the dialog
        dialogView.show();
    }


}