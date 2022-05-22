package com.example.sutddawn.ui.groupprofile;

import static com.example.sutddawn.ui.marketplace.Group_View.currentLoggedInUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.sutddawn.R;
import com.example.sutddawn.user_classes.Group;
import com.google.android.material.chip.Chip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GroupProfile extends AppCompatActivity {

    Group g;
    ImageButton backButton;
    TextView groupName;
    TextView description;
    Chip module;
    Button joinGroup;
    Button changeAccess;
    String currentGroup;
    RecyclerView studentList;
    StudentListAdapter studentListAdapter;
    String access = "closed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_profile);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Groups");

        //retrieves name of selected group; see marketplace > GroupAdapter
        Intent fromMarketplace = getIntent();
        currentGroup = fromMarketplace.getStringExtra("selected");

        // Initialize UI
        groupName = findViewById(R.id.group_name);
        description = findViewById(R.id.description);
        module = findViewById(R.id.module_tag);
        joinGroup = findViewById(R.id.joinGroupButton);
        studentList = findViewById(R.id.studentListGroup);
        changeAccess = findViewById(R.id.changeGroupAccess);

        joinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //user is already in the group
                if (g.getGroupMembers().contains(currentLoggedInUser)) {
                    Toast toast = Toast.makeText(GroupProfile.this,
                            R.string.already_in_group, Toast.LENGTH_LONG);
                    toast.show();
                }
                //group does not have public access, or is already full
                else {
                    g.addGroupMember(currentLoggedInUser);
                    if (!g.getGroupMembers().contains(currentLoggedInUser)) {
                        Toast toast = Toast.makeText(GroupProfile.this,
                                R.string.failed_join_group, Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        databaseReference.child(currentGroup)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            databaseReference.child(currentGroup).setValue(g);
                                            Toast toast = Toast.makeText(GroupProfile.this,
                                                    "Successfully joined " + g.getName() + "!", Toast.LENGTH_LONG);
                                            toast.show();
                                            goBack();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                    }
                }
            }
        });

        //change the group's access between public and private
        changeAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                g.changeAccess();
                if (g.getOpen()) {
                    access = "open";
                }
                databaseReference.child(currentGroup)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    databaseReference.child(currentGroup)
                                            .child("open").setValue(g.getOpen());
                                    Toast toast = Toast.makeText(GroupProfile.this,
                                            g.getName() + " is now " + access, Toast.LENGTH_SHORT);
                                    toast.show();
                                    goBack();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
            }
        });

        //retrieves data from Firebase to populate the page
        databaseReference.child(currentGroup).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    g = snapshot.getValue(Group.class);
                }
                groupName.setText(g.getName());
                description.setText(g.getDescription());
                module.setText(g.getModule());
                //only the group's creator can change its access
                if (g.getGroupMembers().get(0).equals(currentLoggedInUser)) {
                    changeAccess.setVisibility(View.VISIBLE);
                }
                studentListAdapter = new StudentListAdapter(GroupProfile.this, g.getGroupMembers());
                studentList.setAdapter(studentListAdapter);
                studentList.setLayoutManager(new LinearLayoutManager(GroupProfile.this));
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
                goBack();
            }
        });
    }

    public void goBack() {
        Intent goBack = new Intent();
        setResult(Activity.RESULT_OK, goBack);
        finish();
    }
}