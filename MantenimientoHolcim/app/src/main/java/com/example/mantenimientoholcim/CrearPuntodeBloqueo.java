package com.example.mantenimientoholcim;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.mantenimientoholcim.Modelo.PuntoBloqueo;
import com.example.mantenimientoholcim.Modelo.RevisionPuntoBloqueo;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;

public class CrearPuntodeBloqueo extends AppCompatActivity {

    EditText hac,ubicacion, observacion;
    Button ingresarFoto;
    Spinner spinnertipodeEnergia;
    String tipodeEnergia;
    CheckBox checkBox1, checkBox2,checkBox11,checkBox21;
    String checkString1;
    String checkString12;
    ImageView foto;
    String rutaImagen;
    String aleatoria="";
    List<PuntoBloqueo> puntos;
    RevisionPuntoBloqueo rpb;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crear_puntode_bloqueo);
        hac= findViewById(R.id.hac);
        ubicacion= findViewById(R.id.ubicacion);
        observacion= findViewById(R.id.observacion);
        spinnertipodeEnergia= findViewById(R.id.tipoenergia);
        ingresarFoto= findViewById(R.id.btningresarfoto);
        checkBox1= findViewById(R.id.checkBox1);
        checkBox2= findViewById(R.id.checkBox2);
        checkBox11= findViewById(R.id.checkBox11);
        checkBox21= findViewById(R.id.checkBox21);
        foto = findViewById(R.id.foto);
        rpb=(RevisionPuntoBloqueo)getIntent().getSerializableExtra("Revision") ;
        puntos=rpb.getPuntos();
        ingresarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCamara();
            }
        });
        cargarSpinners();
        cargarchecks();
        setAleatoria();
    }

    private void abrirCamara(){
        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager())!=null){

            File imagenArchivo = null;
            try{
                imagenArchivo= crearImagen();
            } catch(IOException ex){
                Log.e("Erorr", ex.toString());

            }
            if(imagenArchivo != null){
                Uri fotoUri = FileProvider.getUriForFile(this, "com.example.mantenimientoholcim", imagenArchivo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,fotoUri);
                startActivityForResult(intent, 1);
            }


        }
    }
    private void setAleatoria(){

        int p=(int) (Math.random() * 25 +1); int s=(int) (Math.random() * 25 +1);
        int t=(int) (Math.random() * 25 +1); int c=(int) (Math.random() * 25 +1);
        int numero1 = (int) (Math.random()*1012+211);
        int numero2 = (int) (Math.random()*1012+211);
        String[] elementos = {"q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x",
                "c","v","b","n","m"};
        aleatoria = elementos[p] + elementos[s]+
                numero1 + elementos[t] + elementos[c]+ numero2 + "comprimido.jpg";

    }

    private File crearImagen() throws IOException{
        String nombreImagen = aleatoria;
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen, ".jpg", directorio);

        rutaImagen = imagen.getAbsolutePath();
        return  imagen;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== 1&& resultCode==RESULT_OK){
            //Bundle extras= data.getExtras();
            Bitmap imgBitmap= BitmapFactory.decodeFile(rutaImagen);//aqui esta leyendo
            foto.setImageBitmap(imgBitmap);
        }
    }

    void cargarSpinners(){
        Resources res = getResources();
        String[] list_tiposdeenergia = res.getStringArray(R.array.combo_tipos_de_energias);
        spinnertipodeEnergia.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list_tiposdeenergia));

        spinnertipodeEnergia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipodeEnergia=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

     public void guardar(View view){
        String hactxt=hac.getText().toString();
        String ubicaciontxt=ubicacion.getText().toString();
        String observaciontxt=observacion.getText().toString();
        cargarchecks();
        cargarSpinners();
        PuntoBloqueo pb=new PuntoBloqueo(hactxt,ubicaciontxt,rutaImagen,tipodeEnergia,observaciontxt,checkString1,checkString12);
        puntos.add(pb);
        rpb.setPuntos(puntos);
        Intent intent= new Intent(context,RevisionPuntosBloqueo.class);
        intent.putExtra("Revision",rpb);
        startActivity(intent);
        finish();


    }
    void cargarchecks(){

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox1.isChecked()==true){
                    checkBox2.setChecked(false);
                    checkString1="cumple";
                }
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox2.isChecked()==true){
                    checkBox1.setChecked(false);
                    checkString1="no cumple";
                }
            }
        });
        checkBox11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox11.isChecked()==true){
                    checkBox21.setChecked(false);
                    checkString12="cumple";
                }
            }
        });
        checkBox21.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox11.isChecked()==true){
                    checkBox11.setChecked(false);
                    checkString12="no cumple";
                }
            }
        });



    }
}