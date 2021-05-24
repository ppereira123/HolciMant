package com.example.mantenimientoholcim.ui.Tareas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mantenimientoholcim.Modelo.ComentarioTarea;
import com.example.mantenimientoholcim.Modelo.InternalStorage;
import com.example.mantenimientoholcim.Modelo.Tarea;
import com.example.mantenimientoholcim.Modelo.UsersData;
import com.example.mantenimientoholcim.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.mantenimientoholcim.Adaptadores.AdapterTareas.getEncargadosString;

public class VistaTarea extends AppCompatActivity implements View.OnClickListener{
    Tarea tarea;
    TextView txtdescripcionVistaTarea;
    TextInputEditText editFechaLimitVistaTarea, editEncargadosVistaTarea;
    AutoCompleteTextView autocompleteSpinnerEstado;
    TextInputLayout textinputEstado;
    String[] estados;
    Context context;
    String estado;
    MessageAdapter messageAdapter;
    UsersData u;
    List<ComentarioTarea> messages;
    RecyclerView rvMessage;
    EditText etMessage;
    ImageButton imageButton;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference messagedb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        init();
        u=new InternalStorage().cargarArchivo(context);
        tarea = (Tarea) getIntent().getSerializableExtra("tarea");
        estados=this.getResources().getStringArray(R.array.combo_estadoTareas);
        estado=tarea.getEstado();

    }

    void  init(){
        setContentView(R.layout.activity_vista_tarea);
        txtdescripcionVistaTarea=findViewById(R.id.txtdescripcionVistaTarea);
        editEncargadosVistaTarea=findViewById(R.id.editEncargadosVistaTarea);
        editFechaLimitVistaTarea=findViewById(R.id.editFechaLimitVistaTarea);
        autocompleteSpinnerEstado=findViewById(R.id.autocompleteSpinnerEstado);
        textinputEstado=findViewById(R.id.textinputEstado);

        //message
        rvMessage=findViewById(R.id.rcComentarios);
        etMessage=findViewById(R.id.etComentario);
        imageButton=findViewById(R.id.imgEnviarComentario);
        //fin message
        imageButton.setOnClickListener(this);
        messages= new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        txtdescripcionVistaTarea.setText(tarea.getDescripcion());
        editFechaLimitVistaTarea.setText(tarea.getFechalimite());
        editFechaLimitVistaTarea.setKeyListener(null);
        autocompleteSpinnerEstado.setKeyListener(null);
        editEncargadosVistaTarea.setText(getEncargadosString(tarea.getEncargados()));
        autocompleteSpinnerEstado.setText(tarea.getEstado());
        cambiarimagenEstado(estado);
        ArrayAdapter<String> adapterTipoEstado=new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item,estados);
        autocompleteSpinnerEstado.setAdapter(adapterTipoEstado);

        autocompleteSpinnerEstado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if(position==2){
                    AlertDialog.Builder builder= new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Tarea Finalizada");
                    builder.setMessage("¿Seguro qué desea dar por finalizada esta tarea?");
                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            estado=estados[position];
                            cambiarimagenEstado(estado);
                            autocompleteSpinnerEstado.setText(estado);
                            ArrayAdapter<String> adapterTipoEstado=new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item,estados);
                            autocompleteSpinnerEstado.setAdapter(adapterTipoEstado);
                        }
                    });
                    builder.setNeutralButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }else {
                    estado=estados[position];
                }
                cambiarimagenEstado(estado);
                autocompleteSpinnerEstado.setText(estado);
                ArrayAdapter<String> adapterTipoEstado=new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item,estados);
                autocompleteSpinnerEstado.setAdapter(adapterTipoEstado);
            }
        });


        //messeges
        /*
        database.getReference("Taller").child("Tareas").child(tarea.getCodigo()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AllMethods.name=u.getName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

         */
        messagedb= database.getReference("Taller").child("Tareas").child(tarea.getCodigo()).child("comentarios");
        messagedb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ComentarioTarea message= snapshot.getValue(ComentarioTarea.class);
                message.setKey(snapshot.getKey());
                messages.add(message);
                displayMessages(messages);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ComentarioTarea message= snapshot.getValue(ComentarioTarea.class);
                message.setKey(snapshot.getKey());
                List<ComentarioTarea> newMessages= new ArrayList<ComentarioTarea>();
                for(ComentarioTarea m: messages){
                    if(m.getKey().equals(message.getKey())){
                        newMessages.add(message);
                    }else {
                        newMessages.add(m);
                    }

                }
                messages = newMessages;
                displayMessages(messages);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                ComentarioTarea message= snapshot.getValue(ComentarioTarea.class);
                message.setKey(snapshot.getKey());
                List<ComentarioTarea> newMessages= new ArrayList<ComentarioTarea>();
                for(ComentarioTarea m: messages){
                    if(m.getKey().equals(message.getKey())){
                        newMessages.add(m);
                    }

                }
                messages = newMessages;
                displayMessages(messages);



            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //fin messages


    }

    @Override
    protected void onResume() {
        super.onResume();
        messages=new ArrayList<>();

    }

    private void displayMessages(List<ComentarioTarea> messages) {
        rvMessage.setLayoutManager(new LinearLayoutManager(VistaTarea.this));
        messageAdapter= new MessageAdapter (VistaTarea.this,messages,messagedb);
        rvMessage.setAdapter(messageAdapter);
    }

    void cambiarimagenEstado(String estado){

        if (estado.equals(estados[0])){
            textinputEstado.setStartIconDrawable(R.drawable.reloj);
        }else if (estado.equals(estados[1])){
            textinputEstado.setStartIconDrawable(R.drawable.enproceso);
        }else if (estado.equals(estados[2])){
            textinputEstado.setStartIconDrawable(R.drawable.cheque);
        }

    }


    @Override
    public void onClick(View v) {
        if()

    }
}