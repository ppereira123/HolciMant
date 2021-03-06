package com.example.mantenimientoholcim.Signin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mantenimientoholcim.MainActivity;
import com.example.mantenimientoholcim.Modelo.InternalStorage;
import com.example.mantenimientoholcim.Modelo.UsersData;
import com.example.mantenimientoholcim.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;

import static android.text.InputType.TYPE_CLASS_NUMBER;

public class LoginFireBase extends AppCompatActivity {
    //Varibales públicas
    GoogleSignInClient mGoogleSignInClient;
    static final int GOOGLE_SIGN_IN = 123;
    FirebaseAuth mAuth;
    ImageButton imgIniciar;
    static  final String claveholcim="1557";

    Context context= this;
    InternalStorage storage;
    boolean admin;
    AlertDialog alert,alertDialog;
    LayoutInflater mInflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_fire_base);
        imgIniciar=findViewById(R.id.btn_login);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Iniciando Sesion...");

        LayoutInflater inflater=LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.res_carga,null);
        builder.setView(view);
        alert = builder.create();
        storage=new InternalStorage();

        mAuth= FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg");
        if(msg != null){
            if(msg.equals("cerrarSesion")){
                cerrarSesion();
            }
        }








    }
    private void cerrarSesion() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                task -> updateUI(null));
    }
    public void iniciarSesion(View view) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        mInflater= LayoutInflater.from(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Alerta");
        builder.setCancelable(false);
        View view = mInflater.inflate(R.layout.adaptador_ingreso, null);
        EditText clave=view.findViewById(R.id.clave);
        clave.setInputType(	TYPE_CLASS_NUMBER);
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (clave.getText().toString().equals("")){
                    Toast.makeText(LoginFireBase.this, "Escriba la clave", Toast.LENGTH_SHORT).show();


                }else {
                    if (clave.getText().toString().equals(claveholcim)){
                       Snackbar snackbar= Snackbar.make(findViewById(R.id.lllogin),"Ingreso exitoso", BaseTransientBottomBar.LENGTH_SHORT);
                       snackbar.show();
                        imgIniciar.setVisibility(View.VISIBLE);
                        //dialog.dismiss();

                    }
                    else {
                        Snackbar snackbar= Snackbar.make(findViewById(R.id.lllogin),"Clave incorrecta", BaseTransientBottomBar.LENGTH_SHORT);
                        snackbar.show();


                    }




                }

            }
        });
        builder.setView(view);
        alertDialog=builder.create();
        alertDialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        alert.show();
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent( data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w("TAG", "Fallo el inicio de sesión con google.", e);
            }
        }

    }
    @Override public void onBackPressed(){
        try {
            System.exit(0);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("TAG", "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);

                        FirebaseDatabase database= FirebaseDatabase.getInstance();
                        String uid= user.getUid();
                        DatabaseReference myRef= database.getReference("Taller").child("admins").child(uid);
                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    admin=true;

                                }
                                else{
                                    admin=false;
                                }

                                try {
                                    UsersData data= new UsersData(admin,user.getUid(),user.getDisplayName(), user.getPhotoUrl().toString(),user.getEmail());
                                    storage.guardarArchivo(data ,context);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                        Intent intent = new Intent(this, MainActivity.class);
                        alert.dismiss();
                        startActivity(intent);
                        finish();


                    } else {
                        System.out.println("error");
                        updateUI(null);
                    }

                });
    }
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            HashMap<String, String> info_user = new HashMap<String, String>();
            info_user.put("user_name", user.getDisplayName());
            info_user.put("user_email", user.getEmail());
            info_user.put("user_photo", String.valueOf(user.getPhotoUrl()));
            info_user.put("user_id", user.getUid());
            finish();
        } else {
            System.out.println("sin registrarse");
        }
    }


}