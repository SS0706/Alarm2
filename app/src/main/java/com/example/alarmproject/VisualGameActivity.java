package com.example.alarmproject;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class VisualGameActivity extends AppCompatActivity {
    ImageButton oddImage;

    AlarmManager alarm_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualgame_layout);

        oddImage = findViewById(R.id.imageButton5);
        oddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VisualGameActivity.this, "Congrats! You chose the correct image. Have a good day", Toast.LENGTH_LONG).show();
                //call the offTheAlarm method to turn off the alarm
                Intent newIntent = new Intent(VisualGameActivity.this, CreateAlarmActivity.class);
                startActivity(newIntent);
            }
        });
        //the rest of the buttons should show a msg when pressed "Sorry! Please choose the correct image."


    }
}
