package com.example.mantenimientoholcim;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mantenimientoholcim.Herramientas.EscanerFragment;
import com.example.mantenimientoholcim.Herramientas.GeneradorQR;
import com.example.mantenimientoholcim.Herramientas.RegistroFragment;

public class PagerAdapterhome extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterhome(@NonNull FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return EscanerFragment.newInstance();
            case 1: return new RegistroFragment();
            case 2: return new GeneradorQR();
            default: return null;}
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
