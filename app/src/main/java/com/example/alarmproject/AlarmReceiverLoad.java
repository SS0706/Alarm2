package com.example.alarmproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

public final class AlarmReceiverLoad extends BroadcastReceiver {

    private OnAlarmsLoadedListener mListener;

    @SuppressWarnings("unused")
    public AlarmReceiverLoad(){}

    public AlarmReceiverLoad(OnAlarmsLoadedListener listener){
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {


    }

    public void setOnAlarmsLoadedListener(OnAlarmsLoadedListener listener) {
        mListener = listener;
    }

    public interface OnAlarmsLoadedListener {
        void onAlarmsLoaded(ArrayList<Alarm> alarms);
    }

}