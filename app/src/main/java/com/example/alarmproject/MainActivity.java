package com.example.alarmproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.example.alarmproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button buttonCreateAlarm;
    Button buttonAlarmsPage;
    Button buttonLeaderboard;
    TextView textViewWelcome;
    public final String TAG = "Logcat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate");

        Intent intentSubToMain = getIntent();

        textViewWelcome = findViewById(R.id.textViewWelcome);
        buttonCreateAlarm = findViewById(R.id.buttonCreateAlarm);
        buttonAlarmsPage = findViewById(R.id.buttonAlarmsPage);
        buttonLeaderboard = findViewById(R.id.buttonLeaderboard);


        buttonCreateAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateAlarmActivity.class);
                startActivity(intent);
            }
        });
        buttonAlarmsPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AlarmsPageActivity.class);
                startActivity(intent);
            }
        });
        buttonLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
                startActivity(intent);
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menuCreateNewAlarm){
            Intent intent = new Intent( this, CreateAlarmActivity.class);
            startActivity(intent);
            return true;
        }
        if(id == R.id.menuAlarmsPage){
            Intent intent = new Intent( this, AlarmsPageActivity.class);
            startActivity(intent);
            return true;
        }
        if(id == R.id.menu_check_out_leaderBoard){
            Intent intent = new Intent( this, AlarmsPageActivity.class);
            startActivity(intent);
            return true;
        }
        if(id == R.id.menu_choose_game){
            Intent intent = new Intent( this, AlarmsPageActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


}
