package com.example.alarmproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class RingtonePlayingService extends Service {

    MediaPlayer media_song;
    boolean isRunning;
    int startId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //fetch extra string, either 'alarm on' or 'alarm off'
        String status = intent.getExtras().getString("extra");
        //fetch extra int
        Integer game_choice = intent.getExtras().getInt("game_id");

        NotificationManager notificationManager;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "notify_001");

        Intent notifyIntent = new Intent(this.getApplicationContext(), VisualGameActivity.class);

        // Set the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Create the PendingIntent
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

        builder.setContentIntent(notifyPendingIntent);//pending bc intent only executes when alarm rings
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setContentTitle("Time to wake up!");
        builder.setContentText("Click me");
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setAutoCancel(true);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }

        assert status != null; //if null pointer exc, don't run if else
        //convert extra strings from intent to ids 0 or 1
        if (status.equals("alarm on")) {
            startId = 1;
        } else if (status.equals("alarm off")) {
            startId = 0;
        } else {
            startId = 0;
        }

        if (!this.isRunning && startId == 1) { //if no music playing AND user press 'alarm on'
            //create instance of MediaPlayer, start playing music
            media_song = MediaPlayer.create(this, R.raw.alarm_ringtone);
            media_song.setLooping(true); //repeat alarm
            media_song.start();

            this.isRunning = true;
            this.startId = 0;

            //set notification call command
            notificationManager.notify(0, builder.build());

        }else if (this.isRunning && startId==0){ //if music playing AND user press 'alarm off'
            media_song.stop();
            media_song.reset();

            this.isRunning=false;
            this.startId=0;

//            if (game_choice==1){
//
//            }else if (game_choice==2){
//
//            }else if (game_choice==3){
//
//            }else{
//                //randomly choose game type
//                int min=1;
//                int max=3;
//                Random r=new Random();
//                int game_num=r.nextInt(max - min + 1) + min;
//
//                if (game_num==1){
//
//                }else if (game_num==2){
//
//                }else if (game_num==3){
//
//                }
//            }
        } else if(!this.isRunning && startId==0){ //no music AND user press 'alarm off'
            //do nth
            this.isRunning=false;
            this.startId=0;

        }else if(this.isRunning && startId==1){ //music AND user press 'alarm on'
            //do nth
            this.isRunning=true;
            this.startId=1;

        }else{
            Log.e("else","idk");
        }

        return START_NOT_STICKY; //if service stops, won't automatically restart
    }

    @Override
    public void onDestroy(){
        Log.e("onDestroyed called","oops");
        super.onDestroy();
        this.isRunning=false;
    }

}
