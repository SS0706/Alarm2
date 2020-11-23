package com.example.alarmproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver  extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){

        //fetch extra string from intent
        //tells app whether user press 'alarm on' or 'alarm off' button
        String get_extra_string=intent.getExtras().getString("extra");

        //fetch extra int from intent
        //tells which value user picked from spinner
        Integer get_game_id=intent.getExtras().getInt("game_id");

        //create intent to RingtonePlayingService
        Intent intent_service=new Intent(context,RingtonePlayingService.class);

        //pass extra string from Receiver to RingtonePlayingService
        intent_service.putExtra("extra",get_extra_string);
        //pass extra int from Receiver to RingtonePlayingService
        intent_service.putExtra("game_id",get_game_id);

        //start RingtonePlayingService
        context.startService(intent_service);
    }
}
