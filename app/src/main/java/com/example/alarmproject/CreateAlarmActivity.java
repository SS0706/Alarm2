package com.example.alarmproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class CreateAlarmActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public final String TAG = "Logcat";

    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    TextView alarm_status;
    Button alarm_on;
    Button alarm_off;
    PendingIntent pending_intent;
    int choose_game;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_alarms_sub);
        Log.i(TAG, "onCreate");

//        Intent intent = new Intent(CreateAlarmActivity.this, MainActivity.class);

        alarm_manager=(AlarmManager) getSystemService(ALARM_SERVICE);
        alarm_timepicker=findViewById(R.id.alarmTimePicker);
        alarm_status=findViewById(R.id.alarm_status);
        final Calendar calendar =Calendar.getInstance(); //create instance of calendar

        Intent intent_alarm=new Intent(CreateAlarmActivity.this,AlarmReceiver.class); //create intent to AlarmReceiver class


        //create spinner
        Spinner spinner = (Spinner) findViewById(R.id.game_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.game_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //set onclick listener to onItemSelected
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);


        //'alarm on' button
        alarm_on=findViewById(R.id.alarm_on);
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                calendar.set(Calendar.HOUR_OF_DAY,alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE,alarm_timepicker.getMinute());

                int hour=alarm_timepicker.getHour();
                int minute=alarm_timepicker.getMinute();

                String hourStr=String.valueOf(hour);
                String minuteStr=String.valueOf(minute);
                if (minute<10){
                    minuteStr="0"+minuteStr;
                }
                //update alarm status
                set_alarm_status("Alarm set to "+hourStr+":"+minuteStr);

                //extra string passed into receiver
                //tell clock that "alarm on" button is pressed
                intent_alarm.putExtra("extra","alarm on");

                //put extra int into intent_alarm
                //tell clock that you want a certain value from the spinner
                intent_alarm.putExtra("game_id",choose_game);

                //create pending intent -> delays intent until specified calendar time
                pending_intent=PendingIntent.getBroadcast(CreateAlarmActivity.this,0,
                        intent_alarm,PendingIntent.FLAG_UPDATE_CURRENT); //Broadcast to receiver when calendar time

                //set alarm manager
                alarm_manager.set(AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        pending_intent);
            }
        });

        alarm_off=findViewById(R.id.alarm_off);
        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_alarm_status("Alarm off");

                //cancel alarm
                alarm_manager.cancel(pending_intent);

                //put extra string into intent_alarm
                //tell clock that "alarm off" is pressed
                intent_alarm.putExtra("extra","alarm_off");
                //put extra int into intent_alarm
                //prevent crashes in Null Pointer Exception
                intent_alarm.putExtra("game_id",choose_game);

                //stop ringtone
                sendBroadcast(intent_alarm);
            }
        });
    }
    private void set_alarm_status(String output){
        alarm_status.setText(output);
    }


    //methods for spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(i)

        //output id that user selected
        choose_game=(int) l; //l is id
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
