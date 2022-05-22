package com.example.sutddawn.ui.userprofile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sutddawn.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class SkillListAdapter extends RecyclerView.Adapter<SkillListAdapter.SkillViewHolder> {

    Context context;
    LayoutInflater inflater;
    ArrayList<String> skills;
    ArrayList<String> skillRatings;

    public SkillListAdapter(Context context, HashMap<String, String> skillRatings){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.skills = new ArrayList<>(skillRatings.keySet());
        this.skillRatings = new ArrayList<>(skillRatings.values());
    }

    @NonNull
    @Override
    public SkillListAdapter.SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View skillView = inflater.inflate(R.layout.listitem_skill, parent, false);
        return new SkillViewHolder(skillView);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillListAdapter.SkillViewHolder holder, int position) {
        holder.skillLabel.setText(skills.get(position));
        holder.ratingScore.setText(skillRatings.get(position));
    }

    @Override
    public int getItemCount() {
        return skillRatings.size();
    }

    public class SkillViewHolder extends RecyclerView.ViewHolder {

        TextView skillLabel;
        TextView ratingScore;

        public SkillViewHolder(@NonNull View itemView) {
            super(itemView);
            skillLabel = itemView.findViewById(R.id.skill_label);
            ratingScore = itemView.findViewById(R.id.rating_score);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //since UserProfile and AllSkills use the same adapter,
                    //we can modify click behaviour here
                    if (context instanceof UserProfile) {
                        final Intent toAllSkills = new Intent(context, AllSkills.class);
                        toAllSkills.putExtra("selected", UserProfile.studentFID);
                        context.startActivity(toAllSkills);
                    }
                    //if current student we are viewing is not the user, we can rate their skills
                    //passes a reference of selected skill to rate to SkillRating
                    else if (context instanceof AllSkills && !UserProfile.isLoggedInUser) {
                        final Intent toSkillRating= new Intent(context, SkillRating.class);
                        toSkillRating.putExtra("selected", skills.get(getLayoutPosition()));
                        context.startActivity(toSkillRating);
                    }
                }
            });
        }
    }
}
