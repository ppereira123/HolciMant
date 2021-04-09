package com.example.mantenimientoholcim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mantenimientoholcim.Modelo.Item;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CrearItem extends AppCompatActivity {
    EditText descripcionTxt,marcaTxt,observacionTxt,codigoTxt;
    Spinner estadoActualSpinner,ubicacionSpinner,tipoInspeccionspinner, estanteSpinner;
    NumberPicker vidaUtilTxt;
    private String estado="",ubicacion="",tipoInspeccion="";
    boolean generarCodigo=false;
    DatabaseReference refItem;
    String codigo="";
    Item item=null;
    Button btnGenerar;
    int stock=1;
    ArrayAdapter<String> adapterestanteSpinner;
    String estanteSeleccionado="";

    public CrearItem(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_item);
        descripcionTxt=findViewById(R.id.descripciontxt);
        marcaTxt=findViewById(R.id.marcatxt);
        btnGenerar=findViewById(R.id.btnGenerar);
        observacionTxt=findViewById(R.id.observacion);
        vidaUtilTxt=findViewById(R.id.vidaUtilNp);
        estadoActualSpinner=findViewById(R.id.estadoActualSpinner);
        ubicacionSpinner=findViewById(R.id.spinnerUbicacion);
        tipoInspeccionspinner=findViewById(R.id.spinnerInspeccion);
        estanteSpinner=findViewById(R.id.spinnerEstante);
        codigoTxt=findViewById(R.id.codigotxt);
        vidaUtilTxt.setMinValue(0);
        vidaUtilTxt.setMaxValue(50);
        cargarSpinners();
        item=(Item)getIntent().getSerializableExtra("item");
        if(item!=null){
            editarDatos(item);
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

        estados.add(0,"N/A");
        estados.add(1,"En uso");
        estados.add(2,"Repuesto");
        String[] listanombre=getResources().getStringArray(R.array.combo_inspeccionesNombre);
        ArrayList<String> listanueva = new ArrayList<String>();
        listanueva.add("N/A");
        for(int cnt=0;cnt<listanombre.length;cnt++)
        {
            listanueva.add(listanombre[cnt]);
        }


        ArrayAdapter<String> adapterEstados=new ArrayAdapter<>(CrearItem.this, android.R.layout.simple_dropdown_item_1line,estados);
        ArrayAdapter<String> adapterUbicaciones=new ArrayAdapter<>(CrearItem.this, android.R.layout.simple_dropdown_item_1line,ubicaciones);
        ArrayAdapter<String> adapterTipos=new ArrayAdapter<>(CrearItem.this, android.R.layout.simple_dropdown_item_1line,listanueva);
        estadoActualSpinner.setAdapter(adapterEstados);
        ubicacionSpinner.setAdapter(adapterUbicaciones);
        tipoInspeccionspinner.setAdapter(adapterTipos);
        //cambio

                //Spinner dependiente fin//
        spinnerEstado();
        spinnerTipo();
        spinnerUbicaciones();

    }

    public void generarCodigo(View view){
        generarCodigo=true;
        codigoTxt.setEnabled(false);
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference ref= database.getReference("Items");
        ref.keepSynced(true);
        refItem=ref.push();
        codigo=refItem.getKey();
        codigoTxt.setText(codigo);
    }

    void spinnerEstado(){
        estadoActualSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                estado=  parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void spinnerUbicaciones(){
        ubicacionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ubicacion=  parent.getItemAtPosition(position).toString();
                //Spinner dependiente//
                ArrayList<String> estante= new ArrayList<>();
                ArrayList<String> bodegaLubricantes= new ArrayList<>();

                bodegaLubricantes.add("Aceite");
                bodegaLubricantes.add("Grasas");
                bodegaLubricantes.add("Refigerante");
                ArrayList<String> bodegaHerramientas= new ArrayList<>();
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
                ArrayList<String> bodegaMateriales= new ArrayList<>();
                bodegaMateriales.add("N/A");
                ArrayList<String> bodega= new ArrayList<>();
                bodega.add("N/A");
                ArrayList<String> bodegaLlantas= new ArrayList<>();
                bodegaLlantas.add("N/A");
                if(position==0){
                    adapterestanteSpinner=new ArrayAdapter<>(CrearItem.this, android.R.layout.simple_dropdown_item_1line,bodegaHerramientas);

                }
                else if(position==1){
                    adapterestanteSpinner= new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, bodegaMateriales);
                }
                else if(position==2){
                    adapterestanteSpinner= new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, bodegaLubricantes);
                }
                else if(position==3){
                    adapterestanteSpinner= new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, bodega);
                }
                else if(position==4){
                    adapterestanteSpinner= new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, bodegaLlantas);
                }
                else
                    adapterestanteSpinner=new ArrayAdapter<>(CrearItem.this, android.R.layout.simple_dropdown_item_1line,bodegaHerramientas);
                estanteSpinner.setAdapter(adapterestanteSpinner);
                estanteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        estanteSeleccionado=parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void spinnerTipo(){
        tipoInspeccionspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoInspeccion=  parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void subirItem(View view){
        String descripcion=descripcionTxt.getText().toString();
        String marca=marcaTxt.getText().toString();
        String observacion=observacionTxt.getText().toString();
       int vidaUtil=vidaUtilTxt.getValue();
        if(!generarCodigo){
            codigo=codigoTxt.getText().toString();
            FirebaseDatabase database= FirebaseDatabase.getInstance();
            refItem=database.getReference("Items").child(codigo);
        }
        String error="";
        if(codigo.equals("")){
            error=error+" Codigo";
        }
        if(descripcion.equals("")){
            error=error+" DESCRIPCION";
        }
        if(error.equals("")){


                Item item= new Item(codigo,marca,descripcion,observacion,stock,stock,ubicacion,vidaUtil,tipoInspeccion,estanteSeleccionado);
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

    public void editarDatos(Item item){
        descripcionTxt.setText(item.getDescripcion());
        codigoTxt.setText(item.getCodigo());
        codigoTxt.setEnabled(false);
        btnGenerar.setEnabled(false);
        marcaTxt.setText(item.getMarca());
        observacionTxt.setText(item.getObservacion());
        vidaUtilTxt.setValue(item.getVidaUtil());
        stock=item.getStock();


    }
}