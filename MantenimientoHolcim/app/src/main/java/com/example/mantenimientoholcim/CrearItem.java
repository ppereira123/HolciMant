package com.example.mantenimientoholcim;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.mantenimientoholcim.Signin.LoginFireBase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CrearItem extends AppCompatActivity {
    EditText descripcionTxt,marcaTxt,observacionTxt,codigoTxt;
    TextView txtubicacion,txtestante,txttipo;
    Spinner ubicacionSpinner,tipoInspeccionspinner, estanteSpinner;
    NumberPicker vidaUtilTxt;
    private String ubicacion="",tipoInspeccion="";
    boolean generarCodigo=false;
    DatabaseReference refItem;
    String codigo="";
    Item item=null;
    Button btnGenerar, btnsubirItem, btnotraubicacion;
    int stock=1;
    ArrayAdapter<String> adapterestanteSpinner;
    String estanteSeleccionado="";
    Context context=this;

    AlertDialog alert;
    LayoutInflater mInflater;

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

        ubicacionSpinner=findViewById(R.id.spinnerUbicacion);
        tipoInspeccionspinner=findViewById(R.id.spinnerInspeccion);
        estanteSpinner=findViewById(R.id.spinnerEstante);
        btnsubirItem=findViewById(R.id.subirItem);
        btnotraubicacion=findViewById(R.id.btnotraubicacion);
        txtubicacion=findViewById(R.id.seleccionado);
        txtestante=findViewById(R.id.seleccionadoestante);

        txttipo=findViewById(R.id.seleccionadotipoinspeccion);
        codigoTxt=findViewById(R.id.codigotxt);
        vidaUtilTxt.setMinValue(0);
        vidaUtilTxt.setMaxValue(50);
        cargarSpinners();
        txtubicacion.setText(ubicacion);
        txtestante.setText(estanteSeleccionado);

        txttipo.setText(tipoInspeccion);
        item=(Item)getIntent().getSerializableExtra("item");
        if(item!=null){
            editarDatos(item);
        }
        InternalStorage storage=new InternalStorage();
        String archivos[]=context.fileList();
        if (storage.ArchivoExiste(archivos,"admin.txt")){
            UsersData data= storage.cargarArchivo(context);
            if (data.isAdmin()==false){

                btnsubirItem.setVisibility(View.GONE);
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

        ubicacionSpinner.setAdapter(adapterUbicaciones);
        tipoInspeccionspinner.setAdapter(adapterTipos);
        //cambio

                //Spinner dependiente fin//

        spinnerTipo();
        spinnerUbicaciones();
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



    void spinnerUbicaciones(){
        ubicacionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ubicacion=  parent.getItemAtPosition(position).toString();
                txtubicacion.setText(ubicacion);

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
                        txtestante.setText(estanteSeleccionado);

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
                txttipo.setText(tipoInspeccion);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void subirItem(View view){
        String codigocambio=codigoTxt.getText().toString();
        boolean cambio=codigocambio.contains("/");
        boolean cambio2=codigocambio.contains(".");
        if(cambio==true||cambio2==true){
            codigoTxt.setText("");
            Toast.makeText(context, "No puede escribir codigos con '.' o '/'", Toast.LENGTH_SHORT).show();
        }
        else {
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

    public void editarDatos(Item item){
        descripcionTxt.setText(item.getDescripcion());
        codigoTxt.setText(item.getCodigo());
        codigoTxt.setEnabled(false);
        btnGenerar.setEnabled(false);
        marcaTxt.setText(item.getMarca());
        observacionTxt.setText(item.getObservacion());
        vidaUtilTxt.setValue(item.getVidaUtil());
        stock=item.getStock();
        txtubicacion.setText(item.getUbicacion());
        txtestante.setText(item.getEstante());
        txttipo.setText(item.getTipoInspeccion());


    }
}