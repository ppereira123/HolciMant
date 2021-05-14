package com.example.mantenimientoholcim.Alarma;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class ExampleJobService extends JobService {
    private  static final String TAG= "ExampleJobService";
    private boolean jobCancelled= false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG,"Job started");
        doBackgroundWork(params);
        return true;
    }

    private void doBackgroundWork(JobParameters params){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    Log.d(TAG,"run:  "+1);
                    if(jobCancelled){
                        return;
                    }
                    try {
                        Thread.sleep(1000);

                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                Log.d(TAG, "Job finished");
                jobFinished(params,false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled besore completion");
        jobCancelled= true;
        return true;

    }
}
