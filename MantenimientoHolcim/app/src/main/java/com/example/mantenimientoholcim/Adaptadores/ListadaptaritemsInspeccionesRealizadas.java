package com.example.mantenimientoholcim.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mantenimientoholcim.Modelo.ElementInspeccion;
import com.example.mantenimientoholcim.R;

import java.util.List;

public class ListadaptaritemsInspeccionesRealizadas extends RecyclerView.Adapter<ListadaptaritemsInspeccionesRealizadas.ViewHolder>{
    private LayoutInflater mInflater;
    private Context context;
    private List<ElementInspeccion> valores;

    public  ListadaptaritemsInspeccionesRealizadas(List<ElementInspeccion> items, Context context){
        this.mInflater= LayoutInflater.from(context);
        this.context = context;
        this.valores= items;
    }
    @Override
    public int getItemCount(){ return  valores.size();}


    @Override
     public ListadaptaritemsInspeccionesRealizadas.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_inspeccion, null);
        return new ListadaptaritemsInspeccionesRealizadas.ViewHolder(view);
    }
    @Override
    public  void onBindViewHolder(final ListadaptaritemsInspeccionesRealizadas.ViewHolder holder, final int position){
        holder.bindData(valores.get(position));
    }
    public  void  setItems(List<ElementInspeccion> items){
        valores=items;
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder{
        CheckBox checkOK, checkNOOK;
        TextView enunciadotv;
        ViewHolder(View itemView){
            super(itemView);
            checkOK= itemView.findViewById(R.id.checkOK);
            checkNOOK= itemView.findViewById(R.id.checkNOOK);
            enunciadotv= itemView.findViewById(R.id.txtEnunciado);

        }
         void bindData(final ElementInspeccion item){
            enunciadotv.setText(item.getEnunciado());
            if (item.getOk().equals("OK")){
                checkOK.setChecked(true);
                checkNOOK.setChecked(false);
                checkNOOK.setEnabled(false);
                checkOK.setEnabled(false);

            }else {
                checkOK.setChecked(false);
                checkNOOK.setChecked(true);
                checkNOOK.setEnabled(false);
                checkOK.setEnabled(false);
            }


         }


    }

}
