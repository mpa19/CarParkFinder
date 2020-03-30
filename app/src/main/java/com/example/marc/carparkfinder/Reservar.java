package com.example.marc.carparkfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Reservar extends AppCompatActivity {
    Button btnR;
    RadioButton rbM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar);

        RadioGroup rg = findViewById(R.id.rg);
        btnR = findViewById(R.id.button10);
        rbM = findViewById(R.id.radioButton5);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButton5) {
                    btnR.setEnabled(false);
                    btnR.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#8A7F0047")));
                    Toast.makeText(Reservar.this, "No hi ha places disponibles", Toast.LENGTH_SHORT).show();

                } else {
                    btnR.setEnabled(true);
                    btnR.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#7F0047")));
                }
            }
        });
    }

    public void back(View v){
        finish();
    }

    public void change(View v){
        Intent i = new Intent(this, Parking.class);
        if(rbM.isChecked()) i.putExtra("Tipo", "Moto");
        else i.putExtra("Tipo", "Car");
        startActivity(i);
    }
    public void reserv(View v){
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
        finish();
    }

}
