package com.example.mantenimientoholcim.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mantenimientoholcim.Modelo.ElementInspeccion;
import com.example.mantenimientoholcim.Modelo.InspeccionTipo1;
import com.example.mantenimientoholcim.R;

import java.util.ArrayList;

public class AdapterList_verInspeccion extends BaseAdapter {
    private Context context;
    Activity activity;
    private ArrayList<ElementInspeccion> listItems;

    public AdapterList_verInspeccion() {
    }

    public AdapterList_verInspeccion(Context context) {
        this.context = context;
    }

    public AdapterList_verInspeccion(Context context, ArrayList<ElementInspeccion> listItems) {
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
                    inflate(R.layout.list_verinspeccion, parent, false);
        }
        ElementInspeccion currentItem = (ElementInspeccion) getItem(position);
        TextView enunciadoTv= (TextView) convertView.findViewById(R.id.txtenunciado);
        TextView txtobservacion= convertView.findViewById(R.id.txtobservacionVista);
        LinearLayout vistaobsrvacion=convertView.findViewById(R.id.linearlayoutvista);
        CheckBox ok,nook;
        ok= convertView.findViewById(R.id.ok);
        nook=convertView.findViewById(R.id.nok);
        enunciadoTv.setText(currentItem.getEnunciado());
        if(!currentItem.getObservacion().equals("")){
            vistaobsrvacion.setVisibility(View.VISIBLE);
            txtobservacion.setText("Observación:"+currentItem.getObservacion());
        }
        if (currentItem.getOk().equals("OK")){
            ok.setChecked(true);
            ok.setEnabled(false);
            nook.setEnabled(false);
        }else {
            nook.setChecked(true);
            nook.setEnabled(false);
            ok.setEnabled(false);

        }
        return convertView;
    }
}
