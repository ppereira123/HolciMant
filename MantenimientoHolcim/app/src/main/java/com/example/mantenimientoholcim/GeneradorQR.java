package com.example.mantenimientoholcim;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
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
    Bitmap etiqueta;
    View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_generador_q_r, container, false);

        edit_code= root.findViewById(R.id.edit_code);
        save= root.findViewById(R.id.save);
        qrCode= root.findViewById(R.id.qr_code);
        //drawTextOnBitmap(getContext(),50,"Pierina");


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
    public void crearEtiqueta() {

    }
    public Bitmap drawTextOnBitmap(Context context, int resId, String text) {
        // prepare canvas
        Resources resources = context.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, resId);

        //android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
        // set default bitmap config if none
      //  if (bitmapConfig == null) {
       //     bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
       // }
        // resource bitmaps are immutable, so we need to convert it to mutable one
        //bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);

        // new antialiased Paint
        TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.rgb(61, 61, 61));
        // text size in pixels
        paint.setTextSize((int) (bitmap.getHeight() / 10 * scale));
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        // set text width to canvas width minus 16dp padding
        int textWidth = canvas.getWidth() - (int) (16 * scale);

        // init StaticLayout for text
        StaticLayout textLayout = new StaticLayout(text, paint, textWidth,
                Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);

        // get height of multiline text
        int textHeight = textLayout.getHeight();

        // get position of text's top left corner
        float x = (bitmap.getWidth() - textWidth) / 2;
        float y = (bitmap.getHeight() - textHeight) / 2;

        // draw text to the Canvas center
        canvas.save();
        canvas.translate(x, y);
        textLayout.draw(canvas);
        canvas.restore();
        qrCode.setImageBitmap(bitmap);

        return bitmap;
    }




    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

    public void qrcode() throws WriterException {
        BitMatrix bitMatrix= multiFormatWriter.encode(edit_code.getText().toString(), BarcodeFormat.QR_CODE,600,600);
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
        qrCode.setImageBitmap(bitmap);
        etiqueta= bitmap;


    }


}