package com.example.mantenimientoholcim;


        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.cardview.widget.CardView;
        import androidx.recyclerview.widget.RecyclerView;
        import com.example.mantenimientoholcim.Modelo.Item;
        import com.example.mantenimientoholcim.Modelo.PuntoBloqueo;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

        import java.util.List;

public class ListAdapterPuntosDeBloqueo extends RecyclerView.Adapter<ListAdapterPuntosDeBloqueo.ViewHolder> {
    private List<PuntoBloqueo> mdata;
    private LayoutInflater mInflater;
    private Context context;
    private CardView cvPuntoBloqueo;


    public ListAdapterPuntosDeBloqueo(List<PuntoBloqueo> itemList, Context context){
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
        holder.binData(mdata.get(position),position);
        holder.setOnClickListeners();
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView hac, lugar, tipoenergia,txtpuntoBloqueo;
        ImageView imgpuntoBloqueo;

        private View view;
        public ViewHolder(View view) {
            super(view);
            hac=view.findViewById(R.id.codigohac);
            lugar=view.findViewById(R.id.ubicaciontxt);
            tipoenergia=view.findViewById(R.id.tipoenergiatxt);
            imgpuntoBloqueo=view.findViewById(R.id.imgpuntoBloqueo);
            cvPuntoBloqueo=view.findViewById(R.id.cvPuntoBloqueo);
            txtpuntoBloqueo= view.findViewById(R.id.txtpuntoBloqueo);

            this.view=view;
        }
        public void setOnClickListeners() {
            cvPuntoBloqueo.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            cvPuntoBloqueo.setOnClickListener(this);
        }


        public void binData(PuntoBloqueo puntobloqueo, int posicion) {
            hac.setText(puntobloqueo.getCodigoHac());
            lugar.setText(puntobloqueo.getLugar());
            tipoenergia.setText(puntobloqueo.getTipoenergia());
            String ruta=puntobloqueo.getRutaimagen();
            Bitmap imgBitmap= BitmapFactory.decodeFile(ruta);//aqui esta leyendo
            imgpuntoBloqueo.setImageBitmap(imgBitmap);
            int puntonumero= posicion+1;
            txtpuntoBloqueo.setText("Punto de bloqueo "+puntonumero);
        }
    }

    }

