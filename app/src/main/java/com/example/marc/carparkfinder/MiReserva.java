package com.example.marc.carparkfinder;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MiReserva extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    LatLng origin;
    TextView titul;
    TextView entrada;
    TextView sortida;
    TextView placa;
    ImageView vehicle;
    Button com;
    Button cancel;

    SupportMapFragment mapFragment;

    private Marker Posi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_reserva);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map4);
        mapFragment.getMapAsync(this);


        titul = findViewById(R.id.textView7);
        entrada = findViewById(R.id.textView39);
        sortida = findViewById(R.id.textView40);
        placa = findViewById(R.id.textView32);
        vehicle = findViewById(R.id.imageView10);
        com = findViewById(R.id.button15);
        cancel = findViewById(R.id.button5);



    }

    public void btnP(View v){
        Intent i = new Intent(this, Profile.class);
        startActivity(i);
        finish();
    }

    public void btnH(View v){
        Intent i = new Intent(this, HomePage.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        origin = new LatLng(41.62, 0.62);
        Posi = mMap.addMarker(new MarkerOptions().position(origin).title("Plaça 5"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 17.0f));
    }

    public void clear(View v){
        titul.setText("No hi ha cap reserva");
        entrada.setText("");
        sortida.setText("");
        placa.setText("");
        vehicle.setBackground(null);
        Posi.remove();
        com.setEnabled(false);
        cancel.setEnabled(false);
        com.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
    }
}
