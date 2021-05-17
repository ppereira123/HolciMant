package com.example.mantenimientoholcim;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mantenimientoholcim.Alarma.AlarmReceiver;
import com.example.mantenimientoholcim.Alarma.ExampleJobService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {



    Context context=this;

    private AppBarConfiguration mAppBarConfiguration;
    private  static final String TAG= MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.perfilGoogle)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        checkVersion();




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }




    private void checkVersion() {

        FirebaseDatabase database= FirebaseDatabase.getInstance();



        DatabaseReference myRef = database.getReference("Taller").child("Caracteristicas");
        myRef.child("Version").

            addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange (@NonNull DataSnapshot snapshot){

                    String version = snapshot.getValue().toString();

                    String installedVersion = getVersionName(context);

                    double intVersion = Double.parseDouble(version);
                    double intInstalledVersion = Double.parseDouble(installedVersion);
                    if (intInstalledVersion < intVersion) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Existe una nueva version de la aplicacion.");
                        builder.setMessage("Porfavor actualizala cuanto antes");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Instalar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DatabaseReference ref = myRef.child("link");
                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String url = snapshot.getValue().toString();
                                        Uri uri = Uri.parse(url);
                                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        });

                        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "Instalar Luego", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }

                @Override
                public void onCancelled (@NonNull DatabaseError error){

                }
            });
        }
/*
    //tareas por cronograma
    public void scheduleJob(){
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
    public void cancelJob(){
        JobScheduler scheduler= (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d(TAG,"Job cancel");


    }

*/

    //tareas por cronograma



        public String getVersionName(Context ctx){
            try {
                return ctx.getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return "";
            }
        }
}