package com.example.marc.carparkfinder.ui.Home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.marc.carparkfinder.R;
import com.example.marc.carparkfinder.ui.Home.ListsAdapter.Campus;
import com.example.marc.carparkfinder.ui.Home.ListsAdapter.ListCampusAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Campus = "nameKey";

    SharedPreferences sharedpreferences;

    List<Campus> campusList;
    List<Campus> campusList2;


    ListView listView;

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

        campusList2 = new ArrayList<>();
        campusList = new ArrayList<>();
        listView = view.findViewById(R.id.listview);

        campusList2.add(new Campus(R.drawable.rectorat, getResources().getString(R.string.rect), "60/62", "0/12"));
        campusList2.add(new Campus(R.drawable.cappont, getResources().getString(R.string.cap), "0/160", "0/0"));
        campusList2.add(new Campus(R.drawable.salut, getResources().getString(R.string.salut), "0/0", "0/0"));
        campusList2.add(new Campus(R.drawable.etsea, getResources().getString(R.string.etsa), "0/0", "0/0"));

        createList();
    }


    public void createList(){


        int type = -1;
        //adding some values to our list
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);



        if(sharedpreferences != null) {
            type = sharedpreferences.getInt(Campus, 0);
            if(type == 0) campusList.add(new Campus(R.drawable.rectorat, getResources().getString(R.string.rect), "60/62", "0/12"));
            else campusList.add(new Campus(R.drawable.cappont, getResources().getString(R.string.cap), "0/160", "0/0"));
        }



        adding(type);
        //creating the adapter
        ListCampusAdapter adapter = new ListCampusAdapter(getContext(), R.layout.view_campus, campusList);

        //attaching adapter to the listview
        listView.setAdapter(adapter);
    }

    public void adding(int add){
        for(int i = 0; i<campusList2.size(); i++){
            if(i!=add) campusList.add(campusList2.get(i));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        campusList = new ArrayList<>();
        listView.setAdapter(null);
        createList();

    }
}
