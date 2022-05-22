package com.example.sutddawn.ui.Registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sutddawn.R;
import com.example.sutddawn.ui.LoginPage.SignIn;
import com.example.sutddawn.user_classes.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Registration extends AppCompatActivity {

    ImageButton backButton;
    Button Registration;
    EditText fullName;
    EditText studentID;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_registration);
        final Registration self = this;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Users");
        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        fullName = findViewById(R.id.Name);
        studentID = findViewById(R.id.StudentID);
        email = findViewById(R.id.studentEmail);
        password = findViewById(R.id.password);

        backButton = findViewById(R.id.backToPage);
        backButton.setOnClickListener(view -> self.intentToSignInActivity());

        Registration = findViewById(R.id.charaButton);
        Registration.setOnClickListener(view -> {
            String creatingUserName = fullName.getText().toString();
            String creatingUserID = studentID.getText().toString();
            String creatingUserEmail = email.getText().toString();
            String creatingUserPassword = password.getText().toString();
            //check that text fields are not empty
            if (creatingUserName.length() == 0 || creatingUserID.length() == 0
                    || creatingUserEmail.length() == 0) {
                Toast toast = Toast.makeText(Registration.this,
                        R.string.blank_text_warning, Toast.LENGTH_SHORT);
                toast.show();
            }
            //check that password is strong enough
            else if (!isPasswordStrong(creatingUserPassword)){
                Toast toast = Toast.makeText(Registration.this,
                        R.string.weak_password, Toast.LENGTH_SHORT);
                toast.show();

            } else {
                database.getReference().get().addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        fAuth.createUserWithEmailAndPassword(creatingUserEmail, creatingUserPassword)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()){
                                        //create a new Student object
                                        Student student = new Student(creatingUserID, creatingUserName,
                                                creatingUserEmail, creatingUserPassword);
                                        String uid = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                                        student.setFirebaseID(uid);
                                        databaseReference.child(uid).setValue(student);
                                        Toast toast = Toast.makeText(Registration.this,
                                                R.string.on_user_creation, Toast.LENGTH_SHORT);
                                        toast.show();
                                        //automatically redirect to SignIn page
                                        Intent goBack = new Intent();
                                        setResult(Activity.RESULT_OK, goBack);
                                        finish();
                                    } else {
                                        Toast toast = Toast.makeText(Registration.this,
                                                R.string.email_exists, Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                });

                    } else {
                        Toast toast = Toast.makeText(Registration.this,
                                R.string.failed_user_creation, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        });

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

    public void intentToSignInActivity() {
        Intent intent = new Intent(Registration.this, SignIn.class);
        startActivity(intent);
    }

}