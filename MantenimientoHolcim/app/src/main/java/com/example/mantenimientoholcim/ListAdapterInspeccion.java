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

import com.example.mantenimientoholcim.Modelo.ElementInspeccion;

import java.util.HashMap;
import java.util.List;

public class ListAdapterInspeccion extends RecyclerView.Adapter<ListAdapterInspeccion.ViewHolder> {
    private List<String> mdata;
    private LayoutInflater mInflater;
    private Context context;
    private HashMap<String, ElementInspeccion> valores= new HashMap<String, ElementInspeccion>();


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
        holder.binData(mdata.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public HashMap<String, ElementInspeccion> getValores(){
        return valores;
    }




    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtEnunciado,txtNum;
        CheckBox checkOk,checkNOOK;
        private View view;
        public ViewHolder(View view) {
            super(view);
            txtEnunciado=view.findViewById(R.id.txtEnunciado);
            txtNum=view.findViewById(R.id.txtNum);
            checkOk=view.findViewById(R.id.checkOK);
            checkNOOK=view.findViewById(R.id.checkNOOK);
            this.view=view;
        }


        public void binData(String item,int posicion) {
            if(valores.containsKey(String.valueOf(posicion))){
                ElementInspeccion e= valores.get(String.valueOf(posicion));
                if(e.getOk().equals("OK")){
                    checkOk.setChecked(true);
                    checkNOOK.setChecked(false);
                }

                if(e.getOk().equals("NO OK")){
                    checkOk.setChecked(false);
                    checkNOOK.setChecked(true);
                }
            }

            else{
                checkOk.setChecked(false);
                checkNOOK.setChecked(false);
                valores.remove(String.valueOf(posicion));
            }
            txtEnunciado.setText(item);
            String pos= String.valueOf(posicion);

           txtNum.setText(String.valueOf(posicion));

                checkOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkNOOK.setChecked(false);
                        ElementInspeccion elementInspeccion= new ElementInspeccion(item,"OK");
                        valores.put(pos,elementInspeccion);
                    }
                });

                checkNOOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkOk.setChecked(false);
                        ElementInspeccion elementInspeccion= new ElementInspeccion(item,"NO OK");
                        valores.put(pos,elementInspeccion);
                    }
                });
                

        }
    }
}
