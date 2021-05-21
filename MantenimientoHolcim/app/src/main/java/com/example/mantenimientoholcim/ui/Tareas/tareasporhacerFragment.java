package com.example.mantenimientoholcim.ui.Tareas;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ListView;
import android.widget.Toast;

import com.example.mantenimientoholcim.Adaptadores.AdapterTareas;
import com.example.mantenimientoholcim.Modelo.ComentarioTarea;
import com.example.mantenimientoholcim.Modelo.Tarea;
import com.example.mantenimientoholcim.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_tareasporhacer, container, false);
        context=root.getContext();
        recyclerView=root.findViewById(R.id.recyclerviewTareas);
        fabItem=root.findViewById(R.id.fabTareas);
        fabItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevaTarea(context);

            }
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        database= FirebaseDatabase.getInstance();
        tareasdb=database.getReference("Taller").child("Tareas");
        tareasdb.keepSynced(true);
        tareasdb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Tarea tarea=snapshot.getValue(Tarea.class);
                tarea.setCodigo(snapshot.getKey());
                tareas.add(tarea);
                displayTareas(tareas);



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Tarea tarea=snapshot.getValue(Tarea.class);
                tarea.setCodigo(snapshot.getKey());
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

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Tarea tarea=snapshot.getValue(Tarea.class);
                tarea.setCodigo(snapshot.getKey());
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

    void nuevaTarea(Context context){
        if(encargadoslist!=null){
            encargadoslist.clear();
        }
        if(slectEncargados!=null){
            slectEncargados.clear();
        }


        mInflater=LayoutInflater.from(context);

        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        View view = mInflater.inflate(R.layout.adapter_dialog_crear_tarea, null);
        TextInputEditText editDescripcion, editEncargados, editFecha;
        Button subir,cancelar;
        subir=view.findViewById(R.id.btnsubirTarea);
        cancelar=view.findViewById(R.id.btncancelarTarea);
        editDescripcion=view.findViewById(R.id.editDescripcion);
        editEncargados=view.findViewById(R.id.editEncargados);
        editFecha=view.findViewById(R.id.editFechaLimit);
        escogerEncargador(context,editEncargados);
        configFecha(editFecha);

        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.show();
        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editDescripcion.getText().toString().equals("")){
                    FirebaseDatabase database= FirebaseDatabase.getInstance();
                    DatabaseReference ref=database.getReference("Taller").child("Tareas");
                    ref.keepSynced(true);
                    DatabaseReference refId=ref.push();
                    String codigo=refId.getKey();
                    Tarea objeto= new Tarea(codigo,editDescripcion.getText().toString(),editFecha.getText().toString(),slectEncargados, "Pendiente","");
                    ref.child(codigo).setValue(objeto);
                    dialog.dismiss();

                }else Toast.makeText(context, "Debe llenar la descripción", Toast.LENGTH_SHORT).show();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



    }
    void escogerEncargador(Context context, TextInputEditText editEncargados){

        editEncargados.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                    List<String> encargadosSeleccionados=new ArrayList<>();
                    encargadoslist=new ArrayList<>();
                    encargadoslist.add("Todos");
                    FirebaseDatabase database= FirebaseDatabase.getInstance();
                    DatabaseReference ref=database.getReference("Taller").child("Miembros");
                    ref.keepSynced(true);
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};
                                List<String> m = snapshot.getValue(t);
                                for(String miembro:m){
                                    encargadoslist.add(miembro);
                                }

                                checkedItems=new boolean[encargadoslist.size()];
                                arryaEncaragdos=new String[encargadoslist.size()];

                                for(int i=0;i<encargadoslist.size();i=i+1){
                                    arryaEncaragdos[i]=encargadoslist.get(i);
                                    checkedItems[i]=false;
                                }


                                AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
                                builder.setTitle("Escoger encragados para la tarea");
                                builder.setMultiChoiceItems(arryaEncaragdos, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if(arryaEncaragdos[which].equals("Todos")){

                                            final AlertDialog alertDialog = (AlertDialog) dialog;
                                            final ListView alertDialogList = alertDialog.getListView();

                                            for (int position = 0; position < alertDialogList.getChildCount(); position++)
                                            {
                                                if (position != which) {
                                                    if(isChecked){
                                                        alertDialogList.getChildAt(position).setVisibility(View.GONE);
                                                        encargadosSeleccionados.clear();
                                                        encargadosSeleccionados.add(arryaEncaragdos[which]);
                                                    }else {
                                                        encargadosSeleccionados.clear();
                                                        alertDialogList.getChildAt(position).setVisibility(View.VISIBLE);
                                                    }



                                                }
                                            }
                                        }else {
                                            if(encargadosSeleccionados.contains("Todos")){
                                                encargadosSeleccionados.remove(encargadosSeleccionados.indexOf("Todos"));
                                            }

                                            if(encargadosSeleccionados.contains(arryaEncaragdos[which])){
                                                encargadosSeleccionados.remove(encargadosSeleccionados.indexOf(arryaEncaragdos[which]));

                                            }
                                            else {
                                                encargadosSeleccionados.add(arryaEncaragdos[which]);
                                            }

                                        }




                                    }
                                });
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        slectEncargados.clear();
                                        editEncargados.clearFocus();

                                        if(!encargadosSeleccionados.contains("Todos")){
                                            int cont=0;

                                            for(boolean seleccionado:checkedItems){
                                                if(seleccionado){
                                                    slectEncargados.add(arryaEncaragdos[cont]);

                                                }
                                                cont++;
                                            }

                                        }else {
                                            slectEncargados.add("Todos");


                                        }
                                        String muestra="";
                                        for(String s: slectEncargados){
                                            muestra=muestra+s+"\n";
                                        }
                                        editEncargados.setText(muestra);



                                    }
                                });
                                builder.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        editEncargados.clearFocus();
                                    }
                                });

                                AlertDialog dialog=builder.create();
                                dialog.show();
                            }
                            else{
                                Toast.makeText(root.getContext(), "No existe referencia", Toast.LENGTH_SHORT).show();
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    editEncargados.clearFocus();
                }
            }
        });


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