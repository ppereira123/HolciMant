package com.example.mantenimientoholcim;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class GeneradorQR extends Fragment {
    private EditText edit_code;
    private Button save;
    private ImageView qrCode;
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_generador_q_r, container, false);

        edit_code= root.findViewById(R.id.edit_code);
        save= root.findViewById(R.id.save);
        qrCode= root.findViewById(R.id.qr_code);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCode();
            }
        });

        return  root;

    }
    private void getCode(){
        try {
            qrcode();

        }
        catch(Exception e){
            e.printStackTrace();

        }
    }
    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

    public void qrcode() throws WriterException {
        BitMatrix bitMatrix= multiFormatWriter.encode(edit_code.getText().toString(), BarcodeFormat.QR_CODE,600,600);
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
        qrCode.setImageBitmap(bitmap);

    }


}