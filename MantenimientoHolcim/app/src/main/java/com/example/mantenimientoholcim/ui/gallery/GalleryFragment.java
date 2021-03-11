package com.example.mantenimientoholcim.ui.gallery;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.viewpager.widget.ViewPager;

import com.example.mantenimientoholcim.AdaptadorItemInspecciones;
import com.example.mantenimientoholcim.Modelo.ItemInspeccion;

import com.example.mantenimientoholcim.PagerAdapter;
import com.example.mantenimientoholcim.PlantillasInspeccion;
import com.example.mantenimientoholcim.R;
import com.example.mantenimientoholcim.RevisionPuntosBloqueo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tab1, tab2;
    private PagerAdapter pagerAdapter;
    private GalleryViewModel galleryViewModel;





    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        View root= inflater.inflate(R.layout.fragment_gallery, container, false);



        //tab
        tabLayout= (TabLayout) root.findViewById(R.id.tablayout);
        tab1 = (TabItem) root.findViewById(R.id.tab1);
        tab2 = (TabItem) root.findViewById(R.id.tab2);
        viewPager = root.findViewById(R.id.viewpager);


        pagerAdapter= new PagerAdapter (getActivity().getSupportFragmentManager(), 2);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        // fin tab






        return root;
    }




}