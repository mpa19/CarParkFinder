package com.example.marc.carparkfinder.ui.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

    List<Campus> campusList;

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

        createList(view);
    }

    public void createList(View view){
        campusList = new ArrayList<>();
        listView = view.findViewById(R.id.listview);

        //adding some values to our list
        campusList.add(new Campus(R.drawable.rectorat, getResources().getString(R.string.rect), "60/62", "0/12"));
        campusList.add(new Campus(R.drawable.cappont, getResources().getString(R.string.cap), "0/0", "0/0"));
        campusList.add(new Campus(R.drawable.salut, getResources().getString(R.string.salut), "0/0", "0/0"));
        campusList.add(new Campus(R.drawable.etsea, getResources().getString(R.string.etsa), "0/0", "0/0"));

        //creating the adapter
        ListCampusAdapter adapter = new ListCampusAdapter(getContext(), R.layout.view_campus, campusList);

        //attaching adapter to the listview
        listView.setAdapter(adapter);
    }
}
