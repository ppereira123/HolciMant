package com.example.mantenimientoholcim;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CrearItem extends AppCompatActivity {
    EditText descripcionTxt,marcaTxt,observacionTxt,codigoTxt;
    Spinner estadoActualSpinner,ubicacionSpinner,tipoInspeccionspinner;
    NumberPicker vidaUtilTxt;
    private String estado="",ubicacion="",tipoInspeccion="";
    boolean generarCodigo=false;
    DatabaseReference refItem;
    String codigo="";
    Item item=null;
    Button btnGenerar;

    public CrearItem(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_item);
        descripcionTxt=findViewById(R.id.descripciontxt);
        marcaTxt=findViewById(R.id.marcatxt);
        btnGenerar=findViewById(R.id.btnGenerar);
        observacionTxt=findViewById(R.id.observaciontxt);
        vidaUtilTxt=findViewById(R.id.vidaUtilNp);
        estadoActualSpinner=findViewById(R.id.estadoActualSpinner);
        ubicacionSpinner=findViewById(R.id.spinnerUbicacion);
        tipoInspeccionspinner=findViewById(R.id.spinnerInspeccion);
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
        List<String> estados= new ArrayList<>();
        List<String> ubicaciones= new ArrayList<>();
        List<String> tipos= new ArrayList<>();
        estados.add("");
        ubicaciones.add(0,"Bodega de Materiales");
        ubicaciones.add(1,"Bodega de Herramientas");
        tipos.add(0,"N/A");
        tipos.add(1,"Trimestral");
        tipos.add(2,"Quinquenal");
        tipos.add(3,"Mensual");
        tipos.add(3,"Anual");
        estados.add(0,"N/A");
        estados.add(1,"En uso");
        estados.add(2,"Repuesto");
        ArrayAdapter<String> adapterEstados=new ArrayAdapter<>(CrearItem.this, android.R.layout.simple_dropdown_item_1line,estados);
        ArrayAdapter<String> adapterUbicaciones=new ArrayAdapter<>(CrearItem.this, android.R.layout.simple_dropdown_item_1line,ubicaciones);
        ArrayAdapter<String> adapterTipos=new ArrayAdapter<>(CrearItem.this, android.R.layout.simple_dropdown_item_1line,tipos);
        estadoActualSpinner.setAdapter(adapterEstados);
        ubicacionSpinner.setAdapter(adapterUbicaciones);
        tipoInspeccionspinner.setAdapter(adapterTipos);
        spinnerEstado();
        spinnerTipo();
        spinnerUbicaciones();

    }

    public void generarCodigo(View view){
        generarCodigo=true;
        codigoTxt.setEnabled(false);
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference ref= database.getReference("Items");
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
            Item item= new Item(codigo,marca,descripcion,observacion,1,estado,ubicacion,vidaUtil,tipoInspeccion);
            refItem.setValue(item);
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
    }
}