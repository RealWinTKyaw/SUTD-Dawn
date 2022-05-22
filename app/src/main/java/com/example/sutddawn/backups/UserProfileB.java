package com.example.sutddawn.backups;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sutddawn.R;
import com.example.sutddawn.ui.chat.MainChatGoodActivity;
import com.example.sutddawn.ui.userprofile.AllSkills;
import com.example.sutddawn.ui.userprofile.EditProfile;
import com.example.sutddawn.ui.userprofile.SkillListAdapter;
import com.example.sutddawn.user_classes.Student;
import com.google.android.material.chip.Chip;

public class UserProfileB extends AppCompatActivity {

    Student s;

    ImageButton backButton;
    ImageButton rightTopButton;
    boolean isLoggedInUser = false;

    ImageView profilePic;
    TextView user_name;
    Chip userRating;
    RecyclerView skillsList;
    SkillListAdapter skillListAdapter;
    TextView bio;
    RecyclerView linksList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // DEBUGGING START
        s = new Student("1005555","Pumpkin Koh","pumpkinkoh@sutd.edu.sg","");
        s.setOverallIndividualRating("0.0");
        s.rateSkill("Programming",s,"5");
        s.rateSkill("Soldering",s,"4");
//        s.setStudentBio("Hello world!");
        // DEBUGGING END

        // Get student Id from marketplace
        Intent intent = getIntent();

        // TODO: Get student object from studentId
        String studentEmailId = intent.getStringExtra("emailId");
        s.setEmail(studentEmailId);

        // * Initialize UI
        Bitmap profilePicBM = null;
//        profilePic = findViewById(R.id.user_profilepicture);
//        profilePic.setImageBitmap(profilePicBM);

        user_name = findViewById(R.id.user_name);
        user_name.setText(s.getName());

        userRating = findViewById(R.id.user_rating);
        userRating.setText(s.getOverallIndividualRating());

        bio = findViewById(R.id.bio);
//        bio.setText(s.getStudentBio());

        // TODO: Check if Student is logged in user. If so, change rightTopButton.
//        rightTopButton.setImageURI();

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

        // * rightTopButton
        // Logged in user: Go to edit user
        // Another user: Go to chat with user
        rightTopButton = findViewById(R.id.chatButton);
        rightTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isLoggedInUser){
                    /*Intent toEditUser = new Intent(com.example.sutddawn.ui.userprofile.UserProfile.this, EditProfile.class);
                    toEditUser.putExtra("studentID", s.getID());
                    startActivity(toEditUser);

                     */
                } else {
                    // TODO: Set Intent to chat screen
                    /*
                    Intent toChat = new Intent(com.example.sutddawn.ui.userprofile.UserProfile.this, MainChatGoodActivity.class);
                    toChat.putExtra("studentId",s.getEmail());
                    startActivityForResult(toChat, 100);

                     */
                }
            }
        });

        // Go to the full Skill list
//        System.out.println(s.getSkills());
        skillsList = findViewById(R.id.skills_list);
        skillsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent allSkills = new Intent(com.example.sutddawn.ui.userprofile.UserProfile.this, AllSkills.class);
//                allSkills.putExtra("studentID", s.getID());
                startActivity(allSkills);

                 */
            }
        });

        //skillListAdapter = new SkillListAdapter(com.example.sutddawn.ui.userprofile.UserProfile.this, s.returnSkills());
        skillsList.setAdapter(skillListAdapter);
        skillsList.setLayoutManager(new LinearLayoutManager(this));
    }
}
