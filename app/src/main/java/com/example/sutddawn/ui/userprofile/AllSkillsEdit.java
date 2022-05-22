package com.example.sutddawn.ui.userprofile;

import static com.example.sutddawn.ui.marketplace.Group_View.currentLoggedInUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sutddawn.R;
import com.example.sutddawn.user_classes.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllSkillsEdit extends AppCompatActivity {

    Student user;
    ImageButton backButton;
    ImageButton addButton;
    ListView skillsList;
    ArrayList<String> userSkills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_skills_edit);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Users");

        databaseReference.child(currentLoggedInUser).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            user = snapshot.getValue(Student.class);
                            userSkills = new ArrayList<>(user.returnSkills());
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                                (AllSkillsEdit.this, R.layout.activity_listitem_editlink, userSkills);
                        skillsList.setAdapter(arrayAdapter);
                        skillsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String selected = userSkills.get(i);
                                userSkills.remove(selected);
                                user.removeCustomSkill(selected);
                                databaseReference.child(currentLoggedInUser).addListenerForSingleValueEvent(
                                        new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if (snapshot.exists()) {
                                                    databaseReference.child(currentLoggedInUser)
                                                            .setValue(user);
                                                }
                                                Toast toast = Toast.makeText(AllSkillsEdit.this,
                                                        "Removed " + selected, Toast.LENGTH_SHORT);
                                                toast.show();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );

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

        // If user is the logged in user allow adding
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toAddSkill = new Intent(AllSkillsEdit.this, EditSkill.class);
                startActivity(toAddSkill);
            }
        });

        skillsList = findViewById(R.id.skill_list);
    }
}