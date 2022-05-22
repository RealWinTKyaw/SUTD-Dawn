package com.example.sutddawn.backups;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sutddawn.R;
import com.example.sutddawn.user_classes.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationBackup extends AppCompatActivity implements View.OnClickListener{

    private Button registerUser;
    private EditText fullName;
    private EditText studentID;
    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        registerUser.setOnClickListener(this);

        //TODO choose the correct layout to show the registration page
        setContentView(R.layout.activity_main);
        //TODO Link up all the widgets using the same IDs as the variable names to avoid errors
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //when register user button is pressed after user fills up everything
            //case R.id.registerUser:
                //registerUser();

            //return to login page
            //case R.id.back:
                //TODO redirect to sign in page

        }
    }
    public static boolean isPasswordStrong(String password){
        int special = 0;
        int upper = 0;
        int lower = 0;
        int number = 0;
        int len = password.length();
        for(int i=0; i<len; i++){
            char ch = password.charAt(i);
            if(Character.isUpperCase(ch))
                upper += 1;
            else if(Character.isLowerCase(ch))
                lower += 1;
            else if(Character.isDigit(ch))
                number += 1;
            else
                special += 1;
        }
        return len >= 6 && upper >= 1 && lower >= 1 && number >= 1 && special >= 1;
    }

    private void registerUser() {
        String registrationEmail = email.getText().toString().trim();
        String registrationPassword = password.getText().toString().trim();
        String registrationStudentId = studentID.getText().toString().trim();
        String registrationFullName = fullName.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(registrationEmail).matches()){
            email.setError("Please provide a valid email");
            email.requestFocus();
        }
        //check if any of the fields are empty
        if (registrationPassword.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
        }
        if (registrationStudentId.isEmpty()){
            studentID.setError("Student ID is required");
            studentID.requestFocus();
        }
        if (registrationFullName.isEmpty()){
            fullName.setError("Full name is required");
            fullName.requestFocus();
        }
        //check password strength
        if (!isPasswordStrong(registrationPassword)){
            password.setError("Password requires at least 1 special character, uppercase letter, lowercase letter and number");
            password.requestFocus();
        }
        mAuth.createUserWithEmailAndPassword(registrationEmail,registrationPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Student user = new Student(registrationStudentId, registrationFullName, registrationEmail, registrationPassword);
                    database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegistrationBackup.this, "User has been successfully registered.", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(RegistrationBackup.this, "User registration failed, please try again.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else{
                    try {
                        throw task.getException();
                    }
                    // if password too weak
                    catch (FirebaseAuthWeakPasswordException weakPassword) {
                        Log.d("Fail to create user", "onComplete: weak_password");
                            password.setError("Password strength too weak. " + weakPassword.getReason());
                            password.requestFocus();
                    }
                    catch (FirebaseAuthUserCollisionException existEmail) {
                        Log.d("Fail to create user", "onComplete: exist_email");
                        email.setError("Email already exists, please use a different email");
                        email.requestFocus();
                    }
                    catch (Exception e) {
                        Log.d("Fail to create user", "onComplete: " + e.getMessage());
                    }
                }
            }
        });

    }
}
