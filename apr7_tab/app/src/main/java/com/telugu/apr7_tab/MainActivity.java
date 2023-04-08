package com.telugu.apr7_tab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("Test1","Test1");

        TabLayout tabLayout = findViewById(R.id.tablayout);
        Log.i("Test2","Test2");

        ViewPager viewPager = findViewById(R.id.viewpager);
        Log.i("Test3","Test3");

        tabLayout.setupWithViewPager(viewPager);
        Log.i("Test4","Test4");


        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new fragment1(),"Words");
        vpAdapter.addFragment(new fragment2(),"Meanings");
        vpAdapter.addFragment(new fragment3(),"Others");
        viewPager.setAdapter(vpAdapter);
    }
}