package com.example.mantenimientoholcim.Dialogos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mantenimientoholcim.ListadaptaritemsInspeccionesRealizadas;
import com.example.mantenimientoholcim.Modelo.ElementInspeccion;
import com.example.mantenimientoholcim.Modelo.InspeccionTipo1;
import com.example.mantenimientoholcim.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DialogoVerInspeccion extends DialogFragment {
    RecyclerView rvInspecciones;
    Activity activity;
    List<List<String>> tipoInspecciones= new ArrayList<>();
    ListadaptaritemsInspeccionesRealizadas li;
    EditText editTextCodigo,nombreInspector,fechaInspeccion,fechaProximaInspeccion;
    TextView txtnombreInspecciones;
    InspeccionTipo1 inspeccionTipo1;
    ImageView imagInspeccion;
    String imagenInspeccion="";
    View context;


    public DialogoVerInspeccion() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return crearDialogoVerInspeccion();
    }
    private AlertDialog crearDialogoVerInspeccion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_dialogo_ver_inspeccion, null);
        builder.setView(v);

        inspeccionTipo1= (InspeccionTipo1) activity.getIntent().getSerializableExtra("inspeccion");
        editTextCodigo=v.findViewById(R.id.editTextCodigoInspeccionRv);
        fechaInspeccion=v.findViewById(R.id.fechaInspecciónRv);
        fechaProximaInspeccion=v.findViewById(R.id.fechaproximaInspeccionRv);
        rvInspecciones=v.findViewById(R.id.rvInspeccionesRv);
        imagInspeccion=v.findViewById(R.id.imgInspeccionRv);
        nombreInspector=v.findViewById(R.id.nombreInspectorRv);
        txtnombreInspecciones=v.findViewById(R.id.txtPI1Rv);
        cargar(inspeccionTipo1);
        return builder.create();

    }
    public void cargar(InspeccionTipo1 inspecion){
        Resources res = getResources();
        String[] nombre_inspecciones = res.getStringArray(R.array.combo_inspeccionesNombre);
        String[] imagenesdeinspeccion = res.getStringArray(R.array.combo_inspeccionesImagenes);
        int posicion=posicionexisteEnArreglo(nombre_inspecciones, inspecion.getEnuunciado());
        if (posicion==-1){
            Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show();
        }else{
            imagenInspeccion= imagenesdeinspeccion[posicion];
            String uri =imagenInspeccion;
            int imageResource = getResources().getIdentifier(uri, null, activity.getPackageName());
            Drawable imagen = ContextCompat.getDrawable(activity.getApplicationContext(), imageResource);
            imagInspeccion.setImageDrawable(imagen);
        }

        txtnombreInspecciones.setText(inspecion.getEnuunciado());
        nombreInspector.setText(inspecion.getNombreInspector());
        fechaInspeccion.setText(inspecion.getFechaInspeccion());
        fechaProximaInspeccion.setText(inspecion.getProximaInspeccion());



        li= new ListadaptaritemsInspeccionesRealizadas(hashToList(inspecion.getValores()),activity);
        rvInspecciones.setHasFixedSize(true);
        rvInspecciones.setLayoutManager(new LinearLayoutManager(activity));
        rvInspecciones.setAdapter(li);


    }
    public static int posicionexisteEnArreglo(String[] arreglo, String busqueda) {
        for (int x = 0; x < arreglo.length; x++) {
            if (arreglo[x].equals(busqueda)) {
                return x;
            }
        }
        return -1;
    }
    public static List<ElementInspeccion> hashToList(HashMap<String, ElementInspeccion> hash){
        List<ElementInspeccion> list= new ArrayList();
        for(Map.Entry m:hash.entrySet()){
            ElementInspeccion i= (ElementInspeccion) m.getValue();
            list.add(i);
        }
        return list;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.activity= (Activity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


}