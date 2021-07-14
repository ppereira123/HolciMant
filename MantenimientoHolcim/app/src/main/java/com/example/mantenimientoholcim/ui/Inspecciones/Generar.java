package com.example.mantenimientoholcim.ui.Inspecciones;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mantenimientoholcim.Adaptadores.AdaptadorItemInspecciones;
import com.example.mantenimientoholcim.Modelo.ItemInspeccion;
import com.example.mantenimientoholcim.R;
import com.example.mantenimientoholcim.RevisionPuntosBloqueo;

import java.util.ArrayList;


public class Generar extends Fragment {
    ListView tipoInspecciones;
    private AdaptadorItemInspecciones adaptador;
    View root;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        root = inflater.inflate(R.layout.fragment_generar, container, false);
        tipoInspecciones=root.findViewById(R.id.listTipoInspecciones);
        adaptador= new AdaptadorItemInspecciones(getContext(), GetArrayItems() );
        tipoInspecciones.setAdapter(adaptador);

        tipoInspecciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==42){
                    Intent intent= new Intent(root.getContext(), RevisionPuntosBloqueo.class);
                    startActivity(intent);

                }
                else {
                    Intent intent= new Intent(root.getContext(),PlantillasInspeccion.class);
                    intent.putExtra("posicion",position);
                    startActivity(intent);
                }

            }
        });



        return root;
    }
    public ArrayList<ItemInspeccion> GetArrayItems(){
        ArrayList<ItemInspeccion> listItems = new ArrayList<>();
        Resources res = getResources();
        String[] nombre_inspecciones = res.getStringArray(R.array.combo_inspeccionesNombre);
        int size= nombre_inspecciones.length;
        for (int i=0; i<size; i++){
            listItems.add(new ItemInspeccion(nombre_inspecciones[i]));

        }
        return listItems;

    }
}