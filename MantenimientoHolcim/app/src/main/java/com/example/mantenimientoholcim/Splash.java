package com.example.mantenimientoholcim;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mantenimientoholcim.Signin.LoginFireBase;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Animation animation= AnimationUtils.loadAnimation(this,R.anim.desplaza_arriba);
        Animation animation2= AnimationUtils.loadAnimation(this,R.anim.desplaza_abajo);

        ImageView imgSplash=findViewById(R.id.imageViewSplash);
        imgSplash.setAnimation(animation);

        ImageView imglogoHolcim=findViewById(R.id.imglogoHolcim);
        imglogoHolcim.setAnimation(animation2);

        TextView txtSlpash=findViewById(R.id.textViewSplash);
        txtSlpash.setAnimation(animation2);

        TimerTask tarea= new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(Splash.this, LoginFireBase.class);
                startActivity(intent);
                finish();
            }
        };

        Timer tiempo= new Timer();
        tiempo.schedule(tarea,6000);
    }
}