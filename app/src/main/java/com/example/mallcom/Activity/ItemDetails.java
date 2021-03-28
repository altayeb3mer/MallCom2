package com.example.mallcom.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.mallcom.Adapter.ViewPagerAdapter;
import com.example.mallcom.Fragment.FragmentDet1;
import com.example.mallcom.Fragment.FragmentDet2;
import com.example.mallcom.R;
import com.google.android.material.tabs.TabLayout;


public class ItemDetails extends AppCompatActivity {

    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    TabLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);
        init();
    }

    private void init() {
        tableLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new FragmentDet1(), "التفاصيل");
        viewPagerAdapter.addFragment(new FragmentDet2(), "المقاسات");
        viewPager.setAdapter(viewPagerAdapter);
        tableLayout.setupWithViewPager(viewPager);

    }
}