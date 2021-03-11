package com.example.mantenimientoholcim;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

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
            case 0: return new tab1Home();
            case 1: return new tab2Home();
            case 2: return new GeneradorQR();
            default: return null;}
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
