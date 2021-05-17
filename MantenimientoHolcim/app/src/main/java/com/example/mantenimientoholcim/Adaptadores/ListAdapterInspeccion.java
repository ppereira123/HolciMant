package com.example.mantenimientoholcim.Adaptadores;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mantenimientoholcim.Modelo.ElementInspeccion;
import com.example.mantenimientoholcim.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.List;

public class ListAdapterInspeccion extends RecyclerView.Adapter<ListAdapterInspeccion.ViewHolder> {
    private List<String> mdata;
    private LayoutInflater mInflater;
    private Context context;
    private HashMap<String, ElementInspeccion> valores= new HashMap<String, ElementInspeccion>();
    String comentario3;



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
        String comentario="";
        String ok="";

        LinearLayout layou_observacion;
        TextView txtEnunciado,txtNum,txtobservacion;
        CheckBox checkOk,checkNOOK;
        private View view;
        public ViewHolder(View view) {
            super(view);
            txtEnunciado=view.findViewById(R.id.txtEnunciado);
            txtNum=view.findViewById(R.id.txtNum);
            checkOk=view.findViewById(R.id.checkOK);
            checkNOOK=view.findViewById(R.id.checkNOOK);
            layou_observacion=view.findViewById(R.id.layout_observacion);
            txtobservacion=view.findViewById(R.id.txtobservacion);
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

           txtNum.setText(String.valueOf(posicion+1));
            checkOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkNOOK.setChecked(false);
                    ok="OK";
                    ElementInspeccion elementInspeccion= new ElementInspeccion(item,ok,comentario);
                    valores.put(pos,elementInspeccion);
                }
            });

            checkNOOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkOk.setChecked(false);
                    ok="NO OK";
                    comentario=AgregarComentario(comentario,ok,item,txtobservacion,pos,checkNOOK);
                }
            });
            layou_observacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ok.equals("")){
                        Toast.makeText(context, "Para agregar debe marcar 'OK' o 'NO OK' primero", Toast.LENGTH_SHORT).show();
                    }else {

                        comentario=AgregarComentario(comentario,ok,item,txtobservacion,pos,checkNOOK);

                    }




                }
            });



                

        }
    }
    public String AgregarComentario(String comentario, String ok,String item,TextView txtobservacion,String pos, CheckBox checkNOOK){
        comentario3="";

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View view = mInflater.inflate(R.layout.dialog_comentarios, null);
        TextInputEditText editcomentario;
        TextView txtanterior;
        editcomentario= view.findViewById(R.id.editobservacion);
        txtanterior=view.findViewById(R.id.txtanterior);
        builder.setTitle("Observación");
        if(!comentario.equals("")){
            txtanterior.setVisibility(View.VISIBLE);
            txtanterior.setText("Comentario anterior: "+comentario);
            builder.setTitle("Modificar Observación");
        }
        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String comentario2= editcomentario.getText().toString();
                if(comentario2.equals("")){
                    checkNOOK.setChecked(false);
                    Toast.makeText(context, "No hay nignun comentario", Toast.LENGTH_SHORT).show();
                    comentario3=comentario;
                }else {
                    comentario3=comentario2;

                    txtobservacion.setText("Modificar Observación");
                    ElementInspeccion elementInspeccion= new ElementInspeccion(item,ok,comentario2);
                    valores.put(pos,elementInspeccion);
                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkNOOK.setChecked(false);
                dialog.dismiss();
            }
        });
        builder.setView(view);
        builder.setCancelable(false);

        AlertDialog alert = builder.create();
        alert.show();
        return comentario3;

    }
}
