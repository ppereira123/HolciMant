package com.example.mantenimientoholcim;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mantenimientoholcim.Modelo.Item;
import com.example.mantenimientoholcim.ui.home.HomeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class tab2Home extends Fragment {
    private tab2Home tab2Home;
    private RecyclerView rvItems;
    SearchView searchView;
    private FloatingActionButton fabItem;
    View root;
    List<Item> listitems;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_tab2_home, container, false);
        //rvItems=root.findViewById(R.id.rvHerramientas);
        fabItem=root.findViewById(R.id.fabItems);
        searchView = root.findViewById(R.id.buscartHerramientas);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                buscar(s);
                return true;
            }
        });


        fabItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(root.getContext(), CrearItem.class);
                startActivity(intent);
            }
        });
        cargarItems();





        return root;
    }
    void buscar(String s){
        ArrayList<Item> milista = new ArrayList<>();
        for (Item obj: listitems){
            if(obj.getDescripcion().toLowerCase().contains(s.toLowerCase())){
                milista.add(obj);
            }

        }
        ListAdapterItem adapterItem= new ListAdapterItem(milista, root.getContext());
        rvItems.setAdapter(adapterItem);

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
                    String tipoInspeccion=ds.child("tipoInspeccion").getValue().toString();
                    Item item= new Item(codigo,marca,descripcion,observacion,stock,estado,ubicacion,vidaUtil,tipoInspeccion);
                    if(!items.contains(item)){
                        items.add(item);
                    }
                }
                listitems=items;
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