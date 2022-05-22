package com.example.sutddawn.ui.userprofile;

import static com.example.sutddawn.ui.marketplace.Group_View.currentLoggedInUser;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sutddawn.R;
import com.example.sutddawn.user_classes.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {

    Student s;
    ImageButton pictureSelect;
    ImageButton backButton;
    Button submit;

    EditText profileName;
    EditText bio;
    EditText classLabel;
    EditText newMod;

    EditText link1;
    EditText link2;
    EditText link3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference().child("Users");

        // Initialize EditText
        profileName = findViewById(R.id.profileName);
        bio = findViewById(R.id.bio);
        classLabel = findViewById(R.id.classLabel);
        newMod = findViewById(R.id.addModule);
        link1 = findViewById(R.id.link1);
        link2 = findViewById(R.id.link2);
        link3 = findViewById(R.id.link3);

        databaseReference.child(currentLoggedInUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    s = snapshot.getValue(Student.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Go back to own User Profile
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        // Change Profile Picture
        pictureSelect = findViewById(R.id.profileImageSelect);
        pictureSelect.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            startActivityForResult(intent, 3);
        });

        // Submit Button
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tracker = 0;
                String newName = profileName.getText().toString();
                String newBio = bio.getText().toString();
                String cohortClass = classLabel.getText().toString();
                String newModule = newMod.getText().toString();
                //check one by one to make sure data not overwritten
                if (newName.length() != 0) {
                    s.setName(newName);
                    tracker += 1;
                } if (cohortClass.length() != 0) {
                    s.setCohortClass(cohortClass);
                    tracker += 1;
                } if (newModule.length() != 0) {
                    s.addCustomModule(newModule);
                    tracker += 1;
                } if (newBio.length() != 0 && newBio.toCharArray().length <= 100) {
                    s.setStudentBio(newBio);
                    tracker += 1;
                } else if (newBio.toCharArray().length > 100) {
                    Toast toast = Toast.makeText(EditProfile.this,
                            R.string.bio_too_long, Toast.LENGTH_SHORT);
                    toast.show();
                } if (tracker > 0) {
                    databaseReference.child(currentLoggedInUser).setValue(s);
                    Toast toast = Toast.makeText(EditProfile.this,
                            R.string.on_user_edit, Toast.LENGTH_SHORT);
                    toast.show();
                    goBack();
                }

            }
        });
    }

    // Handle going to Image Gallery for new Profile Picture
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            pictureSelect.setImageURI(selectedImage);
            // TODO: Set Bitmap image on database
        }
    }

    public void goBack() {
        Intent goBack = new Intent();
        setResult(Activity.RESULT_OK, goBack);
        finish();
    }
}