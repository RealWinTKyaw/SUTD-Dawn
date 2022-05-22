package com.example.sutddawn.ui.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
//import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.sutddawn.R;
//import android.widget.ImageButton;

//import com.example.sutddawn.R;

//import java.util.ArrayList;

//public class MainChatGoodActivity extends AppCompatActivity {
//ImageButton backtoProfile;
//
public class MainChatGoodActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_chat_good);
//

//
//        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat_good);
        ImageButton backtoProfile;



            backtoProfile = findViewById(R.id.backToProfile);
            backtoProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goBack = new Intent();
                    setResult(Activity.RESULT_OK, goBack);
                    finish();
                }
            });

        // Populate dummy messages in List, you can implement your code here
        ArrayList<MessageModel> messagesList = new ArrayList<>();
        for (int i=0;i<1;i++) {
            messagesList.add(new MessageModel("can I join your group", i % 2 == 0 ? CustomAdapter.MESSAGE_TYPE_IN : CustomAdapter.MESSAGE_TYPE_OUT));
        }
        for (int i=1;i<2;i++) {
            messagesList.add(new MessageModel("yes", i % 2 == 0 ? CustomAdapter.MESSAGE_TYPE_IN : CustomAdapter.MESSAGE_TYPE_OUT));
        }
        for (int i=2;i<3;i++) {
            messagesList.add(new MessageModel("Thank you", i % 2 == 0 ? CustomAdapter.MESSAGE_TYPE_IN : CustomAdapter.MESSAGE_TYPE_OUT));
        }

        CustomAdapter adapter = new CustomAdapter(this, messagesList);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

//        @Override
//        public void onClick(View view) {
//            Intent goBack = new Intent();
//            setResult(Activity.RESULT_OK, goBack);
//            finish();
//        }

    }
    }

