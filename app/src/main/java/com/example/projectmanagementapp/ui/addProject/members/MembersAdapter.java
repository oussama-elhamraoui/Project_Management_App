package com.example.projectmanagementapp.ui.addProject.members;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmanagementapp.R;
import com.example.projectmanagementapp.models.Member;

import java.util.List;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MemberViewHolder> {

    private final List<Member> memberList;

    public MembersAdapter(List<Member> memberList) {
        this.memberList = memberList;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_member, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        final Member member = memberList.get(position);
        holder.memberBackground.setCardBackgroundColor(member.user.getColor());
        holder.memberAbbreviation.setText(member.user.getProfile());
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    static class MemberViewHolder extends RecyclerView.ViewHolder {

        TextView memberAbbreviation;
        CardView memberBackground;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            memberAbbreviation = itemView.findViewById(R.id.tv_member);
            memberBackground = itemView.findViewById(R.id.bg_member);
        }
    }

    // Member model class
}