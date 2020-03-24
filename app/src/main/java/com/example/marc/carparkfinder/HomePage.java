package com.example.marc.carparkfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    public void btnV(View v){
        Intent i = new Intent(this, DetailsParking.class);
        startActivity(i);
    }

    public void btnR(View v){
        Intent i = new Intent(this, Reservar.class);
        startActivity(i);
    }

    public void btnP(View v){
        Intent i = new Intent(this, Profile.class);
        startActivity(i);
        finish();
    }

    public void miReserva(View v){
        Intent i = new Intent(this, MiReserva.class);
        startActivity(i);
        finish();
    }
}
