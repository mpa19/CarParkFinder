package com.example.marc.carparkfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Reservar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar);
    }

    public void back(View v){
        finish();
    }

    public void change(View v){
        Intent i = new Intent(this, Parking.class);
        startActivity(i);
    }
    public void reserv(View v){
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
        finish();
    }

}
