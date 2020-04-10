package com.example.marc.carparkfinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Reservar extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    Button btnR;
    Button btnC;
    RadioButton rbM;
    TextView tv;
    TextView tvCelda;
    Intent i;
    boolean changed = false;
    EditText textView;
    EditText textView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar);

        RadioGroup rg = findViewById(R.id.rg);
        btnR = findViewById(R.id.button10);
        rbM = findViewById(R.id.radioButton5);
        btnC = findViewById(R.id.button11);
        tv = findViewById(R.id.textView35);
        tvCelda = findViewById(R.id.textView32);
        textView =  findViewById(R.id.editText);
        textView2 =  findViewById(R.id.editText2);


        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.MINUTE, 30);

        Date date1 = null;
        Date date2 = null;
        Date date3 = null;
        Date date4 = null;
        try {
            date1 = sdf.parse(sdf.format(calendar1.getTime()));
            date2 = sdf.parse("06:50");
            date4 = sdf.parse("22:50");
            date3 = sdf.parse("14:50");
            textView.setText(sdf.format(calendar1.getTime()));
            textView2.setText(sdf.format(calendar2.getTime()));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (calendar1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || date1.before(date2) || date1.after(date4)) {
            desactivar();
        } else if(calendar1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && date1.after(date3)) {
            desactivar();
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

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        textView.setText(hourOfDay + ":" + minute);
    }

    public void desactivar(){
        btnR.setEnabled(false);
        btnR.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#8A7F0047")));
        btnC.setEnabled(false);
        btnC.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#8A7F0047")));
        tv.setVisibility(View.VISIBLE);
    }

    public void back(View v){
        finish();
    }

    public void change(View v){
        changed = true;
        i = new Intent(this, Parking.class);
        if(rbM.isChecked()) i.putExtra("Tipo", "Moto");
        else i.putExtra("Tipo", "Car");
        startActivityForResult(i,1);
    }
    public void reserv(View v){
        Intent a = new Intent(this, MapsActivity.class);
        startActivity(a);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK) {
                int ex = data.getIntExtra("celda", 0);
                tvCelda.setText("Nª: "+ex);
            }
        }
    }
}
