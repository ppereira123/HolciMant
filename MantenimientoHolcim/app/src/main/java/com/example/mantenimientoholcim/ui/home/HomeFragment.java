package com.example.mantenimientoholcim.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mantenimientoholcim.CrearItem;
import com.example.mantenimientoholcim.ListAdapterItem;
import com.example.mantenimientoholcim.Modelo.Item;
import com.example.mantenimientoholcim.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView rvItems;
    private FloatingActionButton fabItem;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        //rvItems=root.findViewById(R.id.rvHerramientas);
        fabItem=root.findViewById(R.id.fabItems);
        fabItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(root.getContext(), CrearItem.class);
                startActivity(intent);
            }
        });
        cargarItems();
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    void cargarItems(){
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("Items");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Item> items=new ArrayList<>();
                for(DataSnapshot ds: snapshot.getChildren()){
                    String codigo=ds.getKey();
                    String marca=ds.child("marca").getValue().toString();
                    String descripcion=ds.child("descripcion").getValue().toString();
                    String observacion=ds.child("observacion").getValue().toString();
                    int stock=Integer.parseInt(ds.child("stock").getValue().toString());
                    String estado=ds.child("estado").getValue().toString();
                    String ubicacion=ds.child("ubicacion").getValue().toString();
                    int vidaUtil=Integer.parseInt(ds.child("vidaUtil").getValue().toString());
                    String tipoInspeccion=ds.child("tipoInspeccion").getValue().toString();;
                    Item item= new Item(codigo,marca,descripcion,observacion,stock,estado,ubicacion,vidaUtil,tipoInspeccion);
                    if(!items.contains(item)){
                    items.add(item);
                    }
                }
                rvItems=root.findViewById(R.id.rvHerramientas);
                ListAdapterItem li= new ListAdapterItem(items,root.getContext());
                rvItems.setHasFixedSize(true);
                rvItems.setLayoutManager(new LinearLayoutManager(root.getContext()));
                rvItems.setAdapter(li);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}