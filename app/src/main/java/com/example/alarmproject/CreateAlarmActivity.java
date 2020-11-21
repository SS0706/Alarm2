package com.example.alarmproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAlarmActivity extends AppCompatActivity {

    public final String TAG = "Logcat";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_alarms_sub);
        Log.i(TAG, "onCreate");

        Intent intent = new Intent(CreateAlarmActivity.this, MainActivity.class);
    }


}
