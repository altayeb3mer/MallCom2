package com.example.mallcom.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.mallcom.Adapter.ViewPagerAdapter;
import com.example.mallcom.Database.SharedPrefManager;
import com.example.mallcom.Database.SqlLiteDataBase;
import com.example.mallcom.Fragment.Fragment1;
import com.example.mallcom.Fragment.Fragment2;
import com.example.mallcom.Fragment.Fragment3;
import com.example.mallcom.Fragment.Fragment4;
import com.example.mallcom.Fragment.Fragment5;
import com.example.mallcom.Models.ModelCart;
import com.example.mallcom.R;
import com.example.mallcom.Utils.CustomViewPager;
import com.example.mallcom.Utils.Global;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    private CustomViewPager viewPager;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView ic_menu_nav,imgCart;
    CardView cardSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer);

        /* if (SharedPrefManager.getInstance(this).getAppToken().equals("")){
            startActivity(new Intent(this,Login.class));
            finish();
        }*/

        new Global().changeStatusBarColor(this,getResources().getColor(R.color.colorPrimary));
        init();
        setBadgeCount();

    }

    private void setBadgeCount() {
        ArrayList<ModelCart> arrayList = new SqlLiteDataBase(getApplicationContext()).GetAllCart();
        TextView badges = findViewById(R.id.txtBadge);
        if (arrayList.size()>0){
            badges.setText(arrayList.size()+"");
            badges.setVisibility(View.VISIBLE);
        }else {
            badges.setVisibility(View.GONE);
        }
    }

    private void init() {
        cardSearch = findViewById(R.id.cardSearch);
        cardSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SearchProducts.class));
            }
        });
        imgCart = findViewById(R.id.imgCart);
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CartActivity.class));
            }
        });
        ic_menu_nav = findViewById(R.id.ic_menu);
        drawerLayout = findViewById(R.id.n_drawer);
        navigationView = findViewById(R.id.n_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        ic_menu_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


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

        adapter.addFragment(fragment1, "????????????????");
        adapter.addFragment(fragment2, "??????????????");
        adapter.addFragment(fragment3, "??????????");
        adapter.addFragment(fragment4, "????????????");
//        adapter.addFragment(fragment5, "????????????");
        viewPager.setAdapter(adapter);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_nav1:{
                switchToFragment(1);
                break;
            }case R.id.dept3:{
                //switchToFragment(1);
                startActivity(new Intent(getApplicationContext(), MyFavorite.class));

                break;
            }
            case R.id.nav_menu_2:{
                //switchToFragment(1);
                startActivity(new Intent(getApplicationContext(), MainDepartment.class));

                break;
            }
            case R.id.btn_nav2:{
                //switchToFragment(2);
                startActivity(new Intent(getApplicationContext(), Notifications.class));

                break;
            }
            case R.id.btn_nav3:{
                //switchToFragment(3);
                startActivity(new Intent(getApplicationContext(), MainDepartment.class));

                break;
            }
            case R.id.btn_nav4:{
               // switchToFragment(4);
                startActivity(new Intent(getApplicationContext(), Myaccount.class));

                break;
            }
            case R.id.login:{

                startActivity(new Intent(getApplicationContext(),Login.class));
                break;
            }
            case R.id.reg:{
                startActivity(new Intent(getApplicationContext(),Registration.class));
                break;
            }
            case R.id.pinCode:{
                startActivity(new Intent(getApplicationContext(),ConfirmPhone.class));
                break;
            }
            case R.id.dept2:{
                startActivity(new Intent(getApplicationContext(), MyOrders.class));
                break;
            }
            case R.id.dept4:{
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
                break;
            }

        }
        drawerLayout.closeDrawers();
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        setBadgeCount();
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
//            case 5: {
//                viewPager.setCurrentItem(4);
//                SetNavigationItemSelected(R.id.btn_nav5);
//                break;
//            }

        }
    }

    private void SetNavigationItemSelected(int id){
        bottomNavigationView.getMenu().findItem(id).setChecked(true);
    }



}