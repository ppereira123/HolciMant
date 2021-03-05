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
import android.content.res.Resources;

import androidx.core.content.ContextCompat;

import com.example.mantenimientoholcim.Modelo.ItemInspeccion;

import java.util.ArrayList;

public class AdaptadorItemInspecciones extends BaseAdapter {
    private Context context;
    private ArrayList<ItemInspeccion> listItems;


    public AdaptadorItemInspecciones(Context context, ArrayList<ItemInspeccion> listItems) {
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
        ItemInspeccion Item= (ItemInspeccion) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.iteminspeccion, null);
        ImageView imgFoto = (ImageView) convertView.findViewById(R.id.imgInspec);
        TextView tvTitulo= (TextView) convertView.findViewById(R.id.itemInspeccion);
        Resources res = context.getResources();
        String[] imagenesdeinspeccion = res.getStringArray(R.array.combo_inspeccionesImagenes);
        String imagen_nombre=imagenesdeinspeccion[position];
        String uri =imagen_nombre;
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        Drawable imagen = ContextCompat.getDrawable(context.getApplicationContext(), imageResource);
        imgFoto.setImageDrawable(imagen);
        tvTitulo.setText(Item.getTitulo());

        return convertView;
    }
}
