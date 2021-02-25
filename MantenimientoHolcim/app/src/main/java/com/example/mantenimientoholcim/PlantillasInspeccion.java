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

        El cableado eléctrico se encuentra en buen estado
        Los enchufes no poseen daños o prensacables faltantes
        El botón de emergencia se encuentra operativo
        La mecánica y el funcionamiento del acelerador se encuentra operativa
        Botoneras marcha y paro se encuentran operativas
        Los fusibles se encuentran operativos, no se han realizado bypass (puentes)
                Los bornes de la batería no presentan corrosión o daños
        Los cables eléctricos de conexión de la batería se encuentran en buen estado
        La carcasa de la batería no presentan daños
        Las conexiones o cables del cargador no presentan daños o partes expuestas



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





















    }

    public  void guardar(View view){
        HashMap<String,String> valores=li.getValores();
        editTextCodigo.setText(String.valueOf(valores.size()));

    }
}