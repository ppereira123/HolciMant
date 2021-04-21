package com.example.mantenimientoholcim.ui.slideshow;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mantenimientoholcim.Dialogos.DialogoVerInspeccion;
import com.example.mantenimientoholcim.R;

public class SlideshowFragment extends Fragment {
    int estadoperfil=1;
    int estadoherramienta=1;
    int estadoinspecion=1;
    int estadoregistro=1;
    int estadoetiquetas=1;
    int estadogenerar=1;
    int estadoguardadas=1;
    int estadoescaner=1;
    LinearLayout perfil,herramientas, inspecciones, escaner,registro,etiquetas,generar,guardadas;
    Button btnperfil,btnherramienta,btninspecion,btnescaner,btnRegistro,btnEtiquetas,btnGenerar,btnGuardadas;
    Context context;


    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        perfil= root.findViewById(R.id.LLInfPerfil);
        herramientas=root.findViewById(R.id.LLinfoHerramienta);
        inspecciones=root.findViewById(R.id.LLinforInspeciones);
        btnperfil=root.findViewById(R.id.btnInfperfil);
        btnherramienta=root.findViewById(R.id.btninfoHerramienta);
        btninspecion=root.findViewById(R.id.btnIngfInspec);
        btnescaner=root.findViewById(R.id.btninfescaner);
        btnRegistro=root.findViewById(R.id.btninfregistro);
        btnEtiquetas=root.findViewById(R.id.btninfetiquetas);
        btnGenerar=root.findViewById(R.id.bntinfGenerar);
        btnGuardadas=root.findViewById(R.id.btninfGuardadas);
        escaner=root.findViewById(R.id.LLinfrescaner);
        registro=root.findViewById(R.id.LLinfRegistro);
        etiquetas=root.findViewById(R.id.LLinfetiquetas);
        generar=root.findViewById(R.id.LLinfgenerar);
        guardadas=root.findViewById(R.id.LLinfGuardadas);






        btnperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estadoperfil=estadoperfil+1;


                if(estadoperfil % 2 == 0){
                    perfil.setVisibility(View.VISIBLE);
                }else {
                    perfil.setVisibility(View.GONE);
                }
            }
        });
        btnherramienta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estadoherramienta=estadoherramienta+1;
                if(estadoherramienta % 2 == 0){
                    herramientas.setVisibility(View.VISIBLE);
                }else {
                    herramientas.setVisibility(View.GONE);
                }
            }
        });
        btninspecion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estadoinspecion=estadoinspecion+1;
                if(estadoinspecion % 2 == 0){
                    inspecciones.setVisibility(View.VISIBLE);
                }else {
                    inspecciones.setVisibility(View.GONE);
                }
            }
        });
        btnGenerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estadogenerar=estadogenerar+1;
                if(estadogenerar % 2 == 0){
                    generar.setVisibility(View.VISIBLE);
                }else {
                    generar.setVisibility(View.GONE);
                }
            }
        });
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estadoregistro=estadoregistro+1;
                if(estadoregistro % 2 == 0){
                    registro.setVisibility(View.VISIBLE);
                }else {
                    registro.setVisibility(View.GONE);
                }
            }
        });
        btnEtiquetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estadoetiquetas=estadoetiquetas+1;
                if(estadoetiquetas % 2 == 0){
                    etiquetas.setVisibility(View.VISIBLE);
                }else {
                    etiquetas.setVisibility(View.GONE);
                }
            }
        });
        btnGuardadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estadoguardadas=estadoguardadas+1;
                if(estadoguardadas % 2 == 0){
                    guardadas.setVisibility(View.VISIBLE);
                }else {
                    guardadas.setVisibility(View.GONE);
                }
            }
        });
        btnescaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estadoescaner=estadoescaner+1;
                if(estadoescaner % 2 == 0){
                    escaner.setVisibility(View.VISIBLE);
                }else {
                    escaner.setVisibility(View.GONE);
                }
            }
        });









        return root;
    }
}