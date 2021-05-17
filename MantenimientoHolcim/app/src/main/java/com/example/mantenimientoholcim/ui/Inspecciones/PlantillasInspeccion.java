package com.example.mantenimientoholcim.ui.Inspecciones;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mantenimientoholcim.Adaptadores.ListAdapterInspeccion;
import com.example.mantenimientoholcim.Modelo.ElementInspeccion;
import com.example.mantenimientoholcim.Modelo.InspeccionTipo1;
import com.example.mantenimientoholcim.Modelo.RealizacionInspeccion;
import com.example.mantenimientoholcim.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlantillasInspeccion extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    RecyclerView rvInspecciones;
    Context context=this;
    List<List<String>> tipoInspecciones= new ArrayList<>();
    ListAdapterInspeccion li;
    EditText editTextCodigo,nombreInspector;
    TextInputEditText fechaInspeccion,fechaProximaInspeccion,editTextubicacion;
    TextView txtnombreInspecciones;
    ImageView imagInspeccion, img_tiposinspecciones;
    String nombreInspeccion="";
    String codigo="";
    String imagenInspeccion="";
    InspeccionTipo1 inspeccionTipo1;
    Button btnGenerar,btnGuardar;
    int posicion;
    boolean x=false;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantillas_inspeccion);

        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        editTextCodigo=findViewById(R.id.editTextCodigoInspeccion);
        Date d=new Date();
        //SACAMOS LA FECHA COMPLETA
        fechaInspeccion=findViewById(R.id.fechaInspección);
        img_tiposinspecciones= findViewById(R.id.img_tipoinspeccion);
        SimpleDateFormat fecc=new SimpleDateFormat("d/MM/yyyy");
        String monthString  = (String) DateFormat.format("MM",d);
        String fechacComplString = fecc.format(d);
        fechaInspeccion.setText(fechacComplString);
        fechaProximaInspeccion=findViewById(R.id.fechaproximaInspeccion);
        btnGuardar=findViewById(R.id.btnGuardar);
        editTextubicacion=findViewById(R.id.editTextubicacion);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                guardar();
            }
        });
        btnGenerar=findViewById(R.id.btnexel);
        btnGenerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigocambio=editTextCodigo.getText().toString();
                boolean cambio=codigocambio.contains("/");
                boolean cambio2=codigocambio.contains(".");
                if(cambio==true||cambio2==true){
                    editTextCodigo.setText("");
                    Toast.makeText(context, "No puede escribir codigos con '.' o '/'", Toast.LENGTH_SHORT).show();
                }else {
                    String inspector="";
                    String fechaI="";
                    String proxima="";
                    String codigo="";
                    String error="";
                    String ubicacion="";

                    HashMap<String, ElementInspeccion> valores=li.getValores();
                    inspector=nombreInspector.getText().toString();
                    fechaI=fechaInspeccion.getText().toString();
                    proxima=fechaProximaInspeccion.getText().toString();
                    codigo=editTextCodigo.getText().toString();
                    ubicacion=editTextubicacion.getText().toString();

                    if(inspector.equals("")){
                        error=error+"Nombre Inspector";
                    }

                    if(fechaI.equals("")){
                        error=error+"Fecha de inspeccion";
                    }

                    if(proxima.equals("")){
                        error=error+"Fecha proxima inspeccion";
                    }

                    if(codigo.equals("")){
                        error=error+"Codigo";
                    }
                    if(ubicacion.equals("")){
                        error=error+"Ubicación";
                    }

                    int diferencia=tipoInspecciones.get(posicion).size()-valores.size();
                    if(diferencia!=0){
                        error=error+"Completar "+String.valueOf(diferencia)+" parametros de inspeccion";
                    }

                    if(error.equals("")){


                        if(contieneNOOK()){
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Calificación");
                            builder.setIcon(R.drawable.cancelar);
                            builder.setMessage("Esta inspección no cumple con los requerimientos, no puede pegar la etiqueta de inspección hasta que el objeto inspeccionado cumpla con todos los parametros de inspección.");
                            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).setNegativeButton("Corregir", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                        }else {
                            existeinspeccionReciente(fechaInspeccion.getText().toString(), editTextCodigo.getText().toString());
                            if(!x){
                                guardar();
                                crearExcel(inspeccionTipo1);
                            }

                        }

                    }

                    else{
                        Toast.makeText(context, "Falta completar: "+error, Toast.LENGTH_LONG).show();
                    }


                }

            }
        });
        cargarInspecciones();
        posicion=getIntent().getIntExtra("posicion",0);
        codigo= getIntent().getStringExtra("codigo");
        if(codigo==null){
            codigo="";
        }

        Resources res = getResources();
        String[] nombre_inspecciones = res.getStringArray(R.array.combo_inspeccionesNombre);
        String[] imagenesdeinspeccion = res.getStringArray(R.array.combo_inspeccionesImagenes);
        nombreInspeccion=nombre_inspecciones[posicion];
        imagenInspeccion= imagenesdeinspeccion[posicion];
        rvInspecciones=findViewById(R.id.rvInspecciones);
        nombreInspector=findViewById(R.id.nombreInspector);
        nombreInspector.setText(currentUser.getDisplayName());
        imagInspeccion=findViewById(R.id.imgInspeccion);
        String uri =imagenInspeccion;
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        Drawable imagen = ContextCompat.getDrawable(getApplicationContext(), imageResource);
        try {
            proximafecha();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        imagInspeccion.setImageDrawable(imagen);
        editTextCodigo.setText(codigo);

        txtnombreInspecciones=findViewById(R.id.txtPI1);
        li= new ListAdapterInspeccion(tipoInspecciones.get(posicion),this);
        rvInspecciones.setHasFixedSize(true);
        rvInspecciones.setLayoutManager(new LinearLayoutManager(context));
        rvInspecciones.setAdapter(li);
        txtnombreInspecciones.setText(nombreInspeccion);
        configFecha(fechaInspeccion);
        fechaInspeccion.setKeyListener(null);
        fechaProximaInspeccion.setKeyListener(null);

        if(!codigo.equals("")){
            existeinspeccionReciente(fechaInspeccion.getText().toString(), editTextCodigo.getText().toString());
        }


    }
    public static boolean existeEnArreglo(String[] arreglo, String busqueda) {
        for (int x = 0; x < arreglo.length; x++) {
            if (arreglo[x].equals(busqueda)) {
                return true;
            }
        }
        return false;
    }
    public static Date variarFecha(Date fecha, int campo, int valor){
        if (valor==0) return fecha;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(campo, valor);
        return calendar.getTime();
    }


    void cargarInspecciones(){
        List<String> inspeccion0= new ArrayList<>();
        inspeccion0.add("Inspeccione hebillas-anillas de cuerpo-broches-dedales y almohadillas. No hay distorsión, o tienen bordes agudos, ruidos, fracturas, o partes desgastadas.");
        inspeccion0.add("Las hebillas trabajan libremente");
        inspeccion0.add("Revisar las costuras no deben existir hilos que se desprendan, sobre todo en los sitios de remate de hilo.");
        inspeccion0.add("Revise que las partes plásticas no presenten deformaciones o rotura");
        inspeccion0.add("Revise si existen las etiquetas de inspección y registre la actual revisión");
        tipoInspecciones.add(inspeccion0);
        List<String> inspeccion1= new ArrayList<>();
        inspeccion1.add("El punto de anclaje está anclado a un sitio o estructura sólida");
        inspeccion1.add("El anillo de enganche está sin corrosión");
        inspeccion1.add("Mantiene su tarjeta de inspección visible y con la última fecha de realización");
        inspeccion1.add("No presenta bordes cortantes");
        inspeccion1.add("No presenta desgaste");
        tipoInspecciones.add(inspeccion1);
        List<String> inspeccion1_2= new ArrayList<>();
        inspeccion1_2.add("El cable de acero no presenta cortes, ni torceduras");
        inspeccion1_2.add("No se evidencian cables rotos");
        inspeccion1_2.add("No se evidencia corrosión");
        inspeccion1_2.add("Mantiene su tarjeta de inspección visible y con la última fecha de realización");
        tipoInspecciones.add(inspeccion1_2);
        List<String> inspeccion1_3= new ArrayList<>();
        inspeccion1_3.add("No se evidencia desgaste concentrado, hilos desgastados, rotos");
        inspeccion1_3.add("No se evidencian nudos en la longitud de la faja");
        inspeccion1_3.add("No se evidencian daños químicos o de calor indicados por áreas marrones, descoloridas o quebradizas");
        inspeccion1_3.add("No se evidencia daño ultravioleta indicado por la decoloración y la presencia de astillas y rajaduras en la superficie de la línea de vida");
        inspeccion1_3.add("Las argollas y/o anillos de conexión no presentan fisuras");
        inspeccion1_3.add("Las argollas y/o anillos de conexión no presenta oxidación que comprometa la intergidad estructural del elemento");
        inspeccion1_3.add("Mantiene su tarjeta de inspección visible y con la última fecha de realización");
        tipoInspecciones.add(inspeccion1_3);
        List<String> inspeccion2= new ArrayList<>();
        inspeccion2.add("El cable de la línea de vida no presenta cortes, ni torceduras");
        inspeccion2.add("No se evidencia desgaste concentrado, ni suciedad excesiva");
        inspeccion2.add("No presenta oxidación o algún tipo de decoloración");
        inspeccion2.add("Mantiene todos sus elementos (absorvedor de energía zorbit, pernos y conexiones) en buen estado");
        inspeccion2.add("Mantiene su tarjeta de inspección visible y con la última fecha de realización");
        inspeccion2.add("Está correctamente templada (sin vanos)");
        tipoInspecciones.add(inspeccion2);

        List<String> inspeccion3= new ArrayList<>();
        inspeccion3.add("No se evidencia desgaste concentrado, hilos desgastados, hilos rotos, quemaduras, cortes o abrasiones");
        inspeccion3.add("No se evidencian nudos en toda la longitud de la cuerda");
        inspeccion3.add("No se evidencia suciedad excesiva, acumulación de pintura o tinción por herrumbre que impide la maleabilidad de la cuerda y que evidencia deterioro de la misma");
        inspeccion3.add("No se evidencian daños químicos o de calor indicados por áreas marrones, descoloridas o quebradizas  ");
        inspeccion3.add("No se evidencia daño ultravioleta indicado por la decoloración y la presencia de astillas y rajaduras en la superficie de la línea de vida  ");
        inspeccion3.add("Los ganchos de conexión no presentan fisuras   ");
        inspeccion3.add("Los ganchos no presentan oxidación que comprometa la integridad estructural del elemento ");
        tipoInspecciones.add(inspeccion3);

        List<String> inspeccion4= new ArrayList<>();
        inspeccion4.add("El cordón de absorción de energía no tiene fibras rotas, bordes deshilachados, distorsionados, ni tiene bordes agudos, ruidos, fracturas o corrosión.");
        inspeccion4.add("Inspeccione la correcta operación de los ganchos de conexión");
        inspeccion4.add("Las puertas de los ganchos se mueven libremente y se bloquean al cierre.");
        inspeccion4.add("Inspeccione el amortiguador de energía para determinar si ha sido activado. (El amortiguador debe estar recogido)");
        inspeccion4.add("Revise si existen las etiquetas de inspección y registre la actual revisión");
        tipoInspecciones.add(inspeccion4);
        List<String> inspeccion5= new ArrayList<>();
        inspeccion5.add("Barandas laterales en buen estado");
        inspeccion5.add("Huella de al menos 200 mm y contrahuella de al menos 230 mm");
        inspeccion5.add("Libres de exceso de aceite, grasa o suciedad");
        inspeccion5.add("Las protecciones, pernos y cabezas de remaches no están corroídos");
        inspeccion5.add("Barandillas y soportes de plataforma en buen estado");
        inspeccion5.add("Los anclajes no están rotos ni sueltos");
        inspeccion5.add("Escalones en buen estado");
        inspeccion5.add("Jaula en buen estado");
        inspeccion5.add("Los travesaños o barandas laterales no están sueltos y se encuentran en buen estado");
        inspeccion5.add("No existen defectos en dispositivos de trepado, incluyendo barandas o cuerdas de transportación");
        inspeccion5.add("Las superficies no se encuentran resbalosas");
        inspeccion5.add("Existe orden y limpieza");
        tipoInspecciones.add(inspeccion5);
        List<String> inspeccion5_1= new ArrayList<>();
        inspeccion5_1.add("Libres de exceso de aceite, grasa o suciedad");
        inspeccion5_1.add("Las protecciones, pernos y cabezas de remaches no están corroídos");
        inspeccion5_1.add("Barandillas y soportes de plataforma en buen estado");
        inspeccion5_1.add("Los anclajes no están rotos ni sueltos");
        inspeccion5_1.add("Los travesaños no están debilitados, ni dañados");
        inspeccion5_1.add("Buena soldadura sobre miembros estructurales y rodapiés"); inspeccion5_1.add("Los rodapiés tienen una altura mayor a 10"); inspeccion5_1.add(
                "El espacio entre la barandilla y el rodapies no excede los 50 cm.");
        tipoInspecciones.add(inspeccion5_1);
        List<String> inspeccion6= new ArrayList<>();
        inspeccion6.add("Libre de exceso de aceite, grasa o suciedad");
        inspeccion6.add("El soporte superior de la escalera no está suelto, roto, faltante");
        inspeccion6.add("Los travesaños o escalones, clavos, tornillos, pernos u otras partes metálicas no se encuentran sueltas están sueltas si usted puede moverlas a mano");
        inspeccion6.add("Largeros, abrazaderas, escalones o travesaños no están rajados, partidos, abiertos, cortados o rotos");
        inspeccion6.add("No existe desgaste en travesaños o peldaños");
        inspeccion6.add("No hay astillas en montantes, travesaños o escalones" );
        inspeccion6.add("Las bases no se encuentra dañadas o desgastadas" );
        inspeccion6.add("No existen fracturas en escalones");
        inspeccion6.add("No existe corrosión, herrumbre, óxido y desgaste excesivo, especialmente en peldaños" );
        inspeccion6.add("No faltan etiquetas de identificación");
        tipoInspecciones.add(inspeccion6);
        List<String> inspeccion6_1= new ArrayList<>();
        inspeccion6_1.add("Los bloqueos de extensión no están sueltos, rotos o faltantes" );
        inspeccion6_1.add("No existen bloqueos defectuosos que no se asienten apropiadamente cuando la escalera está extendida" );
        inspeccion6_1.add("Existe buena lubricación de las partes que trabajan" );
        inspeccion6_1.add("No existen almohadillas o mangas faltantes o defectuosas" );
        inspeccion6_1.add("Cuerdas, cadenas y sogas en buen estado" );
        tipoInspecciones.add(inspeccion6_1);
        List<String> inspeccion6_2= new ArrayList<>();
        inspeccion6_2.add("No existen ruedas faltantes, ni dañadas" );
        inspeccion6_2.add("Las ruedas que se unen y/o las ruedas de carros no están fuera de ajuste" );
        inspeccion6_2.add("Los soportes de ruedas de piso no están rotos, sueltos o faltantes" );
        inspeccion6_2.add("La parada de escalera y de pasamanos no está rota, suelta o faltante" );
        inspeccion6_2.add("Los soportes de pasamanos no están rotos, ni falta la sección de pasamanos" );
        tipoInspecciones.add(inspeccion6_2);
        List<String> inspeccion7= new ArrayList<>();
        inspeccion7.add("Conexión eléctrica en buen estado (revisar estado del cable de conexión y realizar prueba de continuidad de líneas de fases y tierra)" );
        inspeccion7.add("Enchufe en buenas condiciones" );
        inspeccion7.add("Empuñadura auxiliar en buenas condiciones" );
        inspeccion7.add("Accionamiento funciona correctamente" );
        inspeccion7.add("Funciones en correcto estado" );
        inspeccion7.add("Velocímetro en buenas condiciones (no se traba)" );
        inspeccion7.add("Ventanas de refrigeración limpias y en buenas condiciones" );
        inspeccion7.add("Protección de portabroca en buenas condiciones" );
        tipoInspecciones.add(inspeccion7);

        List<String> inspeccion8= new ArrayList<>();
        inspeccion8.add("Conexión eléctrica en buen estado (revisar estado del cable de conexión y realizar prueba de continuidad de líneas de fases y tierra)");
        inspeccion8.add("El protector de cable se encuentra en buenas condiciones");
        inspeccion8.add("El interruptor se encuentra en buenas condiciones");
        inspeccion8.add("La guarda existe y se encuentra en buenas condiciones");
        inspeccion8.add("La guarda se encuentra asegurada con un perno a la herramienta");
        inspeccion8.add("La traba del disco funciona correctamente");
        inspeccion8.add("La empuñadura se encuentra en buenas condiciones");
        tipoInspecciones.add(inspeccion8);
        List<String> inspeccion9= new ArrayList<>();
        inspeccion9.add("Conexión eléctrica en buen estado (revisar estado del cable de conexión y realizar prueba de continuidad de líneas de fases y tierra)");
        inspeccion9.add("Enchufe en buenas condiciones");
        inspeccion9.add("Empuñadura auxiliar en buenas condiciones");
        inspeccion9.add("Porta broca en buenas condiciones");
        inspeccion9.add("El botón de traba del interruptor funciona correctamente");
        inspeccion9.add("Llave del mandril en buenas condiciones");
        inspeccion9.add("Ventanas de refrigeración limpias y en buenas condiciones");
        tipoInspecciones.add(inspeccion9);
        List<String> inspeccion10= new ArrayList<>();
        inspeccion10.add("Conexión eléctrica en buen estado (revisar estado del cable de conexión y realizar prueba de continuidad de líneas de fases y tierra)");
        inspeccion10.add("El protector de cable se encuentra en buenas condiciones");
        inspeccion10.add("Enchufe en buenas condiciones");
        inspeccion10.add("La guarda existe y se encuentra en buenas condiciones");
        inspeccion10.add("La guarda posee sus 4 seguros (2 pernos y 2 tuercas mariposa)");
        tipoInspecciones.add(inspeccion10);

        List<String> inspeccion11= new ArrayList<>();
        inspeccion11.add("Conexión eléctrica en buen estado (revisar estado del cable de conexión y realizar prueba de continuidad de líneas de fases y tierra)");
        inspeccion11.add("El protector de cable se encuentra en buenas condiciones");
        inspeccion11.add("Enchufe en buenas condiciones");
        inspeccion11.add("La boquilla posee sus 2 seguros");
        tipoInspecciones.add(inspeccion11);


        List<String> inspeccion12= new ArrayList<>();
        inspeccion12.add("Conexión eléctrica en buen estado (revisar estado de acometida del trineo y realizar prueba de continuidad de líneas de fases y tierra)");
        inspeccion12.add("Cableado interno en buen estado, no se encuentran huellas de recalentamiento de cables, ni solturas terminales de conexión de tomacorrientes y/o barras");
        inspeccion12.add("El trineo posee su techo en buenas condiciones");
        inspeccion12.add("El trineo posee la puerta protectora para los tomacorrientes");
        inspeccion12.add("El trineo posee la protección para las barras de alimentación");
        inspeccion12.add("Todos los tomacorrientes de propósito general de 220/240 V y 380/415 V están protegidos por un dispositivio de corriente residual aprobado, valorado a una corriente de desconexión de 30 mA");
        inspeccion12.add("Los tomacorrientes de 110 Vac:\n"+"1.- Son dobles y con tapas\n"+"2.- Poseen la conexión a tierra");
        inspeccion12.add("Los tomacorrientes de 220 Vac poseen la conexión a tierra");
        inspeccion12.add("Los tomacorrientes de 480 Vac poseen la conexión a tierra");
        inspeccion12.add("Los terminales primario y segundario del trafo se encuentran ajustados");
        inspeccion12.add("Existe un braker individual para cada tomacorriente de soldar (revisar ajustes terminales de entrada / salida de interruptores");
        inspeccion12.add("El enchufe de 480 Vac es: \n 1.- Tres polos + tierra\n 2.- Posee protección IP44");
        inspeccion12.add("Posee la señal de riesgo eléctrico");
        tipoInspecciones.add(inspeccion12);
        List<String> inspeccion13= new ArrayList<>();
        inspeccion13.add("Conexión eléctrica en buen estado (revisar estado del cable de conexión y realizar prueba de continuidad de líneas de fases y tierra))");
        inspeccion13.add("El protector de cable se encuentra en buenas condiciones");
        inspeccion13.add("El interruptor de encendido se encuentra en buenas  condiciones");
        inspeccion13.add("La  guarda existe y  se encuentra en  buenas condiciones");
        inspeccion13.add("La  guarda se  encuentra  asegurada al equipo");
        tipoInspecciones.add(inspeccion13);
        List<String> inspeccion14= new ArrayList<>();
        inspeccion14.add("Conexión eléctrica en buen estado (revisar estado del cable de conexión y realizar prueba de continuidad de líneas de fases y tierra)");
        inspeccion14.add("El protector de cable se encuentra en buenas condiciones");
        inspeccion14.add("El interruptor de encendido se encuentra en buenas  condiciones");
        inspeccion14.add("Los seguros de las mangas se encuentran operativos");
        inspeccion14.add("Las mangas no presentan roturas, ni agujeros que disminuyen la capaidad de extracción o ventilación");
        tipoInspecciones.add(inspeccion14);
        List<String> inspeccion15= new ArrayList<>();
        inspeccion15.add("La conexión eléctrica se encuentra en buen estado:\n1.- No posee empates\n2.- La protección no esta cortada\n3.- Verificar la continuidad de cables  L, N y GND  (desde el enchufe hasta el tomacorriente");
        inspeccion15.add("El enchufe:\n1.- Es polarizado 110/220 Vac\n2.- Posee la conexión a tierra\n3.- Las conexiones poseen prensacable");
        inspeccion15.add("El tomacorriente de 110/220 Vac:\n1.- Posee doble tapa\n2.- Posee la conexión a tierra\n3.- Las conexiones poseen prensacables");
        inspeccion15.add("Los tomacorrientes están  protegidos por un dispositivo de corriente residual aprobado, valorado a una corriente de desconexión de 30mA.");
        tipoInspecciones.add(inspeccion15);
        List<String> inspeccion16= new ArrayList<>();
        inspeccion16.add("La conexión eléctrica se encuentra en buen estado:\n1- No posee empates\n2.- La protección no está cortada\n3.- Verificar la continuidad de cables  L, N y GND  (desde el enchufe hasta el tomacorriente)");
        inspeccion16.add("El enchufe de 480 Vac es:\n1.- Tres polos + tierra\n2.-Posee protección IP44");
        inspeccion16.add("El enchufe de 480 Vac posee la conexión a tierra");
        tipoInspecciones.add(inspeccion16);

        List<String> inspeccion17= new ArrayList<>();
        inspeccion17.add("El cableado eléctrico se encuentra en buen estado");
        inspeccion17.add("Los enchufes no poseen daños o prensacables faltantes");
        inspeccion17.add("El botón de emergencia se encuentra operativo");
        inspeccion17.add("La mecánica y el funcionamiento del acelerador se encuentra operativa");
        inspeccion17.add("Botoneras marcha y paro se encuentran operativas");
        inspeccion17.add("Los fusibles se encuentran operativos, no se han realizado bypass (puentes)");
        inspeccion17.add("Los bornes de la batería no presentan corrosión o daños");
        inspeccion17.add("Los cables eléctricos de conexión de la batería se encuentran en buen estado");
        inspeccion17.add("La carcasa de la batería no presentan daños");
        inspeccion17.add("Las conexiones o cables del cargador no presentan daños o partes expuestas");
        tipoInspecciones.add(inspeccion17);



        List<String> inspeccion18= new ArrayList<>();
        inspeccion18.add("La protección excluye totalmente a todas las personas del área de peligro");
        inspeccion18.add("No existe ninguna apertura en la protección, la cual permita que una parte de una persona alcancen el área peligrosa");
        inspeccion18.add("Esta protección no ha causado algún punto secundario de atrapamiento");
        inspeccion18.add("La resistencia de los accesorios y aditamentos es apropiada");
        inspeccion18.add("La resistencia de la estructura de protección es segura y no se desviará");
        inspeccion18.add("No existe la probabilidad que algún objeto caiga a través de la protección");
        inspeccion18.add("El material de la protección es adecuado para las condiciones de servicio propuestas en términos de visibilidad, resistencia a la corrosión, resistencia física, limpieza");
        inspeccion18.add("La máquina inspeccionada cuenta con su paro de emergencia o banderola de parada de emergencia y se encuentra funcional.");
        tipoInspecciones.add(inspeccion18);

        List<String> inspeccionff= new ArrayList<>();
        inspeccionff.add("Las conexiones se encuentran libres de grasa y suciedad");
        inspeccionff.add("La medición que muestran los manómetros es visible y correcta");
        inspeccionff.add("Los dispositivos de seguridad de la válvula están conectados correctamente al soplador");
        inspeccionff.add("No existe goteo o fuga en alguna parte del sistema");
        inspeccionff.add("Existe arresta llamas en las válvulas y soplete/mezclador");
        inspeccionff.add("El soplete no presenta fugas en la válvula de corte de paso");
        inspeccionff.add("Se encuentra disponible la tapa protectora/capuchón para la válvula");
        inspeccionff.add("Las conexiones de mangueras se encuentran correctamente ajustadas y sin escapes");
        inspeccionff.add("Las mangueras de gas combustible y oxígeno se encuentran en buen estado y sin perforaciones");
        tipoInspecciones.add(inspeccionff);



        List<String> inspeccion20= new ArrayList<>();
        inspeccion20.add("La conexión eléctrica se encuentra en buen estado\n (no posee  empates, el aislante se encuentra en buenas condiciones)");
        inspeccion20.add("El protector del cable se encuentra en buenas condicione");
        inspeccion20.add("El interruptor se encuentra en buenas  condiciones");
        inspeccion20.add("La pinza portaelectrodos no presenta daño en el material aislante (rotura, fundimiento)");
        inspeccion20.add("La pinza de masa/tierra no presenta fundimiento y está asegurada al cable adecuadamente");
        inspeccion20.add("Conexión de tierra y electrodos: Los pernos, las roscas  y las arandelas son de bronce y están en buen estado");
        inspeccion20.add("Comprobar la conexión a tierra  (eléctrico)");
        inspeccion20.add("Si la soldadora posee llantas verificar si estas son de caucho y están en buen estado");
        inspeccion20.add("La  carcasa exterior se  encuentra en  buenas  condiciones");
        inspeccion20.add("El panel de  control se encuentra en  buenas condiciones");
        tipoInspecciones.add(inspeccion20);

        List<String> inspeccion19= new ArrayList<>();
        inspeccion19.add("Las conexiones se encuentran libres de grasa y suciedad");
        inspeccion19.add("La medición que muestran los manómetros es visible y correcta");
        inspeccion19.add("Los dispositivos de seguridad de la válvula están conectados correctamente al soplador");
        inspeccion19.add("No existe goteo o fuga en alguna parte del sistema");
        inspeccion19.add("Existe arresta llamas en las válvulas y soplete/mezclador");
        inspeccion19.add("El soplete no presenta fugas en la válvula de corte de paso");
        inspeccion19.add("Se encuentra disponible la tapa protectora/capuchón para la válvula");
        inspeccion19.add("Las conexiones de mangueras se encuentran correctamente ajustadas y sin escapes");
        inspeccion19.add("Las mangueras de gas combustible y oxígeno se encuentran en buen estado y sin perforaciones");
        tipoInspecciones.add(inspeccion19);


        List<String> inspeccion21= new ArrayList<>();
        inspeccion21.add("Se dispone de válvula de protección o de alivio.");
        inspeccion21.add("El cilindro se encuentra en posición vertical y fija.");
        inspeccion21.add("Se encuentra marcado claramente el contenido y rombo de seguridad.");
        inspeccion21.add("No existe olor a gas, requerimientos de testeador.");
        inspeccion21.add("Si se cierran las válvulas, no existe paso de gas.");
        inspeccion21.add("No presenta altos niveles de corrosión, deformaciones, grietas u otros defectos.");
        inspeccion21.add("No existen factores alrededor que activen un incendio, como chispas, fuego, etc.");
        inspeccion21.add("Se dispone de prueba hidrostática");
        tipoInspecciones.add(inspeccion21);


        List<String> inspeccion22= new ArrayList<>();
        inspeccion22.add("Accesorio posee nombre del fabricante y/o la capacidad de carga (o tamaño según es requerido)");
        inspeccion22.add("No existe una reducción de más del 10% en la dimensión original del accesorio");
        inspeccion22.add("No existen partes dobladas, retorcidas, distorsionadas, elongadas, fisuradas, o componentes de carga quebrados.");
        inspeccion22.add("Libre de muescas, hendiduras, desgaste o corrosión excesiva");
        inspeccion22.add("Libre de indicios de temperatura excesiva incluyendo salpicadura de soldadura, impactos o daños por chispas eléctricas, o evidencia de soldadura");
        inspeccion22.add("No existen componentes de reemplazo no autorizados");
        inspeccion22.add("No existen pernos, tuercas, chavetas, anillas, que estén sueltos o faltantes u otro accesorio del tipo de seguro o retención");
        tipoInspecciones.add(inspeccion22);

        List<String> inspeccion23= new ArrayList<>();
        inspeccion23.add("Pestillo de seguridad colocado y en buenas condiciones (cierra automáticamente)");
        inspeccion23.add("No existe una reducción de más del 10% en la dimensión original de la base del gancho");
        inspeccion23.add("No existe una reducción de más del 5% en otras áreas del gancho, sin exceder 1/4");
        inspeccion23.add("No existe abertura del gancho");
        tipoInspecciones.add(inspeccion23);

        List<String> inspeccion24= new ArrayList<>();
        inspeccion24.add("El cable se  encuentra limpio, sin  tierra ni grasa)");
        inspeccion24.add("El cable está libre de nudos)");
        inspeccion24.add("El cable  tiene  placa de identificación visible con su capacidad de carga)");
        inspeccion24.add("El cable está libre de corrosión severa)");
        inspeccion24.add("El cable no presenta abrasión severa localizada, dobleces permanentes, aplastamientos, jaulas de pájaro)");
        inspeccion24.add("El cable no presenta hilos rotos (más de 10 alambres rotos en un paso, o 5 alambres rotos en un torón en un solo paso))");
        inspeccion24.add("El cable cumple con las dimensiones mínimos especificadas en la parte inferior del formulario)");
        inspeccion24.add("La cadena tiene  placa de identificación visible con su capacidad de carga)");
        inspeccion24.add("Cadena sin desgaste excesivo,  muescas, hendiduras)");
        inspeccion24.add("Eslabones de la Cadena estan sin fisuras, ni roturas)");
        inspeccion24.add("Eslabones o componentes de la cadena no presentan alargamiento)");
        inspeccion24.add("Eslabones o componentes  de la cadena sin exceso de corrosión, ni hoyos)");
        inspeccion24.add("Eslabones o componentes de la cadena con movimiento libre)");
        inspeccion24.add("Eslabones o componentes de la cadena sin salpicaduras de soldadura)");
        inspeccion24.add("La eslinga tiene  placa de identificación visible con su capacidad de carga)");
        inspeccion24.add("Eslinga sin quemaduras por ácido o cáustica)");
        inspeccion24.add("Libre de partes derretidas o chamuscadas en toda la eslinga)");
        inspeccion24.add("Libre de hoyos, roturas, cortes o partes deshilachadas)");
        inspeccion24.add("Libre de costuras rotas o desgastadas en lugares donde se ha unido la eslinga)");
        inspeccion24.add("Libre de desgaste por abrasión excesiva)");
        inspeccion24.add("Libre de partes decoloradas, quebradizas o lugares tiesos en cualquier parte de la eslinga (puede significar daño por luz solar/ ultravioleta o daño químico))");
        tipoInspecciones.add(inspeccion24);

        List<String> inspeccion25= new ArrayList<>();
        inspeccion25.add("Sistema Hidráulico: Cilindro hidráulico / pistón no presenta problemas de goteo o ruido");
        inspeccion25.add("Sistema Hidráulico: Las conexiones hidráulicas, mangueras y conexiones flexibles no presentan fugas de aceite");
        inspeccion25.add("Sistema Hidráulico: El nivel de aceite hidráulico es el adecuado");
        inspeccion25.add("Sistema Mecánico: Las horquillas no presentan deformaciones o desperfectos que afecten la integridad estructural (quebraduras)");
        inspeccion25.add("Sistema Mecánico: El chasis no presenta deformaciones o roturas que afecten la integridad estructural");
        inspeccion25.add("Sistema Mecánico: El equipo no presenta tornillos sueltos o faltantes");
        inspeccion25.add("Sistema Mecánico: Las varillas de empuje o palancas no presentan deformaciones o roturas  afecten la integridad estructural");
        inspeccion25.add("Sistema Mecánico: La caja de transmisión no presenta ruidos ni goteos (cuando aplique)");
        inspeccion25.add("Sistema Mecánico: Las ruedas no presentan deformación o daños que puedan provocar inestabilidad del equipo o que no permitan su movilidad");
        inspeccion25.add("Todas las señales de funciones, instrucción, precaución, y las etiquetas de advertencia o placas están en el equipo y son legibles");
        inspeccion25.add("La bocina se encuentra operativa");
        inspeccion25.add("El freno se encuentra operativo");
        inspeccion25.add("El paro de emergencia se encuentra operativo (transpaleta eléctrica)");
        tipoInspecciones.add(inspeccion25);


        List<String> inspeccion26= new ArrayList<>();
        inspeccion26.add("La capacidad está marcada en el equipo");
        inspeccion26.add("El seguro funciona correctamente");
        inspeccion26.add("Los apoyos para carga (superior e inferior) están libres de fisuras, dobladuras");
        inspeccion26.add("Los dientes están completos y en buen estado");
        tipoInspecciones.add(inspeccion26);

        List<String> inspeccion27= new ArrayList<>();
        inspeccion27.add("La capacidad está marcada en el equipo");
        inspeccion27.add("El seguro funciona correctamente");
        inspeccion27.add("Existen los apoyos para carga superior e inferior");
        inspeccion27.add("Los dientes están completos y en buen estado");
        inspeccion27.add("No presenta fugas de aceite (o-ring en buen estado)");
        tipoInspecciones.add(inspeccion27);

        List<String> inspeccion28= new ArrayList<>();
        inspeccion28.add("El tecle se encuentra limpio, sin tierra ni grasa en su estructura y cadena");
        inspeccion28.add("El tecle tiene marcada su capacidad en su estructura y en los 2 ganchos");
        inspeccion28.add("Los ganchos poseen su pestillo de seguridad superior e inferior");
        inspeccion28.add("Los ganchos no están deformados; es decir no están abiertos, ni tampoco presentan golpes ni magulladuras");
        inspeccion28.add("Los ganchos no presentan desgaste");
        inspeccion28.add("El gancho superior gira 360° sin problema");
        inspeccion28.add("Los eslabones de las cadenas están en buenas condiciones (no alargados, ni aplastados, ni rotos, ni con señales de soldadura)");
        inspeccion28.add("El gancho superior gira 360° sin problema");
        inspeccion28.add("Los accionamientos (subir, bajar, neutro) funcionan correctamente");
        inspeccion28.add("El gancho superior gira 360° sin problema");
        inspeccion28.add("El manubrio se encuentra en buen estado");
        inspeccion28.add("El gancho superior gira 360° sin problema");
        inspeccion28.add("El gancho inferior posee el perno original");
        inspeccion28.add("El gancho superior gira 360° sin problema");
        inspeccion28.add("La cadena posee el seguro de final de cadena y el perno original del seguro");
        inspeccion28.add("El gancho superior gira 360° sin problema");
        tipoInspecciones.add(inspeccion28);


        List<String> inspeccion29= new ArrayList<>();
        inspeccion29.add("No existen piezas desgastadas, agrietadas o distorsionadas tales como pasadores, rodamientos, ruedas, ejes, engranajes, rodillos, de bloqueo y dispositivos de sujeción, parachoques, y paradas");
        inspeccion29.add("No existen miembros agrietados, deformes, corroídos");
        inspeccion29.add("No existen pernos flojos o faltantes, tuercas, pernos, remaches");
        inspeccion29.add("No existe polea y tambor agrietadas o desgastadas");
        inspeccion29.add("Dispositivo limitador de carrera superior operativo (detiene el movimiento antes de que la carga impacte alguna parte del troley o del bloque de izaje)");
        inspeccion29.add("No existe desgaste excesivo de las piezas del sistema de frenos");
        inspeccion29.add("No existe desgaste excesivo de las ruedas dentadas de la cadena de transmisión y cadena de estiramiento unidad excesiva");
        inspeccion29.add("No existe deterioro de los controladores, interruptores maestros, contactos, finales de carrera");
        inspeccion29.add("Todas las señales de funciones, instrucción, precaución, y las etiquetas de advertencia o placas están legibles y en el equipo");
        inspeccion29.add("Cable sin corrosión, aplastamiento permanente ni cables rotos");
        tipoInspecciones.add(inspeccion29);

        List<String> inspeccion30= new ArrayList<>();
        inspeccion30.add("Cilindro hidráulico / pistón no presenta problemas de goteo o ruido");
        inspeccion30.add("Las conexiones hidráulicas, mangueras y conexiones flexibles no presentan fugas de aceite");
        inspeccion30.add("Las horquillas no presentan deformaciones o desperfectos que afecten la integridad estructural (quebraduras)");
        inspeccion30.add("El equipo no presenta tornillos sueltos o faltantes");
        inspeccion30.add("El cable de acero no presenta daños como torceduras, rotura, jaula de pájaro");
        inspeccion30.add("Los dientes del sistema de tracción no presentan fisuras");
        inspeccion30.add("Las ruedas no presentan deformación o daños que puedan provocar inestabilidad del equipo o que no permitan su movilidad");
        tipoInspecciones.add(inspeccion30);


        List<String> inspeccion31= new ArrayList<>();
        inspeccion31.add("Las  mangueras se  encuentran en  buen estado");
        inspeccion31.add("Los acoples son redondos");
        inspeccion31.add("El nivel de aceite es el correcto");
        inspeccion31.add("El manómetro está en buen estado (0 - 10000 psi)");
        inspeccion31.add("La purga se encuentra en  buen estado");
        tipoInspecciones.add(inspeccion31);


        List<String> inspeccion32= new ArrayList<>();
        inspeccion32.add("Chaleco en buen estado general, con su número claramente visible");
        inspeccion32.add("Libre de aceite, grasa o exceso de suciedad");
        inspeccion32.add("No existe corrosión en las hebillas");
        inspeccion32.add("Cinturón o cremallera de sujeción en buen estado");
        inspeccion32.add("No existen rasgaduras");
        inspeccion32.add("Los materiales reflectantes se encuentran y no están desvanecidos");
        inspeccion32.add("El color del chaleco no se ha desvanecido");
        inspeccion32.add("Luz estroboscópica / flash, silbato, punto de enganche se encuentran en buen estado");
        inspeccion32.add("Los cinturones de sujeción están en buenas condiciones y son lo suficientemente fuertes");
        inspeccion32.add("La flotabilidad del chaleco sigue siendo efectiva para la capacidad según las recomendaciones del fabricante (realizar prueba en agua)");
        inspeccion32.add("Para inflables: cilindro de gas presente y activador automático operativo (etiqueta verde) + proveedor de servicios externo, constatar la verificación de mantenimiento realizada hace menos de un año");
        inspeccion32.add("El chaleco se almacena adecuadamente, dentro de una temperatura razonable (por debajo de 60°C)");
        inspeccion32.add("El chaleco se guarda en un lugar seco y oscuro");
        inspeccion32.add("Después de su uso se dejas tanto el chaleco, como el aro salvavidas colgados hasta secarse completamente, previo a guardarlos");
        tipoInspecciones.add(inspeccion32);

        List<String> inspeccion33= new ArrayList<>();
        inspeccion33.add("Gancho o soporte a máximo 1,5 m de altura desde el piso");
        inspeccion33.add("Extintor ubicado en el sitio asignado");
        inspeccion33.add("Libre de acceso para uso inmediato y sin obstrucciones");
        inspeccion33.add("Manómetro en buen estado (PQS)");
        inspeccion33.add("Indicador de presión en franja verde a 195 PSI (PQS)");
        inspeccion33.add("Sellos de seguridad en buenas condiciones, no rotos o faltantes");
        inspeccion33.add("Cartel de uso del extintor legible y con vista hacia fuera");
        inspeccion33.add("Libre de daños físicos y corrosión");
        inspeccion33.add("Boquilla de la manguera sin obstrucciones");
        inspeccion33.add("Recarga anual vigente y con etiqueta disponible en buen estado");
        inspeccion33.add("Manija del extintor en buen estado");
        inspeccion33.add("Extintor adecuado al tipo de fuego que podría presentarse");
        inspeccion33.add("Condición de pintura del cilindro en buen estado");
        inspeccion33.add("Boquilla y/o corneta no cristalizada o quebradiza por efecto del sol");
        inspeccion33.add("Manguera del extintor libre de daños");
        inspeccion33.add("Carretilla o ruedas del extintor corroída, doblada o rota");
        inspeccion33.add("Soporte o sujetador de la manguera disponible y en buen estado");
        tipoInspecciones.add(inspeccion33);

        List<String> inspeccion34= new ArrayList<>();
        inspeccion34.add("El tenchufe se encuentra en buen estado:\n1.- No posee empates\n2.- La protección no esta cortada\n3.- Verificar la continuidad de cables  L, N y GND  (desde el enchufe hasta el tomacorriente");
        inspeccion34.add("El enchufe:\n1.- Es polarizado 110/220 Vac\n2.- Posee la conexión a tierra\n3.- Las conexiones poseen prensacable");
        inspeccion34.add("El tomacorriente de 110/220 Vac:\n1.- Posee doble tapa\n2.- Posee la conexión a tierra\n3.- Las conexiones poseen prensacables");
        inspeccion34.add("Los tomacorrientes están  protegidos por un dispositivo de corriente residual aprobado, valorado a una corriente de desconexión de 30mA.");
        tipoInspecciones.add(inspeccion34);


    }

    public void cancel(View view){
        finish();
    }

    public  void guardar(){
        String codigocambio=editTextCodigo.getText().toString();
        boolean cambio=codigocambio.contains("/");
        boolean cambio2=codigocambio.contains(".");
        if(cambio==true||cambio2==true){
            editTextCodigo.setText("");
            Toast.makeText(context, "No puede escribir codigos con '.' o '/'", Toast.LENGTH_SHORT).show();
        }else {
            String inspector="";
            String fechaI="";
            String proxima="";
            String codigo="";
            String error="";
            String ubicacion="";

            HashMap<String, ElementInspeccion> valores=li.getValores();
            inspector=nombreInspector.getText().toString();
            fechaI=fechaInspeccion.getText().toString();
            proxima=fechaProximaInspeccion.getText().toString();
            codigo=editTextCodigo.getText().toString();
            ubicacion=editTextubicacion.getText().toString();

            if(inspector.equals("")){
                error=error+"Nombre Inspector";
            }

            if(fechaI.equals("")){
                error=error+"Fecha de inspeccion";
            }

            if(proxima.equals("")){
                error=error+"Fecha proxima inspeccion";
            }

            if(codigo.equals("")){
                error=error+"Codigo";
            }
            if(ubicacion.equals("")){
                error=error+"Ubicación";
            }

            int diferencia=tipoInspecciones.get(posicion).size()-valores.size();
            if(diferencia!=0){
                error=error+"Completar "+String.valueOf(diferencia)+" parametros de inspeccion";
            }

            if(error.equals("")){

                if(contieneNOOK()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Calificación");
                    builder.setIcon(R.drawable.cancelar);
                    builder.setMessage("Esta inspección no cumple con los requerimientos, no puede pegar la etiqueta de inspección hasta que el objeto inspeccionado cumpla con todos los parametros de inspección, una vez corregido los errores porfavor volver a realizar el checklist de inspección");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            crearInspeccion(true);


                            finish();
                        }
                    }).setNegativeButton("Corregir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }else {
                    crearInspeccion(false);

                }



            }

            else{
                Toast.makeText(context, "Falta completar: "+error, Toast.LENGTH_LONG).show();
            }


        }


    }
    void crearInspeccion(boolean corregir){
        String inspector="";
        String fechaI="";
        String proxima="";
        String codigo="";
        String error="";
        String ubicacion="";

        HashMap<String, ElementInspeccion> valores=li.getValores();
        inspector=nombreInspector.getText().toString();
        fechaI=fechaInspeccion.getText().toString();
        proxima=fechaProximaInspeccion.getText().toString();
        codigo=editTextCodigo.getText().toString();
        ubicacion=editTextubicacion.getText().toString();
        existeinspeccionReciente(fechaInspeccion.getText().toString(), editTextCodigo.getText().toString());
        if(!x){
            inspeccionTipo1= new InspeccionTipo1(nombreInspeccion,inspector,fechaI,proxima,codigo,ubicacion,valores);
            FirebaseDatabase database= FirebaseDatabase.getInstance();

            DatabaseReference ref1=database.getReference("Taller").child("Inspecciones").child(nombreInspeccion);
            DatabaseReference refitems=database.getReference("Taller").child("RealizacionInspecciones");
            String tipo="rutinaria";

            if (corregir){
                proxima=fechaInspeccion.getText().toString();
                tipo="correccion";

            }

            RealizacionInspeccion objeto=new RealizacionInspeccion(codigo,proxima,nombreInspeccion,tipo);
            refitems.child(codigo).setValue(objeto);
            ref1.keepSynced(true);
            DatabaseReference ref2=ref1.push();
            ref2.keepSynced(true);
            ref2.setValue(inspeccionTipo1).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(context, "Se creo correctamente la inspeccion de: "+nombreInspeccion, Toast.LENGTH_SHORT).show();
                }
            });
            finish();

        }
    }

    /// crear excel
    public void proximafecha() throws ParseException {
        Date d=convertStringaDate(fechaInspeccion.getText().toString());
        String monthString= (String) DateFormat.format("MM",d);
        Resources res = getResources();
        String[] tipodeinspeccionSemestral = res.getStringArray(R.array.Semestral);
        boolean existeSemestral= existeEnArreglo(tipodeinspeccionSemestral,nombreInspeccion);
        String[] tipodeinspeccionAnual = res.getStringArray(R.array.Anual);
        boolean existeAnual= existeEnArreglo(tipodeinspeccionAnual,nombreInspeccion);
        String[] tipodeinspeccionMensual = res.getStringArray(R.array.Mensual);
        boolean existeMensual= existeEnArreglo(tipodeinspeccionMensual,nombreInspeccion);
        String[] tipodeinspeccionTrimestral = res.getStringArray(R.array.Trimestral);
        boolean existeTrimestral= existeEnArreglo(tipodeinspeccionTrimestral,nombreInspeccion);
        String[] tipodeinspeccionQuinquenal = res.getStringArray(R.array.Quinquenal);
        boolean existeQuinquenal= existeEnArreglo(tipodeinspeccionQuinquenal,nombreInspeccion);
        String[] tipodeinspeccionHss102 = res.getStringArray(R.array.HSS_102);
        boolean existeHSS102= existeEnArreglo(tipodeinspeccionHss102,nombreInspeccion);
        String[] tipodeinspeccionHss103 = res.getStringArray(R.array.HSS_103);
        boolean existeHSS103= existeEnArreglo(tipodeinspeccionHss103,nombreInspeccion);
        String[] tipodeinspeccionHss105 = res.getStringArray(R.array.HSS_105);
        boolean existeHSS105= existeEnArreglo(tipodeinspeccionHss105,nombreInspeccion);
        String[] tipodeinspeccionHss106 = res.getStringArray(R.array.HSS_106);
        boolean existeHSS106= existeEnArreglo(tipodeinspeccionHss106,nombreInspeccion);
        String[] tipodeinspeccionHss107 = res.getStringArray(R.array.HSS_107);
        boolean existeHSS107= existeEnArreglo(tipodeinspeccionHss107,nombreInspeccion);
        String[] tipodeinspeccionHss108 = res.getStringArray(R.array.HSS_108);
        boolean existeHSS108= existeEnArreglo(tipodeinspeccionHss108,nombreInspeccion);
        String[] tipodeinspeccionsemestre1 = res.getStringArray(R.array.semestre1);
        boolean existeSemestre1= existeEnArreglo(tipodeinspeccionsemestre1,monthString);
        String[] tipodeinspeccionsemestre2 = res.getStringArray(R.array.semestre2);
        boolean existeSemestre2= existeEnArreglo(tipodeinspeccionsemestre2,monthString);
        String[] tipodeinspecciontrimestre1 = res.getStringArray(R.array.trimestre1);
        boolean existetrimestre1= existeEnArreglo(tipodeinspecciontrimestre1,monthString);
        String[] tipodeinspecciontrimestre2 = res.getStringArray(R.array.trimestre2);
        boolean existetrimestre2= existeEnArreglo(tipodeinspecciontrimestre2,monthString);
        String[] tipodeinspecciontrimestre3 = res.getStringArray(R.array.trimestre3);
        boolean existetrimestre3= existeEnArreglo(tipodeinspecciontrimestre3,monthString);
        String[] tipodeinspecciontrimestre4 = res.getStringArray(R.array.trimestre4);
        boolean existetrimestre4= existeEnArreglo(tipodeinspecciontrimestre4,monthString);

        if(existeSemestral){
            Date fechaFinal = variarFecha(d, Calendar.MONTH, 6);
            SimpleDateFormat fecc2=new SimpleDateFormat("d/MM/yyyy");
            String fechacComplString2 = fecc2.format(fechaFinal);
            fechaProximaInspeccion.setText(fechacComplString2);

            if(existeHSS102&&existeSemestre1){
                String uri2 ="@mipmap/hss102naranja";
                int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
                Drawable imagen2 = ContextCompat.getDrawable(getApplicationContext(), imageResource2);
                img_tiposinspecciones.setImageDrawable(imagen2);
            }
            else if(existeHSS102&&existeSemestre2){
                String uri2 ="@mipmap/hss102morado";
                int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
                Drawable imagen2 = ContextCompat.getDrawable(getApplicationContext(), imageResource2);
                img_tiposinspecciones.setImageDrawable(imagen2);
            }
            if(existeHSS108&&existeSemestre1){
                String uri2 ="@mipmap/hss108naranja";
                int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
                Drawable imagen2 = ContextCompat.getDrawable(getApplicationContext(), imageResource2);
                img_tiposinspecciones.setImageDrawable(imagen2);
            }
            else if(existeHSS108&&existeSemestre2){
                String uri2 ="@mipmap/hss108morado";
                int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
                Drawable imagen2 = ContextCompat.getDrawable(getApplicationContext(), imageResource2);
                img_tiposinspecciones.setImageDrawable(imagen2);
            }

        }
        else if(existeAnual){
            Date fechaFinal = variarFecha(d, Calendar.MONTH, 12);
            SimpleDateFormat fecc2=new SimpleDateFormat("d/MM/yyyy");
            String fechacComplString2 = fecc2.format(fechaFinal);
            fechaProximaInspeccion.setText(fechacComplString2);
            if(existeHSS102){
                String uri2 ="@mipmap/hss102negro";
                int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
                Drawable imagen2 = ContextCompat.getDrawable(getApplicationContext(), imageResource2);
                img_tiposinspecciones.setImageDrawable(imagen2);
            }
            else if(existeHSS103){
                String uri2 ="@mipmap/hss103negro";
                int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
                Drawable imagen2 = ContextCompat.getDrawable(getApplicationContext(), imageResource2);
                img_tiposinspecciones.setImageDrawable(imagen2);
            }
            else if(existeHSS105){
                String uri2 ="@mipmap/hss105negro";
                int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
                Drawable imagen2 = ContextCompat.getDrawable(getApplicationContext(), imageResource2);
                img_tiposinspecciones.setImageDrawable(imagen2);
            }


        }
        else if(existeTrimestral){
            Date fechaFinal = variarFecha(d, Calendar.MONTH, 3);
            SimpleDateFormat fecc2=new SimpleDateFormat("d/MM/yyyy");
            String fechacComplString2 = fecc2.format(fechaFinal);
            fechaProximaInspeccion.setText(fechacComplString2);
            if(nombreInspeccion.equals("Extintor Portátil")){
                if(existetrimestre1){
                    String uri2 ="@mipmap/extintorrojo";
                    int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
                    Drawable imagen2 = ContextCompat.getDrawable(getApplicationContext(), imageResource2);
                    img_tiposinspecciones.setImageDrawable(imagen2);
                }
                else if(existetrimestre2){
                    String uri2 ="@mipmap/extintorverde";
                    int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
                    Drawable imagen2 = ContextCompat.getDrawable(getApplicationContext(), imageResource2);
                    img_tiposinspecciones.setImageDrawable(imagen2);
                }
                else if(existetrimestre3){
                    String uri2 ="@mipmap/extintorazul";
                    int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
                    Drawable imagen2 = ContextCompat.getDrawable(getApplicationContext(), imageResource2);
                    img_tiposinspecciones.setImageDrawable(imagen2);
                }
                else if(existetrimestre4){
                    String uri2 ="@mipmap/extintoramarillo";
                    int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
                    Drawable imagen2 = ContextCompat.getDrawable(getApplicationContext(), imageResource2);
                    img_tiposinspecciones.setImageDrawable(imagen2);
                }

            }
            else if(existeHSS105) {
                if (existetrimestre1) {
                    String uri2 = "@mipmap/hss105rojo";
                    int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
                    Drawable imagen2 = ContextCompat.getDrawable(getApplicationContext(), imageResource2);
                    img_tiposinspecciones.setImageDrawable(imagen2);
                }
                else if (existetrimestre2) {
                    String uri2 = "@mipmap/hss105verde";
                    int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
                    Drawable imagen2 = ContextCompat.getDrawable(getApplicationContext(), imageResource2);
                    img_tiposinspecciones.setImageDrawable(imagen2);
                }
                else if (existetrimestre3) {
                    String uri2 = "@mipmap/hss105azul";
                    int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
                    Drawable imagen2 = ContextCompat.getDrawable(getApplicationContext(), imageResource2);
                    img_tiposinspecciones.setImageDrawable(imagen2);
                }
                else if (existetrimestre4) {
                    String uri2 = "@mipmap/hss105amarillo";
                    int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
                    Drawable imagen2 = ContextCompat.getDrawable(getApplicationContext(), imageResource2);
                    img_tiposinspecciones.setImageDrawable(imagen2);
                }
            }
            else if(existeHSS107) {
                if (existetrimestre1) {
                    String uri2 = "@mipmap/hss107rojo";
                    int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
                    Drawable imagen2 = ContextCompat.getDrawable(getApplicationContext(), imageResource2);
                    img_tiposinspecciones.setImageDrawable(imagen2);
                }
                else if (existetrimestre2) {
                    String uri2 = "@mipmap/hss107verde";
                    int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
                    Drawable imagen2 = ContextCompat.getDrawable(getApplicationContext(), imageResource2);
                    img_tiposinspecciones.setImageDrawable(imagen2);
                }
                else if (existetrimestre3) {
                    String uri2 = "@mipmap/hss107azul";
                    int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
                    Drawable imagen2 = ContextCompat.getDrawable(getApplicationContext(), imageResource2);
                    img_tiposinspecciones.setImageDrawable(imagen2);
                }
                else if (existetrimestre4) {
                    String uri2 = "@mipmap/hss107amarillo";
                    int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
                    Drawable imagen2 = ContextCompat.getDrawable(getApplicationContext(), imageResource2);
                    img_tiposinspecciones.setImageDrawable(imagen2);
                }
            }







        }
        else if(existeMensual){
            Date fechaFinal = variarFecha(d, Calendar.MONTH, 1);
            SimpleDateFormat fecc2=new SimpleDateFormat("d/MM/yyyy");
            String fechacComplString2 = fecc2.format(fechaFinal);
            fechaProximaInspeccion.setText(fechacComplString2);
            if(existeHSS106){
                if (existetrimestre1) {
                    String uri2 = "@mipmap/hss106rojo";
                    int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
                    Drawable imagen2 = ContextCompat.getDrawable(getApplicationContext(), imageResource2);
                    img_tiposinspecciones.setImageDrawable(imagen2);
                }
                else if (existetrimestre2) {
                    String uri2 = "@mipmap/hss106verde";
                    int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
                    Drawable imagen2 = ContextCompat.getDrawable(getApplicationContext(), imageResource2);
                    img_tiposinspecciones.setImageDrawable(imagen2);
                }
                else if (existetrimestre3) {
                    String uri2 = "@mipmap/hss106azul";
                    int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
                    Drawable imagen2 = ContextCompat.getDrawable(getApplicationContext(), imageResource2);
                    img_tiposinspecciones.setImageDrawable(imagen2);
                }
                else if (existetrimestre4) {
                    String uri2 = "@mipmap/hss106amarillo";
                    int imageResource2 = getResources().getIdentifier(uri2, null, getPackageName());
                    Drawable imagen2 = ContextCompat.getDrawable(getApplicationContext(), imageResource2);
                    img_tiposinspecciones.setImageDrawable(imagen2);
                }
            }



        }
        else if(existeQuinquenal){
            Date fechaFinal = variarFecha(d, Calendar.MONTH, 5);
            SimpleDateFormat fecc2=new SimpleDateFormat("d/MM/yyyy");
            String fechacComplString2 = fecc2.format(fechaFinal);
            fechaProximaInspeccion.setText(fechacComplString2);

        }


    }

    public void crearExcel(InspeccionTipo1 inspecion){

        Workbook wb = new HSSFWorkbook();


        Map<String, CellStyle> styles = createStyles(wb);



        Sheet sheet = wb.createSheet("Inspección");
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);


        //title row
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(20);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(inspecion.getEnuunciado());
        titleCell.setCellStyle(styles.get("title"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$K$1"));


        Row row = sheet.createRow(1);
        Cell cell = row.createCell(0);
        cell.setCellValue("Empresa: ");
        cell.setCellStyle(styles.get("item_left1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$2:$B$2"));
        cell = row.createCell(2);
        cell.setCellValue("Holcim Ecuador");
        cell.setCellStyle(styles.get("item_left"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$C$2:$D$2"));

        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue("Fecha: ");
        cell.setCellStyle(styles.get("item_left1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$3:$B$3"));
        cell = row.createCell(2);
        cell.setCellValue(inspecion.getFechaInspeccion());
        cell.setCellStyle(styles.get("item_left"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$C$3:$F$3"));


        row = sheet.createRow(3);
        cell = row.createCell(0);
        cell.setCellValue("Inspector: ");
        cell.setCellStyle(styles.get("item_left1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$4:$B$4"));
        cell = row.createCell(2);
        cell.setCellValue(inspecion.getNombreInspector());
        cell.setCellStyle(styles.get("item_left"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$C$4:$F$4"));


        row = sheet.createRow(4);
        cell = row.createCell(0);
        cell.setCellValue("Código: ");
        cell.setCellStyle(styles.get("item_left1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$5:$B$5"));
        cell = row.createCell(2);
        cell.setCellValue(inspecion.getCodigo());
        cell.setCellStyle(styles.get("item_left"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$C$5:$F$5"));


        row = sheet.createRow(6);
        cell = row.createCell(0);
        cell.setCellValue("#");
        cell.setCellStyle(styles.get("header"));

        cell = row.createCell(1);
        cell.setCellValue("Punto de revisión");
        cell.setCellStyle(styles.get("header"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$B$7:$I$7"));

        cell = row.createCell(9);
        cell.setCellValue("OK");
        cell.setCellStyle(styles.get("header"));

        cell = row.createCell(10);
        cell.setCellValue("NO OK");
        cell.setCellStyle(styles.get("header"));


        ///item
        int i=0;
        for(Map.Entry<String, ElementInspeccion> entry :inspecion.getValores().entrySet() ) {
            row = sheet.createRow(i + 7);
            cell = row.createCell(0);
            cell.setCellValue(i+1);
            cell.setCellStyle(styles.get("cell"));
            ///punto de revision
            cell = row.createCell(1);
            cell.setCellValue(entry.getValue().getEnunciado());
            cell.setCellStyle(styles.get("cell"));
            for (int n = 2; n < 9; n++) {
                cell = row.createCell(n);
                cell.setCellValue("");
                cell.setCellStyle(styles.get("cell"));
            }

            //merge de las celdas
            int cont=7;
            cont= cont+i;
            sheet.addMergedRegion(new CellRangeAddress(
                    cont, //first row (0-based)
                    cont, //last row  (0-based)
                    1, //first column (0-based)
                    8 //last column  (0-based)
            ));

            //ok

            if(entry.getValue().getOk().equals("OK")){
                cell = row.createCell(9);
                cell.setCellValue("✓");
                cell.setCellStyle(styles.get("cell"));
                //no ok
                cell = row.createCell(10);
                cell.setCellValue("");
                cell.setCellStyle(styles.get("cell"));
            }
            else{
                cell = row.createCell(9);
                cell.setCellValue("");
                cell.setCellStyle(styles.get("cell"));
                cell = row.createCell(10);
                cell.setCellValue("✓");
                cell.setCellStyle(styles.get("cell"));
                //no ok

            }
            i=i+1;

        }


        String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss").format(Calendar.getInstance().getTime());
        String nombreFile=inspecion.getCodigo()+timeStamp+".xls";
        File file = new File(getExternalFilesDir(null),nombreFile);
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(file);
            wb.write(outputStream);
            Toast.makeText(getApplicationContext(),"Reporte generado correctamente",Toast.LENGTH_LONG).show();
        } catch (java.io.IOException e) {
            e.printStackTrace();

            Toast.makeText(getApplicationContext(),"NO OK",Toast.LENGTH_LONG).show();
            try {
                outputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


    }
    private static Map<String, CellStyle> createStyles(Workbook wb){
        Map<String, CellStyle> styles = new HashMap<>();
        CellStyle style;
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short)18);
        titleFont.setBold(true);
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(titleFont);
        styles.put("title", style);

        Font itemFont = wb.createFont();
        itemFont.setFontHeightInPoints((short)12);
        itemFont.setFontName("Trebuchet MS");
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setFont(titleFont);
        style.setFont(itemFont);
        styles.put("item_left", style);

        Font itemresFont = wb.createFont();
        itemresFont.setFontHeightInPoints((short)12);
        itemresFont.setFontName("Trebuchet MS");
        itemresFont.setBold(true);
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setFont(itemresFont);
        styles.put("item_left1", style);



        Font monthFont = wb.createFont();
        monthFont.setFontHeightInPoints((short)12);
        monthFont.setColor(IndexedColors.WHITE.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(monthFont);
        style.setWrapText(true);
        styles.put("header", style);

        Font cellFont = wb.createFont();
        cellFont.setFontHeightInPoints((short)12);
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setFont(cellFont);
        style.setWrapText(true);
        styles.put("cell", style);



        return styles;
    }




    ///fin crear excel
    private void configFecha(TextInputEditText tietFecha) {
        Calendar calendar= Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        tietFecha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date= day+"/"+month+"/"+year;
                        tietFecha.setText(date);
                        tietFecha.clearFocus();
                        try {
                            proximafecha();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }

                },year,month,day);
                datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_NEGATIVE) {
                            dialog.dismiss();
                            tietFecha.clearFocus();

                        }

                    }
                });

                if(hasFocus){
                    datePickerDialog.show();
                }
                else{
                    datePickerDialog.dismiss();
                    tietFecha.clearFocus();
                }

            }
        });
    }
    public static Date convertStringaDate(String fecha) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date= format.parse(fecha);
        return  date;
    }
    //confirmar presencia de inspeccion
    void existeinspeccionReciente(String fechahoy, String codigoInspeccion){
        x=false;
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("Taller").child("RealizacionInspecciones");
        myRef.keepSynced(true);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if(ds.exists()) {
                            if(ds.child("codigo").getValue().toString().equals(codigoInspeccion)){
                                String fecha=ds.child("siguientefecha").getValue().toString();
                                int dias=0;
                                try {
                                    dias=diferenciaDias(fecha, fechahoy);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if(dias>10){
                                    x=true;
                                    AlertDialog builder = new AlertDialog.Builder(context).create();
                                    builder.setCanceledOnTouchOutside(false);
                                    builder.setTitle("Alerta");
                                    builder.setIcon(R.drawable.advertencia);
                                    builder.setMessage("Esta inspección ya fue realizada para este periodo, faltan "+dias+" días para que llegue la fecha de la siguiente inspección pero puede ser realizada hasta 15 días antes del "+fecha);
                                    builder.setCancelable(false);

                                    builder.setButton(AlertDialog.BUTTON_POSITIVE, "ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            finish();
                                        }
                                    });
                                    builder.show();
                                }
                            }
                        }

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static int diferenciaDias(String fecha1,String fecha2) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date1= format.parse(fecha1);
        Date date2=format.parse(fecha2);
        int dias = (int) ((date1.getTime() - date2.getTime()) / 86400000);
        return  dias;

    }
    public boolean contieneNOOK(){
        boolean x=false;
        HashMap<String, ElementInspeccion> valores=li.getValores();
        for (ElementInspeccion valor:valores.values()){
            if(valor.getOk().equals("NO OK")){
                x=true;
            }
        }
        return x;
    }






}