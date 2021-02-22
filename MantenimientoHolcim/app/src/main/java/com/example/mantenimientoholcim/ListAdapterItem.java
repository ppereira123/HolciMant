package com.example.mantenimientoholcim;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mantenimientoholcim.Modelo.Item;

import java.util.List;

public class ListAdapterItem extends RecyclerView.Adapter<ListAdapterItem.ViewHolder> {
    private List<Item> mdata;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapterItem(List<Item> itemList, Context context){
        this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.mdata =itemList;
    }
    @NonNull
    @Override
    public ListAdapterItem.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= mInflater.inflate(R.layout.list_element_items,null);
        return new ListAdapterItem.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterItem.ViewHolder holder, int position) {
        holder.binData(mdata.get(position));
        holder.setOnClickListeners();
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtNombreItem,txtStock;
        ImageButton btnMas,btnMenos;
        public ViewHolder(View view) {
            super(view);
            txtNombreItem=view.findViewById(R.id.txtNombreItem);
            txtStock=view.findViewById(R.id.txtStock);
            btnMas=view.findViewById(R.id.btnMas);
            btnMenos=view.findViewById(R.id.btnMenos);
        }

        public void setOnClickListeners() {
            btnMas.setOnClickListener(this);
            btnMenos.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String caso;
            switch (v.getId()){
                case R.id.btnMas:
                    break;

                case R.id.btnMenos:
                    break;
            }


        }

        public void binData(Item item) {
            txtNombreItem.setText(item.getNombres());
            txtStock.setText(String.valueOf(item.getStock()));
        }
    }
}
