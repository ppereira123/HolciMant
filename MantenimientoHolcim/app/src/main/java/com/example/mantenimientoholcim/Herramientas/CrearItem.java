package com.example.mantenimientoholcim.Herramientas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mantenimientoholcim.Modelo.InternalStorage;
import com.example.mantenimientoholcim.Modelo.Item;
import com.example.mantenimientoholcim.Modelo.UsersData;
import com.example.mantenimientoholcim.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CrearItem extends AppCompatActivity {
    EditText descripcionTxt,marcaTxt,observacionTxt,codigoTxt;
    TextView txtubicacion,txtestante,txttipo,titulotxt,selc1,selc2,selc3;
    Spinner ubicacionSpinner,tipoInspeccionspinner, estanteSpinner;
    NumberPicker vidaUtilTxt;
    private String ubicacion="Bodega de Herramientas",tipoInspeccion="ARNÉS DE CUERPO ENTERO";
    boolean generarCodigo=false;
    DatabaseReference refItem;
    String codigo="";
    Item item=null;
    boolean x=true;

    Button btnsubirItem, btnotraubicacion;
    int stock=1;
    ArrayAdapter<String> adapterestanteSpinner;
    String estanteSeleccionado="";
    Context context=this;

    AlertDialog alert;
    LayoutInflater mInflater;
    ArrayList<String> listanueva;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_item);
        descripcionTxt=findViewById(R.id.descripciontxt);
        marcaTxt=findViewById(R.id.marcatxt);

        observacionTxt=findViewById(R.id.observacion);
        vidaUtilTxt=findViewById(R.id.vidaUtilNp);

        ubicacionSpinner=findViewById(R.id.spinnerUbicacion);
        tipoInspeccionspinner=findViewById(R.id.spinnerInspeccion);
        estanteSpinner=findViewById(R.id.spinnerEstante);
        btnsubirItem=findViewById(R.id.subirItem);
        btnotraubicacion=findViewById(R.id.btnotraubicacion);
        txtubicacion=findViewById(R.id.seleccionado);
        txtestante=findViewById(R.id.seleccionadoestante);
        titulotxt=findViewById(R.id.titulotxt);


        selc1=findViewById(R.id.selec1);
        selc2=findViewById(R.id.selec2);
        selc3=findViewById(R.id.selec3);

        txttipo=findViewById(R.id.seleccionadotipoinspeccion);
        codigoTxt=findViewById(R.id.codigotxt);
        vidaUtilTxt.setMinValue(0);
        vidaUtilTxt.setMaxValue(50);

        txtubicacion.setText(ubicacion);
        txtestante.setText(estanteSeleccionado);

        txttipo.setText(tipoInspeccion);
        item=(Item)getIntent().getSerializableExtra("item");
        if(item!=null){
            editarDatos(item);
        }
        cargarSpinners();
        InternalStorage storage=new InternalStorage();
        String archivos[]=context.fileList();
        if (storage.ArchivoExiste(archivos,"admin.txt")){
            UsersData data= storage.cargarArchivo(context);
            if (data.isAdmin()==false){
                titulotxt.setText("Datos del item");
                btnsubirItem.setVisibility(View.GONE);
                descripcionTxt.setKeyListener(null);
                marcaTxt.setKeyListener(null);
                observacionTxt.setKeyListener(null);
                ubicacionSpinner.setVisibility(View.GONE);
                tipoInspeccionspinner.setVisibility(View.GONE);
                estanteSpinner.setVisibility(View.GONE);

                btnsubirItem.setVisibility(View.GONE);
                btnotraubicacion.setVisibility(View.GONE);
                vidaUtilTxt.setEnabled(false);
                selc1.setVisibility(View.GONE);
                selc2.setVisibility(View.GONE);
                selc3.setVisibility(View.GONE);
                txtubicacion.setTextColor(getResources().getColor(R.color.black));
                txtestante.setTextColor(getResources().getColor(R.color.black));
                txttipo.setTextColor(getResources().getColor(R.color.black));
                txtestante.setTextSize(18);
                txtubicacion.setTextSize(18);
                txttipo.setTextSize(18);




            }
            else{
                btnsubirItem.setVisibility(View.VISIBLE);
            }
        }

    }


    void cargarSpinners(){
        ArrayList<String> estados= new ArrayList<>();
        ArrayList<String> ubicaciones= new ArrayList<>();
        ArrayList<String> tipos= new ArrayList<>();

        estados.add("");
        ubicaciones.add(0,"Bodega de Herramientas");
        ubicaciones.add(1,"Bodega de Materiales");
        ubicaciones.add(2,"Bodega Lubricantes");
        ubicaciones.add(3,"Bodega.");
        ubicaciones.add(4,"Bodega de llantas y componentes grandes");
        ubicaciones.add(5,"");

        estados.add(0,"N/A");
        estados.add(1,"En uso");
        estados.add(2,"Repuesto");
        String[] listanombre=getResources().getStringArray(R.array.combo_inspeccionesNombre);
        listanueva = new ArrayList<String>();
        listanueva.add("N/A");
        for(int cnt=0;cnt<listanombre.length;cnt++)
        {
            listanueva.add(listanombre[cnt]);
        }


        ArrayAdapter<String> adapterEstados=new ArrayAdapter<>(CrearItem.this, android.R.layout.simple_dropdown_item_1line,estados);
        ArrayAdapter<String> adapterUbicaciones=new ArrayAdapter<>(CrearItem.this, android.R.layout.simple_dropdown_item_1line,ubicaciones);
        ArrayAdapter<String> adapterTipos=new ArrayAdapter<>(CrearItem.this, android.R.layout.simple_dropdown_item_1line,listanueva);

        ubicacionSpinner.setAdapter(adapterUbicaciones);
        tipoInspeccionspinner.setAdapter(adapterTipos);
        //cambio

                //Spinner dependiente fin//

        spinnerTipo();
        spinnerUbicaciones(ubicaciones);
        btnotraubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInflater= LayoutInflater.from(context);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Ubicación");
                View view = mInflater.inflate(R.layout.dialog_otro, null);
                TextInputEditText otro=view.findViewById(R.id.otro);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (otro.getText().toString().equals("")){
                            Toast.makeText(context, "No ha escrito nada", Toast.LENGTH_SHORT).show();

                        }else {
                           txtubicacion.setText(otro.getText().toString());
                            txtestante.setText("N/A");

                        }

                    }
                });
                builder.setView(view);
                builder.show();
            }
        });

    }




    void spinnerUbicaciones(List<String> ubicaciones){
        ubicacionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ubicacion = parent.getItemAtPosition(position).toString();
                txtubicacion.setText(ubicacion);

                //Spinner dependiente//
                ArrayList<String> estante = new ArrayList<>();
                ArrayList<String> bodegaLubricantes = new ArrayList<>();

                bodegaLubricantes.add("Aceite");
                bodegaLubricantes.add("Grasas");
                bodegaLubricantes.add("Refigerante");
                ArrayList<String> bodegaHerramientas = new ArrayList<>();
                bodegaHerramientas.add("1A");
                bodegaHerramientas.add("1B");
                bodegaHerramientas.add("1C");
                bodegaHerramientas.add("1D");
                bodegaHerramientas.add("1E");
                bodegaHerramientas.add("2A");
                bodegaHerramientas.add("2B");
                bodegaHerramientas.add("2C");
                bodegaHerramientas.add("2D");
                bodegaHerramientas.add("3A");
                bodegaHerramientas.add("3B");
                bodegaHerramientas.add("3C");
                bodegaHerramientas.add("3D");
                bodegaHerramientas.add("1P");
                bodegaHerramientas.add("2P");
                bodegaHerramientas.add("3P");
                bodegaHerramientas.add("4P");
                ArrayList<String> bodegaMateriales = new ArrayList<>();
                bodegaMateriales.add("N/A");
                ArrayList<String> bodega = new ArrayList<>();
                bodega.add("N/A");
                ArrayList<String> bodegaLlantas = new ArrayList<>();
                bodegaLlantas.add("N/A");
                if (position == 0) {
                    adapterestanteSpinner = new ArrayAdapter<>(CrearItem.this, android.R.layout.simple_dropdown_item_1line, bodegaHerramientas);
                    estanteSpinner.setSelection(bodegaHerramientas.indexOf(estanteSeleccionado));

                } else if (position == 1) {
                    adapterestanteSpinner = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, bodegaMateriales);
                    estanteSpinner.setSelection(bodegaMateriales.indexOf(estanteSeleccionado));
                } else if (position == 2) {
                    adapterestanteSpinner = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, bodegaLubricantes);
                    estanteSpinner.setSelection(bodegaLubricantes.indexOf(estanteSeleccionado));
                } else if (position == 3) {
                    adapterestanteSpinner = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, bodega);
                    estanteSpinner.setSelection(bodega.indexOf(estanteSeleccionado));
                } else if (position == 4) {
                    adapterestanteSpinner = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, bodegaLlantas);
                    estanteSpinner.setSelection(bodegaLlantas.indexOf(estanteSeleccionado));
                } else {
                    ArrayList<String> vacio = new ArrayList<>();
                    if (item == null) {
                        vacio.add(estanteSeleccionado);
                    } else {
                        txtestante.setText(item.getEstante());
                        txtubicacion.setText(item.getUbicacion());
                        estanteSeleccionado = item.getEstante();
                        vacio.add(estanteSeleccionado);
                    }
                    adapterestanteSpinner = new ArrayAdapter<>(CrearItem.this, android.R.layout.simple_dropdown_item_1line, vacio);
                    estanteSpinner.setSelection(vacio.indexOf(estanteSeleccionado));
                }
                estanteSpinner.setAdapter(adapterestanteSpinner);
                if(item==null){
                estanteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        estanteSeleccionado = parent.getItemAtPosition(position).toString();
                        txtestante.setText(estanteSeleccionado);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
                else{
                    estanteSeleccionado=item.getEstante();
                    txtestante.setText(estanteSeleccionado);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(ubicaciones.contains(ubicacion)){
        ubicacionSpinner.setSelection(ubicaciones.indexOf(ubicacion));}

        else{
            ubicacionSpinner.setSelection(ubicaciones.indexOf(""));
            txtubicacion.setText(ubicacion);
            txtestante.setText(item.getEstante());

        }
    }

    void spinnerTipo(){
        tipoInspeccionspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoInspeccion=  parent.getItemAtPosition(position).toString();
                txttipo.setText(tipoInspeccion);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tipoInspeccionspinner.setSelection(listanueva.indexOf(tipoInspeccion));
    }

    public void subirItem(View view){
        String codigocambio=codigoTxt.getText().toString();
        boolean cambio=codigocambio.contains("/");
        boolean cambio2=codigocambio.contains(".");
        boolean cambio3=codigocambio.contains("-");
        boolean net=!isOnlineNet();

        if(item!=null){
            cambio3=false;
            cambio2=false;
            cambio=false;
            net=false;
        }
        if(net){
            Toast.makeText(context, "No existe conexión a internet, busque un punto con acceso a internet o intentelo más tarde ", Toast.LENGTH_SHORT).show();
        }else{
            if(cambio || cambio2 || cambio3){
                codigoTxt.setText("");
                Toast.makeText(context, "No puede escribir codigos con '-','.' o '/'", Toast.LENGTH_SHORT).show();

            }else if(comprobarcodigo()){

                Toast.makeText(context, "Codigo ya existe en la base de datos", Toast.LENGTH_SHORT).show();

            } else {
                String descripcion=descripcionTxt.getText().toString();
                String marca=marcaTxt.getText().toString();
                String observacion=observacionTxt.getText().toString();
                int vidaUtil=vidaUtilTxt.getValue();
                if(!generarCodigo){
                    codigo=codigoTxt.getText().toString();
                    FirebaseDatabase database= FirebaseDatabase.getInstance();
                    refItem=database.getReference("Taller").child(("Items")).child(codigo);
                }
                String error="";
                if(codigo.equals("")){
                    error=error+" Codigo";
                }
                if(descripcion.equals("")){
                    error=error+" DESCRIPCION";
                }
                if(error.equals("")){


                    Item item= new Item(codigo,marca,descripcion,observacion,stock,stock,txtubicacion.getText().toString(),vidaUtil,txttipo.getText().toString(),txtestante.getText().toString());
                    refItem.setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(CrearItem.this, "Item creado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });

                    finish();
                }
                else{
                    Toast.makeText(this, "Falta completar:"+error, Toast.LENGTH_LONG).show();
                }

            }

        }





    }
    public boolean comprobarcodigo(){
        x=false;
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("Taller").child("Items");
        myRef.keepSynced(true);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds:snapshot.getChildren()){
                        String codigo= ds.child("codigo").getValue().toString();
                        if (codigoTxt.getText().toString().equals(codigo)){
                            x=true;
                        }
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return x;
    }

    public void editarDatos(Item item){
        titulotxt.setText("Editar item");
        estanteSeleccionado=item.getEstante();
        ubicacion=item.getUbicacion();
        codigo=item.getCodigo().toString();
        tipoInspeccion=item.getTipoInspeccion();
        descripcionTxt.setText(item.getDescripcion());
        codigoTxt.setText(item.getCodigo());
        codigoTxt.setKeyListener(null);

        marcaTxt.setText(item.getMarca());
        observacionTxt.setText(item.getObservacion());
        vidaUtilTxt.setValue(item.getVidaUtil());
        stock=item.getStock();
        txtubicacion.setText(item.getUbicacion());
        txtestante.setText(item.getEstante());
        txttipo.setText(item.getTipoInspeccion());


    }
    public Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}