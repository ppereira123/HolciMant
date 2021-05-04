package com.example.mantenimientoholcim;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mantenimientoholcim.Modelo.ElementInspeccion;
import com.example.mantenimientoholcim.Modelo.InspeccionTipo1;

import com.example.mantenimientoholcim.Modelo.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class tab2 extends Fragment {
    ListView inspeccionesRealizadas;
    View root;
    private AdaptadorInspeccionesRealizadas adaptador;
    SearchView searchView;
    ArrayList<InspeccionTipo1> buscarlist=new ArrayList<>();
    String[] meses={"enero","febrero","marzo","abril","mayo","junio","julio","agosto","septiembre","octubre","noviembre","diciembre"};
    List<String> listmeses=new ArrayList<>();
    TextView txtPrueba;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_tab2, container, false);
        searchView=root.findViewById(R.id.buscartinspecciones);
        inspeccionesRealizadas=root.findViewById(R.id.listInspeccionesrealizadas);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                buscar(s);
                return true;
            }
        });
        cargarItems();
        //adaptador= new AdaptadorInspeccionesRealizadas(getContext(), listitems);
        //inspeccionesRealizadas.setAdapter(adaptador);




        inspeccionesRealizadas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //poner lo que la vista de la inspección realizada.

            }
        });

        // Inflate the layout for this fragment
        return root;
    }

    void buscar(String s){
        ArrayList<InspeccionTipo1> milista = new ArrayList<>();
        for (InspeccionTipo1 obj: buscarlist){
            if(obj.getEnuunciado().toLowerCase().contains(s.toLowerCase())){
                milista.add(obj);
            }

        }
        AdaptadorInspeccionesRealizadas adaptadorbuscador= new AdaptadorInspeccionesRealizadas(root.getContext(), milista );
        inspeccionesRealizadas.setAdapter(adaptadorbuscador);

    }



    void cargarItems(){
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("Inspecciones");
        DatabaseReference refitems=database.getReference("Items");

        myRef.keepSynced(false);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<InspeccionTipo1> listitems=new ArrayList<>();
                if(snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if(ds.exists()) {
                            for (DataSnapshot ds2 : ds.getChildren()) {
                                String p=ds2.getValue().toString();

                                String enunciado = ds2.child("enuunciado").getValue().toString();
                                String nombreInspector = ds2.child("nombreInspector").getValue().toString();
                                String fechaInspeccion = ds2.child("fechaInspeccion").getValue().toString();
                                String proximaInspeccion = ds2.child("proximaInspeccion").getValue().toString();
                                String codigo = ds2.child("codigo").getValue().toString();
                                String ubicacion = ds2.child("ubicacion").getValue().toString();
                                /*
                                String[] partscodigo = codigo.split("-");

                                refitems.child(partscodigo[0]).addListenerForSingleValueEvent(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){

                                            String ubicacionCodigo=snapshot.child("ubicacion").getValue().toString();
                                            if(!ubicacionCodigo.equals("")){
                                                myRef.child(ds.getKey()).child(ds2.getKey()).child("ubicacion").setValue(ubicacionCodigo);
                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {


                                    }
                                });


 */

                                /*
                                listmeses= Arrays.asList(meses);
                                String[] parts = fechaInspeccion.split("/");

                                if(parts.length==3){

                                    if(listmeses.contains(parts[1])){
                                        int mes=listmeses.indexOf(parts[1])+1;
                                        fechaInspeccion=parts[0]+"/"+mes+"/"+parts[2];
                                        myRef.child(ds.getKey()).child(ds2.getKey()).child("fechaInspeccion").setValue(fechaInspeccion);

                                    }
                                    String[] parts2 = proximaInspeccion.split("/");
                                    if(listmeses.contains(parts2[1])){
                                        int mes=listmeses.indexOf(parts2[1])+1;
                                        proximaInspeccion=parts2[0]+"/"+mes+"/"+parts2[2];
                                        myRef.child(ds.getKey()).child(ds2.getKey()).child("proximaInspeccion").setValue(proximaInspeccion);
                                    }

                                }else {
                                    Toast.makeText(root.getContext(), ds.getKey(), Toast.LENGTH_SHORT).show();
                                }

                                 */
                                HashMap<String,ElementInspeccion> valores= new HashMap<>();
                                for(DataSnapshot ds3:ds2.child("valores").getChildren()){
                                    String key=ds3.getKey();
                                    String enun=ds3.child("enunciado").getValue().toString();
                                    String ok=ds3.child("ok").getValue().toString();
                                    ElementInspeccion itemInspeccion=new ElementInspeccion(enun,ok);
                                    valores.put(key,itemInspeccion);
                                }
                                buscarlist=listitems;
                                InspeccionTipo1 item= new InspeccionTipo1(enunciado,nombreInspector,fechaInspeccion,proximaInspeccion,codigo,ubicacion,valores);
                                if(!listitems.contains(item)){
                                  listitems.add(item);

                                }
                            }
                        }

                    }
                }
                AdaptadorInspeccionesRealizadas adaptador= new AdaptadorInspeccionesRealizadas(root.getContext(), listitems );
                inspeccionesRealizadas.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}