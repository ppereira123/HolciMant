package com.example.mantenimientoholcim.ui.Tareas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mantenimientoholcim.Adaptadores.adaptador_novedad_por_equipo;
import com.example.mantenimientoholcim.Modelo.InternalStorage;
import com.example.mantenimientoholcim.Modelo.Tarea;
import com.example.mantenimientoholcim.Modelo.UsersData;
import com.example.mantenimientoholcim.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import id.zelory.compressor.Compressor;

import static com.theartofdev.edmodo.cropper.CropImage.getPickImageResultUri;

public class NuevaTarea extends AppCompatActivity {
    AutoCompleteTextView autocompleteSpinnerEquipoMovil;
    Button btnInsertarFotoMovil, subir;
    Context context;
    ImageView imgNovedadEquipo;
    Bitmap thumb_bitmap= null;
    byte[] thumb_byte;

    List<String> encargadoslist;
    boolean[] checkedItems;
    List<String> slectEncargados=new ArrayList<>();
    String [] arryaEncaragdos;

    TextInputEditText txtDescripcionNovedades,editEncargados;
    FirebaseDatabase database;
    DatabaseReference novedadesdb;
    DatabaseReference equipodb;
    UsersData userdata;
    ArrayList<Tarea> listNovedadesAnterioresEquipo=new ArrayList<>();
    Resources res;
    StorageReference storageReference;
    private LayoutInflater mInflater;

