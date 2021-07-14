package com.example.mantenimientoholcim.ui.Tareas;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mantenimientoholcim.Adaptadores.AdapterTareas;
import com.example.mantenimientoholcim.Modelo.InternalStorage;
import com.example.mantenimientoholcim.Modelo.Tarea;
import com.example.mantenimientoholcim.Modelo.UsersData;
import com.example.mantenimientoholcim.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HistorialTareasFragment extends Fragment {
    View root;


    RecyclerView recyclerView;

    Context context;

    List<Tarea> tareas=new ArrayList<>();
    AdapterTareas tareasadapter;
    FirebaseDatabase database;
    DatabaseReference tareasdb;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root=inflater.inflate(R.layout.fragment_historial_tareas, container, false);
        context=root.getContext();
        recyclerView=root.findViewById(R.id.recyclerviewTareasHistorial);
        return  root;
    }
    @Override
    public void onStart() {
        super.onStart();
        tareas.clear();
        database= FirebaseDatabase.getInstance();
        tareasdb=database.getReference("Taller").child("Tareas");
        tareasdb.keepSynced(true);
        tareasdb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Tarea tarea=snapshot.getValue(Tarea.class);
                tarea.setCodigo(snapshot.getKey());
                if(tarea.getEstado().equals("Finalizada")){
                    tareas.add(tarea);
                    displayTareas(tareas);
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Tarea tarea=snapshot.getValue(Tarea.class);
                tarea.setCodigo(snapshot.getKey());
                if(tarea.getEstado().equals("Finalizada")){
                    List<Tarea> nuevasTareas=new ArrayList<>();
                    for(Tarea m:tareas)
                    {
                        if(m.getCodigo().equals(tarea.getCodigo())){
                            nuevasTareas.add(tarea);
                        }
                        else {
                            nuevasTareas.add(m);
                        }

                    }

                    tareas=nuevasTareas;

                    displayTareas(tareas);

                }


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Tarea tarea=snapshot.getValue(Tarea.class);

                tarea.setCodigo(snapshot.getKey());
                if(tarea.getEstado().equals("Finalizada")){
                    List<Tarea> nuevasTareas=new ArrayList<>();
                    for(Tarea m:tareas)
                    {
                        if(!m.getCodigo().equals(tarea.getCodigo())){
                            nuevasTareas.add(m);
                        }


                    }
                    tareas=nuevasTareas;

                    displayTareas(tareas);
                }


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void displayTareas(List<Tarea> tareas){
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        tareasadapter= new AdapterTareas(context,tareas,tareasdb);
        recyclerView.setAdapter(tareasadapter);
    }
}