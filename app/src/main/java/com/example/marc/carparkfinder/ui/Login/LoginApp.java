package com.example.marc.carparkfinder.ui.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.marc.carparkfinder.ManageMain;
import com.example.marc.carparkfinder.R;

public class LoginApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_app);
    }

    public void signin(View v){
        Intent i = new Intent(this, ManageMain.class);
        startActivity(i);
        finish();
    }
}
