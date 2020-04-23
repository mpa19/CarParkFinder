package com.example.marc.carparkfinder.ui.ShraredPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marc.carparkfinder.R;

public class ShraredPreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shrared_preferences);

        actionBar();

        ImageView btnC = findViewById(R.id.btnCheck);

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent result = new Intent();
                setResult(RESULT_OK, result);
                finish();
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
}
