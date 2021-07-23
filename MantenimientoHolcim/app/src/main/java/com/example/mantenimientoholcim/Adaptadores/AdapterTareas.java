package com.example.mantenimientoholcim.Adaptadores;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mantenimientoholcim.Modelo.Tarea;
import com.example.mantenimientoholcim.R;
import com.example.mantenimientoholcim.ui.Inspecciones.PlantillasInspeccion;
import com.example.mantenimientoholcim.ui.Tareas.VistaTarea;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.example.mantenimientoholcim.ui.Inspecciones.PlantillasInspeccion.diferenciaDias;

public class AdapterTareas extends  RecyclerView.Adapter<AdapterTareas.AdapterTareasViewHolder>{

    Context context;
    List<Tarea> tareaslist;


    public AdapterTareas(Context context, List<Tarea> tareaslist) {
        this.context = context;
        this.tareaslist = tareaslist;
    }

    @NonNull
    @Override
    public AdapterTareasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.adaptador_list_tarea,parent,false);
        return new AdapterTareasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTareasViewHolder holder, int position) {

        String[] estados=context.getResources().getStringArray(R.array.combo_estadoTareas);
        Tarea tarea= tareaslist.get(position);

        holder.txtdescripcion.setText(tarea.getDescripcion());
        holder.txtestado.setText(tarea.getEstado());
        holder.txtfecha.setText(tarea.getFechadeEnvio());
        holder.txtautor.setText(tarea.getAutor());

        Resources res = context.getResources();
        List<String> nombre_equipos = Arrays.asList(res.getStringArray(R.array.combo_inspeccion_equipo_movil));
        if(nombre_equipos.contains(tarea.getCodEquipo())){
            holder.txtcodEquipo.setText(tarea.getCodEquipo());
        }else
        {
            holder.txtcodEquipo.setText("Tarea del taller");
        }


        if(tarea.getDirImagen()==null){
            holder.imgFotoAdaptadorTarea.setVisibility(View.GONE);
        }else {

            Picasso.with(context).load(tarea.getDirImagen()).into(holder.imgFotoAdaptadorTarea);
            holder.imgFotoAdaptadorTarea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle(tarea.getCodEquipo());
                    WebView wv = new WebView(context);
                    wv.loadUrl(tarea.getDirImagen());
                    wv.getSettings().setBuiltInZoomControls(true);
                    wv.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            view.loadUrl(url);
                            return true;
                        }
                    });

                    alert.setView(wv);
                    alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                }
            });


        }



        if(tarea.getEstado().equals(estados[0])){
            holder.cvTareaG.setCardBackgroundColor(Color.parseColor("#1565C0"));
            holder.imgEstado.setImageResource(R.drawable.reloj);
        }else if(tarea.getEstado().equals(estados[1])){
            holder.cvTareaG.setCardBackgroundColor(Color.parseColor("#3DDC84"));
            holder.imgEstado.setImageResource(R.drawable.enproceso);
        }

        if(tarea.getEstado().equals(estados[2])){
            holder.cvTareaG.setCardBackgroundColor(Color.parseColor("#9b9b9b"));
            holder.cvTareaG.setRadius(0);
            holder.cvtarea.setRadius(0);
            holder.imgEstado.setImageResource(R.drawable.cheque);
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
        TextView txtestado, txtfecha, txtdescripcion,txtcodEquipo,txtautor;
        CardView cvtarea, cvTareaG;
        ImageView imgEstado,imgFotoAdaptadorTarea;


        public AdapterTareasViewHolder(@NonNull View itemView) {
            super(itemView);
            txtestado= itemView.findViewById(R.id.estadotxt);
            txtfecha=itemView.findViewById(R.id.txtfechalimite);
            txtdescripcion=itemView.findViewById(R.id.txtdescripcion);
            imgFotoAdaptadorTarea=itemView.findViewById(R.id.imgFotoAdaptadorTarea);
            cvtarea=itemView.findViewById(R.id.cvTarea);
            cvTareaG=itemView.findViewById(R.id.cvTareaG);
            imgEstado=itemView.findViewById(R.id.imgEstado);
            txtcodEquipo=itemView.findViewById(R.id.txtcodEquipo);
            txtautor=itemView.findViewById(R.id.txtautor);
            imgFotoAdaptadorTarea=itemView.findViewById(R.id.imgFotoAdaptadorTarea);


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
