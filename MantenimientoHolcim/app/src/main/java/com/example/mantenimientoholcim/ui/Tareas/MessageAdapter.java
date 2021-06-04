package com.example.mantenimientoholcim.ui.Tareas;

import android.content.Context;
import android.graphics.Color;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mantenimientoholcim.Adaptadores.AdapterTareas;
import com.example.mantenimientoholcim.Modelo.ComentarioTarea;
import com.example.mantenimientoholcim.Modelo.InternalStorage;
import com.example.mantenimientoholcim.Modelo.UsersData;
import com.example.mantenimientoholcim.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageAdapterViewHolder>
{
    Context context;
    List<ComentarioTarea> messages;
    DatabaseReference messageDb;
    InternalStorage storage;
    UsersData userdata;
    public MessageAdapter(Context context, List<ComentarioTarea> messages, DatabaseReference messageDb)
    {
        this.context= context;
        this.messageDb= messageDb;
        this.messages= messages;
        
    }


    @NonNull
    @Override
    public MessageAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.mensaje_vista,parent,false);
        return new MessageAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapterViewHolder holder, int position) {
        ComentarioTarea message = messages.get(position);
        userdata=new InternalStorage().cargarArchivo(holder.itemView.getContext());
        if(message.getAutor().equals(userdata.getName())){

            holder.tvname.setText("Yo");
            holder.lnTitle.setGravity(Gravity.END);
            holder.tvtitle.setGravity(View.FOCUS_LEFT);
            holder.messageCV.setCardBackgroundColor(Color.parseColor("#1E88E5"));
            holder.tvtitle.setTextColor(Color.parseColor("#FFFFFF"));
            holder.tvname.setTextColor(Color.parseColor("#FFFFFF"));
        }
        holder.tvname.setText(message.getAutor());
        holder.tvtitle.setText(message.getComentario());

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class  MessageAdapterViewHolder extends  RecyclerView.ViewHolder{
        TextView tvtitle, tvname;
        LinearLayout lnTitle;
        CardView messageCV;


        public MessageAdapterViewHolder(View itemView){
            super(itemView);
            tvtitle=itemView.findViewById(R.id.txtmessage);
            tvname=itemView.findViewById(R.id.tvnameMessage);
            lnTitle=itemView.findViewById(R.id.lntxtmessage);
            messageCV=itemView.findViewById(R.id.messageCV);


        }
    }
}
