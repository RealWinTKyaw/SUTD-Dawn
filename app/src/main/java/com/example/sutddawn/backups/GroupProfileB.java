package com.example.sutddawn.backups;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sutddawn.R;
import com.example.sutddawn.ui.groupprofile.StudentListAdapter;
import com.example.sutddawn.user_classes.Group;
import com.example.sutddawn.user_classes.Student;
import com.google.android.material.chip.Chip;

public class GroupProfileB extends AppCompatActivity {

    Group g;

    ImageButton backButton;
    TextView groupName;
    TextView description;
    Chip module;
    RecyclerView studentList;
    StudentListAdapter studentListAdapter;
    Button joinGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_profile);

        // DEBUGGING START
        Student s = new Student("1005555","Pumpkin Koh","pumpkinkoh@sutd.edu.sg","");
        s.setOverallIndividualRating("0.0");
        s.rateSkill("Programming",s,"5");
        s.rateSkill("WAAA",s,"4");
        //g = new Group(8, "The Thinkers");
        g.setModule("50.001");
        g.setDescription("Mighty fine.");

        // FETCH FROM DATABASE
//        g.addGroupMember("9BkFy2A4J4cOJba03QilvQZWK532");

        // HARDCODED
        //g.addGroupMember(s);

        // DEBUGGING END

        Intent fromMarketplace = getIntent();
        String g_temp = fromMarketplace.getStringExtra("groupName");
        g.setName(g_temp);

        // Initialize UI
        groupName = findViewById(R.id.group_name);
        description = findViewById(R.id.description);
        module = findViewById(R.id.module_tag);
        //studentList = findViewById(R.id.studentListGroup);
        joinGroup = findViewById(R.id.joinGroupButton);

        groupName.setText(g.getName());
        description.setText(g.getDescription());
        module.setText(g.getModule());

        // FETCH FROM DATABASE
//        studentListAdapter = new StudentListAdapter(GroupProfile.this, g.retrieveStudentsFromFirebase());

        // HARDCODED
//        studentListAdapter = new StudentListAdapter(com.example.sutddawn.ui.groupprofile.GroupProfile.this, g.returnGroupMembersObject());

        studentList.setAdapter(studentListAdapter);
        studentList.setLayoutManager(new LinearLayoutManager(this));

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
    }
}
