package com.andeeni.alarmapp;

import android.app.AppComponentFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class VisualGameActivity extends AppCompatActivity {
    ImageButton oddImage;

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
            }
        });
        //the rest of the buttons should show a msg when pressed "Sorry! Please choose the correct image."


    }




    }
}
