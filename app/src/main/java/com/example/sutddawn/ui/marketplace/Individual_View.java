package com.example.sutddawn.ui.marketplace;

import static com.example.sutddawn.ui.marketplace.Group_View.currentLoggedInUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sutddawn.R;
import com.example.sutddawn.ui.userprofile.UserProfile;
import com.example.sutddawn.user_classes.Student;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Individual_View extends AppCompatActivity {

    StudentAdapter adapter;
    Button button;
    ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_no_cohorts);
        final Individual_View self = this;

        button = findViewById(R.id.functionalButton);
        toggleButton = findViewById(R.id.toggleButton);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Users");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //recycler view updates in real-time
        FirebaseRecyclerOptions<Student> options =
                new FirebaseRecyclerOptions.Builder<Student>()
                        .setQuery(databaseReference, Student.class).build();
        adapter = new StudentAdapter(options);
        recyclerView.setAdapter(adapter);

        button.setOnClickListener(view -> self.intentToUserProfile());

        //stylistic choice
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                toggleButton.setOnClickListener(view -> {
                    //TODO: Set intent go to Individual
                    Intent goBack = new Intent();
                    setResult(Activity.RESULT_OK, goBack);
                    finish();
                });
            }  //No Change

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void intentToUserProfile() {
        //passes a reference to current user to UserProfile
        Intent intent = new Intent(Individual_View.this, UserProfile.class);
        intent.putExtra("selected", currentLoggedInUser);
        startActivity(intent);
    }

}