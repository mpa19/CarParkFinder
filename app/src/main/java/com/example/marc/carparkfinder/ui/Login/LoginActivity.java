package com.example.marc.carparkfinder.ui.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marc.carparkfinder.ManageMainActivity;
import com.example.marc.carparkfinder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    TextView tvEmail;
    TextView tvPassword;
    TextView tvRegis;

    Button bntEntrar;

    SharedPreferences sharedpreferences;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Email = "emailKey";


    CheckBox remb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_app);

        FirebaseMessaging.getInstance().subscribeToTopic("android");

        init();

        if (mAuth.getCurrentUser() != null) go();

        bntEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvPassword.getText().toString().equals("") || tvEmail.getText().toString().equals("")){
                    Toast.makeText(getApplication(), "Es necessari omplir tots els camps", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(tvEmail.getText().toString().trim(), tvPassword.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        if(remb.isChecked()) {
                                            editor.putString(Email, tvEmail.getText().toString());
                                        } else {
                                            editor.putString(Email, "");
                                        }
                                        editor.commit();

                                        go();

                                    } else {
                                        Toast.makeText(getApplication(), "Email i/o contrasenya incorrectes!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }

    private void go(){
        Intent i = new Intent(getApplication(), ManageMainActivity.class);
        startActivity(i);
        finish();
    }

    private void init(){
        mAuth = FirebaseAuth.getInstance();

        tvEmail = findViewById(R.id.tvEmail);
        tvPassword = findViewById(R.id.tvPassword);
        tvRegis = findViewById(R.id.tvRegis);
        bntEntrar = findViewById(R.id.btnEntrar);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        remb = findViewById(R.id.remember);

        check();
    }

    private void check(){
        if(sharedpreferences != null) {
            String text = sharedpreferences.getString(Email, "");
            if(!text.equals("")) {
                remb.setChecked(true);
                tvEmail.setText(text);
            }
        }

    }

    public void regis(View v){
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
}
