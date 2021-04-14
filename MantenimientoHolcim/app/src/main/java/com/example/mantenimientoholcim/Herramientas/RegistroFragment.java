package com.example.mantenimientoholcim.Herramientas;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mantenimientoholcim.CrearItem;
import com.example.mantenimientoholcim.ListAdapterItem;
import com.example.mantenimientoholcim.Modelo.InternalStorage;
import com.example.mantenimientoholcim.Modelo.Item;
import com.example.mantenimientoholcim.Modelo.UsersData;
import com.example.mantenimientoholcim.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class RegistroFragment extends Fragment {
    private RegistroFragment tab2Home;
    private RecyclerView rvItems;
    private SwipeRefreshLayout swipe;
    SearchView searchView;
    private FloatingActionButton fabItem;
    View root;
    List<Item> listitems=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_registro, container, false);
        rvItems=root.findViewById(R.id.rvHerramientas);
        fabItem=root.findViewById(R.id.fabItems);
        searchView = root.findViewById(R.id.buscartHerramientas);
        swipe=root.findViewById(R.id.swipeRegistros);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarItems();
            }
        });

        InternalStorage storage=new InternalStorage();
        String archivos[]=getContext().fileList();
        if (storage.ArchivoExiste(archivos,"admin.txt")){
            UsersData data= storage.cargarArchivo(root.getContext());
            if (data.isAdmin()==false){

                 fabItem.setVisibility(View.GONE);
            }
            else{
                fabItem.setVisibility(View.VISIBLE);
            }
        }
        cargarItems();
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
        myRef.keepSynced(true);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Item> items=new ArrayList<>();

                for(DataSnapshot ds: snapshot.getChildren()){
                    String codigo=ds.getKey();
                    String marca=ds.child("marca").getValue().toString();
                    String descripcion=ds.child("descripcion").getValue().toString();
                    String observacion=ds.child("observacion").getValue().toString();
                    int stock=Integer.parseInt(ds.child("stock").getValue().toString());
                    int stockdisponible=Integer.parseInt(ds.child("stockDisponible").getValue().toString());
                    String estante=ds.child("estante").getValue().toString();
                    String ubicacion=ds.child("ubicacion").getValue().toString();
                    int vidaUtil=Integer.parseInt(ds.child("vidaUtil").getValue().toString());
                    String tipoInspeccion=ds.child("tipoInspeccion").getValue().toString();
                        Item item= new Item(codigo,marca,descripcion,observacion,stock,stockdisponible,ubicacion,vidaUtil,tipoInspeccion,estante);
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
                swipe.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    void actualizarDatos(){
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("Items");
        myRef.keepSynced(true);
        /*for (Item i:listitems){
            if(listitems[i].)
        }
*/
    }

    public boolean isAtLeast(Lifecycle.State state) {
        return false;
    }
}