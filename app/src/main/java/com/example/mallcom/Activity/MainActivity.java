package com.example.mallcom.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
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
import com.example.mallcom.Utils.Api;
import com.example.mallcom.Utils.CustomViewPager;
import com.example.mallcom.Utils.Global;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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

//         if (SharedPrefManager.getInstance(this).getAppToken().equals("")){
//            startActivity(new Intent(this,Login.class));
//            finish();
//        }

        new Global().changeStatusBarColor(this,getResources().getColor(R.color.colorPrimary));
        init();
        setBadgeCount();
        getProfile();

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
        layTop = findViewById(R.id.layTop);
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

        adapter.addFragment(fragment1, "الرئيسية");
        adapter.addFragment(fragment2, "اشعارات");
        adapter.addFragment(fragment3, "اقسام");
//        adapter.addFragment(fragment4, "الحساب");
        adapter.addFragment(fragment5, "الحساب");
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
                switchToFragment(2);
//                startActivity(new Intent(getApplicationContext(), Notifications.class));

                break;
            }
            case R.id.btn_nav3:{
                switchToFragment(3);
//                startActivity(new Intent(getApplicationContext(), MainDepartment.class));

                break;
            }
            case R.id.btn_nav4:{
                switchToFragment(4);

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





    LinearLayout layTop;
    public void switchToFragment(int f_no) {
//        FragmentManager manager = getSupportFragmentManager();
        switch (f_no) {
            case 1: {
                layTop.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(0);
                SetNavigationItemSelected(R.id.btn_nav1);
                break;
            }
            case 2: {
                layTop.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(1);
                SetNavigationItemSelected(R.id.btn_nav2);
                break;
            }
            case 3: {
                layTop.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(2);
                SetNavigationItemSelected(R.id.btn_nav3);
                break;
            }
            case 4: {
                layTop.setVisibility(View.GONE);
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





    private void getProfile() {
        View headerview = navigationView.getHeaderView(0);
        final ImageView profile_image = headerview.findViewById(R.id.profile_image);
        final TextView textViewName =  headerview.findViewById(R.id.name);
        TextView textViewHello = headerview.findViewById(R.id.hello);
        knowingTime(textViewHello);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Request.Builder ongoing = chain.request().newBuilder();
                        ongoing.addHeader("Content-Type", "application/json;");
                        ongoing.addHeader("Accept", "application/json");
//                        ongoing.addHeader("lang", SharedPrefManager.getInstance(getApplicationContext()).GetAppLanguage());
                        String token = SharedPrefManager.getInstance(getApplicationContext()).getAppToken();
//                        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xMjcuMC4wLjE6ODAwMFwvYXBpXC92MVwvdXNlclwvbG9naW4iLCJpYXQiOjE2MTYzNzQzMTQsIm5iZiI6MTYxNjM3NDMxNCwianRpIjoiVjY2bXVxM2FpSHJwenFBayIsInN1YiI6MSwicHJ2IjoiMjNiZDVjODk0OWY2MDBhZGIzOWU3MDFjNDAwODcyZGI3YTU5NzZmNyJ9.TF70v29HuwEQCb9ySR--bbY1pRivGv2831d0M1k_Wt0";

                        ongoing.addHeader("Authorization", token);
                        return chain.proceed(ongoing.build());
                    }
                })
                .readTimeout(60 * 5, TimeUnit.SECONDS)
                .connectTimeout(60 * 5, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.ROOT_URL)
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api.RetrofitGetMyProfile service = retrofit.create(Api.RetrofitGetMyProfile.class);

        Call<String> call = service.putParam();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                try {
                    JSONObject object = new JSONObject(response.body());
                    String success = object.getString("success");
                    switch (success) {
                        case "true": {
                            JSONArray data = object.getJSONArray("data");
                            JSONObject dataObj = data.getJSONObject(0);
                            String name = dataObj.getString("firstName") + " " + dataObj.getString("middleName");

                            textViewName.setText(name);

//                            JSONObject stateObj = dataObj.getJSONObject("state");




                            Glide.with(getApplicationContext()).load(dataObj.getString("thumbnail")).
                                    into(profile_image);

                            break;
                        }

                        default: {
//                            Toast.makeText(context, "حدث خطأ حاول مجددا", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
//                    progressLay.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
//                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
//                progressLay.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
//                Toast.makeText(context, ""+ throwable.getMessage(), Toast.LENGTH_SHORT).show();
//                progressLay.setVisibility(View.GONE);
            }
        });
    }

    private void knowingTime(TextView textView){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String getCurrentTime  = sdf.format(new Date());
        String getTestTime="12:00";

        if (getCurrentTime.compareTo(getTestTime) < 0) {
            // Do your staff
            Log.d("Return", "getTestTime less than getCurrentTime ");
            textView.setText("صباح الخير");

        } else {
            Log.d("Return", "getTestTime older than getCurrentTime ");
            textView.setText("مساء الخير");
        }


    }

}