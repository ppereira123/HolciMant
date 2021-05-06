package com.example.mantenimientoholcim.Adaptadores;


import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.mantenimientoholcim.Modelo.PendientesInspeciones;
import com.example.mantenimientoholcim.Modelo.RealizacionInspeccion;
import com.example.mantenimientoholcim.R;
import com.example.mantenimientoholcim.ui.Inspecciones.PlantillasInspeccion;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.mantenimientoholcim.Herramientas.EscanerFragment.posicionEnArreglo;
import static com.example.mantenimientoholcim.ui.Inspecciones.PlantillasInspeccion.diferenciaDias;

public class AdaptadorListPendientes extends BaseAdapter {
    private Context context;
    private ArrayList<RealizacionInspeccion> listItems;


    public AdaptadorListPendientes() {
    }

    public AdaptadorListPendientes(Context context, ArrayList<RealizacionInspeccion> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.adaptador_list_pendientes, parent, false);
        }

        TextView txttipoinspeccion,txtultimainspeccion,txtdiasrestantes,txtcdItem;
        SwitchMaterial alarmaswitch;
        txttipoinspeccion=convertView.findViewById(R.id.txttipoinspeccion);
        txtultimainspeccion=convertView.findViewById(R.id.txtultimainspeccion);
        txtdiasrestantes=convertView.findViewById(R.id.txtdiasrestantes);
        txtcdItem=convertView.findViewById(R.id.txtcdItem);
        LinearLayout linearListPendiente=convertView.findViewById(R.id.linearListPendiente);
        RealizacionInspeccion currentItem = (RealizacionInspeccion) getItem(position);
        txttipoinspeccion.setText(currentItem.getTipoInspeccion());
        txtultimainspeccion.setText(currentItem.getSiguientefecha());
        txtcdItem.setText(currentItem.getCodigo());
        Date d=new Date();
        SimpleDateFormat fecc=new SimpleDateFormat("d/MM/yyyy");
        String fechacActual = fecc.format(d);
        try {
            txtdiasrestantes.setText(String.valueOf(diferenciaDias(currentItem.getSiguientefecha(),fechacActual)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String[] nombresdeinspeccion =convertView.getResources().getStringArray(R.array.combo_inspeccionesNombre);
        int posicion=posicionEnArreglo(nombresdeinspeccion,currentItem.getTipoInspeccion());
        View finalConvertView = convertView;
        linearListPendiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(finalConvertView.getContext(), PlantillasInspeccion.class);
                intent.putExtra("posicion",posicion);
                intent.putExtra("codigo", currentItem.getCodigo());
                context.startActivity(intent);
            }
        });


        return convertView;
    }

}
