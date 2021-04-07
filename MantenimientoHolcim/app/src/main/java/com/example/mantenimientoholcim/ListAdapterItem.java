package com.example.mantenimientoholcim;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mantenimientoholcim.Modelo.Item;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        TextView txtNombreItem,txtStock,txtCodigo;
        ImageButton btnMas,btnMenos;
        CardView cvItem;
        Item item;
        private View view;
        public ViewHolder(View view) {
            super(view);
            txtNombreItem=view.findViewById(R.id.txtNombreItem);
            txtStock=view.findViewById(R.id.txtStock);
            txtCodigo=view.findViewById(R.id.txtCodigo);
            btnMas=view.findViewById(R.id.btnMas);
            btnMenos=view.findViewById(R.id.btnMenos);
            cvItem=view.findViewById(R.id.cvPuntoBloqueo);
            this.view=view;
        }

        public void setOnClickListeners() {
            btnMas.setOnClickListener(this);
            btnMenos.setOnClickListener(this);
            cvItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            FirebaseDatabase database=FirebaseDatabase.getInstance();
            DatabaseReference refStock=database.getReference("Items").child(item.getCodigo()).child("stock");
            switch (v.getId()){
                case R.id.btnMas:
                    refStock.setValue(item.getStock()+1);

                    break;

                case R.id.btnMenos:
                    refStock.setValue(item.getStock()-1);
                    break;

                case R.id.cvPuntoBloqueo:
                    Intent intent= new Intent(context,CrearItem.class);
                    intent.putExtra("item",item);
                    context.startActivity(intent);
            }


        }

        public void binData(Item item) {
            txtNombreItem.setText(item.getDescripcion());
            txtStock.setText(String.valueOf(item.getStock()));
            txtCodigo.setText(item.getCodigo());
            this.item=item;
            cvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                            FirebaseDatabase database=FirebaseDatabase.getInstance();
                            DatabaseReference refStock=database.getReference("Items").child(item.getCodigo());
                            refStock.removeValue();
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
// Create the AlertDialog
                    builder.setTitle("Deseas eliminar "+item.getDescripcion());
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    return false;
                }
            });

        }
    }
}
