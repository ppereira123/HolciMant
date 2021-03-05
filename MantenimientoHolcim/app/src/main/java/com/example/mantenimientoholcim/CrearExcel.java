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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
        Workbook workbook= new HSSFWorkbook();
        Cell cell = null;
        CellStyle cellStyle= workbook.createCellStyle();
        cellStyle.setFillBackgroundColor(HSSFColor.LIGHT_BLUE.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        Sheet sheet = null;
        sheet = workbook.createSheet("Lista de usuarios");

        Row row = null;
        row= sheet.createRow(0);
        cell=row.createCell(0);
        cell.setCellValue("USUARIO");
        cell.setCellStyle(cellStyle);

        sheet.createRow(1);
        cell= row.createCell(1);
        cell.setCellStyle(cellStyle);
        row=sheet.createRow(1);
        cell= row.createCell(0);
        cell.setCellValue("Nombre:");
        cell= row.createCell(1);
        cell.setCellValue("Pierina Pereira");

        File file= new File(getExternalFilesDir(null),"Relacion_Usuario.xls");
        FileOutputStream outputStream= null;
        try{
            outputStream= new FileOutputStream(file);
            workbook.write(outputStream);
            Toast.makeText(this, "Documento creado", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

}