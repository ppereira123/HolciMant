package com.example.mantenimientoholcim.ui.Tareas;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mantenimientoholcim.Adaptadores.AdapterTareas;
import com.example.mantenimientoholcim.Modelo.InternalStorage;
import com.example.mantenimientoholcim.Modelo.Tarea;
import com.example.mantenimientoholcim.Modelo.UsersData;
import com.example.mantenimientoholcim.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.mantenimientoholcim.ui.Inspecciones.PlantillasInspeccion.diferenciaDias;

public class HistorialTareasFragment extends Fragment {
    View root;


    RecyclerView recyclerView;

    Context context;

    List<Tarea> tareas=new ArrayList<>();
    AdapterTareas tareasadapter;
    FirebaseDatabase database;
    DatabaseReference tareasdb;

    SearchView searchView;
    AutoCompleteTextView autocompleteSpinnerFiltroHistorial;
    int posFiltro=0;
    String[] opcfiltro;
    String stringbusqueda="";

    LayoutInflater mInflater;
    String fechadesde="",fechahasta="";
    String fechadesdetemporal="";
    String fechahastatemporal="";

    TextView txtfiltrarfecha;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root=inflater.inflate(R.layout.fragment_historial_tareas, container, false);
        context=root.getContext();
        mInflater= LayoutInflater.from(context);
        recyclerView=root.findViewById(R.id.recyclerviewTareasHistorial);
        searchView=root.findViewById(R.id.buscartTareasHistorial);
        autocompleteSpinnerFiltroHistorial=root.findViewById(R.id.autocompleteSpinnerFiltroHistorial);
        autocompleteSpinnerFiltroHistorial.setKeyListener(null);
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

