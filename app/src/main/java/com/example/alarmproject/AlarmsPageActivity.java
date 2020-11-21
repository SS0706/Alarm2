package com.example.alarmproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class AlarmsPageActivity extends AppCompatActivity {

    public final String TAG = "Logcat";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarms_page_sub);
        Log.i(TAG, "onCreate");

        Intent intent = new Intent(AlarmsPageActivity.this, MainActivity.class);
    }


}
