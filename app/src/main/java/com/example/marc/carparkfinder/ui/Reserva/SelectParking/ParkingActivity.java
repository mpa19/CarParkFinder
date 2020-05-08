package com.example.marc.carparkfinder.ui.Reserva.SelectParking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marc.carparkfinder.R;

import java.util.ArrayList;
import java.util.List;


public class ParkingActivity extends AppCompatActivity {
    GripAdapter adapter;
    GridView gv;
    private int selected = -1;


    String[] values = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32",
                        "33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60","61","62",
                        "63","64","65","66","67","68","69","70","71","72","73","74"};

    String[] valuesM = {"63","64","65","66","67","68","69","70","71","72","73","74"};

    String[] carAval = {"1","2","3","4","5","6","7","8","9","10","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32",
            "33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","52","53","54","55","56","57","58","59","60","61","62"};

    String[] motoAv = {};

    List<String> lastSource1 =  new ArrayList<>();
    List<String> lastSourceM =  new ArrayList<>();
    List<String> lastSource2 =  new ArrayList<>();
    List<String> lastSource3 =  new ArrayList<>();
    String val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);
        Bundle extras = getIntent().getExtras();
        val = extras.getString("Tipo");

        getUpList();

        createAdapter();

        actionBar();

        ImageView tv = findViewById(R.id.btnCheck);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selected>-1){
                    Intent result = new Intent();
                    result.putExtra("celda", selected+1);
                    setResult(RESULT_OK, result);
                    finish();
                } else Toast.makeText(ParkingActivity.this, R.string.sel, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createAdapter() {
        gv = findViewById(R.id.gv1);
        adapter = new GripAdapter(lastSource1, this);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(adapter);
    }

    public void actionBar(){
        Toolbar tb = findViewById(R.id.toolbar6);
        setSupportActionBar(tb);

        // Añadir flecha atras en toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getUpList() {
        for(String item:values)
            lastSource1.add(item);

        for(String item:valuesM)
            lastSourceM.add(item);

        for(String item:carAval)
            lastSource2.add(item);

        for(String item:motoAv)
            lastSource3.add(item);
    }


    class GripAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
        Context context;
        List<String> lastSource;
        Button button;


        public GripAdapter(List<String> values, Context context){
            this.context = context;
            this.lastSource = values;
        }

        @Override
        public int getCount() {
            return lastSource.size();
        }

        @Override
        public Object getItem(int i) {
            return lastSource.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if(view == null) {
                view = inflater.inflate(R.layout.seat, null);
            }
            if(lastSourceM.contains(String.valueOf(i+1)))  view = inflater.inflate(R.layout.seatm, null);
            else view = inflater.inflate(R.layout.seat, null);

            final TextView gridText = view.findViewById(R.id.tvNumero);
            gridText.setText(lastSource.get(i));
             if(val.equals("Car")) {
                 if (lastSource2.contains(String.valueOf(i+1))) {
                     view.setBackgroundColor(Color.GREEN);
                     if (selected == i) {
                         view.setBackgroundColor(Color.YELLOW);
                     }
                 } else view.setBackgroundColor(Color.RED);

                 if (lastSourceM.contains(String.valueOf(i+1))) view.setBackgroundColor(Color.GRAY);
             } else {
                 if (lastSource3.contains(String.valueOf(i+1))) {
                     view.setBackgroundColor(Color.GREEN);
                     if (selected == i) {
                         view.setBackgroundColor(Color.YELLOW);
                     }
                 }else view.setBackgroundColor(Color.RED);
                 if (!lastSourceM.contains(String.valueOf(i+1))) view.setBackgroundColor(Color.GRAY);
             }
            return view;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {

            if(val.equals("Car")) {
                if(!lastSourceM.contains(String.valueOf(position+1)) && lastSource2.contains(String.valueOf(position+1))) {
                    selected = position;
                    adapter.notifyDataSetChanged();
                } else Toast.makeText(context, R.string.fail, Toast.LENGTH_SHORT).show();
            } else {
                if(lastSourceM.contains(String.valueOf(position+1)) && lastSource3.contains(String.valueOf(position+1))) {
                    selected = position;
                    adapter.notifyDataSetChanged();
                } else Toast.makeText(context, String.valueOf(R.string.fail), Toast.LENGTH_SHORT).show();
            }
        }
    }
}



