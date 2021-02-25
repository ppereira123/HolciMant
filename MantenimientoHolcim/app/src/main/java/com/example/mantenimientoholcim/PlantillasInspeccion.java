package com.example.mantenimientoholcim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlantillasInspeccion extends AppCompatActivity {
    RecyclerView rvInspecciones;
    Context context=this;
    List<List<String>> tipoInspecciones= new ArrayList<>();
    ListAdapterInspeccion li;
    EditText editTextCodigo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantillas_inspeccion);
        editTextCodigo=findViewById(R.id.editTextCodigoInspeccion);
        cargarInspecciones();
        int posicion=getIntent().getIntExtra("posicion",0);
        rvInspecciones=findViewById(R.id.rvInspecciones);
        li= new ListAdapterInspeccion(tipoInspecciones.get(posicion),this);
        rvInspecciones.setHasFixedSize(true);
        rvInspecciones.setLayoutManager(new LinearLayoutManager(context));
        rvInspecciones.setAdapter(li);
    }

    void cargarInspecciones(){
        List<String> inspeccion0= new ArrayList<>();
        inspeccion0.add("No se evidencia desgaste concentrado, hilos desgastados, hilos rotos, quemaduras, cortes o abrasiones");
        inspeccion0.add("No se evidencian nudos en toda la longitud de la cuerda");
        inspeccion0.add("No se evidencia suciedad excesiva, acumulación de pintura o tinción por herrumbre que impide la maleabilidad de la cuerda y que evidencia deterioro de la misma");
        inspeccion0.add("No se evidencian daños químicos o de calor indicados por áreas marrones, descoloridas o quebradizas  ");
        inspeccion0.add("No se evidencia daño ultravioleta indicado por la decoloración y la presencia de astillas y rajaduras en la superficie de la línea de vida  ");
        inspeccion0.add("Los ganchos de conexión no presentan fisuras   ");
        inspeccion0.add("Los ganchos no presentan oxidación que comprometa la integridad estructural del elemento ");
        tipoInspecciones.add(inspeccion0);

        List<String> inspeccion1= new ArrayList<>();
        inspeccion1.add("Paramatro 1");
        inspeccion1.add("Paramatro 2");
        inspeccion1.add("Paramatro 3");
        inspeccion1.add("Paramatro 4");
        inspeccion1.add("Paramatro 5");
        inspeccion1.add("Paramatro 6");
        inspeccion1.add("Paramatro 7");
        tipoInspecciones.add(inspeccion1);

        List<String> inspeccion2= new ArrayList<>();
        inspeccion2.add("Paramatro 8");
        inspeccion2.add("Paramatro 9");
        inspeccion2.add("Paramatro 3");
        inspeccion2.add("Paramatro 10");
        inspeccion2.add("Paramatro 11");
        inspeccion2.add("Paramatro 12");
        inspeccion2.add("Paramatro 13");
        tipoInspecciones.add(inspeccion2);

    }

    public  void guardar(View view){
        HashMap<String,String> valores=li.getValores();
        editTextCodigo.setText(String.valueOf(valores.size()));

    }
}