package com.example.alarmproject;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.List;

public final class AlarmServiceLoad extends IntentService {

    private static final String TAG = AlarmServiceLoad.class.getSimpleName();
    public static final String ACTION_COMPLETE = TAG + ".ACTION_COMPLETE";
    public static final String ALARMS_EXTRA = "alarms_extra";

    public AlarmServiceLoad(String name){
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        final List<Alarm> alarms = DataBase.getInstance(this).getAlarms();

        final Intent i = new Intent(ACTION_COMPLETE);
        i.putParcelableArrayListExtra(ALARMS_EXTRA, new ArrayList<>(alarms));
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);

    }

    public static void launchLoadAlarmsService(Context context) {
        final Intent launchLoadAlarmsServiceIntent = new Intent(context, AlarmServiceLoad.class);
        context.startService(launchLoadAlarmsServiceIntent);
    }

}