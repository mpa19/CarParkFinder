package com.example.marc.carparkfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Reservar extends AppCompatActivity {
    Button btnR;
    Button btnC;
    RadioButton rbM;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar);

        RadioGroup rg = findViewById(R.id.rg);
        btnR = findViewById(R.id.button10);
        rbM = findViewById(R.id.radioButton5);
        btnC = findViewById(R.id.button11);
        tv = findViewById(R.id.textView35);

        Calendar calendar1 = Calendar.getInstance();

        if (calendar1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            btnR.setEnabled(false);
            btnR.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#8A7F0047")));
            btnC.setEnabled(false);
            btnC.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#8A7F0047")));
            tv.setVisibility(View.VISIBLE);
        } else {
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.radioButton5) {
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
