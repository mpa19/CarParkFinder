package com.example.marc.carparkfinder.ui.MiReserva;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MiReservaFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;

    LatLng origin;
    TextView titul;
    TextView entrada;
    TextView sortida;
    TextView placa;
    ImageView vehicle;
    Button com;
    Button cancel;
    ScrollView sc;
    String tipo;

    SupportMapFragment mapFragment;

    boolean mapReady = false;

    private Marker Posi;

    private FirebaseAuth mAuth;
    FirebaseUser user;

    SharedPreferences sharedpreferences;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Zoom = "zoomKey";

    private float zoomMap = 17.0f;

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

        check();

        llamar();

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
                i.putExtra("Campus", titul.getText().toString());
                i.putExtra("Placa", placa.getText().toString());
                startActivity(i);
            }
        });
    }

    public void inicializar(View view){
        titul = view.findViewById(R.id.tvNom);
        entrada = view.findViewById(R.id.textView39);
        sortida = view.findViewById(R.id.textView40);
        placa = view.findViewById(R.id.textView32);
        vehicle = view.findViewById(R.id.imageView10);
        com = view.findViewById(R.id.btnArr);
        cancel = view.findViewById(R.id.btnCancelar);
        sc = view.findViewById(R.id.sc1);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        sharedpreferences = getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }

    private void check(){
        if(sharedpreferences != null) {
             zoomMap = sharedpreferences.getFloat(Zoom, 0.0f);
             if(zoomMap == 0.0f) zoomMap = 17.0f;
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //origin = new LatLng(41.615451, 0.618851);
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, 17.0f));
        mapReady = true;
    }

    private void llamar(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Reserva")
                .document(user.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            sc.setVisibility(View.VISIBLE);
                            com.setEnabled(true);
                            cancel.setEnabled(true);
                            com.setVisibility(View.VISIBLE);
                            cancel.setVisibility(View.VISIBLE);
                            titul.setText(documentSnapshot.getString("Campus"));
                            placa.setText(documentSnapshot.getString("Parking"));
                            entrada.setText(documentSnapshot.getString("HEntrada"));
                            sortida.setText(documentSnapshot.getString("HSortida"));

                            if(documentSnapshot.getString("Tipo").equals("Car")) {
                                vehicle.setBackgroundResource(R.drawable.car);
                                tipo = "Car";
                            }
                            else {
                                vehicle.setBackgroundResource(R.drawable.moto);
                                tipo = "Moto";
                            }

                            getLatLong();

                        }
                    }
                });
    }

    private void getLatLong(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(titul.getText().toString())
                .document(placa.getText().toString())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {

                            while(true) if(mapReady) break;
                            GeoPoint a = (GeoPoint) documentSnapshot.get("Posi");
                            origin = new LatLng(a.getLatitude(), a.getLongitude());
                            Posi = mMap.addMarker(new MarkerOptions().position(origin).title("Plaça Nº:"+placa.getText().toString()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origin, zoomMap));
                        }

                    }
                });
    }

    private void delete(){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Reserva").document(user.getUid())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        titul.setText("No hi ha cap reserva");
                        sc.setVisibility(View.INVISIBLE);
                        Posi.remove();
                        com.setEnabled(false);
                        cancel.setEnabled(false);
                        com.setVisibility(View.INVISIBLE);
                        cancel.setVisibility(View.INVISIBLE);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "No ha sigut possible eliminar la reserva, torna a intentar-ho en uns minuts", Toast.LENGTH_SHORT).show();
                    }
                });


        Map<String, Object> docData = new HashMap<>();
        docData.put("Disponible", "Si");
        db.collection(titul.getText().toString())
                .document(placa.getText().toString())
                .update(docData);


        db.collection("Campus")
                .document(titul.getText().toString())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            Map<String, Object> docData = new HashMap<>();

                            if(tipo.equals("Moto"))  {
                                int z = Integer.parseInt(documentSnapshot.getString("DisponiblesM"))+1;
                                docData.put("DisponiblesM", Integer.toString(z));
                            }
                            else {
                                int z = Integer.parseInt(documentSnapshot.getString("DisponiblesC"))+1;
                                docData.put("DisponiblesC", Integer.toString(z));
                            }
                            db.collection("Campus")
                                    .document(titul.getText().toString())
                                    .update(docData);
                        }
                    }
                });

    }


    public void clear(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.cancelRe))
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        delete();
                        /* DELETE BASE DE DADES */

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
