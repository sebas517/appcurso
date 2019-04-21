package com.example.sebas.appcurso;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sebas.appcurso.Pojo.Lugar;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {

    public interface OnItemClickListener{
        public void onClick(ViewHolder holder, int id);
    }

    private OnItemClickListener oyente;
    List<Lugar> lugares;

    public Adaptador(OnItemClickListener oyente, List<Lugar> lugares) {
        this.oyente = oyente;
        this.lugares = lugares;
        //this.cursor = cursor;
    }

    @NonNull
    @Override
    public Adaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.ViewHolder viewHolder, int i) {
        Lugar l = lugares.get(i);

        viewHolder.tvComentario.setText(l.getComentario());
        viewHolder.tvLugar.setText(l.getLocalidad() + ", " + l.getPais());
        viewHolder.tvNombre.setText(l.getNombre());

    }

    @Override
    public int getItemCount() {
        return lugares.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvNombre;
        public TextView tvLugar;
        public TextView tvComentario;

        public ViewHolder(View v) {
            super(v);
            tvNombre = v.findViewById(R.id.tvNombre);
            tvComentario = v.findViewById(R.id.tvComentario);
            tvLugar = v.findViewById(R.id.tvLugar);
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int id = getAdapterPosition();
            oyente.onClick(this, id);
        }
    }
}
