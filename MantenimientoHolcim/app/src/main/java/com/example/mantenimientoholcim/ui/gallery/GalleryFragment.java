package com.example.mantenimientoholcim.ui.gallery;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mantenimientoholcim.AdaptadorItemInspecciones;
import com.example.mantenimientoholcim.Modelo.ItemInspeccion;
import com.example.mantenimientoholcim.PlantillasInspeccion;
import com.example.mantenimientoholcim.R;
import com.example.mantenimientoholcim.RevisionPuntosBloqueo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FloatingActionButton fabInspeccion;
    ListView tipoInspecciones;
    private AdaptadorItemInspecciones adaptador;
    View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        root = inflater.inflate(R.layout.fragment_gallery, container, false);
        tipoInspecciones=root.findViewById(R.id.listTipoInspecciones);
        adaptador= new AdaptadorItemInspecciones(getContext(), GetArrayItems() );
        tipoInspecciones.setAdapter(adaptador);

        tipoInspecciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==41){
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


        //final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
    private  ArrayList<ItemInspeccion> GetArrayItems(){
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