    public static final int REQUEST_CODE_TAKE_PHOTO = 3 /*1*/;
    private String mCurrentPhotoPath;
    private Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_tarea);
        context=this;
        autocompleteSpinnerEquipoMovil=findViewById(R.id.autocompleteSpinnerEquipoMovil);
        btnInsertarFotoMovil=findViewById(R.id.btnInsertarFotoMovil);
        imgNovedadEquipo=findViewById(R.id.imgNovedadEquipo);
        txtDescripcionNovedades=findViewById(R.id.txtDescripcionNovedades);
        subir=findViewById(R.id.btnSubirNovedadEquipo);
        autocompleteSpinnerEquipoMovil.setKeyListener(null);
        res = context.getResources();
        String[] nombre_equipos = res.getStringArray(R.array.combo_inspeccion_equipo_movil);
        ArrayAdapter<String> adapterTipoInspecciones=new ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item,nombre_equipos);

        editEncargados=findViewById(R.id.editEncargados);
        escogerEncargador(context,editEncargados);

        database= FirebaseDatabase.getInstance();

        novedadesdb=database.getReference("Taller").child("Novedades");
        equipodb=database.getReference("Taller").child("Equipo");
        //cargar imagen
        storageReference= FirebaseStorage.getInstance().getReference().child("Fotos_Inspecciones");
        autocompleteSpinnerEquipoMovil.setAdapter(adapterTipoInspecciones);
        autocompleteSpinnerEquipoMovil.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cargarDatosEquipos(nombre_equipos[position],context);
            }
        });
        subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobarllenado(context);
            }
        });
        btnInsertarFotoMovil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCamara();
            }
        });
    }
    public void  abrirCamara(){
        CropImage.startPickImageActivity(NuevaTarea.this);

    }
    void escogerEncargador(Context context, TextInputEditText editEncargados){

        editEncargados.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                    List<String> encargadosSeleccionados=new ArrayList<>();
                    encargadoslist=new ArrayList<>();
                    encargadoslist.add("Todos");
                    FirebaseDatabase database= FirebaseDatabase.getInstance();
                    DatabaseReference ref=database.getReference("Taller").child("Miembros");
                    ref.keepSynced(true);
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {};
                                List<String> m = snapshot.getValue(t);
                                for(String miembro:m){
                                    encargadoslist.add(miembro);
                                }

                                checkedItems=new boolean[encargadoslist.size()];
                                arryaEncaragdos=new String[encargadoslist.size()];

                                for(int i=0;i<encargadoslist.size();i=i+1){
                                    arryaEncaragdos[i]=encargadoslist.get(i);
                                    checkedItems[i]=false;
                                }


                                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                                builder.setTitle("Escoger encragados para la tarea");
                                builder.setMultiChoiceItems(arryaEncaragdos, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                        if(arryaEncaragdos[which].equals("Todos")){

                                            final AlertDialog alertDialog = (AlertDialog) dialog;
                                            final ListView alertDialogList = alertDialog.getListView();

                                            for (int position = 0; position < alertDialogList.getChildCount(); position++)
                                            {
                                                if (position != which) {
                                                    if(isChecked){
                                                        alertDialogList.getChildAt(position).setVisibility(View.GONE);
                                                        encargadosSeleccionados.clear();
                                                        encargadosSeleccionados.add(arryaEncaragdos[which]);
                                                    }else {
                                                        encargadosSeleccionados.clear();
                                                        alertDialogList.getChildAt(position).setVisibility(View.VISIBLE);
                                                    }



                                                }
                                            }
                                        }else {
                                            if(encargadosSeleccionados.contains("Todos")){
                                                encargadosSeleccionados.remove(encargadosSeleccionados.indexOf("Todos"));
                                            }

                                            if(encargadosSeleccionados.contains(arryaEncaragdos[which])){
                                                encargadosSeleccionados.remove(encargadosSeleccionados.indexOf(arryaEncaragdos[which]));

                                            }
                                            else {
                                                encargadosSeleccionados.add(arryaEncaragdos[which]);
                                            }

                                        }




                                    }
                                });
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        slectEncargados.clear();
                                        editEncargados.clearFocus();

                                        if(!encargadosSeleccionados.contains("Todos")){
                                            int cont=0;

                                            for(boolean seleccionado:checkedItems){
                                                if(seleccionado){
                                                    slectEncargados.add(arryaEncaragdos[cont]);

                                                }
                                                cont++;
                                            }

                                        }else {
                                            slectEncargados.add("Todos");


                                        }
                                        String muestra="";
                                        for(String s: slectEncargados){
                                            muestra=muestra+s+"\n";
                                        }
                                        editEncargados.setText(muestra);



                                    }
                                });
                                builder.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        editEncargados.clearFocus();
                                    }
                                });

                                AlertDialog dialog=builder.create();
                                dialog.show();
                            }
                            else{
                                Toast.makeText(context, "No existe referencia", Toast.LENGTH_SHORT).show();
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    editEncargados.clearFocus();
                }
            }
        });


    }

    public void insertarImagen(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Set the alert dialog title
        builder.setTitle("Choose a flower.");

        // Initializing an array of opciones
        final String[] opciones = new String[]{
                "Tomar foto con la camara",
                "Insertar desde la galeria",
        };

                /*
                    AlertDialog.Builder setSingleChoiceItems (CharSequence[] items,
                                        int checkedItem,
                                        DialogInterface.OnClickListener listener)

                        Set a list of items to be displayed in the dialog as the content, you will
                        be notified of the selected item via the supplied listener. The list will
                        have a check mark displayed to the right of the text for the checked item.
                        Clicking on an item in the list will not dismiss the dialog. Clicking
                        on a button will dismiss the dialog.

                        Parameters
                            items CharSequence: the items to be displayed.
                            checkedItem int: specifies which item is checked. If -1 no items are checked.
                            listener DialogInterface.OnClickListener: notified when an item on
                                        the list is clicked. The dialog will not be dismissed when
                                        an item is clicked. It will only be dismissed if clicked
                                        on a button, if no buttons are supplied it's up to the
                                        user to dismiss the dialog.
                        Returns
                            AlertDialog.Builder This Builder object to allow for
                                                chaining of calls to set methods
                */

        // Set a single choice items list for alert dialog
        builder.setSingleChoiceItems(
                opciones, // Items list
                -1, // Index of checked item (-1 = no selection)
                new DialogInterface.OnClickListener() // Item click listener
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Get the alert dialog selected item's text
                        if(i==0){
                            if (ContextCompat.checkSelfPermission(NuevaTarea.this,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(NuevaTarea.this,
                                    Manifest.permission.CAMERA)
                                    != PackageManager.PERMISSION_GRANTED) {


                                if (ActivityCompat.shouldShowRequestPermissionRationale(NuevaTarea.this,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                                } else {
                                    ActivityCompat.requestPermissions(NuevaTarea.this,
                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                            225);
                                }


                                if (ActivityCompat.shouldShowRequestPermissionRationale(NuevaTarea.this,
                                        Manifest.permission.CAMERA)) {

                                } else {
                                    ActivityCompat.requestPermissions(NuevaTarea.this,
                                            new String[]{Manifest.permission.CAMERA},
                                            226);
                                }
                            } else {
                                dispatchTakePictureIntent();
                            }



                        }else{
                            abrirCamara();
                        }

                    }
                });

        // Set the a;ert dialog positive button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Just dismiss the alert dialog after selection
                // Or do something now
            }
        });

        // Create the alert dialog
        AlertDialog dialog = builder.create();

        // Finally, display the alert dialog
        dialog.show();

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "MyPicture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
                photoURI = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                //Uri photoURI = FileProvider.getUriForFile(AddActivity.this, "com.example.android.fileprovider", photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*

        if (resultCode == Activity.RESULT_OK) {
            Uri imageUri =  getPickImageResultUri(context,data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage,
            // but we don't know if we need to for the URI so the simplest is to try open the stream and see if we get error.
            boolean requirePermissions = false;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    isUriRequiresPermissions(context,imageUri)) {

                // request permissions and handle the result in onRequestPermissionsResult()
                requirePermissions = true;
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }

            if (!requirePermissions) {
                mCropImageView.setImageUriAsync(imageUri);
            }
        }

         */
        if (requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode == RESULT_OK) {

            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
                imgNovedadEquipo.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*if (requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras(); // Aquí es null
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                mPhotoImageView.setImageBitmap(imageBitmap);
            }*/

        }

        if(requestCode==CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri imagenuri = getPickImageResultUri(this,data);
            //Recortar imagen
            CropImage.activity(imagenuri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setRequestedSize(640,640)
                    .setAspectRatio(2,2)
                    .start(NuevaTarea.this);

        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result= CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                Uri resultUri=result.getUri();
                File url=new File(resultUri.getPath());
                imgNovedadEquipo.setVisibility(View.VISIBLE);
                Picasso.with(this).load(url).into(imgNovedadEquipo);

                //comprimiendo imagen
                try {
                    thumb_bitmap= new Compressor(this)
                            .setMaxWidth(640)
                            .setMaxHeight(640)
                            .setQuality(90)
                            .compressToBitmap(url);

                }catch (IOException e){
                    e.printStackTrace();
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG,90,byteArrayOutputStream);
                thumb_byte = byteArrayOutputStream.toByteArray();
                ///fin compresor


            }
        }


    }
    /*
    @Override

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mCropImageView.setImageUriAsync(mCropImageUri);
        } else {
            Toast.makeText(this, "Required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }

     */

    void comprobarllenado(Context context){
        String codEquipo="";
        String descripcion="";
        String error="";
        codEquipo=autocompleteSpinnerEquipoMovil.getText().toString();
        descripcion=txtDescripcionNovedades.getText().toString();

        if (descripcion.equals("")){
            if(error.equals("")){
                error="Descripción";
            }else {
                error=error+"\nDescripción";
            }
        }

        if(error.equals("")){
            setSubir(codEquipo,descripcion,context);
        }else {
            Toast.makeText(context, "Falta completar: "+error, Toast.LENGTH_SHORT).show();
        }

    }
    void cargarDatosEquipos(String codEquipo, Context context){
        String[] estados = res.getStringArray(R.array.combo_estadoNovedad);
        ProgressDialog progressDialog= new ProgressDialog(context);
        //agregas un mensaje en el ProgressDialog
        progressDialog.setMessage("Cargando...");
//muestras el ProgressDialog
        progressDialog.setCancelable(false);
        progressDialog.show();

        ArrayList<Tarea> listNovedadesEquipo=new ArrayList<>();
        novedadesdb.keepSynced(true);
        novedadesdb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds:snapshot.getChildren()){
                        GenericTypeIndicator<Tarea> t = new GenericTypeIndicator<Tarea>() {};
                        Tarea m = ds.getValue(t);
                        if(m.getCodEquipo().equals(codEquipo)&& !m.getEstado().equals(estados[2])){
                            listNovedadesEquipo.add(m);
                        }
                    }
                }
                if(listNovedadesEquipo.size()>0){

                    AlertDialog.Builder builder= new AlertDialog.Builder(context);
                    builder.setCancelable(false);
                    View view = mInflater.inflate(R.layout.dialog_novedades_pasada, null);
                    RecyclerView rvNovedadesPasadas;
                    rvNovedadesPasadas=view.findViewById(R.id.rvNovedadesPasadas);
                    displayNovedades(listNovedadesEquipo, rvNovedadesPasadas);
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressDialog.dismiss();
                            finish();

                        }
                    });
                    builder.setPositiveButton("Seguir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressDialog.dismiss();
                        }
                    });

                    builder.setView(view);
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }else {
                    Toast.makeText(context, "no hay", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(context, "cancelado", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });
        listNovedadesAnterioresEquipo= listNovedadesEquipo;

        mInflater= LayoutInflater.from(context);





    }
    private void displayNovedades(List<Tarea> novedades, RecyclerView rvNovedades){
        rvNovedades.setLayoutManager(new LinearLayoutManager(context));
        adaptador_novedad_por_equipo novedadesadapter= new adaptador_novedad_por_equipo(context,novedades);
        rvNovedades.setAdapter(novedadesadapter);
    }



    void setSubir(String codEquip,String descrip,Context context){
        String[] estados = res.getStringArray(R.array.combo_estadoNovedad);
        UsersData usersData=new InternalStorage().cargarArchivo(context);
        String estado=estados[0];
        Date d=new Date();
        SimpleDateFormat fecc=new SimpleDateFormat("d/MM/yyyy");
        String fechacComplString = fecc.format(d);

        String autor=usersData.getName();
        ProgressDialog progressDialog=new ProgressDialog(context);
        novedadesdb.keepSynced(true);
        //agregas un mensaje en el ProgressDialog
        progressDialog.setMessage("Subiendo novedad");
//muestras el ProgressDialog
        progressDialog.setCancelable(false);
        progressDialog.show();
        DatabaseReference ref2=novedadesdb.push();
        ref2.keepSynced(true);
        String codigo=ref2.getKey();



        if(thumb_byte!=null){
            StorageReference refimg= storageReference.child(ref2.getKey()+".jpg");
            UploadTask uploadTask= refimg.putBytes(thumb_byte);
            //subir imagen storage
            Task<Uri> uriTask= uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw Objects.requireNonNull(task.getException());

                    }
                    return refimg.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    Uri dowloaduri = task.getResult();
                    String dirImagen=dowloaduri.toString();
                    //

                    Tarea novedad= new Tarea(codigo,descrip,slectEncargados,estado,fechacComplString,dirImagen,codEquip,autor);
                    ref2.setValue(novedad).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Se creo correctamente la novedad del equipo: "+codEquip, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            });
            //novedadesdb.push().setValue()
        }else {
            //

            Tarea novedad= new Tarea(codigo,descrip,slectEncargados,estado,fechacComplString,codEquip,autor);
            ref2.setValue(novedad).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Se creo correctamente la novedad del equipo: "+codEquip, Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
            //

        }


    }
}