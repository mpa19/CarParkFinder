package com.example.marc.carparkfinder.ui.Detalles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marc.carparkfinder.R;
import com.example.marc.carparkfinder.ui.Reserva.ReservarActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class DetailsParkingActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    int val;
    TextView tvC;
    TextView tvM;
    Boolean enabled = true;
    TextView titul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_parking);

        Bundle extras = getIntent().getExtras();
        val = extras.getInt("Campus");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        actionBar();

        getVar();

        if(val == 1) setInfoC();

        getParking();
    }

    private void getVar(){
        titul = findViewById(R.id.tvNom);
        tvC = findViewById(R.id.tvC);
        tvM = findViewById(R.id.tvM);
    }

    public void available(){
        Button btnR = findViewById(R.id.btnRes);
        String[] coche = tvC.getText().toString().split("/");
        String[] moto = tvM.getText().toString().split("/");

        if(coche[0].equals("0") && moto[0].equals("0")) {
            btnR.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#8A7F0047")));
            enabled = false;
        }
    }

    public void setInfoC(){
        TextView tvD = findViewById(R.id.tvD);
        TextView tvL = findViewById(R.id.tvL);

        tvD.setText(R.string.cappontD);
        tvL.setText(R.string.cappontL);
        titul.setText(R.string.cap);
    }

    public void actionBar(){
        Toolbar tb = findViewById(R.id.toolbar3);
        setSupportActionBar(tb);

        // Añadir flecha atras en toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng recto;
        if(val == 1) {
            recto = new LatLng(41.609155, 0.624183);
        } else recto = new LatLng(41.615451, 0.618851);

        mMap.addMarker(new MarkerOptions().position(recto));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(recto, 17.0f));
    }

    public void btnR(View v){
        if(enabled) {
            Intent i = new Intent(this, ReservarActivity.class);
            if(titul.getText().toString().equals(getResources().getString(R.string.cap))){
                i.putExtra("Campus", 1);

            } else i.putExtra("Campus", 2);
            startActivity(i);
        } else Toast.makeText(this, "No hi ha places disponibles actualment", Toast.LENGTH_SHORT).show();
    }

    private void getParking(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Campus")
                .document(titul.getText().toString())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            String car = documentSnapshot.getString("DisponiblesC");
                            if(car.equals("0")) tvC.setTextColor(Color.parseColor("#FF0101"));
                            else tvC.setTextColor(Color.parseColor("#38D101"));
                            tvC.setText(car+"/"+documentSnapshot.getString("MaxC"));

                            String moto = documentSnapshot.getString("DisponiblesM");
                            if(moto.equals("0")) tvM.setTextColor(Color.parseColor("#FF0101"));
                            else tvM.setTextColor(Color.parseColor("#38D101"));
                            tvM.setText(moto+"/"+documentSnapshot.getString("MaxM"));
                            available();
                        }
                    }
                });
    }
}
