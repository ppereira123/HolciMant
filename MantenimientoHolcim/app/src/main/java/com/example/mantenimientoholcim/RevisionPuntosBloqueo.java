package com.example.mantenimientoholcim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.mantenimientoholcim.Adaptadores.ListAdapterPuntosDeBloqueo;
import com.example.mantenimientoholcim.Modelo.PuntoBloqueo;
import com.example.mantenimientoholcim.Modelo.RevisionPuntoBloqueo;
import com.example.mantenimientoholcim.ui.Inspecciones.CrearPuntodeBloqueo;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.mantenimientoholcim.ui.Inspecciones.PlantillasInspeccion.variarFecha;

public class RevisionPuntosBloqueo extends AppCompatActivity {
    RecyclerView rvPuntosBloqueo;
    Context context=this;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    EditText txtNombre,txtFecha,txtFechaProxima;
    List<PuntoBloqueo> puntos= new ArrayList<>();
    String nombre="";
    String fecha="";
    String fechaProxima="";
    RevisionPuntoBloqueo revisionActual=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        setContentView(R.layout.activity_revision_puntos_bloqueo);
        rvPuntosBloqueo=findViewById(R.id.rvpuntosdebloqueo);
        txtNombre=findViewById(R.id.txtPbNombre);
        txtFecha=findViewById(R.id.txtPbfechaInspección);
        txtFechaProxima=findViewById(R.id.txtPbfechaproximaInspeccion);
        revisionActual=(RevisionPuntoBloqueo)getIntent().getSerializableExtra("Revision");
        txtNombre.setText(currentUser.getDisplayName());
        //fecha
        Date d=new Date();
        SimpleDateFormat fecc=new SimpleDateFormat("d/MMMM/yyyy");
        String fechacComplString = fecc.format(d);
        txtFecha.setText(fechacComplString);
        Date fechaFinal = variarFecha(d, Calendar.MONTH, 12);
        SimpleDateFormat fecc2=new SimpleDateFormat("d/MMMM/yyyy");
        String fechacComplString2 = fecc2.format(fechaFinal);
        txtFechaProxima.setText(fechacComplString2);




        //fin fecha
        if(revisionActual==null){

        }
        else{
            puntos=revisionActual.getPuntos();
            cargarObjeto();
        }
        ListAdapterPuntosDeBloqueo lpb= new ListAdapterPuntosDeBloqueo(puntos,context);
        rvPuntosBloqueo.setHasFixedSize(true);
        rvPuntosBloqueo.setLayoutManager(new LinearLayoutManager(context));
        rvPuntosBloqueo.setAdapter(lpb);


    }

    private void cargarObjeto() {
        txtNombre.setText(revisionActual.getNombre());
        txtFecha.setText(revisionActual.getFecha());
        txtFechaProxima.setText(revisionActual.getProximaFecha());
    }

    public void maspuntodebloqueo(View view){
        Intent intent= new Intent(context, CrearPuntodeBloqueo.class);

        nombre=txtNombre.getText().toString();
        fecha=txtFecha.getText().toString();
        fechaProxima=txtFechaProxima.getText().toString();
        RevisionPuntoBloqueo rpb=new RevisionPuntoBloqueo(nombre,fecha,fechaProxima,puntos);
        intent.putExtra("Revision",rpb);
        startActivity(intent);
        finish();

    }
    public void subirinspeccion(View view){
        

    }
}