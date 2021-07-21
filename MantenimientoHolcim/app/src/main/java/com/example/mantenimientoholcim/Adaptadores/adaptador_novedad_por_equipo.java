package com.example.mantenimientoholcim.Adaptadores;

import android.content.Context;
import android.content.DialogInterface;
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
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class adaptador_novedad_por_equipo extends RecyclerView.Adapter<adaptador_novedad_por_equipo.adaptador_novedad_por_equipoViewHolder> {
    Context context;
    List<Tarea> novedadeslist;


    public adaptador_novedad_por_equipo(Context context, List<Tarea> novedadeslist) {
        this.context = context;
        this.novedadeslist = novedadeslist;
    }

    @NonNull
    @Override
    public adaptador_novedad_por_equipoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.adaptador_novedades_por_equipo,parent,false);
        return new adaptador_novedad_por_equipoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull adaptador_novedad_por_equipo.adaptador_novedad_por_equipoViewHolder holder, int position) {
        Tarea novedad=novedadeslist.get(position);
        holder.txtAutorNovedad.setText("Autor: "+novedad.getAutor());
        holder.txtDescripcionNovedadEquido.setText("Descripción: "+novedad.getDescripcion());
        holder.txtEstadoNovedad.setText("Estado: "+novedad.getEstado());
        if(!novedad.getCodEquipo().equals("")){
            holder.txtCodEquipo.setText(novedad.getCodEquipo());
        }else
        {
            holder.txtCodEquipo.setText("Tarea de Taller");
        }

        String dirImg=novedad.getDirImagen();
        Picasso.with(context).load(dirImg).into(holder.imgNovedaEquipo);
        holder.imgNovedaEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle(novedad.getCodEquipo());
                WebView wv = new WebView(context);
                wv.loadUrl(novedad.getDirImagen());
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

    @Override
    public int getItemCount() {
        return novedadeslist.size();
    }
    public class adaptador_novedad_por_equipoViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtAutorNovedad,txtEstadoNovedad,txtDescripcionNovedadEquido,txtCodEquipo;
        ImageView imgNovedaEquipo;


        public adaptador_novedad_por_equipoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAutorNovedad= itemView.findViewById(R.id.txtAutorNovedad);
            txtEstadoNovedad=itemView.findViewById(R.id.txtEstadoNovedad);
            txtDescripcionNovedadEquido=itemView.findViewById(R.id.txtDescripcionNovedadEquido);
            imgNovedaEquipo=itemView.findViewById(R.id.imgNovedaEquipo);
            txtCodEquipo=itemView.findViewById(R.id.txtCodEquipo);

        }
    }


}