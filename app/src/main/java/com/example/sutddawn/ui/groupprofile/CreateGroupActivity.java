package com.example.sutddawn.ui.groupprofile;

import static com.example.sutddawn.ui.marketplace.Group_View.currentLoggedInUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sutddawn.R;
import com.example.sutddawn.ui.marketplace.Group_View;
import com.example.sutddawn.user_classes.Group;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateGroupActivity extends AppCompatActivity {

    ImageButton backButton;
    EditText groupName;
    EditText groupCapacity;
    EditText groupModule;
    EditText groupDescription;
    Button createGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group);
        final CreateGroupActivity self = this;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Groups");

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent();
                setResult(Activity.RESULT_OK, goBack);
                finish();
            }
        });

        groupName = findViewById(R.id.groupName);
        groupCapacity = findViewById(R.id.groupCapacity);
        groupModule = findViewById(R.id.groupModule);
        groupDescription = findViewById(R.id.groupDescription);

        createGroup = findViewById(R.id.createGroup);
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String creatingGroupName = groupName.getText().toString();
                String creatingGroupCapacity = groupCapacity.getText().toString();
                String creatingGroupModule = groupModule.getText().toString();
                String creatingGroupDescription = groupDescription.getText().toString();

                //check that text fields not empty
                if (creatingGroupName.length() == 0 || creatingGroupCapacity.length() == 0
                || creatingGroupModule.length() == 0) {
                    Toast toast = Toast.makeText(CreateGroupActivity.this,
                            R.string.blank_text_warning, Toast.LENGTH_SHORT);
                    toast.show();
                }
                //check that group names are valid (no special characters due to Firebase restrictions)
                else if (!isGroupNameValid(creatingGroupName)){
                    Toast toast = Toast.makeText(CreateGroupActivity.this,
                            R.string.invalid_grpName, Toast.LENGTH_SHORT);
                    toast.show();
                }
                //description not too long
                else if (creatingGroupDescription.length() > 100) {
                    Toast toast = Toast.makeText(CreateGroupActivity.this,
                            R.string.bio_too_long, Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    database.getReference().get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {

                            if (task.isSuccessful()) {
                                databaseReference.child(creatingGroupName)
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (!snapshot.exists()){
                                                    //create a new Group object with entered fields
                                                    int numGroupCapacity = Integer.parseInt(creatingGroupCapacity);
                                                    Group newGroup = new Group(numGroupCapacity,
                                                            creatingGroupName, creatingGroupModule);
                                                    newGroup.addGroupMember(currentLoggedInUser);
                                                    if (creatingGroupDescription.length() != 0) {
                                                        newGroup.setDescription(creatingGroupDescription);
                                                    }
                                                    //store the object under its name
                                                    databaseReference.child(creatingGroupName)
                                                            .setValue(newGroup);
                                                    Toast toast = Toast.makeText(CreateGroupActivity.this,
                                                            R.string.on_group_creation, Toast.LENGTH_SHORT);
                                                    toast.show();
                                                    //automatically redirect to marketplace
                                                    Intent goBack = new Intent();
                                                    setResult(Activity.RESULT_OK, goBack);
                                                    finish();
                                                } else {
                                                    Toast toast = Toast.makeText(CreateGroupActivity.this,
                                                            R.string.group_exists, Toast.LENGTH_SHORT);
                                                    toast.show();
                                                }
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                            } else {
                                Toast toast = Toast.makeText(CreateGroupActivity.this,
                                        R.string.failed_group_creation, Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    });
                }
            }
        });

    }
    public boolean isGroupNameValid(String name) {
        boolean letters = true;
        for (int i=0; i<name.length(); i++) {
            char ch = name.charAt(i);
            if (!(Character.isAlphabetic(ch) || Character.isDigit(ch) || Character.isWhitespace(ch))) {
                letters = false;
                break;
            }
        }
        return letters;
    }
    public void intentToGroup_ViewActivity() {
        Intent intent = new Intent(CreateGroupActivity.this, Group_View.class);
        startActivity(intent);
    }
}
