package com.example.sutddawn.ui.LoginPage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sutddawn.R;
import com.example.sutddawn.ui.Registration.Registration;
import com.example.sutddawn.ui.marketplace.Group_View;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignIn extends AppCompatActivity {

    private Button doneButton;
    private Button signInButton;
    private EditText password;
    private EditText email;
    private FirebaseAuth mAuth;
    DatabaseReference mRootDatabaseRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mRootDatabaseRef = FirebaseDatabase.getInstance().getReference();
        //TODO choose the correct layout to show the sign in page
        setContentView(R.layout.activity_main_login);

        //TODO Link up all the widgets using the same IDs as the variable names to avoid errors
        doneButton = findViewById(R.id.done);
        signInButton = findViewById(R.id.signButton);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this, Registration.class);
                startActivity(intent);
            }
        });

        email =  findViewById(R.id.studentEmail);
        password =  findViewById(R.id.password);

    }

    private void signIn() {
        String signInEmail = email.getText().toString().trim();
        String signInPassword = password.getText().toString().trim();
        //check if any of the fields are empty
        if (signInPassword.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
        }
        if (signInEmail.isEmpty()){
            email.setError("Student ID is required");
            email.requestFocus();
        }
        // check for valid email format
        if (!Patterns.EMAIL_ADDRESS.matcher(signInEmail).matches()){
            email.setError("Please provide a valid email");
            email.requestFocus();
        }

        mAuth.signInWithEmailAndPassword(signInEmail, signInPassword).addOnCompleteListener(task -> {
            //TODO redirect to login page
            if (task.isSuccessful()){

                String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                Toast.makeText(SignIn.this, "Logged in to "+email.getText(),
                        Toast.LENGTH_SHORT).show();
                //gets a reference to the current user
                Intent intent = new Intent(SignIn.this, Group_View.class);
                intent.putExtra("currentUser", uid);
                startActivity(intent);

            }
            else{
                Toast.makeText(SignIn.this, "Failed to login, invalid credentials", Toast.LENGTH_LONG).show();
            }
        });
    }
}