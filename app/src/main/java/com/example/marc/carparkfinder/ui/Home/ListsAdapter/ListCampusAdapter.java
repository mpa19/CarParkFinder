package com.example.marc.carparkfinder.ui.Home.ListsAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.marc.carparkfinder.R;
import com.example.marc.carparkfinder.ui.Detalles.DetailsParkingActivity;
import com.example.marc.carparkfinder.ui.Reserva.ReservarActivity;

import java.util.List;

public class ListCampusAdapter extends ArrayAdapter<Campus> {

    @NonNull
    private final Context context;
    private final int resource;
    @NonNull
    private final List<Campus> objects;

    public ListCampusAdapter(@NonNull Context context, int resource, @NonNull List<Campus> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        boolean enabled = true;
        boolean enabled2 = true;
        //getting the view
        View view = layoutInflater.inflate(resource, null, false);

        //getting the view elements of the list from the view
        ImageView imageView = view.findViewById(R.id.ivCampus);
        final TextView tvName = view.findViewById(R.id.tvCampus);
        TextView tvNumC = view.findViewById(R.id.tvNumC);
        TextView tvNumM = view.findViewById(R.id.tvNumM);

        Button btnR = view.findViewById(R.id.btnReservar);
        Button btnM = view.findViewById(R.id.btnMas);


        //getting the hero of the specified position
        final Campus campus = objects.get(position);

        //adding values to the list item
        imageView.setBackgroundResource(campus.getImage());
        tvName.setText(campus.getCampus());
        tvNumC.setText(campus.getCar());
        tvNumM.setText(campus.getMoto());

        if(tvNumC.getText().equals("0/0") && tvNumM.getText().equals("0/0")) {
            view.setAlpha((float) 0.4);
            enabled = false;
        } else {
            String[] coche = tvNumC.getText().toString().split("/");
            String[] moto = tvNumM.getText().toString().split("/");

            if(coche[0].equals("0") && moto[0].equals("0")) {
                btnR.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#8A7F0047")));
                enabled2 = false;
                tvNumC.setTextColor(Color.RED);
            }

            if(coche[0].equals("0")) tvNumC.setTextColor(Color.RED);
            else tvNumC.setTextColor(Color.parseColor("#38D101"));

            if(moto[0].equals("0")) tvNumM.setTextColor(Color.RED);
            else tvNumM.setTextColor(Color.parseColor("#38D101"));

        }


        //adding a click listener to the button to remove item from the list
        final boolean finalEnabled = enabled;
        final boolean finalEnabled2 = enabled2;
        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(finalEnabled && finalEnabled2) {
                    Intent i = new Intent(parent.getContext(), ReservarActivity.class);
                    if(campus.getCampus().equals(parent.getResources().getString(R.string.cap))){
                        i.putExtra("Campus", 1);

                    } else i.putExtra("Campus", 2);
                    parent.getContext().startActivity(i);
                } else if(!finalEnabled2) {
                    Toast.makeText(parent.getContext(), "No hi ha places disponibles actualment", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(parent.getContext(), "No disponible actualment", Toast.LENGTH_SHORT).show();
            }
        });

        btnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(finalEnabled) {
                    Intent i = new Intent(parent.getContext(), DetailsParkingActivity.class);
                    if(campus.getCampus().equals(parent.getResources().getString(R.string.cap))){
                        i.putExtra("Campus", 1);

                    } else i.putExtra("Campus", 2);
                    parent.getContext().startActivity(i);

                } else Toast.makeText(parent.getContext(), "No disponible actualment", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
