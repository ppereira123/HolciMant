package com.example.mantenimientoholcim.Herramientas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mantenimientoholcim.Adaptadores.ListadaptaritemsInspeccionesRealizadas;
import com.example.mantenimientoholcim.Modelo.ElementInspeccion;
import com.example.mantenimientoholcim.Modelo.InspeccionTipo1;
import com.example.mantenimientoholcim.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerInspeccion extends AppCompatActivity {
    RecyclerView rvInspecciones;
    Context context=this;
    List<List<String>> tipoInspecciones= new ArrayList<>();
    ListadaptaritemsInspeccionesRealizadas li;
    EditText editTextCodigo,nombreInspector,fechaInspeccion,fechaProximaInspeccion;
    TextView txtnombreInspecciones;
    ImageView imagInspeccion;
    String imagenInspeccion="";
    InspeccionTipo1 inspeccionTipo1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_ver_inspeccion);
        inspeccionTipo1= (InspeccionTipo1) getIntent().getSerializableExtra("inspeccion");
        editTextCodigo=findViewById(R.id.editTextCodigoInspeccionRv);
        fechaInspeccion=findViewById(R.id.fechaInspecciónRv);
        fechaProximaInspeccion=findViewById(R.id.fechaproximaInspeccionRv);
        rvInspecciones=findViewById(R.id.rvInspeccionesRv);
        nombreInspector=findViewById(R.id.nombreInspectorRv);
        imagInspeccion=findViewById(R.id.imgInspeccionRv);
        txtnombreInspecciones=findViewById(R.id.txtPI1Rv);
        cargar(inspeccionTipo1);
    }
    public void cargar(InspeccionTipo1 inspecion){
        Resources res = getResources();
        String[] nombre_inspecciones = res.getStringArray(R.array.combo_inspeccionesNombre);
        String[] imagenesdeinspeccion = res.getStringArray(R.array.combo_inspeccionesImagenes);
        int posicion=posicionexisteEnArreglo(nombre_inspecciones, inspecion.getEnuunciado());
        if (posicion==-1){
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }else{
            imagenInspeccion= imagenesdeinspeccion[posicion];
            String uri =imagenInspeccion;
            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            Drawable imagen = ContextCompat.getDrawable(getApplicationContext(), imageResource);
            imagInspeccion.setImageDrawable(imagen);
        }

        txtnombreInspecciones.setText(inspecion.getEnuunciado());
        nombreInspector.setText(inspecion.getNombreInspector());
        fechaInspeccion.setText(inspecion.getFechaInspeccion());
        fechaProximaInspeccion.setText(inspecion.getProximaInspeccion());



        li= new ListadaptaritemsInspeccionesRealizadas(hashToList(inspecion.getValores()),this);
        rvInspecciones.setHasFixedSize(true);
        rvInspecciones.setLayoutManager(new LinearLayoutManager(context));
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

}