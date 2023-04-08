package com.telugu.a5pm1;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class fragment2 extends Fragment {

    ListView listView;
    public ArrayAdapter<String> arrayAdapter;
    String[] data = {};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Frag1_OnCreateView","Frag1_OnCreateView_Inside");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);
        Log.i("Frag1_OnCreateView","Frag1_OnCreateView_After View inflater");
        listView = (ListView) view.findViewById(R.id.listview2);
        Log.i("Frag1_OnCreateView","Frag1_OnCreateView_After view.findViewById");
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, data);
        Log.i("Frag1_OnCreateView","Frag1_OnCreateView_After arrayAdapter");
        listView.setAdapter(arrayAdapter);
        Log.i("Frag1_OnCreateView","Frag1_OnCreateView_After istView.setAdapter(arrayAdapter)");
        return view;
    }

    public ArrayAdapter<String> getMyArrayAdapter(ArrayList<String> frag2data)
    {
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, frag2data);
        listView.setAdapter(arrayAdapter);
        return arrayAdapter;
    }
}