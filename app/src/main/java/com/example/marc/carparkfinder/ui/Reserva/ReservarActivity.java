package com.example.marc.carparkfinder.ui.Reserva;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.marc.carparkfinder.ui.Reserva.SelectParking.ParkingActivity;
import com.example.marc.carparkfinder.R;
import com.example.marc.carparkfinder.ui.Reserva.TimerHelper.TimePickerFragment;
import com.example.marc.carparkfinder.ui.Route.MapsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReservarActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    Button btnR;
    Button btnC;
    RadioButton rbM;
    TextView tv;
    TextView tvCelda;
    Intent i;
    boolean changed = false;
    EditText textView;
    EditText textView2;
    int timer = 0;
    Calendar calendar1;
    SimpleDateFormat sdf;
    Date date2 = null;
    Date date3 = null;
    Date date4 = null;
    RadioGroup rg;

    boolean vehicle = true;
    boolean hora = true;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Tipus = "phoneKey";

    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar);

        getFindVar();

        setVar();

        actionBar();

        controlTime();

        sharedpref();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer = 1;
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer = 2;
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
    }

    public void sharedpref(){
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String tipus = sharedpreferences.getString(Tipus, "");
        if(tipus.equals("moto")) rbM.setChecked(true);
    }

    public void getFindVar() {
        rg = findViewById(R.id.rg);
        btnR = findViewById(R.id.button10);
        rbM = findViewById(R.id.radioButton5);
        btnC = findViewById(R.id.button11);
        tv = findViewById(R.id.textView35);
        tvCelda = findViewById(R.id.textView32);
        textView =  findViewById(R.id.editText);
        textView2 =  findViewById(R.id.editText2);
    }

    public void setVar() {
        textView.setShowSoftInputOnFocus(false);
        textView.setInputType(InputType.TYPE_NULL);
        textView.setFocusable(false);

        textView2.setShowSoftInputOnFocus(false);
        textView2.setInputType(InputType.TYPE_NULL);
        textView2.setFocusable(false);
    }

    public void actionBar() {
        Toolbar tb = findViewById(R.id.toolbar4);
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

    public void controlTime() {
        calendar1 = Calendar.getInstance();
        sdf = new SimpleDateFormat("HH:mm");

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.MINUTE, 30);

        Date date1 = null;

        try {
            date1 = sdf.parse(sdf.format(calendar1.getTime()));
            date2 = sdf.parse("07:00");
            date4 = sdf.parse("23:00");
            date3 = sdf.parse("15:00");

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
                        vehicle = false;
                        btnR.setEnabled(false);
                        btnR.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#8A7F0047")));

                        Toast.makeText(ReservarActivity.this, "No hi ha places disponibles", Toast.LENGTH_SHORT).show();

                    } else {
                        vehicle = true;
                        if(hora){
                            btnR.setEnabled(true);
                            btnR.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#7F0047")));
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(timer == 1) {
            try {
                if(validation(1, hourOfDay, minute)) {
                    textView.setText(hourOfDay + ":" + minute);
                    Date date6 = sdf.parse(textView2.getText().toString());
                    Date date7 = sdf.parse(textView.getText().toString());

                    if(date7.after(date6)) {
                        btnR.setEnabled(false);
                        btnR.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#8A7F0047")));
                        hora = false;
                    }
                }
                else {
                    Toast.makeText(this, "Hora d'entrada errònia", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                if(validation(2, hourOfDay, minute)) {
                    textView2.setText(hourOfDay + ":" + minute);
                    hora = true;
                    if(vehicle){
                        btnR.setEnabled(true);
                        btnR.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#7F0047")));
                    }
                }
                else {
                    Toast.makeText(this, "Hora de sortida errònia", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }


    private boolean validation(int type, int hourOfDay, int minute) throws ParseException {
        Date date5 = sdf.parse(hourOfDay+":"+minute);
        if(type == 1){
            if(date5.before(date2) || ((calendar1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) && date5.after(date3)) || date5.after(date4)) return false;
        } else {
            Date date6 = sdf.parse(textView.getText().toString());
            if(date5.before(date2) || ((calendar1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) && date5.after(date3)) || date5.after(date4) || date5.before(date6)) return false;
        }
        return true;
    }

    public void desactivar(){
        btnR.setEnabled(false);
        btnR.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#8A7F0047")));
        btnC.setEnabled(false);
        btnC.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#8A7F0047")));
        tv.setVisibility(View.VISIBLE);
    }

    public void change(View v){
        changed = true;
        i = new Intent(this, ParkingActivity.class);
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
