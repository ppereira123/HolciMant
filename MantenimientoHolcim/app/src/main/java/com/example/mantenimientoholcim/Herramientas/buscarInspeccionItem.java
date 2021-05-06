package com.example.mantenimientoholcim.Herramientas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.mantenimientoholcim.Adaptadores.AdaptadorInspeccionesRealizadas;
import com.example.mantenimientoholcim.Modelo.ElementInspeccion;
import com.example.mantenimientoholcim.Modelo.InspeccionTipo1;
import com.example.mantenimientoholcim.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class buscarInspeccionItem extends AppCompatActivity {
    ListView inspeccionesRealizadas;
    private AdaptadorInspeccionesRealizadas adaptador;
    String codigoInspeccion;
    ImageButton imgbtnSalir;
    Context context=this;


    ArrayList<InspeccionTipo1> buscarlist=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_inspeccion_item);
        inspeccionesRealizadas=findViewById(R.id.listInspeccionesrealizadas2);
        codigoInspeccion= getIntent().getStringExtra("codigo");
        imgbtnSalir= findViewById(R.id.btnSalirHistorialPrestamos);
        cargarItems();
    }
    void cargarItems(){
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("Inspecciones");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<InspeccionTipo1> listitems=new ArrayList<>();
                imgbtnSalir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();

                    }
                });
                if(snapshot.exists()) {


                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if(ds.exists()) {
                            for (DataSnapshot ds2 : ds.getChildren()) {
                                String p=ds2.getValue().toString();

                                String enunciado = ds2.child("enuunciado").getValue().toString();
                                String nombreInspector = ds2.child("nombreInspector").getValue().toString();
                                String fechaInspeccion = ds2.child("fechaInspeccion").getValue().toString();
                                String proximaInspeccion = ds2.child("proximaInspeccion").getValue().toString();
                                String codigo = ds2.child("codigo").getValue().toString();
                                HashMap<String, ElementInspeccion> valores= new HashMap<>();
                                for(DataSnapshot ds3:ds2.child("valores").getChildren()){
                                    String key=ds3.getKey();
                                    String enun=ds3.child("enunciado").getValue().toString();
                                    String ok=ds3.child("ok").getValue().toString();
                                    ElementInspeccion itemInspeccion=new ElementInspeccion(enun,ok);
                                    valores.put(key,itemInspeccion);

                                }
                                buscarlist=listitems;
                                InspeccionTipo1 item= new InspeccionTipo1(enunciado,nombreInspector,fechaInspeccion,proximaInspeccion,codigo,valores);
                                if(!listitems.contains(item)){
                                    if(item.getCodigo().equals(codigoInspeccion)){
                                        listitems.add(item);
                                    }


                                }
                            }
                        }

                    }
                }
                AdaptadorInspeccionesRealizadas adaptador= new AdaptadorInspeccionesRealizadas(context, listitems );
                inspeccionesRealizadas.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}