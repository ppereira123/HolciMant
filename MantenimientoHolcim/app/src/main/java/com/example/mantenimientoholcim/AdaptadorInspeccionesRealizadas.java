package com.example.mantenimientoholcim;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.mantenimientoholcim.Modelo.InspeccionTipo1;
import com.example.mantenimientoholcim.Modelo.ItemInspeccion;

import java.util.ArrayList;

public class AdaptadorInspeccionesRealizadas extends BaseAdapter {
    private Context context;
    private ArrayList<InspeccionTipo1> listItems;
    public AdaptadorInspeccionesRealizadas(Context context, ArrayList<InspeccionTipo1> listItems) {
        this.context = context;
        this.listItems = listItems;

    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return listItems.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InspeccionTipo1 Item= (InspeccionTipo1) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.list_inspecciones_realizadas, null);
        TextView nombretv= (TextView) convertView.findViewById(R.id.inspector_ir);
        TextView titulotv= (TextView) convertView.findViewById(R.id.nombre_inspeccion);
        TextView fechatv= (TextView) convertView.findViewById(R.id.fechaIr);
        TextView codigotv= (TextView) convertView.findViewById(R.id.codigoIr);

        nombretv.setText(Item.getNombreInspector());
        titulotv.setText(Item.getEnuunciado());
        fechatv.setText(Item.getFechaInspeccion());
        codigotv.setText(Item.getCodigo());

        return convertView;
    }
}
