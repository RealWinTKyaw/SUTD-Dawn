package com.example.sutddawn.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.sutddawn.R;
import com.example.sutddawn.ui.userprofile.UserProfile;
import com.example.sutddawn.user_classes.Group;
import com.example.sutddawn.user_classes.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Search extends AppCompatActivity{
    private EditText searchObject;
    private SwitchCompat toggle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        setContentView(R.layout.test_search_input);

        ImageButton searchButton = (ImageButton) findViewById(R.id.search_icon_button);
        searchObject = findViewById(R.id.search_bar);
        toggle = findViewById(R.id.search_switch);

        ArrayList<Student> userData = new ArrayList<>();
        DatabaseReference databaseReferenceStudent = database.getReference().child("Users");
        ArrayList<Group> groupData = new ArrayList<>();
        DatabaseReference databaseReferenceGroup = database.getReference().child("Groups");

        toggle.setOnClickListener(view -> {
            boolean check = toggle.isChecked();
            if (check){
                Toast.makeText(Search.this, "Searching for Groups", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(Search.this, "Searching for Students", Toast.LENGTH_SHORT).show();
            }
        });

        searchButton.setOnClickListener(view -> {
            if (!toggle.isChecked()) {
                databaseReferenceStudent.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            for (DataSnapshot user : task.getResult().getChildren()) {
                                Student student = user.getValue(Student.class);
                                userData.add(student);
                            }
                            ArrayList<String> result = new ArrayList<>();
                            if (userData.size()!=0){
                                //Student Search
                                for(Student student : userData){
                                    Log.d("Username", student.getName());
                                    if(!(result.contains(student.getName()))
                                            // Skill Search
                                            && ((student.returnSkills().contains(searchObject.getText().toString()))
                                            // Name Search
                                            || (student.getName().contains(searchObject.getText().toString())))){
                                        result.add(student.getName());
                                    }
                                }
                            }
                            //list displays list of students
                            ListView listView = (ListView) findViewById(R.id.listview);
                            listView.setAdapter(new ArrayAdapter<>(this, R.layout.activity_search, result));
                            //when you click on one of the student, it goes to our one and only user profile page
                            listView.setOnItemClickListener((adapterView, view1, i, l) -> startActivity(new Intent(Search.this, UserProfile.class)));
                        } else {
                            Toast.makeText(Search.this, "There are no registered users", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Search.this, "Unable to fetch data from database", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                databaseReferenceGroup.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            for (DataSnapshot group : task.getResult().getChildren()) {
                                Group groupName = group.getValue(Group.class);
                                groupData.add(groupName);
                            }
                            //search from the list of groups
                            ArrayList<String> result = new ArrayList<>();
                            if (groupData.size() != 0) {
                                for (Group group : groupData) {
                                    if (!(result.contains(group.getName()))
                                            //Name Search
                                            && ((group.getName().contains(searchObject.getText().toString()))
                                            // Module Search
                                            || (group.getModule().contains(searchObject.getText().toString())) )) {
                                        result.add(group.getName());
                                    }
                                }
                            }
                            //list displays list of students
                            ListView listView = (ListView) findViewById(R.id.listview);
                            listView.setAdapter(new ArrayAdapter<>(this, R.layout.activity_search, result));
                            //when you click on one of the student, it goes to our one and only user profile page
                            listView.setOnItemClickListener((adapterView, view1, i, l) -> startActivity(new Intent(Search.this, UserProfile.class)));
                        } else {
                            Toast.makeText(Search.this, "There are no registered groups", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Search.this, "Unable to fetch data from database", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

}