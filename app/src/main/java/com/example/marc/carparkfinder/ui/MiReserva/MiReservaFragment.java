package com.example.marc.carparkfinder.ui.MiReserva;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.marc.carparkfinder.ui.Route.MapsActivity;
import com.example.marc.carparkfinder.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MiReservaFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;

    LatLng origin;
    TextView titul;
    TextView entrada;
    TextView sortida;
    TextView hora;
    TextView placas;
    TextView tipus;
    TextView placa;
    TextView ent;
    TextView sort;
    ImageView vehicle;
    Button com;
    Button cancel;


    SupportMapFragment mapFragment;

    private Marker Posi;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_mi_reserva, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map4);
        mapFragment.getMapAsync(this);

        TextView tv = getActivity().findViewById(R.id.title);
        tv.setText(R.string.mires);

        inicializar(view);

        Button btnC = view.findViewById(R.id.btnCancelar);
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });


        Button btnArr = view.findViewById(R.id.btnArr);
        btnArr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MapsActivity.class);
                startActivity(i);
            }
        });
    }

    public void inicializar(View view){
        titul = view.findViewById(R.id.tvNom);
        entrada = view.findViewById(R.id.textView39);
        sortida = view.findViewById(R.id.textView40);
        hora = view.findViewById(R.id.textView36);
        placas = view.findViewById(R.id.textView31);
        tipus = view.findViewById(R.id.textView38);
        placa = view.findViewById(R.id.textView32);
        vehicle = view.findViewById(R.id.imageView10);
        com = view.findViewById(R.id.btnArr);
        cancel = view.findViewById(R.id.btnCancelar);
        ent = view.findViewById(R.id.textView21);
        sort = view.findViewById(R.id.textView19);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        origin = new LatLng(41.615370, 0.619103);
        Posi = mMap.addMarker(new MarkerOptions().position(origin).title("Plaça Nº: 5"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 17.0f));
    }

    public void clear(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.cancelRe))
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        titul.setText("No hi ha cap reserva");
                        entrada.setText("");
                        sortida.setText("");
                        placa.setText("");
                        vehicle.setBackground(null);
                        Posi.remove();
                        com.setEnabled(false);
                        cancel.setEnabled(false);
                        com.setVisibility(View.INVISIBLE);
                        cancel.setVisibility(View.INVISIBLE);
                        placas.setText("");
                        hora.setText("");
                        tipus.setText("");
                        ent.setText("");
                        sort.setText("");
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
