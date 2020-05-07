package com.example.marc.carparkfinder.ui.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marc.carparkfinder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegis;

    TextView tvCancel;
    TextView tvPassword;
    TextView tvEmail;
    TextView tvPassword2;
    TextView tvFullName;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tvPassword.getText().toString().equals("") || tvEmail.getText().toString().equals("")){
                    Toast.makeText(getApplication(), "Es necessari omplir tots els camps", Toast.LENGTH_SHORT).show();
                } else if(!tvPassword.getText().toString().equals(tvPassword2.getText().toString())) {
                    Toast.makeText(getApplication(), "Contrasenya incorrecta introduïda", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(tvEmail.getText().toString().trim(), tvPassword.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(tvFullName.getText().toString()).build();

                                        user.updateProfile(profileUpdates);

                                        Toast.makeText(getApplication(), "Usuari creat correctament!", Toast.LENGTH_SHORT).show();
                                        finish();

                                    } else {
                                        // If sign in fails, display a message to the user.Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getApplication(), "Email i/o contrasenya incorrectes!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void init(){
        mAuth = FirebaseAuth.getInstance();

        btnRegis = findViewById(R.id.btnRegistrar);
        tvCancel = findViewById(R.id.tvCancelar);
        tvEmail = findViewById(R.id.tvEmailR);
        tvPassword = findViewById(R.id.tvPasswordR);
        tvPassword2 = findViewById(R.id.tvPassword2);
        tvFullName = findViewById(R.id.tvFullName);
    }

    public void cancel(View v){
        finish();
    }
}
