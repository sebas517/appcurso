package com.example.sebas.appcurso;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.sebas.appcurso.Pojo.Lugar;
import com.example.sebas.appcurso.bbdd.GestorLugar;
import com.example.sebas.appcurso.geolocalizacion.servicioGeocoder;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {
    public static String LOG = "ZZ";
    private static final int REQUEST_LOCATION = 1;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private AddressResultReceiver resultReceiver;
    private LocationCallback callback;
    private LocationRequest request;
    private Location ultimaPosicion = null;
    private TextView tvLatitud;
    private TextView tvLongitud;
    private Button btActualizar;
    private EditText etNombre;
    private Button btSitios;
    private Button btGuardar;
    private  Lugar lugarActual;
    private EditText etComentario;
    private RatingBar  puntos;
    private GestorLugar gestor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLatitud = findViewById(R.id.textView);
        tvLongitud = findViewById(R.id.textView2);
        btActualizar = findViewById(R.id.btact);
        etNombre = findViewById(R.id.editTextNombre);
        btGuardar = findViewById(R.id.btGuardar);
        btSitios = findViewById(R.id.btVer);
        etComentario = findViewById(R.id.etComentariodet);
        puntos = findViewById(R.id.ratingBardet);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){

            }else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            }
        }else{
            getLocation();
        }

        btActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gestor.add(lugarActual);
            }
        });


    }

    @SuppressLint("MissingPermission")
    private void getLocation(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        resultReceiver = new AddressResultReceiver(new Handler());
        callback = createLocationCallback();
        request = createLocationRequest();
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    ultimaPosicion = location;
                    tvLatitud.setText("latitud: " + location.getLatitude());
                    tvLongitud.setText("longitud:" + location.getLongitude());
                    System.out.println("latitud: " + location.getLatitude());
                    System.out.println("longitud:" + location.getLongitude());
                }else{
                    System.out.println("localizacion vacia");
                }
            }
        });
        fusedLocationProviderClient.requestLocationUpdates(request, callback, null);
    }

    private LocationCallback createLocationCallback(){
        final LocationCallback locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                for (Location location : locationResult.getLocations()){
                    ultimaPosicion = location;
                    System.out.println("latitud: " + location.getLatitude());
                    System.out.println("longitud:" + location.getLongitude());

                }
                requestAddress(ultimaPosicion);
                //parar fused
                fusedLocationProviderClient.removeLocationUpdates(callback);
                tvLatitud.setText("latitud: " + ultimaPosicion.getLatitude());
                tvLongitud.setText("longitud:" + ultimaPosicion.getLongitude());

            }
        };
        return locationCallback;
    }

    private LocationRequest createLocationRequest(){
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        return locationRequest;
    }

    private void requestAddress(Location location){

        Intent intent = new Intent(this, servicioGeocoder.class);
        intent.putExtra(servicioGeocoder.Constants.RECEIVER, resultReceiver);
        intent.putExtra(servicioGeocoder.Constants.LOCATION_DATA_EXTRA, location);
        startService(intent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getLocation();
                }
            }

        }
    }

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if (resultData == null) {
                return;
            }
            String resultado = resultData.getString(servicioGeocoder.Constants.RESULT_DATA_KEY);
            String[] resultadoLimpio = resultado.split(", ");
            for (String res : resultadoLimpio){
                System.out.println("resultado cortado   ---- "+res);
            }
            //Log.v("GEOLOCALIZACION2.0", resultado);

            lugarActual = new Lugar(0, etNombre.getText().toString(), ultimaPosicion.getLatitude(), ultimaPosicion.getLongitude(),
                    resultadoLimpio[2], resultadoLimpio[3], etComentario.getText().toString(), puntos.getNumStars(), "22/04/2019");
        }
    }
}
