package com.telugu.apr4_teldict;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.preference.PreferenceScreen;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper DB;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrName = new ArrayList<String>();
    ArrayList<String> meaning = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listview);
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

        Cursor res = DB.getdata();
        while (res.moveToNext())
        {
            arrName.add(res.getString(0));
        }
         arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, arrName);
         listView.setAdapter(arrayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s) {
                // arrayAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}
