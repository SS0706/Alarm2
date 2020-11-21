package com.example.alarmproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class LeaderboardActivity extends AppCompatActivity {

    public final String TAG = "Logcat";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_sub);
        Log.i(TAG, "onCreate");

        Intent intent = new Intent(LeaderboardActivity.this, MainActivity.class);
    }
}
