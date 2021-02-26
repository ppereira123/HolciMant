package com.example.mantenimientoholcim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListAdapterInspeccion extends RecyclerView.Adapter<ListAdapterInspeccion.ViewHolder> {
    private List<String> mdata;
    private LayoutInflater mInflater;
    private Context context;
    private List<ElementInspeccion> valores= new ArrayList<>();


    public ListAdapterInspeccion(List<String> itemList, Context context){
        this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.mdata =itemList;
    }
    @NonNull
    @Override
    public ListAdapterInspeccion.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= mInflater.inflate(R.layout.list_inspeccion,null);
        return new ListAdapterInspeccion.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterInspeccion.ViewHolder holder, int position) {
        holder.binData(mdata.get(position));
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public List<ElementInspeccion> getValores(){
        return valores;
    }




    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtEnunciado;
        CheckBox checkOk,checkNOOK;
        private View view;
        public ViewHolder(View view) {
            super(view);
            txtEnunciado=view.findViewById(R.id.txtEnunciado);
            checkOk=view.findViewById(R.id.checkOK);
            checkNOOK=view.findViewById(R.id.checkNOOK);
            this.view=view;
        }


        public void binData(String item) {
            txtEnunciado.setText(item);
            checkOk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                    checkNOOK.setChecked(false);
                    ElementInspeccion elementInspeccion= new ElementInspeccion(item,"OK");
                    valores.add(elementInspeccion);
                    }
                }
            });

            checkNOOK.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                    checkOk.setChecked(false);
                        ElementInspeccion elementInspeccion= new ElementInspeccion(item,"NO OK");
                        valores.add(elementInspeccion);}
                }
            });

        }
    }
}
