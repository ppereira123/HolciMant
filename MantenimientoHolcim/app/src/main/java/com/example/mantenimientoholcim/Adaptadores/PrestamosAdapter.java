package com.example.mantenimientoholcim.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mantenimientoholcim.Modelo.HistorialPrestamo;
import com.example.mantenimientoholcim.Modelo.ItemInspeccion;
import com.example.mantenimientoholcim.R;

import java.util.List;

public class PrestamosAdapter extends BaseAdapter {
    Context context;
    List<HistorialPrestamo> lst;

    public PrestamosAdapter(Context context) {
        this.context = context;
    }

    public PrestamosAdapter(Context context, List<HistorialPrestamo> lst) {
        this.context = context;
        this.lst = lst;
    }

    @Override
    public int getCount() {
        return lst.size();
    }

    @Override
    public Object getItem(int position) {
        return lst.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView nombre,fechaprestamo,fechadevolucion;
        HistorialPrestamo prestamo= (HistorialPrestamo) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.adapter_prestamos, null);
        nombre=convertView.findViewById(R.id.nombrePrestamo);
        fechadevolucion=convertView.findViewById(R.id.fechaDevolucion);
        fechaprestamo=convertView.findViewById(R.id.fechaPrestamo);
        nombre.setText(prestamo.getNombre());
        fechaprestamo.setText(prestamo.getFechaPrestamo());
        if (prestamo.getEstadoPrestamo().equals("")){
            fechadevolucion.setText("Pendiente");
        }
        else if (prestamo.getEstadoPrestamo().equals("No devuelto")){
            fechadevolucion.setText("No devuelto");
        }
        else {
            fechadevolucion.setText(prestamo.getFechaDevolucion());
        }
        return convertView;
    }
}
