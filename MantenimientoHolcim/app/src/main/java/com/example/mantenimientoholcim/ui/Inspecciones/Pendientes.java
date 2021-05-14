package com.example.mantenimientoholcim.ui.Inspecciones;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mantenimientoholcim.Adaptadores.AdaptadorInspeccionesRealizadas;
import com.example.mantenimientoholcim.Adaptadores.AdaptadorListPendientes;
import com.example.mantenimientoholcim.Modelo.InspeccionTipo1;
import com.example.mantenimientoholcim.Modelo.PendientesInspeciones;
import com.example.mantenimientoholcim.Modelo.RealizacionInspeccion;
import com.example.mantenimientoholcim.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import static com.example.mantenimientoholcim.ui.Inspecciones.PlantillasInspeccion.diferenciaDias;


public class Pendientes extends Fragment {
    View root;
    ListView listPendientes;
    private SwipeRefreshLayout swipe;
    TextView txtaviso;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root=inflater.inflate(R.layout.fragment_pendientes, container, false);
        listPendientes=root.findViewById(R.id.listPendientes);
        txtaviso=root.findViewById(R.id.txtaviso);
        swipe=root.findViewById(R.id.swipePedientes);
       cargarItems();
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarItems();
            }
        });


        return root;
    }
    public void cargarItems(){
        Date d=new Date();
        SimpleDateFormat fecc=new SimpleDateFormat("d/MM/yyyy");
        String fechacActual = fecc.format(d);
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("RealizacionInspecciones");
        myRef.keepSynced(true);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<RealizacionInspeccion> listitems=new ArrayList<>();
                if(snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        GenericTypeIndicator<RealizacionInspeccion> t = new GenericTypeIndicator<RealizacionInspeccion>() {};
                        RealizacionInspeccion m = ds.getValue(t);
                        try {
                            if (diferenciaDias(m.getSiguientefecha(),fechacActual)<=30){
                                listitems.add(m);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Collections.sort(listitems, new Comparator<RealizacionInspeccion>() {
                            @Override
                            public int compare(RealizacionInspeccion o1, RealizacionInspeccion o2) {
                                int x=0;

                                try {
                                    x=diferenciaDias(o1.getSiguientefecha(),o2.getSiguientefecha());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                return x;
                            }


                        });

                        AdaptadorListPendientes adaptador= new AdaptadorListPendientes(root.getContext(), listitems );
                        listPendientes.setAdapter(adaptador);
                        if(listitems.size()>0){
                            txtaviso.setVisibility(View.GONE);
                        }
                        swipe.setRefreshing(false);



                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}