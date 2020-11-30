package com.example.alarmproject;

import android.app.AlarmManager;
import android.app.AlarmManager.AlarmClockInfo;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;
import java.util.List;

import static android.app.NotificationManager.IMPORTANCE_HIGH;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.os.Build.VERSION_CODES.O;
import static com.example.alarmproject.AlarmsPageActivity.launchIntent;


public final class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = AlarmReceiver.class.getSimpleName();
    private static final String alarm_channel_id = "alarm_channel";

    private static final String extra_bundle = "extra_bundle";
    private static final String alarm_key = "alarm_key";

    @Override
    public void onReceive(Context context, Intent intent) {

        final Alarm alarm = intent.getBundleExtra(extra_bundle).getParcelable(alarm_key);
        if(alarm == null) {
            Log.e(TAG, "Alarm is null", new NullPointerException());
            return;
        }
        final MediaPlayer mp  = MediaPlayer.create(context,  Uri.parse(String.format("android.resource://%s/%s/%s","com.example.alarmproject","raw","peanuts_ringtone")));
        mp.setLooping(true);
        final int id = alarm.notificationId();

        final NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createNotificationChannel(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, alarm_channel_id);
        builder.setSmallIcon(R.drawable.clockicon);
        builder.setColor(ContextCompat.getColor(context, R.color.some_colour));
        builder.setContentTitle(context.getString(R.string.app_name));
        builder.setContentText(alarm.getLabel());
        builder.setTicker(alarm.getLabel());
        builder.setVibrate(new long[] {1000,500,1000,500,1000,500,1000, 500, 1000, 500, 1000, 500, 1000, 500, 1000, 500, 1000, 500});
        //builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.setContentIntent(launchAlarmLandingPage(context, alarm));
        builder.setAutoCancel(true);
        builder.setPriority(Notification.PRIORITY_HIGH);

        manager.notify(id, builder.build());
        mp.start();

        //Reset Alarm manually
        setReminderAlarm(context, alarm);
    }

    //Convenience method for setting a notification
    public static void setReminderAlarm(Context context, Alarm alarm) {

        //Check whether the alarm is set to run on any days
        if(!UtilsAlarm.isAlarmActive(alarm)) {
            //If alarm not set to run on any days, cancel any existing notifications for this alarm
            cancelReminderAlarm(context, alarm);
            return;
        }

        final Calendar nextAlarmTime = getTimeForNextAlarm(alarm);
        alarm.setTime(nextAlarmTime.getTimeInMillis());

        final Intent intent = new Intent(context, AlarmReceiver.class);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(alarm_key, alarm);
        intent.putExtra(extra_bundle, bundle);

        final PendingIntent pIntent = PendingIntent.getBroadcast(
                context,
                alarm.notificationId(),
                intent,
                FLAG_UPDATE_CURRENT
        );

        ScheduleAlarm.with(context).schedule(alarm, pIntent);
    }

    public static void setReminderAlarms(Context context, List<Alarm> alarms) {
        for(Alarm alarm : alarms) {
            setReminderAlarm(context, alarm);
        }
    }

    /**
     * Calculates the actual time of the next alarm/notification based on the user-set time the
     * alarm should sound each day, the days the alarm is set to run, and the current time.
     * @param alarm Alarm containing the daily time the alarm is set to run and days the alarm
     *              should run
     * @return A Calendar with the actual time of the next alarm.
     */
    private static Calendar getTimeForNextAlarm(Alarm alarm) {

        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(alarm.getTime());

        final long currentTime = System.currentTimeMillis();
        final int startIndex = getStartIndexFromTime(calendar);

        int count = 0;
        boolean isAlarmSetForDay;

        final SparseBooleanArray daysArray = alarm.getDays();

        do {
            final int index = (startIndex + count) % 7;
            isAlarmSetForDay =
                    daysArray.valueAt(index) && (calendar.getTimeInMillis() > currentTime);
            if(!isAlarmSetForDay) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                count++;
            }
        } while(!isAlarmSetForDay && count < 7);

        return calendar;

    }

    public static void cancelReminderAlarm(Context context, Alarm alarm) {

        final Intent intent = new Intent(context, AlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(
                context,
                alarm.notificationId(),
                intent,
                FLAG_UPDATE_CURRENT
        );

        final AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pIntent);
    }

    private static int getStartIndexFromTime(Calendar c) {

        final int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        int startIndex = 0;
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                startIndex = 0;
                break;
            case Calendar.MONDAY:
                startIndex = 1;
                break;
            case Calendar.TUESDAY:
                startIndex = 2;
                break;
            case Calendar.WEDNESDAY:
                startIndex = 3;
                break;
            case Calendar.THURSDAY:
                startIndex = 4;
                break;
            case Calendar.FRIDAY:
                startIndex = 5;
                break;
            case Calendar.SATURDAY:
                startIndex = 6;
                break;

        }

        return startIndex;

    }

    private static void createNotificationChannel(Context ctx) {
        if(SDK_INT < O) return;

        final NotificationManager mgr = ctx.getSystemService(NotificationManager.class);
        if(mgr == null) return;

        final String name = ctx.getString(R.string.channel);
        if(mgr.getNotificationChannel(name) == null) {
            final NotificationChannel channel =
                    new NotificationChannel(alarm_channel_id, name, IMPORTANCE_HIGH);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[] {1000,500,1000,500,1000,500});
            channel.setBypassDnd(true);
            mgr.createNotificationChannel(channel);
        }
    }

    private static PendingIntent launchAlarmLandingPage(Context ctx, Alarm alarm) {
        return PendingIntent.getActivity(
                ctx, alarm.notificationId(), launchIntent(ctx), FLAG_UPDATE_CURRENT
        );
    }

    private static class ScheduleAlarm {

        @NonNull private final Context ctx;
        @NonNull private final AlarmManager alarmMang;

        private ScheduleAlarm(@NonNull AlarmManager alarmM, @NonNull Context ctx) {
            this.alarmMang = alarmM;
            this.ctx = ctx;
        }

        static ScheduleAlarm with(Context context) {
            final AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            if(am == null) {
                throw new IllegalStateException("AlarmManager is null");
            }
            return new ScheduleAlarm(am, context);
        }

        void schedule(Alarm alarm, PendingIntent pi) {
            if(SDK_INT > LOLLIPOP) {
                alarmMang.setAlarmClock(new AlarmClockInfo(alarm.getTime(), launchAlarmLandingPage(ctx, alarm)), pi);
            } else if(SDK_INT > KITKAT) {
                alarmMang.setExact(AlarmManager.RTC_WAKEUP, alarm.getTime(), pi);
            } else {
                alarmMang.set(AlarmManager.RTC_WAKEUP, alarm.getTime(), pi);
            }
        }

    }

}