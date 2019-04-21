package com.example.sebas.appcurso;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.sebas.appcurso.Pojo.Lugar;
import com.example.sebas.appcurso.bbdd.GestorLugar;

public class Detalle extends AppCompatActivity {

    private Lugar l;
    private GestorLugar gestor;
    private TextView tvLatitud;
    private TextView tvLongitud;
    private EditText etNombre;
    private EditText etComentario;
    private RatingBar rb;
    private Button btEditar;
    private Button btCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        tvLatitud = findViewById(R.id.tvLatitud);
        tvLongitud = findViewById(R.id.tvLongitud);
        etNombre = findViewById(R.id.editTextNombre);
        etComentario = findViewById(R.id.etComentariodet);
        rb = findViewById(R.id.ratingBardet);
        btEditar = findViewById(R.id.btEditar);
        btCancelar = findViewById(R.id.btcancelar);

        Bundle extras = getIntent().getExtras();
        l = extras.getParcelable("lugar");

        tvLatitud.setText(l.getLatitud()+"");
        tvLongitud.setText(l.getLongitud()+"");
        etNombre.setText(l.getNombre());
        etComentario.setText(l.getComentario());
        rb.setNumStars(l.getPuntos());


        btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gestor.edit(l);
            }
        });

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
