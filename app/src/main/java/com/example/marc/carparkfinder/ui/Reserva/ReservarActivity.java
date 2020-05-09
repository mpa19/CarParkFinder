package com.example.marc.carparkfinder.ui.Reserva;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReservarActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    Button btnR;
    Button btnC;
    RadioButton rbM;
    TextView tv;
    TextView tvCelda;
    TextView titul;
    Intent i;
    boolean changed = false;
    EditText etEntrada;
    EditText etSortida;
    int timer = 0;
    Calendar calendar1;
    SimpleDateFormat sdf;
    Date date2 = null;
    Date date3 = null;
    Date date4 = null;
    RadioGroup rg;
    TextView tvC;
    TextView tvM;
    int motosA;
    int carA;

    boolean vehicle = true;
    boolean hora = true;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Tipus = "phoneKey";

    SharedPreferences sharedpreferences;

    private FirebaseAuth mAuth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar);

        getFindVar();

        setVar();

        actionBar();

        controlTime();

        sharedpref();

        etEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer = 1;
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        etSortida.setOnClickListener(new View.OnClickListener() {
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
        etEntrada =  findViewById(R.id.editText);
        etSortida =  findViewById(R.id.editText2);
        titul = findViewById(R.id.textView20);
        tvC = findViewById(R.id.tvC);
        tvM = findViewById(R.id.tvM);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    public void setVar() {
        etEntrada.setShowSoftInputOnFocus(false);
        etEntrada.setInputType(InputType.TYPE_NULL);
        etEntrada.setFocusable(false);

        etSortida.setShowSoftInputOnFocus(false);
        etSortida.setInputType(InputType.TYPE_NULL);
        etSortida.setFocusable(false);

        getParking();
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
                            carA = Integer.valueOf(car);

                            String moto = documentSnapshot.getString("DisponiblesM");
                            if(moto.equals("0")) tvM.setTextColor(Color.parseColor("#FF0101"));
                            else tvM.setTextColor(Color.parseColor("#38D101"));
                            tvM.setText(moto+"/"+documentSnapshot.getString("MaxM"));
                            motosA = Integer.valueOf(moto);
                        }
                    }
                });
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

            etEntrada.setText(sdf.format(calendar1.getTime()));
            etSortida.setText(sdf.format(calendar2.getTime()));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (calendar1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || date1.before(date2) || date1.after(date4)) {
            desactivar();
        } else if(calendar1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && date1.after(date3)) {
            desactivar();
        } else {
            vehicleCheck();
        }
    }

    private void vehicleCheck(){
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton5) {
                    /* Moto */
                    if(motosA == 0) {
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


                } else {
                    /* Car */
                    if(carA == 0) {
                        vehicle = false;
                        btnR.setEnabled(false);
                        btnR.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#8A7F0047")));

                        Toast.makeText(ReservarActivity.this, "No hi ha places disponibles", Toast.LENGTH_SHORT).show();
                    } else {
                        vehicle = true;
                        if (hora) {
                            btnR.setEnabled(true);
                            btnR.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#7F0047")));
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(timer == 1) {
            try {
                if(validation(1, hourOfDay, minute)) {
                    etEntrada.setText(hourOfDay + ":" + minute);
                    Date date6 = sdf.parse(etSortida.getText().toString());
                    Date date7 = sdf.parse(etEntrada.getText().toString());

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
                    etSortida.setText(hourOfDay + ":" + minute);
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
            Date date6 = sdf.parse(etEntrada.getText().toString());
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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> docData = new HashMap<>();
        docData.put("Campus", titul.getText());
        docData.put("HEntrada", etEntrada.getText().toString());
        docData.put("HSortida", etSortida.getText().toString());
        docData.put("Parking", tvCelda.getText());

        if(rbM.isChecked())  docData.put("Tipo", "Moto");
        else docData.put("Tipo", "Car");

        db.collection("Reserva").document(user.getUid())
                .set(docData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        getParking();

                        changeCampus();

                        Intent a = new Intent(getApplication(), MapsActivity.class);
                        startActivity(a);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ReservarActivity.this, "Error al reserva, intenta un altre cop en uns minuts", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void changeCampus(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> docData = new HashMap<>();
        if(rbM.isChecked())  docData.put("DisponiblesM", Integer.toString(motosA-1));
        else docData.put("DisponiblesC", Integer.toString(carA-1));


        db.collection("Campus").document(titul.getText().toString())
                .update(docData);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK) {
                int ex = data.getIntExtra("celda", 0);
                tvCelda.setText(Integer.toString(ex));
            }
        }
    }
}
