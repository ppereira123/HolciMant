package com.example.mantenimientoholcim.Herramientas;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mantenimientoholcim.CrearItem;
import com.example.mantenimientoholcim.Modelo.InspeccionTipo1;
import com.example.mantenimientoholcim.PlantillasInspeccion;
import com.example.mantenimientoholcim.R;
import com.example.mantenimientoholcim.RevisionPuntosBloqueo;
import com.example.mantenimientoholcim.buscarInspeccionItem;
import com.google.android.gms.tasks.OnSuccessListener;
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


public class EscanerFragment extends DialogFragment {
    String etcodigo="";
    String etcodigo2="";
    Integer codigonumero=-1;
    DatabaseReference refItem;
    String tipo="";
    Button btnLeerCodigo,btndevolucion,btnprestamo;
    ImageButton  btninformacion, btnBuscarInspecciones,btnHacerInspecciones;
    ImageView imgcodigo;
    TextView codigoview;
    LinearLayout llBtnDev,llOpc;
    private  LayoutInflater mInflater;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static EscanerFragment newInstance() {
        EscanerFragment fragment = new EscanerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_escaner, container, false);
        btnLeerCodigo = root.findViewById(R.id.btnLeerCodigo);
        imgcodigo= root.findViewById(R.id.qr_img);
        codigoview= root.findViewById(R.id.txtcod);
        btninformacion= root.findViewById(R.id.btninformacion);
        btndevolucion= root.findViewById(R.id.btndevolucion);
        btnprestamo= root.findViewById(R.id.btnprestamo);
        btnBuscarInspecciones= root.findViewById(R.id.btnBuscarInspecciones);
        btnHacerInspecciones= root.findViewById(R.id.btnHacerInspeccion);
        llBtnDev=root.findViewById(R.id.llbtnDevuPres);
        llOpc=root.findViewById(R.id.llbtnopc);

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
                boolean valid= etcodigo2.equals("");
                if(valid==false){
                    FirebaseDatabase database= FirebaseDatabase.getInstance();
                    DatabaseReference myRef= database.getReference("Items");
                    myRef.child(etcodigo2).addValueEventListener(new ValueEventListener() {
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
        btnHacerInspecciones.setOnClickListener(new View.OnClickListener() {
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
                                tipo=snapshot.child("tipoInspeccion").getValue().toString();
                                String[] nombresdeinspeccion =getResources().getStringArray(R.array.combo_inspeccionesNombre);
                                int position=posicionEnArreglo(nombresdeinspeccion,tipo);
                                if (position!=-1){
                                    if (position==41){
                                        Intent intent= new Intent(root.getContext(), RevisionPuntosBloqueo.class);
                                        startActivity(intent);

                                    }
                                    else {
                                        Intent intent= new Intent(root.getContext(), PlantillasInspeccion.class);
                                        intent.putExtra("posicion",position);
                                        intent.putExtra("codigo", etcodigo);
                                        startActivity(intent);
                                    }

                                }else{
                                    Toast.makeText(getContext(), "No existe formato para este tipo de inspección", Toast.LENGTH_SHORT).show(); }
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
        btnBuscarInspecciones.setOnClickListener(new View.OnClickListener() {
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
                                tipo=snapshot.child("tipoInspeccion").getValue().toString();
                                String[] nombresdeinspeccion =getResources().getStringArray(R.array.combo_inspeccionesNombre);
                                int position=posicionEnArreglo(nombresdeinspeccion,tipo);
                                if (position!=-1){
                                    Intent intent= new Intent(root.getContext(), buscarInspeccionItem.class);
                                    intent.putExtra("codigo", etcodigo);
                                    startActivity(intent);


                                }else{
                                    Toast.makeText(getContext(), "No existe inspecciones para este articulo", Toast.LENGTH_SHORT).show(); }
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
                                               boolean valid= etcodigo2.equals("");
                                               if(valid==false){
                                                   FirebaseDatabase database= FirebaseDatabase.getInstance();
                                                   DatabaseReference myRef= database.getReference("Items");
                                                   DatabaseReference ref1=database.getReference("Prestamos").child(etcodigo2);
                                                   ref1.keepSynced(true);
                                                   DatabaseReference ref2=ref1.push();
                                                   ref2.keepSynced(true);

                                                   myRef.child(etcodigo2).addValueEventListener(new ValueEventListener() {
                                                       @Override
                                                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                           if(snapshot.exists()){

                                                               refItem=myRef.child(etcodigo2).child("stockDisponible");

                                                               AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                                                               builder2.setTitle("Prestamo de herramienta");
                                                               builder2.setMessage("Esta seguro que desea prestar la herramienta con codigo:" + etcodigo)
                                                                       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                           @Override
                                                                           public void onClick(DialogInterface dialog, int which) {


                                                                               refItem.setValue(Integer.parseInt(snapshot.child("stockDisponible").getValue().toString())-1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                   @Override
                                                                                   public void onSuccess(Void aVoid) {
                                                                                       Toast.makeText(getContext(), "Ha prestado la herramienta: " + etcodigo, Toast.LENGTH_SHORT).show();
                                                                                   }
                                                                               });




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
        IntentIntegrator intent= IntentIntegrator.forSupportFragment(EscanerFragment.this);
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
                etcodigo2=etcodigo.split("-")[0];
                codigoview.setText(etcodigo);
                getCode();
                llOpc.setVisibility(View.VISIBLE);
                llBtnDev.setVisibility(View.VISIBLE);

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
    public static int posicionEnArreglo(String[] arreglo, String busqueda) {
        for (int x = 0; x < arreglo.length; x++) {
            if (arreglo[x].equals(busqueda)) {
                return x;
            }
        }
        return -1;
    }

}