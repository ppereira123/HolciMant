package com.example.mantenimientoholcim.Herramientas;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mantenimientoholcim.Adaptadores.ListAdapterItem;
import com.example.mantenimientoholcim.Modelo.ElementInspeccion;
import com.example.mantenimientoholcim.Modelo.InspeccionTipo1;
import com.example.mantenimientoholcim.Modelo.InternalStorage;
import com.example.mantenimientoholcim.Modelo.Item;
import com.example.mantenimientoholcim.Modelo.RealizacionInspeccion;
import com.example.mantenimientoholcim.Modelo.UsersData;
import com.example.mantenimientoholcim.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.mantenimientoholcim.ui.Inspecciones.PlantillasInspeccion.diferenciaDias;


public class RegistroFragment extends Fragment {
    private RegistroFragment tab2Home;
    private RecyclerView rvItems;
    private SwipeRefreshLayout swipe;
    SearchView searchView;
    private FloatingActionButton fabItem;
    View root;
    List<Item> listitems=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_registro, container, false);
        rvItems=root.findViewById(R.id.rvHerramientas);
        fabItem=root.findViewById(R.id.fabItems);
        searchView = root.findViewById(R.id.buscartHerramientas);
        swipe=root.findViewById(R.id.swipeRegistros);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarItems();
            }
        });

        InternalStorage storage=new InternalStorage();
        String archivos[]=getContext().fileList();
        if (storage.ArchivoExiste(archivos,"admin.txt")){
            UsersData data= storage.cargarArchivo(root.getContext());
            if (data.isAdmin()==false){

                 fabItem.setVisibility(View.GONE);
            }
            else{
                fabItem.setVisibility(View.VISIBLE);
            }
        }
        cargarItems();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                buscar(s);
                return true;
            }
        });


        fabItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(root.getContext(), CrearItem.class);
                startActivity(intent);
            }
        });






        return root;
    }
    void buscar(String s){
        ArrayList<Item> milista = new ArrayList<>();
        for (Item obj: listitems){
            if(obj.getDescripcion().toLowerCase().contains(s.toLowerCase())){
                milista.add(obj);
            }

        }
        Collections.sort(listitems, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return (o1.getDescripcion().compareTo(o2.getDescripcion()));
            }
        });
        ListAdapterItem adapterItem= new ListAdapterItem(milista, root.getContext());
        rvItems.setAdapter(adapterItem);

    }

    void cargarItems(){
        List<String> listadescripcion=new ArrayList<>();

        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("Taller").child("Items");
        myRef.keepSynced(true);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Item> items=new ArrayList<>();

                for(DataSnapshot ds: snapshot.getChildren()){
                    String codigo=ds.getKey();
                    String marca=ds.child("marca").getValue().toString();
                    String descripcion=ds.child("descripcion").getValue().toString();
                    String observacion=ds.child("observacion").getValue().toString();
                    int stock=Integer.parseInt(ds.child("stock").getValue().toString());
                    int stockdisponible=Integer.parseInt(ds.child("stockDisponible").getValue().toString());
                    String estante=ds.child("estante").getValue().toString();
                    String ubicacion=ds.child("ubicacion").getValue().toString();
                    int vidaUtil=Integer.parseInt(ds.child("vidaUtil").getValue().toString());
                    String tipoInspeccion=ds.child("tipoInspeccion").getValue().toString();
                        Item item= new Item(codigo,marca,descripcion,observacion,stock,stockdisponible,ubicacion,vidaUtil,tipoInspeccion,estante);
                    if(!items.contains(item)){
                        items.add(item);
                        listadescripcion.add(item.getDescripcion());
                    }
                }
                Log.d("myTag", listadescripcion.toString());
                listitems=items;
                rvItems=root.findViewById(R.id.rvHerramientas);
                Collections.sort(items, new Comparator<Item>() {
                    @Override
                    public int compare(Item o1, Item o2) {
                        return (o1.getDescripcion().compareTo(o2.getDescripcion()));
                    }
                });
                //crearExcel(items);
                ListAdapterItem li= new ListAdapterItem(items,root.getContext());
                rvItems.setHasFixedSize(true);
                rvItems.setLayoutManager(new LinearLayoutManager(root.getContext()));
                rvItems.setAdapter(li);
                swipe.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    /*

    public void crearExcel(List<Item> inspecion){

        Workbook wb = new HSSFWorkbook();


        Map<String, CellStyle> styles = createStyles(wb);



        Sheet sheet = wb.createSheet("Listado");
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);


        //title row
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(20);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Listado de herramientas");
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
        cell.setCellValue("17/5/2021");
        cell.setCellStyle(styles.get("item_left"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$C$3:$F$3"));


        row = sheet.createRow(3);
        cell = row.createCell(0);
        cell.setCellValue("Inspector: ");
        cell.setCellStyle(styles.get("item_left1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$4:$B$4"));
        cell = row.createCell(2);
        cell.setCellValue("Pierina Pereira");
        cell.setCellStyle(styles.get("item_left"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$C$4:$F$4"));


        row = sheet.createRow(4);
        cell = row.createCell(0);
        cell.setCellValue("Código: ");
        cell.setCellStyle(styles.get("item_left1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$5:$B$5"));
        cell = row.createCell(2);
        cell.setCellValue("pieirna");
        cell.setCellStyle(styles.get("item_left"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$C$5:$F$5"));


        row = sheet.createRow(6);
        cell = row.createCell(0);
        cell.setCellValue("Codigo");
        cell.setCellStyle(styles.get("header"));

        cell = row.createCell(1);
        cell.setCellValue("Descripción");
        cell.setCellStyle(styles.get("header"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$B$7:$I$7"));

        cell = row.createCell(9);
        cell.setCellValue("Ubicación");
        cell.setCellStyle(styles.get("header"));

        cell = row.createCell(10);
        cell.setCellValue("Marca");
        cell.setCellStyle(styles.get("header"));

        cell = row.createCell(10);
        cell.setCellValue("Cantidad");
        cell.setCellStyle(styles.get("header"));


        ///item
        int i=0;
        for(Item objeto :inspecion) {
            row = sheet.createRow(i + 7);
            cell = row.createCell(0);
            cell.setCellValue(objeto.getCodigo());
            cell.setCellStyle(styles.get("cell"));
            ///punto de revision
            cell = row.createCell(1);
            cell.setCellValue(objeto.getDescripcion());
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


                cell = row.createCell(9);
                cell.setCellValue(objeto.getUbicacion());
                cell.setCellStyle(styles.get("cell"));
                //no ok
                cell = row.createCell(10);
                cell.setCellValue(objeto.getMarca());
                cell.setCellStyle(styles.get("cell"));

            cell = row.createCell(11);
            cell.setCellValue(objeto.getStock());
            cell.setCellStyle(styles.get("cell"));

            i=i+1;

        }



        String nombreFile="reporte.xls";
        File file = new File(root.getContext().getExternalFilesDir(null),nombreFile);
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(file);
            wb.write(outputStream);
            Toast.makeText(root.getContext().getApplicationContext(),"Reporte generado correctamente",Toast.LENGTH_LONG).show();
        } catch (java.io.IOException e) {
            e.printStackTrace();

            Toast.makeText(root.getContext().getApplicationContext(),"NO OK",Toast.LENGTH_LONG).show();
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

     */
 

    public boolean isAtLeast(Lifecycle.State state) {
        return false;
    }
}