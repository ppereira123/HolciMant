package com.example.mantenimientoholcim.ui.Tareas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
    private LayoutInflater mInflater;
    Context context;
    List<String> encargadoslist;
    boolean[] checkedItems;
    List<String> slectEncargados=new ArrayList<>();
    String [] arryaEncaragdos;
    List<Tarea> tareas=new ArrayList<>();
    AdapterTareas tareasadapter;
    FirebaseDatabase database;
    DatabaseReference tareasdb;
    UsersData userdata;
    Bitmap thumb_bitmap= null;
    byte[] thumb_byte;
//
    TextInputEditText editDescripcion, editEncargados;
    ImageView imgFotoTarea;
//


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_tareasporhacer, container, false);
        context=root.getContext();
        recyclerView=root.findViewById(R.id.recyclerviewTareas);
        fabItem=root.findViewById(R.id.fabTareas);
        userdata=new InternalStorage().cargarArchivo(context);
        fabItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(root.getContext(), NuevaTarea.class);
                startActivity(intent);

            }
        });
        return root;
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
                    displayTareas(tareas);
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

                    displayTareas(tareas);
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

    @Override
    public void onResume() {
        super.onResume();
    }




    private void configFecha(TextInputEditText tietFecha) {
        Calendar calendar= Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        tietFecha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date= day+"/"+month+"/"+year;
                        tietFecha.setText(date);
                        tietFecha.clearFocus();


                    }

                },year,month,day);
                datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_NEGATIVE) {
                            dialog.dismiss();
                            tietFecha.clearFocus();

                        }

                    }
                });

                if(hasFocus){
                    datePickerDialog.show();
                }
                else{
                    datePickerDialog.dismiss();
                    tietFecha.clearFocus();
                }

            }
        });
    }
}