package com.example.sutddawn.backups;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sutddawn.ui.marketplace.Group_View;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Map;

public class MainActivityB extends AppCompatActivity {

    //private ActivityMainBinding binding;

    // test

    List<String> parentList; //cohort list
    List<String> childList; //group list in each cohort
    Map<String, List<String>> cohortCollection; //to link parent and child
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    private FirebaseDatabase database;
    TextView register;
    EditText username;
    EditText password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);


        //Main activity is the login page
        //TODO link the username, password and login widgets to the activity

        //TODO Link register to the textview in the registration page
        register.setOnClickListener(view -> {
            //TODO bring user to the registration page
        });

        //test view for marketplace(group) UI page
        Intent i = new Intent(this, Group_View.class);
        startActivity(i);

    }
}