        txtfiltrarfecha=root.findViewById(R.id.txtfiltrarfecha);
        txtfiltrarfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtroPorFecha();
            }
        });

        return  root;
    }


    @Override
    public void onStart() {
        super.onStart();
        tareas.clear();
        database= FirebaseDatabase.getInstance();
        tareasdb=database.getReference("Taller").child("Novedades");
        tareasdb.keepSynced(true);
        tareasdb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Tarea tarea=snapshot.getValue(Tarea.class);
                tarea.setCodigo(snapshot.getKey());
                if(tarea.getEstado().equals("Finalizada")){
                    tareas.add(tarea);
                    if(stringbusqueda.equals("")){
                        if(fechadesde.equals("")&& fechahasta.equals("")){
                            displayTareas(tareas);
                        }else {
                            try {
                                filtrarListPorfecha(tareas);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }else {
                        buscar(stringbusqueda);
                    }
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

                    if(stringbusqueda.equals("")){
                        if(fechadesde.equals("")&& fechahasta.equals("")){
                            displayTareas(tareas);
                        }else {
                            try {
                                filtrarListPorfecha(tareas);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }else {
                        buscar(stringbusqueda);
                    }






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

                    if(stringbusqueda.equals("")){
                        if(fechadesde.equals("")&& fechahasta.equals("")){
                            displayTareas(tareas);
                        }else {
                            try {
                                filtrarListPorfecha(tareas);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
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

    private void filtroPorFecha() {
        fechadesdetemporal="";
        fechahastatemporal="";
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        View view=mInflater.inflate(R.layout.dialogo_adapter_filtro_fecha,null);
        TextInputEditText editFechadesde,editFechahasta;
        builder.setTitle("Filtro por fecha");
        builder.setMessage("Escoger fechas entre las que quiere buscar las inspecciones");
        TextInputLayout textinputlayoutFechadesde,textinputlayoutFechahasta;
        editFechadesde=view.findViewById(R.id.editFechadesde);
        editFechahasta=view.findViewById(R.id.editFechahasta);
        textinputlayoutFechadesde=view.findViewById(R.id.textinputlayoutFechadesde);
        textinputlayoutFechahasta=view.findViewById(R.id.textinputlayoutFechahasta);
        configFecha(editFechadesde,editFechahasta,false,textinputlayoutFechadesde);
        configFecha(editFechahasta,editFechadesde,true,textinputlayoutFechahasta);

        builder.setNegativeButton("Limpiar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fechahasta="";
                fechadesde="";
                displayTareas(tareas);
            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fechadesdetemporal=editFechadesde.getText().toString();
                fechahastatemporal=editFechahasta.getText().toString();
                if(!fechadesdetemporal.equals("")||!fechahastatemporal.equals("")){

                    fechahasta=fechahastatemporal;
                    fechadesde=fechadesdetemporal;
                    try {
                        filtrarListPorfecha(tareas);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }else {
                    Toast.makeText(context, "Error, no escogio las dos fechas para la busqueda", Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setView(view);
        builder.show();



    }

    void filtrarListPorfecha(List<Tarea> tareas) throws ParseException {
        ArrayList<Tarea> milista = new ArrayList<>();
        for (Tarea obj: tareas){
            if(diferenciaDias(obj.getFechadeEnvio(),fechadesde)>=0 && diferenciaDias(obj.getFechadeEnvio(),fechahasta)<=0){
                milista.add(obj);
            }
        }
        displayTareas(milista);


    }

    ArrayList<Tarea> listfiltrarListPorfecha(List<Tarea> tareas) throws ParseException {
        ArrayList<Tarea> milista = new ArrayList<>();
        for (Tarea obj: tareas){
            if(diferenciaDias(obj.getFechadeEnvio(),fechadesde)>=0 && diferenciaDias(obj.getFechadeEnvio(),fechahasta)<=0){
                milista.add(obj);
            }
        }
        return milista;

    }

    private void configFecha(EditText tietFecha, EditText tietFecha2, boolean isfechahasta, TextInputLayout textinput) {


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
                        String monthstring,yearstring,daystring;
                        monthstring=String.valueOf(month);
                        String date= day+"/"+month+"/"+year;



                        ParsePosition pos = new ParsePosition(0);
                        String fecha2= tietFecha2.getText().toString();
                        SimpleDateFormat formatter1=new SimpleDateFormat("dd/MM/yyyy");

                        if(!fecha2.equals("")){

                            if(isfechahasta){
                                try {
                                    if(diferenciaDias(date,fecha2)>=0){
                                        tietFecha.setText(date);
                                    }
                                    else {
                                        Toast.makeText(context, "Error! la fecha debe ser mayor a la de Fecha desde", Toast.LENGTH_SHORT).show();
                                        tietFecha.setText("");
                                        int color= tietFecha.getDrawingCacheBackgroundColor();
                                        textinput.setBoxBackgroundColor(Color.parseColor("#FFCDD2"));
                                        TimerTask tarea= new TimerTask() {
                                            @Override
                                            public void run() {
                                                textinput.setBoxBackgroundColor(Color.parseColor("#FFFFFF"));



                                            }
                                        };

                                        Timer tiempo= new Timer();
                                        tiempo.schedule(tarea,1000);
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                            else {
                                try {
                                    if(diferenciaDias(date,fecha2)<=0){
                                        tietFecha.setText(date);
                                    }
                                    else {
                                        Toast.makeText(context, "Error! la fecha debe ser menor a la de Fecha hasta", Toast.LENGTH_SHORT).show();
                                        tietFecha.setText("");
                                        int color= tietFecha.getDrawingCacheBackgroundColor();
                                        textinput.setBoxBackgroundColor(Color.parseColor("#FFCDD2"));
                                        TimerTask tarea= new TimerTask() {
                                            @Override
                                            public void run() {
                                                textinput.setBoxBackgroundColor(Color.parseColor("#FFFFFF"));
                                            }
                                        };

                                        Timer tiempo= new Timer();
                                        tiempo.schedule(tarea,1000);
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }else {
                            tietFecha.setText(date);
                        }


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



    void buscar(String s) {
        ArrayList<Tarea>  milista = new ArrayList<>();

        List<Tarea>  milistabusqueda = new ArrayList<>();

        if(fechadesde.equals("")&& fechahasta.equals("")){
            milistabusqueda=tareas;
        }else {
            try {
                milistabusqueda=listfiltrarListPorfecha(tareas);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        for (Tarea obj: milistabusqueda){
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
    private void setfiltro() {
        ArrayAdapter<String> adapterfiltro=new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item,opcfiltro);
        autocompleteSpinnerFiltroHistorial.setAdapter(adapterfiltro);
        autocompleteSpinnerFiltroHistorial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                posFiltro=position;

            }
        });
    }
}