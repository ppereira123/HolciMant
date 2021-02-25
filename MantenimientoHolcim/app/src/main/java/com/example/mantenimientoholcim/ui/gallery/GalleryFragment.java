package com.example.mantenimientoholcim.ui.gallery;

import android.content.Intent;
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

import com.example.mantenimientoholcim.PlantillasInspeccion;
import com.example.mantenimientoholcim.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FloatingActionButton fabInspeccion;
    ListView tipoInspecciones;
    EditText txtInspecciones;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        root = inflater.inflate(R.layout.fragment_gallery, container, false);
        tipoInspecciones=root.findViewById(R.id.listTipoInspecciones);
        txtInspecciones=root.findViewById(R.id.editTextInspeccion);
        tipoInspecciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtInspecciones.setText(String.valueOf(position));
                Intent intent= new Intent(root.getContext(),PlantillasInspeccion.class);
                intent.putExtra("posicion",position);
                startActivity(intent);
            }
        });
        cargarInspecciones();

        //final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    void cargarInspecciones(){
        List<String> tipos= new ArrayList<>();
        tipos.add("Restrictor de movimiento");
        tipos.add("Inspeccion 1");
        tipos.add("Inspeccion 2");
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_dropdown_item_1line,tipos);
        tipoInspecciones.setAdapter(adapter);
    }
}