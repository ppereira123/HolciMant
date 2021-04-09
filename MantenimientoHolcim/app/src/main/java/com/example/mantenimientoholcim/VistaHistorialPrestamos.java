package com.example.mantenimientoholcim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.mantenimientoholcim.Modelo.HistorialPrestamo;
import com.example.mantenimientoholcim.Modelo.Prestamo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VistaHistorialPrestamos extends AppCompatActivity {
    private ListView listPrestamos;
    ImageButton btnSalirPrestamos;
    TableLayout tablaPrestamos;
    Context context=this;
    TextView txtprestamo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_historial_prestamos);
        btnSalirPrestamos= findViewById(R.id.btnSalirHistorialPrestamos);
        txtprestamo= findViewById(R.id.txtprestamos);
        tablaPrestamos= findViewById(R.id.tablaPrestamos);
        listPrestamos= findViewById(R.id.listPrestamos);
        btnSalirPrestamos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //List<HistorialPrestamo> historialPrestamos= new ArrayList<>();
        //HistorialPrestamo prestamo=new HistorialPrestamo("nombre","fechaPrestamo","fechaDevolucion","estadoPrestamo");
        //historialPrestamos.add(prestamo);
        PrestamosAdapter adapter = new PrestamosAdapter(context,GetData());
        listPrestamos.setAdapter(adapter);


    }

    private List<HistorialPrestamo> GetData() {
        List<HistorialPrestamo> historialPrestamos= new ArrayList<>();

        String codigo= getIntent().getStringExtra("codigo");
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference("Prestamos").child(codigo);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    txtprestamo.setVisibility(View.GONE);
                    String estado=snapshot.child("estado").getValue().toString();
                    for(DataSnapshot ds: snapshot.child("historial").getChildren()){
                        String nombre=ds.child("nombre").getValue().toString();
                        String fechaPrestamo=ds.child("fechaPrestamo").getValue().toString();
                        String fechaDevolucion=ds.child("fechaDevolucion").getValue().toString();
                        String estadoPrestamo=ds.child("estadoPrestamo").getValue().toString();
                        HistorialPrestamo prestamo=new HistorialPrestamo(nombre,fechaPrestamo,fechaDevolucion,estadoPrestamo);
                        historialPrestamos.add(prestamo);
                    }

                }else{
                    tablaPrestamos.setVisibility(View.GONE);
                    listPrestamos.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return historialPrestamos;
    }
}