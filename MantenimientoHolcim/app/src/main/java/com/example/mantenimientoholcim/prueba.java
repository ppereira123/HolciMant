package com.example.mantenimientoholcim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mantenimientoholcim.Alarma.ExampleJobService;
import com.example.mantenimientoholcim.Alarma.NotificationHelper;


public class prueba extends AppCompatActivity {

    private static final String TAG = prueba.class.getSimpleName();
    private Object NotificationChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
    }
    //tareas por cronograma
    public void scheduleJob(View v){
        ComponentName componentName=new ComponentName(this, ExampleJobService.class);
        JobInfo info = new JobInfo.Builder(123,componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15*60*1000)
                .build();
        JobScheduler scheduler=(JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode=scheduler.schedule(info);
        if(resultCode==JobScheduler.RESULT_SUCCESS){
            Log.d(TAG,"Job schelued");


        }else{
            Log.d(TAG,"Job scheduling failed");

        }

    }
    public void cancelJob(View v){
        JobScheduler scheduler= (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d(TAG,"Job cancel");


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        NotificationHelper notificationHelper=new NotificationHelper(this);
        notificationHelper.createChannels();
        getNotification();
    }

    private void getNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "M_CH_ID")
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setTicker("Hearty365")
                .setPriority(Notification.PRIORITY_MAX) // this is deprecated in API 26 but you can still use for below 26. check below update for 26 API
                .setContentTitle("Default notification")
                .setContentText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
                .setContentInfo("Info");

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());


    }


//tareas por cronograma
}