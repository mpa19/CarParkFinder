package com.example.marc.carparkfinder.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.marc.carparkfinder.DetailsParking;
import com.example.marc.carparkfinder.R;
import com.example.marc.carparkfinder.Reservar;


public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_home_page, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tv = getActivity().findViewById(R.id.title);
        tv.setText(R.string.app_name);

        Button btnR = view.findViewById(R.id.btnReservar);
        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Reservar.class);
                startActivity(i);
            }
        });

        Button btnM = view.findViewById(R.id.btnMas);
        btnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DetailsParking.class);
                startActivity(i);
            }
        });
    }
}
