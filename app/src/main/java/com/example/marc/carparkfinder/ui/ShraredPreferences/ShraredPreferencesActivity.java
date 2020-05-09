package com.example.marc.carparkfinder.ui.ShraredPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marc.carparkfinder.R;

public class ShraredPreferencesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    int selected = -1;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Campus = "nameKey";
    public static final String Tipus = "phoneKey";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shrared_preferences);

        actionBar();

        ImageView btnC = findViewById(R.id.btnCheck);
        ListView lv = findViewById(R.id.lv);
        String[] mobileArray = {getResources().getString(R.string.cap),getResources().getString(R.string.rect)};
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.simple_list_view, R.id.textView, mobileArray);

        lv.setAdapter(adapter);
        lv.setSelector(R.color.colorSel);
        
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final RadioButton rbM = findViewById(R.id.radioButton5);


        lv.setOnItemClickListener(this);

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selected == -1) {
                    Toast.makeText(ShraredPreferencesActivity.this, "És necessari seleccionar un campus", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    String type = "car";
                    if(rbM.isChecked()) type = "moto";
                    editor.putInt(Campus, selected);
                    editor.putString(Tipus, type);
                    editor.commit();
                    Intent result = new Intent();
                    setResult(RESULT_OK, result);
                    finish();
                }
            }
        });
    }

    public void actionBar(){
        Toolbar tb = findViewById(R.id.toolbar7);
        TextView title1 = findViewById(R.id.title2);
        title1.setText(R.string.prefe);
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
    public void onItemClick(AdapterView parent, View view, int index, long arg3) {
        // gets the contact from adapter

        selected = index;
    }
}
