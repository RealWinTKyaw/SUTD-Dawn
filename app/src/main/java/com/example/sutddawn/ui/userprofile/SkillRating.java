package com.example.sutddawn.ui.userprofile;

import static com.example.sutddawn.ui.marketplace.Group_View.currentLoggedInUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.sutddawn.R;
import com.example.sutddawn.user_classes.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SkillRating extends AppCompatActivity {

    Student user;
    Student toRate;
    ImageButton backButton;
    Button submitButton;
    RatingBar skillRating;
    RatingBar overallRating;
    String skillToRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_rating);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Users");

        Intent intent = getIntent();
        skillToRate = intent.getStringExtra("selected");
        Toast toast = Toast.makeText(SkillRating.this,
                "Selected skill: "+skillToRate, Toast.LENGTH_SHORT);
        toast.show();

        databaseReference.child(currentLoggedInUser).addListenerForSingleValueEvent
                (new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            user = snapshot.getValue(Student.class);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        skillRating = findViewById(R.id.skill_rating);
        overallRating = findViewById(R.id.eval_rating);

        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String givenSkillRating = String.valueOf(skillRating.getRating());
                String givenOverallRating = String.valueOf(overallRating.getRating());
                if (!givenSkillRating.equals("0.0") || !givenOverallRating.equals("0.0")) {
                    databaseReference.child(UserProfile.studentFID).addListenerForSingleValueEvent
                            (new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        toRate = snapshot.getValue(Student.class);
                                    }
                                    if (!givenSkillRating.equals("0.0")) {
                                        user.rateSkill(skillToRate, toRate, givenSkillRating);
                                    }
                                    if (!givenOverallRating.equals("0.0")) {
                                        user.rateOverall(toRate, givenOverallRating);
                                    }
                                    databaseReference.child(UserProfile.studentFID)
                                            .setValue(toRate);
                                    Toast toast = Toast.makeText(SkillRating.this,
                                            R.string.feedback_thx, Toast.LENGTH_SHORT);
                                    toast.show();
                                    goBack();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }
            }
        });
    }

    public void goBack() {
        Intent goBack = new Intent();
        setResult(Activity.RESULT_OK, goBack);
        finish();
    }
}