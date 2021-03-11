package com.example.mantenimientoholcim;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mantenimientoholcim.Modelo.ElementInspeccion;
import com.example.mantenimientoholcim.Modelo.InspeccionTipo1;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;



public class tab2 extends Fragment {
    ListView inspeccionesRealizadas;
    View root;
    private AdaptadorInspeccionesRealizadas adaptador;

    TextView txtPrueba;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_tab2, container, false);
        inspeccionesRealizadas=root.findViewById(R.id.listInspeccionesrealizadas);
        cargarItems();
        //adaptador= new AdaptadorInspeccionesRealizadas(getContext(), listitems);
        //inspeccionesRealizadas.setAdapter(adaptador);




        inspeccionesRealizadas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //poner lo que la vista de la inspección realizada.

            }
        });

        // Inflate the layout for this fragment
        return root;
    }




    void cargarItems(){
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("Inspecciones");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<InspeccionTipo1> listitems=new ArrayList<>();
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
                                HashMap<String,ElementInspeccion> valores= new HashMap<>();
                                for(DataSnapshot ds3:ds2.child("valores").getChildren()){
                                    String key=ds3.getKey();
                                    String enun=ds3.child("enunciado").getValue().toString();
                                    String ok=ds3.child("ok").getValue().toString();
                                    ElementInspeccion itemInspeccion=new ElementInspeccion(enun,ok);
                                    valores.put(key,itemInspeccion);

                                }
                                InspeccionTipo1 item= new InspeccionTipo1(enunciado,nombreInspector,fechaInspeccion,proximaInspeccion,codigo,valores);
                                if(!listitems.contains(item)){
                                  listitems.add(item);

                                }
                            }
                        }

                    }
                }
                AdaptadorInspeccionesRealizadas adaptador= new AdaptadorInspeccionesRealizadas(root.getContext(), listitems );
                inspeccionesRealizadas.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}