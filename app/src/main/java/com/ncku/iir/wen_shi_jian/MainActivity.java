package com.ncku.iir.wen_shi_jian;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ncku.iir.wen_shi_jian.core.AlarmReceiver;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // notify
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page);

        //Init SharedPreferences
        // pref usage
        Global.initPrefInstance(getSharedPreferences("user", MODE_PRIVATE));

//        SharedPreferences mPreferences = Global.getPrefInstance();
//        String tmpUserName = mPreferences.getString("username", "Not Value");
//        Log.d("MainActivity", tmpUserName);

        // Save to Firebase pattern
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("score");
//        myRef.child("username").setValue("reward");

//        Intent intent = new Intent(this, LeaderBoardActivity.class);
//        startActivity(intent);

        //notify
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Set up the Notification Broadcast Intent.
        Intent notifyIntent = new Intent(this, AlarmReceiver.class);

        boolean alarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID,
                notifyIntent, PendingIntent.FLAG_NO_CREATE) != null);

        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        final AlarmManager alarmManager = (AlarmManager) getSystemService
                (ALARM_SERVICE);

        long repeatInterval = AlarmManager.INTERVAL_DAY;

        long triggerTime = System.currentTimeMillis();

        // Set the alarm to start at approximately 2:00 p.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // If the Toggle is turned on, set the repeating alarm with
        // a 15 minute interval.
        if (alarmManager != null) {
            alarmManager.setInexactRepeating
                    (AlarmManager.RTC_WAKEUP,
                            calendar.getTimeInMillis(), repeatInterval,
                            notifyPendingIntent);
        }
        else {
        // Cancel notification if the alarm is turned off.
        mNotificationManager.cancelAll();

            if (alarmManager != null) {
                alarmManager.cancel(notifyPendingIntent);
            }

        }


        // Create the notification channel.
        createNotificationChannel();

    }

    // TODO: step 1. click 'start' button
    public void detection(View view) {
        Intent intent = new Intent(this, DetectionActivity.class);
        startActivity(intent);
    }

    public void createNotificationChannel() {

        // Create a notification manager object.
        mNotificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "Stand up notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifies every 15 minutes to " +
                    "stand up and walk");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
