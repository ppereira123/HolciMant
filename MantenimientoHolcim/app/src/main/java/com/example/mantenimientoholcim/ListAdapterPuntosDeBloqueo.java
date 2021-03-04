package com.example.mantenimientoholcim;


        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.cardview.widget.CardView;
        import androidx.recyclerview.widget.RecyclerView;
        import com.example.mantenimientoholcim.Modelo.Item;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

        import java.util.List;

public class ListAdapterPuntosDeBloqueo extends RecyclerView.Adapter<ListAdapterPuntosDeBloqueo.ViewHolder> {
    private List<Integer> mdata;
    private LayoutInflater mInflater;
    private Context context;


    public ListAdapterPuntosDeBloqueo(List<Integer> itemList, Context context){
        this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.mdata =itemList;
    }
    @NonNull
    @Override
    public ListAdapterPuntosDeBloqueo.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= mInflater.inflate(R.layout.list_puntosdebloqueo,null);
        return new ListAdapterPuntosDeBloqueo.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterPuntosDeBloqueo.ViewHolder holder, int position) {
        holder.binData(mdata.get(position));
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText hac, ubicacion, tipoenergia,observaciontxt;
        Button btningresarfoto;
        CheckBox checkbox11, checkbox1, checkbox2, checkbox21;
        TextView puntobloqeotxt;

        private View view;
        public ViewHolder(View view) {
            super(view);
            hac=view.findViewById(R.id.hac);
            ubicacion=view.findViewById(R.id.ubicacion);
            tipoenergia=view.findViewById(R.id.tipoenergia);
            observaciontxt=view.findViewById(R.id.observaciontxt);
            btningresarfoto=view.findViewById(R.id.btningresarfoto);
            checkbox11=view.findViewById(R.id.checkBox11);
            checkbox1=view.findViewById(R.id.checkBox1);
            checkbox2=view.findViewById(R.id.checkBox2);
            checkbox21=view.findViewById(R.id.checkBox21);

            this.view=view;
        }


        public void binData(Integer integer) {
            ubicacion.getText();
        }
    }

    }

