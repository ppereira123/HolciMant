package com.example.mantenimientoholcim;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mantenimientoholcim.Modelo.InspeccionTipo1;
import com.example.mantenimientoholcim.Modelo.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.List;


public class tab1Home extends DialogFragment {
    String etcodigo="";
    Button btnLeerCodigo, btninformacion,btndevolucion,btnprestamo;
    ImageView imgcodigo;
    TextView codigoview;
    private  LayoutInflater mInflater;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_tab1_home, container, false);
        btnLeerCodigo = root.findViewById(R.id.btnLeerCodigo);
        imgcodigo= root.findViewById(R.id.qr_img);
        codigoview= root.findViewById(R.id.txtcod);
        btninformacion= root.findViewById(R.id.btninformacion);
        btndevolucion= root.findViewById(R.id.btndevolucion);
        btnprestamo= root.findViewById(R.id.btnprestamo);
        mInflater=LayoutInflater.from(getContext());


        btnLeerCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escanear();

            }
        });
        btninformacion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean valid= etcodigo.equals("");
                if(valid==false){
                    FirebaseDatabase database= FirebaseDatabase.getInstance();
                    DatabaseReference myRef= database.getReference("Items");
                    myRef.child(etcodigo).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                View view = mInflater.inflate(R.layout.dialogoinformacion, null);
                                TextView descripciontv,marcatv,tipotxt,ubicaciontv,estantetv,vidautiltv,observaciontv;
                                ImageButton imgbtnSalir;
                                imgbtnSalir= view.findViewById(R.id.imgbtnSalir);
                                descripciontv= view.findViewById(R.id.descripciontv);
                                marcatv= view.findViewById(R.id.marcatv);
                                tipotxt= view.findViewById(R.id.tipotxt);
                                ubicaciontv= view.findViewById(R.id.ubicaciontv);
                                estantetv= view.findViewById(R.id.estantetv);
                                vidautiltv= view.findViewById(R.id.vidautiltv);
                                observaciontv= view.findViewById(R.id.observaciontv);
                                descripciontv.setText(snapshot.child("descripcion").getValue().toString());
                                marcatv.setText(snapshot.child("marca").getValue().toString());
                                tipotxt.setText(snapshot.child("tipoInspeccion").getValue().toString());
                                ubicaciontv.setText(snapshot.child("ubicacion").getValue().toString());
                                estantetv.setText("A1");
                                vidautiltv.setText(snapshot.child("vidaUtil").getValue().toString());
                                observaciontv.setText(snapshot.child("observacion").getValue().toString());
                                builder.setView(view);
                                AlertDialog alert = builder.create();
                                alert.show();
                                imgbtnSalir.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alert.dismiss();

                                    }
                                });



                            }else{
                                Toast.makeText(getContext(), "No existe ese codigo de articulo en la base de datos", Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });






                }else{
                    Toast.makeText(getContext(), "Debe escanear un codigo QR primero", Toast.LENGTH_SHORT).show();
                }


            }





        });
        btnprestamo.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               boolean valid= etcodigo.equals("");
                                               if(valid==false){
                                                   FirebaseDatabase database= FirebaseDatabase.getInstance();
                                                   DatabaseReference myRef= database.getReference("Items");
                                                   myRef.child(etcodigo).addValueEventListener(new ValueEventListener() {
                                                       @Override
                                                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                           if(snapshot.exists()){
                                                               AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                                                               builder2.setTitle("Prestamo de herramienta");
                                                               builder2.setMessage("Esta seguro que desea prestar la herramienta con codigo:" + etcodigo)
                                                                       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                           @Override
                                                                           public void onClick(DialogInterface dialog, int which) {
                                                                               Toast.makeText(getContext(), "Ha prestado la herramienta: " + etcodigo, Toast.LENGTH_SHORT).show();
                                                                           }

                                                                       })
                                                                       .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                                           @Override
                                                                           public void onClick(DialogInterface dialog, int which) {
                                                                               dialog.dismiss();
                                                                           }

                                                                       }).show();

                                                           }else{
                                                               Toast.makeText(getContext(), "No existe ese codigo de articulo en la base de datos", Toast.LENGTH_SHORT).show();


                                                           }

                                                       }

                                                       @Override
                                                       public void onCancelled(@NonNull DatabaseError error) {

                                                       }
                                                   });



                                               }else{
                                                   Toast.makeText(getContext(), "Debe escanear un codigo QR primero", Toast.LENGTH_SHORT).show();
                                               }


                                           }
                                           
                                       });
        btndevolucion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid= etcodigo.equals("");
                if(valid==false){
                    FirebaseDatabase database= FirebaseDatabase.getInstance();
                    DatabaseReference myRef= database.getReference("Items");
                    myRef.child(etcodigo).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                                builder2.setTitle("Devolución de herramienta");
                                builder2.setMessage("Esta seguro que desea devolver la herramienta con codigo:" + etcodigo)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(getContext(), "Ha devuelto la herramienta: " + etcodigo, Toast.LENGTH_SHORT).show();
                                            }

                                        })
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }

                                        }).show();

                            }else{
                                Toast.makeText(getContext(), "No existe ese codigo de articulo en la base de datos", Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }else{
                    Toast.makeText(getContext(), "Debe escanear un codigo QR primero", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return root;
    }
    public void escanear(){
        IntentIntegrator intent= IntentIntegrator.forSupportFragment(tab1Home.this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intent.setPrompt("ESCANEAR CODIGO");
        intent.setCameraId(0);
        intent.setBeepEnabled(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() == null){
                Toast.makeText(getContext(), "Cancelaste el escaneo", Toast.LENGTH_SHORT).show();
            }else  {
                etcodigo=result.getContents();
                codigoview.setText(etcodigo);
                getCode();

            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);

        }


    }
    private void getCode(){
        try {
            imaggeneretor();

        }
        catch(Exception e){
            e.printStackTrace();

        }
    }
    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

    public void imaggeneretor() throws WriterException {
        BitMatrix bitMatrix= multiFormatWriter.encode(etcodigo, BarcodeFormat.QR_CODE,600,600);
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
        imgcodigo.setImageBitmap(bitmap);

    }

}