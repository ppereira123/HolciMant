package com.example.mantenimientoholcim.Adaptadores;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mantenimientoholcim.Modelo.Tarea;

import java.util.List;

public class AdapterTareas {
    private List<Tarea> tareaslist;
    Context context;

    public AdapterTareas(List<Tarea> tareaslist, Context context) {
        this.tareaslist = tareaslist;
        this.context = context;
    }


}
