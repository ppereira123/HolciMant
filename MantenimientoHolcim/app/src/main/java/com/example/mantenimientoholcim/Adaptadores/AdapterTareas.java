package com.example.mantenimientoholcim.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mantenimientoholcim.Modelo.Tarea;
import com.example.mantenimientoholcim.R;
import com.example.mantenimientoholcim.ui.Inspecciones.PlantillasInspeccion;
import com.example.mantenimientoholcim.ui.Tareas.VistaTarea;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.mantenimientoholcim.ui.Inspecciones.PlantillasInspeccion.diferenciaDias;

public class AdapterTareas extends  RecyclerView.Adapter<AdapterTareas.AdapterTareasViewHolder>{

    Context context;
    List<Tarea> tareaslist;
    DatabaseReference tareasDb;

    public AdapterTareas(Context context, List<Tarea> tareaslist, DatabaseReference tareasDb) {
        this.context = context;
        this.tareaslist = tareaslist;
        this.tareasDb = tareasDb;
    }

    @NonNull
    @Override
    public AdapterTareasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.adaptador_list_tarea,parent,false);
        return new AdapterTareasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTareasViewHolder holder, int position) {
        Date d=new Date();
        SimpleDateFormat fecc=new SimpleDateFormat("d/MM/yyyy");
        String fechacActual = fecc.format(d);
        String[] estados=context.getResources().getStringArray(R.array.combo_estadoTareas);
        Tarea tarea= tareaslist.get(position);
        holder.txtresponsable.setText(getEncargadosString(tarea.getEncargados()));
        holder.txtdescripcion.setText(tarea.getDescripcion());
        holder.txtestado.setText(tarea.getEstado());
        holder.txtfecha.setText(tarea.getFechalimite());

        if(tarea.getEstado().equals(estados[0])){
            holder.cvTareaG.setCardBackgroundColor(Color.parseColor("#1565C0"));
            holder.imgEstado.setImageResource(R.drawable.reloj);
        }else if(tarea.getEstado().equals(estados[1])){
            holder.cvTareaG.setCardBackgroundColor(Color.parseColor("#3DDC84"));
            holder.imgEstado.setImageResource(R.drawable.enproceso);
        }
        try {
            if (diferenciaDias(tarea.getFechalimite(),fechacActual)<0){
                holder.cvTareaG.setCardBackgroundColor(Color.parseColor("#F41111"));
                holder.imgEstado.setImageResource(R.drawable.atraso);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.cvtarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, VistaTarea.class);

                intent.putExtra("tarea", (Tarea) tarea);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tareaslist.size();
    }

    public class AdapterTareasViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtestado, txtfecha, txtdescripcion, txtresponsable;
        CardView cvtarea, cvTareaG;
        ImageView imgEstado;


        public AdapterTareasViewHolder(@NonNull View itemView) {
            super(itemView);
            txtestado= itemView.findViewById(R.id.estadotxt);
            txtfecha=itemView.findViewById(R.id.txtfechalimite);
            txtdescripcion=itemView.findViewById(R.id.txtdescripcion);
            txtresponsable=itemView.findViewById(R.id.txtresponsable);
            cvtarea=itemView.findViewById(R.id.cvTarea);
            cvTareaG=itemView.findViewById(R.id.cvTareaG);
            imgEstado=itemView.findViewById(R.id.imgEstado);


        }
    }
    public static String getEncargadosString(List<String> encargados){
        String encargadosjuntos="";
        if(encargados!=null){
            for (String encargado:encargados){
                encargadosjuntos=encargadosjuntos+encargado+"\n";
            }
        }

        return encargadosjuntos;
    }


}
