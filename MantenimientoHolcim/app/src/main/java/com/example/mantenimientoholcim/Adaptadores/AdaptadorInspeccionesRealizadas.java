package com.example.mantenimientoholcim.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.StrictMode;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mantenimientoholcim.Modelo.ElementInspeccion;
import com.example.mantenimientoholcim.Modelo.InspeccionTipo1;
import com.example.mantenimientoholcim.R;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;


public class AdaptadorInspeccionesRealizadas extends BaseAdapter {
    private Context context;
    private ArrayList<InspeccionTipo1> listItems;
    ListadaptaritemsInspeccionesRealizadas li;
    Activity activity;
    View root;
    private String correo="";
    LayoutInflater mInflater;
    ImageView imagInspeccion;
    String imagenInspeccion="";


    public AdaptadorInspeccionesRealizadas(Context context) {
        this.context = context;
    }

    public AdaptadorInspeccionesRealizadas(Context context, ArrayList<InspeccionTipo1> listItems) {
        this.context = context;
        this.listItems = listItems;

    }
    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        InspeccionTipo1 item= (InspeccionTipo1) getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.list_inspecciones_realizadas, null);
        mInflater= LayoutInflater.from(context);
        TextView nombretv= (TextView) convertView.findViewById(R.id.inspector_ir);
        TextView titulotv= (TextView) convertView.findViewById(R.id.nombre_inspeccion);
        TextView fechatv= (TextView) convertView.findViewById(R.id.fechaIr);
        TextView codigotv= (TextView) convertView.findViewById(R.id.codigoIr);
        Button generarExcel= (Button) convertView.findViewById(R.id.crearExcel);
        Button ver= (Button) convertView.findViewById(R.id.ver);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder(); StrictMode.setVmPolicy(builder.build());


        nombretv.setText(item.getNombreInspector());
        titulotv.setText(item.getEnuunciado());
        fechatv.setText(item.getFechaInspeccion());
        codigotv.setText(item.getCodigo());
        generarExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedirCorreo(item);
            }
        });
        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(item.getEnuunciado());
                View view = mInflater.inflate(R.layout.adaptardorverinspeciones, null);
                TextView editTextCodigo,fechaInspeccion,fechaProximaInspeccion,nombreInspector,txtubicacionIR;
                ImageView imagInspeccion;
                ListView rvInspeccionesRv;
                rvInspeccionesRv=view.findViewById(R.id.rvInspeccionesRv);
                editTextCodigo=view.findViewById(R.id.txtCodigoArticulo);
                fechaInspeccion=view.findViewById(R.id.txtFechaInspeccion);
                fechaProximaInspeccion=view.findViewById(R.id.txtProximaInspeccion);
                nombreInspector=view.findViewById(R.id.txtNombreInspeccion);
                imagInspeccion=view.findViewById(R.id.imgInspeccionRv);
                txtubicacionIR=view.findViewById(R.id.txtubicacionIR);

                Resources res = context.getResources();
                String[] nombre_inspecciones = res.getStringArray(R.array.combo_inspeccionesNombre);
                String[] imagenesdeinspeccion = res.getStringArray(R.array.combo_inspeccionesImagenes);
                int posicion=posicionexisteEnArreglo(nombre_inspecciones, item.getEnuunciado());
                if (posicion==-1){
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }else{
                    imagenInspeccion= imagenesdeinspeccion[posicion];
                    String uri =imagenInspeccion;
                    int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
                    Drawable imagen = ContextCompat.getDrawable(context.getApplicationContext(), imageResource);
                    imagInspeccion.setImageDrawable(imagen);
                }
                ArrayList<ElementInspeccion> listItems= (ArrayList<ElementInspeccion>) hashToList(item.getValores());
                AdapterList_verInspeccion adaptador= new AdapterList_verInspeccion(context, listItems);
                rvInspeccionesRv.setAdapter(adaptador);

                nombreInspector.setText("Nombre: "+item.getNombreInspector());
                fechaInspeccion.setText("Fecha Inspeccion: "+item.getFechaInspeccion());
                fechaProximaInspeccion.setText("Fecha Proxima Inspeccion: "+item.getProximaInspeccion());
                editTextCodigo.setText("Codigo articulo: "+item.getCodigo());
                txtubicacionIR.setText("Ubicación: "+item.getUbicacion());
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


                builder.setView(view);
                builder.show();





            }
        });



        return convertView;
    }

    public void cargar(InspeccionTipo1 inspecion){



    }
    public static int posicionexisteEnArreglo(String[] arreglo, String busqueda) {
        for (int x = 0; x < arreglo.length; x++) {
            if (arreglo[x].equals(busqueda)) {
                return x;
            }
        }
        return -1;
    }
    public static List<ElementInspeccion> hashToList(HashMap<String, ElementInspeccion> hash){
        List<ElementInspeccion> list= new ArrayList();
        for(Map.Entry m:hash.entrySet()){
            ElementInspeccion i= (ElementInspeccion) m.getValue();
            list.add(i);
        }
        return list;
    }



    void pedirCorreo(InspeccionTipo1 inspecion){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Ingresa correo al que deseas enviar");

// Set up the input
        final EditText input = new EditText(context);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                correo = input.getText().toString();
                crearExcel(inspecion);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
/// crear excel

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
        row = sheet.createRow(5);
        cell = row.createCell(0);
        cell.setCellValue("Ubicación: ");
        cell.setCellStyle(styles.get("item_left1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$6:$B$6"));
        cell = row.createCell(2);
        cell.setCellValue(inspecion.getUbicacion());
        cell.setCellStyle(styles.get("item_left"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$C$6:$F$6"));


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
        File file = new File(context.getExternalFilesDir(null),nombreFile);
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(file);
            wb.write(outputStream);
            Toast.makeText(context.getApplicationContext(),"Reporte generado correctamente",Toast.LENGTH_LONG).show();
            String[] mailto = {correo};
            Uri uri = Uri.fromFile(file);
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, mailto);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Reporte de la inspección de:  "+inspecion.getEnuunciado()+", del articulo con codigo: "+inspecion.getCodigo());
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,"Fecha de reporte:  "+inspecion.getFechaInspeccion()+".\nInspector:"+inspecion.getNombreInspector()+".\nAtentamente HolcimMant.");
            emailIntent.setType("application/pdf");
            emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
            context.startActivity(Intent.createChooser(emailIntent, "Send email using:"));




        } catch (java.io.IOException e) {
            e.printStackTrace();

            Toast.makeText(context.getApplicationContext(),"NO OK",Toast.LENGTH_LONG).show();
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





}
