package com.example.marc.carparkfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void btnH(View v){
        Intent i = new Intent(this, HomePage.class);
        startActivity(i);
        finish();
    }

    public void cerrar(View v){
        Intent i = new Intent(this, LoginApp.class);
        startActivity(i);
        finish();
    }

    public void miReserva(View v){
        Intent i = new Intent(this, MiReserva.class);
        startActivity(i);
        finish();
    }
}
