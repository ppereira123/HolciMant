package com.example.mantenimientoholcim.ui.Tareas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mantenimientoholcim.Adaptadores.AdapterTareas;
import com.example.mantenimientoholcim.MainActivity;
import com.example.mantenimientoholcim.Modelo.ComentarioTarea;
import com.example.mantenimientoholcim.Modelo.InternalStorage;
import com.example.mantenimientoholcim.Modelo.Tarea;
import com.example.mantenimientoholcim.Modelo.UsersData;
import com.example.mantenimientoholcim.R;
import com.example.mantenimientoholcim.RevisionPuntosBloqueo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;
import static com.theartofdev.edmodo.cropper.CropImage.getPickImageResultUri;


public class tareasporhacerFragment extends Fragment {
    View root;
    private SwipeRefreshLayout swipe;
    SearchView searchView;
    private FloatingActionButton fabItem;
    RecyclerView recyclerView;
    Context context;
    List<Tarea> tareas=new ArrayList<>();
    AdapterTareas tareasadapter;
    FirebaseDatabase database;
    DatabaseReference tareasdb;
    UsersData userdata;
    AutoCompleteTextView autocompleteSpinnerFiltroGuardadas;
    int posFiltro=0;
    String[] opcfiltro;
    String stringbusqueda="";

//


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_tareasporhacer, container, false);
        context=root.getContext();
        recyclerView=root.findViewById(R.id.recyclerviewTareas);
        fabItem=root.findViewById(R.id.fabTareas);
        searchView=root.findViewById(R.id.buscartTareas);
        autocompleteSpinnerFiltroGuardadas=root.findViewById(R.id.autocompleteSpinnerFiltroGuardadas);
        userdata=new InternalStorage().cargarArchivo(context);

        fabItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(root.getContext(), NuevaTarea.class);
                startActivity(intent);

            }
        });

        autocompleteSpinnerFiltroGuardadas.setKeyListener(null);
        opcfiltro= new String[]{"Codigo", "Autor", "Descripción"};
        setfiltro();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                buscar(s);
                stringbusqueda=s;

                return true;
            }
        });
        return root;
    }
    private void setfiltro() {
        ArrayAdapter<String> adapterfiltro=new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item,opcfiltro);
        autocompleteSpinnerFiltroGuardadas.setAdapter(adapterfiltro);
        autocompleteSpinnerFiltroGuardadas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                posFiltro=position;

            }
        });
    }

    void buscar(String s) {
        ArrayList<Tarea>  milista = new ArrayList<>();
        for (Tarea obj: tareas){
            switch (posFiltro){
                case 0:
                    if(!obj.getCodEquipo().equals("")){

                        if(obj.getCodEquipo().toLowerCase().contains(s.toLowerCase())){
                            milista.add(obj);
                        }
                    }else {
                        if("Tarea del taller".toLowerCase().contains(s.toLowerCase())){
                            milista.add(obj);
                        }

                    }


                    break;
                case 1:
                    if(obj.getAutor().toLowerCase().contains(s.toLowerCase())){
                        milista.add(obj);
                    }
                    break;
                case 2:
                    if(obj.getDescripcion().toLowerCase().contains(s.toLowerCase())){
                        milista.add(obj);
                    }

                    break;
            }


        }
        displayTareas(milista);


    }



    @Override
    public void onStart() {
        super.onStart();
        tareas.clear();
        database= FirebaseDatabase.getInstance();
        tareasdb=database.getReference("Taller").child("Novedades");

        tareasdb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Tarea tarea=snapshot.getValue(Tarea.class);
                tarea.setCodigo(snapshot.getKey());
                if(!tarea.getEstado().equals("Finalizada")){
                    tareas.add(tarea);
                    if(stringbusqueda.equals("")){
                        displayTareas(tareas);
                    }else {
                        buscar(stringbusqueda);
                    }
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Tarea tarea=snapshot.getValue(Tarea.class);
                tarea.setCodigo(snapshot.getKey());
                if(!tarea.getEstado().equals("Finalizada")){
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

                    if(stringbusqueda.equals("")){
                        displayTareas(tareas);
                    }else {
                        buscar(stringbusqueda);
                    }
                }


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Tarea tarea=snapshot.getValue(Tarea.class);
                tarea.setCodigo(snapshot.getKey());

                if(!tarea.getEstado().equals("Finalizada")){
                    List<Tarea> nuevasTareas=new ArrayList<>();
                    for(Tarea m:tareas)
                    {
                        if(!m.getCodigo().equals(tarea.getCodigo())){
                            nuevasTareas.add(m);
                        }


                    }
                    tareas=nuevasTareas;

                    if(stringbusqueda.equals("")){
                        displayTareas(tareas);
                    }else {
                        buscar(stringbusqueda);
                    }
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
        ArrayList<Tarea> lista=new ArrayList<>();
        for(Tarea novedad:tareas){
            if(!lista.contains(novedad)){
                lista.add(novedad);
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        tareasadapter= new AdapterTareas(context,lista);
        recyclerView.setAdapter(tareasadapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }




}