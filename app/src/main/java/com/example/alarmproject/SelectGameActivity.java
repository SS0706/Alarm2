package com.example.alarmproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class SelectGameActivity extends AppCompatActivity {

    public final String TAG = "Logcat";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectgame);
        Log.i(TAG, "onCreate");

        Intent intent = new Intent(SelectGameActivity.this, MainActivity.class);
    }


}