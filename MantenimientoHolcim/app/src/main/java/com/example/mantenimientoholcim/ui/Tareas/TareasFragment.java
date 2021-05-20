package com.example.mantenimientoholcim.ui.Tareas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mantenimientoholcim.Adaptadores.PagerAdapter;
import com.example.mantenimientoholcim.Adaptadores.PagerAdapterTareas;
import com.example.mantenimientoholcim.R;
import com.example.mantenimientoholcim.ui.Inspecciones.GalleryViewModel;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class TareasFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tab1, tab2, tab3;
    private PagerAdapterTareas pagerAdapter;
    private GalleryViewModel galleryViewModel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_tareas, container, false);
        //tab
        tabLayout= (TabLayout) root.findViewById(R.id.tablayoutTareas);
        tab1 = (TabItem) root.findViewById(R.id.tab1Tareas);
        tab2 = (TabItem) root.findViewById(R.id.tab2Tareas);
        viewPager = root.findViewById(R.id.viewpagerTareas);


        pagerAdapter= new PagerAdapterTareas (getActivity().getSupportFragmentManager(), 2);
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
        return root;
    }
}