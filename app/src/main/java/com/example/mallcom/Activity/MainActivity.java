package com.example.mallcom.Activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.mallcom.Adapter.ViewPagerAdapter;
import com.example.mallcom.Fragment.Fragment1;
import com.example.mallcom.Fragment.Fragment2;
import com.example.mallcom.Fragment.Fragment3;
import com.example.mallcom.Fragment.Fragment4;
import com.example.mallcom.Fragment.Fragment5;
import com.example.mallcom.R;
import com.example.mallcom.Utils.CustomViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    private CustomViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        bottomNavigationView = findViewById(R.id.btn_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(5);
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        Fragment3 fragment3 = new Fragment3();
        Fragment4 fragment4 = new Fragment4();
        Fragment5 fragment5 = new Fragment5();

        adapter.addFragment(fragment1, "الرئيسية");
        adapter.addFragment(fragment2, "المحادثات");
        adapter.addFragment(fragment3, "اضف اعلانك");
        adapter.addFragment(fragment4, "المفضلة");
        adapter.addFragment(fragment5, "الحساب");
        viewPager.setAdapter(adapter);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_nav1:{
                switchToFragment(1);
                break;
            }
            case R.id.btn_nav2:{
                switchToFragment(2);
                break;
            }
            case R.id.btn_nav3:{
                switchToFragment(3);
                break;
            }
            case R.id.btn_nav4:{
                switchToFragment(4);
                break;
            }
            case R.id.btn_nav5:{
                switchToFragment(5);
                break;
            }
        }
        return true;
    }




    public void switchToFragment(int f_no) {
//        FragmentManager manager = getSupportFragmentManager();
        switch (f_no) {
            case 1: {
                viewPager.setCurrentItem(0);
                SetNavigationItemSelected(R.id.btn_nav1);
                break;
            }
            case 2: {
                viewPager.setCurrentItem(1);
                SetNavigationItemSelected(R.id.btn_nav2);
                break;
            }
            case 3: {
                viewPager.setCurrentItem(2);
                SetNavigationItemSelected(R.id.btn_nav3);
                break;
            }
            case 4: {
                viewPager.setCurrentItem(3);
                SetNavigationItemSelected(R.id.btn_nav4);
                break;
            }
            case 5: {
                viewPager.setCurrentItem(4);
                SetNavigationItemSelected(R.id.btn_nav5);
                break;
            }

        }
    }

    private void SetNavigationItemSelected(int id){
        bottomNavigationView.getMenu().findItem(id).setChecked(true);
    }



}