package com.example.mantenimientoholcim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlantillasInspeccion extends AppCompatActivity {
    RecyclerView rvInspecciones;
    Context context=this;
    List<List<String>> tipoInspecciones= new ArrayList<>();
    ListAdapterInspeccion li;
    EditText editTextCodigo;
    TextView txtnombreInspecciones;
    String nombreInspeccion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantillas_inspeccion);
        editTextCodigo=findViewById(R.id.editTextCodigoInspeccion);

        cargarInspecciones();
        int posicion=getIntent().getIntExtra("posicion",0);
        rvInspecciones=findViewById(R.id.rvInspecciones);
        li= new ListAdapterInspeccion(tipoInspecciones.get(posicion),this);
        rvInspecciones.setHasFixedSize(true);
        rvInspecciones.setLayoutManager(new LinearLayoutManager(context));
        rvInspecciones.setAdapter(li);
        //nombreInspeccion= new String(R.array.combo_posiciones.get(posicion));
        //txtnombreInspecciones.setText(nombreInspeccion);
    }

    void cargarInspecciones(){
        List<String> inspeccion0= new ArrayList<>();
        inspeccion0.add("Inspeccione hebillas, anillas de cuerpo, broches, dedales y almohadillas. No hay distorsión, o tienen bordes agudos, ruidos, fracturas, o partes desgastadas.");
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
        inspeccion6.add("Los travesaños o escalones, clavos, tornillos, pernos u otras partes metálicas no se encuentran sueltas" );
        inspeccion6.add("están sueltas si usted puede moverlas a mano");inspeccion6.add("Largeros, abrazaderas, escalones o travesaños no están rajados, partidos, abiertos, cortados o rotos");
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



    }

    public  void guardar(View view){
        HashMap<String,String> valores=li.getValores();
        editTextCodigo.setText(String.valueOf(valores.size()));

    }
}