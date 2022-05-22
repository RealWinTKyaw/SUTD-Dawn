package com.example.sutddawn.ui.marketplace;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sutddawn.R;
import com.example.sutddawn.ui.groupprofile.CreateGroupActivity;
import com.example.sutddawn.user_classes.Group;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Group_View extends AppCompatActivity {

    GroupAdapter adapter;
    Button button;
    ToggleButton toggleButton;
    public static String currentLoggedInUser; //firebaseID of current user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marketplace_no_cohorts);
        final Group_View self = this;

        button = findViewById(R.id.functionalButton);
        toggleButton = findViewById(R.id.toggleButton);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Groups");

        Intent intent = getIntent();
        currentLoggedInUser = intent.getStringExtra("currentUser");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //recycler view updates in real-time
        FirebaseRecyclerOptions<Group> options =
                new FirebaseRecyclerOptions.Builder<Group>()
                        .setQuery(databaseReference, Group.class).build();
        adapter = new GroupAdapter(options);
        recyclerView.setAdapter(adapter);
        button.setOnClickListener(view -> self.intentToCreateGroup());

        //stylistic choice
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                toggleButton.setOnClickListener(view -> {
                    Intent i = new Intent(Group_View.this, Individual_View.class);
                    startActivity(i);
                });
            }

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

    public void intentToCreateGroup() {
        Intent intent = new Intent(Group_View.this, CreateGroupActivity.class);
        startActivity(intent);
    }
}