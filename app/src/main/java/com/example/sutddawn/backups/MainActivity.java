package com.example.sutddawn.backups;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sutddawn.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ArrayList<Integer> images;
    Button charaButtonTwo;

    ImageView charaImage;
    TextView textViewRandomImages;
    int count = 0;
    Date d = new Date();
    Random random = new Random(d.getTime());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        images = new ArrayList<>();

        charaButtonTwo.setOnClickListener(view -> {
            int i = random.nextInt(images.size());
            charaImage.setImageResource(images.get(i));
            Toast.makeText(MainActivity.this, "done " + charaButtonTwo, Toast.LENGTH_LONG).show();
        }
        );}}



