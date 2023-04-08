package com.telugu.a5pm1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<String> frag1arrayAdapter;
    ArrayAdapter<String> frag2arrayAdapter;
    ArrayList<String> frag1_Data = new ArrayList<String>();
    ArrayList<String> frag2_Data = new ArrayList<String>();
    VPAdapter vpAdapter;
    ListView listView;
    fragment1 fragment1 = new fragment1();
    fragment2 fragment2 = new fragment2();
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB = new DBHelper(this);

        try {
            DB.createDB();
        } catch (Exception e) {
            throw new Error("Database not created....");
        }

        try {
            DB.openDB();
        }catch(SQLException sqle){
            throw sqle;
        }

        Cursor res1 = DB.getWordsdata();
        while (res1.moveToNext())
        {
            frag1_Data.add(res1.getString(0));
        }

        Cursor res2= DB.getWordsMeaningsdata();
        while (res2.moveToNext())
        {
            frag2_Data.add(res2.getString(0));
        }
        Log.i("MainAct_OnCreate","MainAct_OnCreate_after setcontentview");
        listView = findViewById(R.id.listview1);
        Log.i("MainAct_OnCreate","MainAct_OnCreate_after listview1");
        TabLayout tabLayout = findViewById(R.id.tablayout);
        Log.i("MainAct_OnCreate","MainAct_OnCreate_after tablayout");

        ViewPager viewPager = findViewById(R.id.viewpager);
        Log.i("MainAct_OnCreate","MainAct_OnCreate_after viewpager");

        tabLayout.setupWithViewPager(viewPager);
        Log.i("MainAct_OnCreate","MainAct_OnCreate_after setupWithViewPager");


        vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        Log.i("MainAct_OnCreate","MainAct_OnCreate_after vpAdapter initiation");
        vpAdapter.addFragment(fragment1,"Words");
        Log.i("MainAct_OnCreate","MainAct_OnCreate_after adding Words fragment");
        vpAdapter.addFragment(fragment2,"Meanings");
        Log.i("MainAct_OnCreate","MainAct_OnCreate_after adding Meanings fragment");
        viewPager.setAdapter(vpAdapter);
        Log.i("MainAct_OnCreate","MainAct_OnCreate_after settings vpAdapter");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        Log.i("MainAct_onCreateOpt","MainAct_OnCreateOptions_after inflater");
        MenuItem menuItem = menu.findItem(R.id.action_search);
        Log.i("MainAct_onCreateOpt","MainAct_OnCreateOptions_after action_search");
        SearchView searchView = (SearchView) menuItem.getActionView();
        Log.i("MainAct_onCreateOpt","MainAct_OnCreateOptions_after searchView");
        searchView.setQueryHint("Type here to search");
        Log.i("MainAct_onCreateOpt","MainAct_OnCreateOptions_after Type here to search");
        frag1arrayAdapter = fragment1.getMyArrayAdapter(frag1_Data);
        Log.i("MainAct_onCreateOpt","MainAct_OnQueryTextChange_after fragment1.getMyArrayAdapter");

        frag2arrayAdapter = fragment2.getMyArrayAdapter(frag2_Data);
        Log.i("MainAct_onCreateOpt","MainAct_OnQueryTextChange_after fragment2.getMyArrayAdapter");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                frag1arrayAdapter.getFilter().filter(newText);
                Log.i("MainAct_OnCreate","MainAct_OnQueryTextChange_after getFilter");

                 frag2arrayAdapter.getFilter().filter(newText);
                Log.i("MainAct_OnCreate","MainAct_OnQueryTextChange_after getFilter");

                return false;
            }
        });
        return true;
    }
}