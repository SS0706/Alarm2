package com.example.alarmproject;

import android.content.Context;
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
    public static Intent launchIntent(Context context) {
        final Intent i = new Intent(context, AlarmsPageActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }



}
