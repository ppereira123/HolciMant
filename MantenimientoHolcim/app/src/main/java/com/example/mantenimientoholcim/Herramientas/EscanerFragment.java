package com.example.mantenimientoholcim.Herramientas;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mantenimientoholcim.CrearItem;
import com.example.mantenimientoholcim.Modelo.HistorialPrestamo;
import com.example.mantenimientoholcim.Modelo.InspeccionTipo1;
import com.example.mantenimientoholcim.Modelo.Prestamo;
import com.example.mantenimientoholcim.PlantillasInspeccion;
import com.example.mantenimientoholcim.R;
import com.example.mantenimientoholcim.RevisionPuntosBloqueo;
import com.example.mantenimientoholcim.VistaHistorialPrestamos;
import com.example.mantenimientoholcim.buscarInspeccionItem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class EscanerFragment extends DialogFragment {
    String etcodigo="";
    String codigoDiv="";
    Integer codigonumero=-1;
    DatabaseReference refItem;
    String tipo="";
    Button btnLeerCodigo,btndevolucion,btnprestamo,btnHistorialPrestamos;
    ImageButton  btninformacion, btnBuscarInspecciones,btnHacerInspecciones;
    ImageView imgcodigo;
    TextView codigoview;
    LinearLayout llBtnDev,llOpc;
    String nombre="";
    private  LayoutInflater mInflater;
    Prestamo prestamo=null;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    List<HistorialPrestamo> historial;
    String fechaDevolucion;
    View root;


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

         root = inflater.inflate(R.layout.fragment_escaner, container, false);
        btnLeerCodigo = root.findViewById(R.id.btnLeerCodigo);
        imgcodigo= root.findViewById(R.id.qr_img);
        codigoview= root.findViewById(R.id.txtcod);
        btninformacion= root.findViewById(R.id.btninformacion);
        btndevolucion= root.findViewById(R.id.btndevolucion);
        btnprestamo= root.findViewById(R.id.btnprestamo);
        btnBuscarInspecciones= root.findViewById(R.id.btnBuscarInspecciones);
        btnHacerInspecciones= root.findViewById(R.id.btnHacerInspeccion);
        btnHistorialPrestamos= root.findViewById(R.id.btnHistorialprestamo);
        llBtnDev=root.findViewById(R.id.llbtnDevuPres);
        llOpc=root.findViewById(R.id.llbtnopc);

        SimpleDateFormat format= new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar=Calendar.getInstance();
        Date hoy=calendar.getTime();
        fechaDevolucion=format.format(hoy);

        mInflater=LayoutInflater.from(getContext());



        btnLeerCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                nombre=currentUser.getDisplayName();
                escanear();


            }
        });
        btninformacion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    FirebaseDatabase database= FirebaseDatabase.getInstance();
                    DatabaseReference myRef= database.getReference("Items");
                    myRef.child(codigoDiv).addValueEventListener(new ValueEventListener() {
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
            }
        });
        btnHacerInspecciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    FirebaseDatabase database= FirebaseDatabase.getInstance();
                    DatabaseReference myRef= database.getReference("Items");
                    myRef.child(codigoDiv).addValueEventListener(new ValueEventListener() {
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







            }
        });
        btnBuscarInspecciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    FirebaseDatabase database= FirebaseDatabase.getInstance();
                    DatabaseReference myRef= database.getReference("Items");
                    myRef.child(codigoDiv).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            obtenerPrestamo();
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











            }
        });

        btnHistorialPrestamos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(root.getContext(), VistaHistorialPrestamos.class);
                intent.putExtra("codigo", etcodigo);
                startActivity(intent);

            }
        });

        btnprestamo.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               prestar();
                                           }
                                           
                                       });
        btndevolucion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                builder2.setTitle("Devolucion de herramienta");
                builder2.setMessage("Esta seguro que desea devolver la herramienta con codigo:" + etcodigo)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                obtenerPrestamo();
                                historial = prestamo.getHistorial();
                                HistorialPrestamo historialPrestamo;
                                int tamano = historial.size() - 1;
                                //Veo si ya se ha creado una lista de historial, si no se ha creado
                                if (tamano < 0) {
                                    Snackbar snackbar = Snackbar.make(root, "Item no ha sido\nDesearias prestarlo?", BaseTransientBottomBar.LENGTH_LONG);
                                    snackbar.setAction("Prestar", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            prestar();
                                        }
                                    });
                                    snackbar.show();
                                }
                                //Si no se creo , se crea una nueva lista
                                else {
                                    historialPrestamo = historial.get(historial.size() - 1);
                                //Veo si ya esta prestado elitem
                                if (prestamo.getEstado().equals("Prestado")) {
                                    //Si sigue prestado por la misma persona, se develve sin problema
                                    if (!historialPrestamo.getNombre().equals(nombre)) {
                                        Snackbar snackbar = Snackbar.make(root, "Item devuelto por usuario distinto al que lo presto", BaseTransientBottomBar.LENGTH_LONG);
                                        snackbar.show();
                                        historialPrestamo.setFechaDevolucion(fechaDevolucion);
                                        historialPrestamo.setEstadoPrestamo("No devuelto");
                                        //Si la lista tiene menos de 10 items, se agrega sin problema
                                        historial.remove(historial.size() - 1);
                                        historial.add(historial.size() , historialPrestamo);
                                        //Se setea la lista nueva de historial se pone de estado Prestado y se sube
                                        prestamo.setHistorial(historial);
                                        prestamo.setEstado("Devuelto");
                                        subirPrestamo(prestamo,false);
                                    }
                                    //Si otra persona no lo ha devuelto, se escribe no devuelto y se crea un nuevo historial
                                    else {
                                        historialPrestamo.setFechaDevolucion(fechaDevolucion);
                                        historialPrestamo.setEstadoPrestamo("Devuelto");
                                        //Si la lista tiene menos de 10 items, se agrega sin problema
                                            historial.remove(historial.size()-1);
                                            historial.add(historialPrestamo);

                                        //Se setea la lista nueva de historial se pone de estado Prestado y se sube
                                        prestamo.setHistorial(historial);
                                        prestamo.setEstado("Devuelto");
                                        subirPrestamo(prestamo,false);

                                    }
                                }

                                //Si el item no tiene el estado prestado, se presta sin problema
                                else {
                                    Snackbar snackbar = Snackbar.make(root, "Item ya devuelto\nDesearias ?", BaseTransientBottomBar.LENGTH_LONG);
                                    snackbar.setAction("Prestar", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            prestar();
                                        }
                                    });
                                    snackbar.show();

                                }
                            }




                            }

                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }

                        }).show();


            }
        });

        return root;
    }

    private void prestar() {

        AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
        builder2.setTitle("Prestamo de herramienta");
        builder2.setMessage("Esta seguro que desea prestar la herramienta con codigo:" + etcodigo)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        historial= prestamo.getHistorial();
                        HistorialPrestamo historialPrestamo;
                        int tamano=historial.size()-1;
                        //Veo si ya se ha creado una lista de historial
                        if(tamano<0){
                            historialPrestamo=new HistorialPrestamo(nombre,fechaDevolucion,"","");
                            prestamo.setEstado("");
                        }
                        //Si no se creo , se crea una nueva lista
                        else{
                            historialPrestamo= historial.get(historial.size()-1);}

                        //Veo si ya esta prestado elitem
                        if(prestamo.getEstado().equals("Prestado")){
                            //Si sigue prestado por la misma persona, pide que lo devuelva primero
                            if(historialPrestamo.getNombre().equals(nombre)){
                                dialog.dismiss();
                                Snackbar snackbar= Snackbar.make(root,"Devuelve el item antes de volverlo a prestar",BaseTransientBottomBar.LENGTH_LONG);
                                snackbar.show();
                            }
                            //Si otra persona no lo ha devuelto, se escribe no devuelto y se crea un nuevo historial
                            else{
                                historialPrestamo.setFechaDevolucion(fechaDevolucion);
                                historialPrestamo.setEstadoPrestamo("No devuelto");
                                historial.remove(historial.size()-1);
                                historial.add(historial.size(),historialPrestamo);
                                HistorialPrestamo historialPrestamo1= new HistorialPrestamo(nombre,fechaDevolucion,"","Prestado");
                                //Si la lista tiene menos de 10 items, se agrega sin problema
                                if(historial.size()<10){
                                    historial.add(historialPrestamo1);
                                    prestamo.setHistorial(historial);
                                    prestamo.setEstado("Prestado");
                                    subirPrestamo(prestamo,true);
                                }
                                //Si la lista ya tiene 10 items, se borra el primero y se agrega al final
                                else{
                                    historial.add(historialPrestamo1);
                                    List<HistorialPrestamo> historialNuevo=new ArrayList<>();
                                    for(int i=1;i<historial.size();i++){
                                        historialNuevo.add(historial.get(i));
                                    }
                                    prestamo.setHistorial(historialNuevo);
                                    prestamo.setEstado("Prestado");
                                    subirPrestamo(prestamo,true);
                                }
                                //Se setea la lista nueva de historial se pone de estado Prestado y se sube


                            }
                        }

                        //Si el item no tiene el estado prestado, se presta sin problema
                        else{
                            HistorialPrestamo historialPrestamo1= new HistorialPrestamo(nombre,fechaDevolucion,"","Prestado");
                            //Si la lista tiene menos de 10 items, se agrega sin problema
                            if(historial.size()<10){
                                historial.add(historialPrestamo1);
                                prestamo.setHistorial(historial);
                                prestamo.setEstado("Prestado");
                                subirPrestamo(prestamo,true);
                            }
                            //Si la lista ya tiene 10 items, se borra el primero y se agrega al final
                            else{
                                historial.add(historialPrestamo1);
                                prestamo.setHistorial(historial);
                                subirPrestamo(prestamo,true);
                                List<HistorialPrestamo> historialNuevo=new ArrayList<>();
                                for(int i=1;i<historial.size();i++){
                                    historialNuevo.add(historial.get(i));
                                }
                                prestamo.setHistorial(historialNuevo);
                                prestamo.setEstado("Prestado");
                                subirPrestamo(prestamo,true);
                            }
                            //Se setea la lista nueva de historial se pone de estado Prestado y se sube


                        }




                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                }).show();

    }

    public void subirPrestamo(Prestamo prestamo,boolean prestado){

            DatabaseReference refPrestamos=database.getReference("Prestamos").child(etcodigo);
            refPrestamos.setValue(prestamo).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    DatabaseReference ref= database.getReference("Items").child(codigoDiv).child("stockDisponible");
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int stock=Integer.parseInt(snapshot.getValue().toString());
                            if(prestado){
                                stock--;
                            }
                            else{
                                stock++;
                            }
                            ref.setValue(stock).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Snackbar snackbar= Snackbar.make(root,"Subido Correctamente",BaseTransientBottomBar.LENGTH_LONG);
                                    snackbar.show();
                                    Animation animation= AnimationUtils.loadAnimation(root.getContext() ,R.anim.desplaza_arriba);
                                    imgcodigo.setAnimation(animation);
                                    llOpc.setVisibility(View.GONE);
                                    llBtnDev.setVisibility(View.GONE);
                                    imgcodigo.setVisibility(View.GONE);
                                    codigoview.setVisibility(View.GONE);
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });


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
                codigoDiv=etcodigo.split("-")[0];
                DatabaseReference ref= database.getReference("Items").child(codigoDiv);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            codigoview.setText(etcodigo);
                            obtenerPrestamo();
                            getCode();
                            FrameLayout.LayoutParams params; params = (FrameLayout.LayoutParams) btnLeerCodigo.getLayoutParams();
                            params.gravity = Gravity.CENTER_HORIZONTAL;
                            btnLeerCodigo.setLayoutParams(params);
                            llOpc.setVisibility(View.VISIBLE);
                            llBtnDev.setVisibility(View.VISIBLE);
                        }

                        else{
                            Snackbar snackbar= Snackbar.make(root,"El codigo no existe",BaseTransientBottomBar.LENGTH_LONG);
                            snackbar.show();
                            llOpc.setVisibility(View.INVISIBLE);
                            llBtnDev.setVisibility(View.INVISIBLE);



                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);

        }


    }

    private void obtenerPrestamo() {
        DatabaseReference ref=database.getReference("Prestamos").child(etcodigo);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String estado=snapshot.child("estado").getValue().toString();
                    List<HistorialPrestamo> historialPrestamos= new ArrayList<>();
                    for(DataSnapshot ds: snapshot.child("historial").getChildren()){
                        String nombre=ds.child("nombre").getValue().toString();
                        String fechaPrestamo=ds.child("fechaPrestamo").getValue().toString();
                        String fechaDevolucion=ds.child("fechaDevolucion").getValue().toString();
                        String estadoPrestamo=ds.child("estadoPrestamo").getValue().toString();
                        HistorialPrestamo historialPrestamo=new HistorialPrestamo(nombre,fechaPrestamo,fechaDevolucion,estadoPrestamo);
                        historialPrestamos.add(historialPrestamo);
                    }
                    prestamo=new Prestamo(etcodigo,estado,historialPrestamos);

                }else{
                    String estado="";
                    List<HistorialPrestamo> historialPrestamos= new ArrayList<>();
                    //LLenas los datos correspondientes
                    prestamo=new Prestamo(etcodigo,estado,historialPrestamos);



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
        imgcodigo.setVisibility(View.VISIBLE);

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