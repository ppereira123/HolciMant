package com.example.mantenimientoholcim;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mantenimientoholcim.Modelo.Item;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ListAdapterInspeccion extends RecyclerView.Adapter<ListAdapterInspeccion.ViewHolder> {
    private List<String> mdata;
    private LayoutInflater mInflater;
    private Context context;


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
                    checkNOOK.setChecked(false);}
                }
            });

            checkNOOK.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                    checkOk.setChecked(false);}
                }
            });

        }
    }
}
