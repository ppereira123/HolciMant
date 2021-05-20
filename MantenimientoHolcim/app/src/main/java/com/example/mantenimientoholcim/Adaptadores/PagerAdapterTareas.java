package com.example.mantenimientoholcim.Adaptadores;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mantenimientoholcim.ui.Tareas.HistorialTareasFragment;
import com.example.mantenimientoholcim.ui.Tareas.tareasporhacerFragment;

public class PagerAdapterTareas extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    public PagerAdapterTareas(@NonNull FragmentManager fm, int mNumOfTabs) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new tareasporhacerFragment();
            case 1: return new HistorialTareasFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
