package com.example.sutddawn.backups;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sutddawn.R;

import java.util.ArrayList;
import java.util.Random;
import java.util.Date;




public class MainActivityRegistration extends AppCompatActivity {


    ArrayList<Integer> images;
    Button charaButton;

    ImageView charaImage;
    TextView textViewRandomImages;
    int count = 0;
    Date d = new Date();
    Random random = new Random(d.getTime());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_registration);

        images = new ArrayList<Integer>();







        charaButton = findViewById(R.id.charaButton);


        charaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = random.nextInt(images.size());
                charaImage.setImageResource(images.get(i));
                Toast.makeText(MainActivityRegistration.this, "done " + charaButton, Toast.LENGTH_LONG).show();
            }
        });

    };


}


