package com.example.sutddawn.ui.userprofile;

import static com.example.sutddawn.ui.marketplace.Group_View.currentLoggedInUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sutddawn.R;
import com.example.sutddawn.user_classes.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditSkill extends AppCompatActivity {

    ImageButton backButton;
    EditText skillName;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_skill);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Users");

        // Go back to the marketplace
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent();
                setResult(Activity.RESULT_OK, goBack);
                finish();
            }
        });

        skillName = findViewById(R.id.newSkillName);
        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newSkill = skillName.getText().toString();
                if (newSkill.length() != 0) {
                    databaseReference.child(currentLoggedInUser).addListenerForSingleValueEvent
                            (new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        Student user = snapshot.getValue(Student.class);
                                        user.addCustomSkill(newSkill);
                                        databaseReference.child(currentLoggedInUser)
                                                .setValue(user);
                                        Toast toast = Toast.makeText(EditSkill.this,
                                                "Added new skill: " + newSkill, Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }
            }
        });
    }
}