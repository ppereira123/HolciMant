package com.example.mantenimientoholcim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class PlantillasInspeccion extends AppCompatActivity {
    RecyclerView rvInspecciones;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantillas_inspeccion);
        rvInspecciones=findViewById(R.id.rvInspecciones);
        List<String> inspecciones= new ArrayList<>();
        inspecciones.add("No se evidencia desgaste concentrado, hilos desgastados, hilos rotos, quemaduras, cortes o abrasiones");
        inspecciones.add("No se evidencian nudos en toda la longitud de la cuerda");
        inspecciones.add("No se evidencia suciedad excesiva, acumulación de pintura o tinción por herrumbre que impide la maleabilidad de la cuerda y que evidencia deterioro de la misma");
        inspecciones.add("No se evidencian daños químicos o de calor indicados por áreas marrones, descoloridas o quebradizas  ");
        inspecciones.add("No se evidencia daño ultravioleta indicado por la decoloración y la presencia de astillas y rajaduras en la superficie de la línea de vida  ");
        inspecciones.add("Los ganchos de conexión no presentan fisuras   ");
        inspecciones.add("Los ganchos no presentan oxidación que comprometa la integridad estructural del elemento ");

        ListAdapterInspeccion li= new ListAdapterInspeccion(inspecciones,this);
        rvInspecciones.setHasFixedSize(true);
        rvInspecciones.setLayoutManager(new LinearLayoutManager(context));
        rvInspecciones.setAdapter(li);


    }
}