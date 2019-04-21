package com.example.sebas.appcurso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.sebas.appcurso.Pojo.Lugar;
import com.example.sebas.appcurso.bbdd.GestorLugar;

import java.util.List;

public class ActivityLugares extends AppCompatActivity {
    private Adaptador adaptador;
    private List<Lugar> lugares;
    private GestorLugar gestor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugares);
        lugares = gestor.get();

        if (lugares.isEmpty()){
            Toast.makeText(this,"Aun no hay lugares en la base de datos...", Toast.LENGTH_SHORT).show();
            finish();
        }


        Adaptador.OnItemClickListener oyente = new Adaptador.OnItemClickListener() {
            @Override
            public void onClick(Adaptador.ViewHolder holder, int id) {
                for (Lugar l : lugares ) {
                    Intent i = new Intent(ActivityLugares.this, Detalle.class);
                    i.putExtra("lugar", l);
                    startActivity(i);
                }
            }
        };

        RecyclerView rv = findViewById(R.id.rvLugares);
        adaptador = new Adaptador(oyente, lugares);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adaptador);

    }
}
