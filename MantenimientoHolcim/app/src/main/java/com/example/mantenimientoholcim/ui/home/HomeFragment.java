package com.example.mantenimientoholcim.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.mantenimientoholcim.AdaptadorItemInspecciones;
import com.example.mantenimientoholcim.CrearItem;
import com.example.mantenimientoholcim.ListAdapterItem;
import com.example.mantenimientoholcim.Modelo.Item;
import com.example.mantenimientoholcim.PagerAdapter;
import com.example.mantenimientoholcim.PagerAdapterhome;
import com.example.mantenimientoholcim.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;



    //tab
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tab1, tab2, tab3;
    private PagerAdapterhome pagerAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
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