package com.example.marc.carparkfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        builder.setTitle(getString(R.string.cerrarS))
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent a = new Intent(getApplicationContext(), LoginApp.class);
                        startActivity(a);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

    }

    public void miReserva(View v){
        Intent i = new Intent(this, MiReserva.class);
        startActivity(i);
        finish();
    }
}
