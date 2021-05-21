package com.example.mantenimientoholcim.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mantenimientoholcim.Modelo.Tarea;
import com.example.mantenimientoholcim.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

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
        Tarea tarea= tareaslist.get(position);
        holder.txtresponsable.setText(tarea.getEncargadosString());
        holder.txtdescripcion.setText(tarea.getDescripcion());
        holder.txtestado.setText(tarea.getEstado());
        holder.txtfecha.setText(tarea.getFechalimite());

    }

    @Override
    public int getItemCount() {
        return tareaslist.size();
    }

    public class AdapterTareasViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtestado, txtfecha, txtdescripcion, txtresponsable;

        public AdapterTareasViewHolder(@NonNull View itemView) {
            super(itemView);
            txtestado= itemView.findViewById(R.id.estadotxt);
            txtfecha=itemView.findViewById(R.id.txtfechalimite);
            txtdescripcion=itemView.findViewById(R.id.txtdescripcion);
            txtresponsable=itemView.findViewById(R.id.txtresponsable);

        }
    }


}
