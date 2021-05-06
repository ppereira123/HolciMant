package com.example.mantenimientoholcim.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.mantenimientoholcim.Adaptadores.PagerAdapterhome;
import com.example.mantenimientoholcim.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class HerramientasFragment extends Fragment {

    private HerramientasViewModel herramientasViewModel;



    //tab
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tab1, tab2, tab3;
    private PagerAdapterhome pagerAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        herramientasViewModel =
                new ViewModelProvider(this).get(HerramientasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_herramientas, container, false);
        //rvItems=root.findViewById(R.id.rvHerramientas);

        //tab
        tabLayout= (TabLayout) root.findViewById(R.id.tablayouthome);
        tab1 = (TabItem) root.findViewById(R.id.tab1home);
        tab2 = (TabItem) root.findViewById(R.id.tab2home);
        tab3 = (TabItem) root.findViewById(R.id.tab3home);
        viewPager = root.findViewById(R.id.viewpagerhome);


        pagerAdapter= new PagerAdapterhome(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
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