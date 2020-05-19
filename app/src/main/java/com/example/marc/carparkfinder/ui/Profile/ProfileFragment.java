package com.example.marc.carparkfinder.ui.Profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.marc.carparkfinder.ui.Login.LoginActivity;
import com.example.marc.carparkfinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {
    private FirebaseAuth mAuth;

    TextView tv;
    TextView btnC;
    TextView email;
    TextView name;

    FirebaseUser user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_profile, container, false);
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        setInit();

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrar();
            }
        });


    }

    private void setInit() {
        tv.setText(R.string.profile);
        email.setText(user.getEmail());
        name.setText(user.getDisplayName());
    }

    private void init(View view){
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        email = view.findViewById(R.id.tvEmailP);
        name = view.findViewById(R.id.tvNameP);
        tv = getActivity().findViewById(R.id.title);
        btnC = view.findViewById(R.id.tvSurt);

    }

    public void cerrar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.cerrarS))
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        Intent a = new Intent(getContext(), LoginActivity.class);
                        startActivity(a);
                        getActivity().finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

    }


}
