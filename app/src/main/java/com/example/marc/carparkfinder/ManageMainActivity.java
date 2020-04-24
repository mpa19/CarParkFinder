package com.example.marc.carparkfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marc.carparkfinder.ui.ShraredPreferences.ShraredPreferencesActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class ManageMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        setActionBar();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        ImageView btnSetting = findViewById(R.id.btnSettings);

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ShraredPreferencesActivity.class);
                startActivityForResult(i,1);
            }
        });
    }

    public void setActionBar() {
        Toolbar tb = findViewById(R.id.toolbar);
        TextView title1 = findViewById(R.id.title);
        title1.setText(R.string.app_name);
        setSupportActionBar(tb);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK) {
                Toast.makeText(this, R.string.prefeEx, Toast.LENGTH_SHORT).show();
            }
        }
    }


}
