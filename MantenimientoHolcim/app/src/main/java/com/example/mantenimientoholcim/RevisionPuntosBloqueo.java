package com.example.mantenimientoholcim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RevisionPuntosBloqueo extends AppCompatActivity {
    RecyclerView rvPuntosBloqueo;
    Context context=this;

    int puntosdebloqueo=1;
    List<Integer> puntos= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revision_puntos_bloqueo);
        rvPuntosBloqueo=findViewById(R.id.rvpuntosdebloqueo);
        puntos.add(puntosdebloqueo);
        ListAdapterPuntosDeBloqueo lpb= new ListAdapterPuntosDeBloqueo(puntos,context);
        rvPuntosBloqueo.setHasFixedSize(true);
        rvPuntosBloqueo.setLayoutManager(new LinearLayoutManager(context));
        rvPuntosBloqueo.setAdapter(lpb);

    }
    public void maspuntodebloqueo(View view){
        puntosdebloqueo=puntosdebloqueo+1;
        puntos.add(puntosdebloqueo);
        ListAdapterPuntosDeBloqueo lpb= new ListAdapterPuntosDeBloqueo(puntos,context);
        rvPuntosBloqueo.setHasFixedSize(true);
        rvPuntosBloqueo.setLayoutManager(new LinearLayoutManager(context));
        rvPuntosBloqueo.setAdapter(lpb);
    }
}