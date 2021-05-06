package com.example.mantenimientoholcim.Adaptadores;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mantenimientoholcim.ui.Inspecciones.Generar;
import com.example.mantenimientoholcim.ui.Inspecciones.Guardadas;
import com.example.mantenimientoholcim.ui.Inspecciones.Pendientes;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(@NonNull FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new Generar();
            case 1: return new Guardadas();
            case 2: return new Pendientes();
            default: return null;}
    }

    @Override
    public int getCount() {
            return mNumOfTabs;
    }


}

