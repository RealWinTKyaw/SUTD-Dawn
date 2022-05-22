package com.example.sutddawn.ui.userprofile;

import static com.example.sutddawn.ui.marketplace.Group_View.currentLoggedInUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sutddawn.R;
import com.example.sutddawn.ui.chat.MainChatGoodActivity;
import com.example.sutddawn.ui.marketplace.Individual_View;
import com.example.sutddawn.user_classes.Student;
import com.google.android.material.chip.Chip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserProfile extends AppCompatActivity {

    Student s;
    static String studentFID;
    ImageButton backButton;
    ImageButton chatButton;
    ImageButton editButton;
    static boolean isLoggedInUser = false;

    ImageView profilePic;
    TextView user_name;
    Chip userRating;
    TextView bio;
    RecyclerView skillsList;
    SkillListAdapter skillListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Users");

        Intent intent = getIntent();
        studentFID = intent.getStringExtra("selected");
        isLoggedInUser = studentFID.equals(currentLoggedInUser);

        user_name = findViewById(R.id.user_name);
        userRating = findViewById(R.id.user_rating);
        bio = findViewById(R.id.bio);
        skillsList = findViewById(R.id.skills_list);

        databaseReference.child(studentFID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    s = snapshot.getValue(Student.class);
                }
                user_name.setText(s.getName());
                userRating.setText(s.getOverallIndividualRating());
                bio.setText(s.getStudentBio());
                skillListAdapter = new SkillListAdapter(UserProfile.this, s.getAverageSkillRatings());
                skillsList.setAdapter(skillListAdapter);
                skillsList.setLayoutManager(new LinearLayoutManager(UserProfile.this));
                //the user can edit their own profile and chat with others
                if (isLoggedInUser) {
                    chatButton.setVisibility(View.GONE);
                    editButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Go back to the individual view marketplace
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent();
                setResult(Activity.RESULT_OK, goBack);
                finish();
            }
        });

        editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toEditUser = new Intent(UserProfile.this, EditProfile.class);
                startActivity(toEditUser);
            }
        });

        chatButton = findViewById(R.id.chatButton);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Set Intent to chat screen
                Intent toChat = new Intent(UserProfile.this, MainChatGoodActivity.class);
                toChat.putExtra("studentId",s.getFirebaseID());
                startActivityForResult(toChat, 100);

            }
        });

    }
}