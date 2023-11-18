package com.example.lab7_20190212;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder>{
    private List<Cita> mdata;
    private LayoutInflater minflater;
    private Context context;

    public Adaptador(List<Cita> itemlist, Context context){
        this.minflater = LayoutInflater.from(context);
        this.context =context;
        this.mdata = itemlist;
    }

    @Override
    public Adaptador.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = minflater.inflate(R.layout.lista, null);
        return new Adaptador.ViewHolder(view);
    }
    @Override
    public int getItemCount() {
        return mdata.size();
    }
    @Override
    public void onBindViewHolder(final Adaptador.ViewHolder holder,final int position){
        holder.bindData(mdata.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, correo, hora;

        ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);
            correo = itemView.findViewById(R.id.correo);
            hora = itemView.findViewById(R.id.hora);

        }

        void bindData(final Cita item) {
            if (item != null) {
                nombre.setText(item.getNombre());
                correo.setText(item.getCorreo());
                hora.setText(item.getHora());
            }
        }
    }
}
