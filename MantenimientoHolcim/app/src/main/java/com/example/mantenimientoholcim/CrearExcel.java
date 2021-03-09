package com.example.mantenimientoholcim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class CrearExcel extends AppCompatActivity {
    Button btnGuardarExcel;
    TextView tvDatos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_excel);
        btnGuardarExcel = findViewById(R.id.btnGuardarExcel);
        tvDatos= findViewById(R.id.tvDatos);
        btnGuardarExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });
    }

    public  void guardar(){

        Workbook wb = new HSSFWorkbook();


        Map<String, CellStyle> styles = createStyles(wb);



        Sheet sheet = wb.createSheet("Timesheet");
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);


        //title row
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(20);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Inspección");
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
        cell.setCellValue("10/10/2021");
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
        cell.setCellValue("TEP-2");
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
        row = sheet.createRow(7);
        cell = row.createCell(0);
        cell.setCellValue("1");
        cell.setCellStyle(styles.get("cell"));
        ///punto de revision
        cell = row.createCell(1);
        cell.setCellValue("Gancho o soporte a máximo 1,5 m de altura desde el piso");
        cell.setCellStyle(styles.get("cell"));
        for (int n=2; n<9; n++ ){
            cell = row.createCell(n);
            cell.setCellValue("");
            cell.setCellStyle(styles.get("cell"));
        }


        sheet.addMergedRegion(CellRangeAddress.valueOf("$B$8:$I$8"));

        //ok
        cell = row.createCell(9);
        cell.setCellValue("✓");
        cell.setCellStyle(styles.get("cell"));
        //no ok
        cell = row.createCell(10);
        cell.setCellValue("");
        cell.setCellStyle(styles.get("cell"));
















        String nombreFile="w2.xls";
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



}

