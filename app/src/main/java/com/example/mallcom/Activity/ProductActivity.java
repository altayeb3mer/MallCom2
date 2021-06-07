package com.example.mallcom.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.mallcom.Adapter.AdapterProductsWithRate;
import com.example.mallcom.Adapter.SlideShow_adapter;
import com.example.mallcom.Database.SqlLiteDataBase;
import com.example.mallcom.Models.ModelCart;
import com.example.mallcom.Models.ModelProducts;
import com.example.mallcom.Models.ModelSlider;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ProductActivity extends AppCompatActivity {

    SlideShow_adapter slideShow_adapter;
    ViewPager viewPager;
    CircleIndicator circleIndicator;

    //rec
   TextView subname;
    ImageView imgBack;
    View view;
    AdapterProductsWithRate adapterProductsWithRate;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_ac);
        subname=findViewById(R.id.subname);
        imgBack=findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        subname.setText(getIntent().getExtras().getString("subCategory"));
       // Toast.makeText(getApplicationContext(), "in sub", Toast.LENGTH_SHORT).show();

        init();
        getProducts();
    }

    LinearLayout noData;
    ImageView imgCart,imgSearch;
    private void init() {
        imgSearch = findViewById(R.id.imgSearch);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SearchProducts.class));
            }
        });
        noData = findViewById(R.id.noData);
        progressLay = findViewById(R.id.progressLay);
        imgCart = findViewById(R.id.imgCart);
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductActivity.this,CartActivity.class));
            }
        });
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

    private void initSlider(ArrayList<String> list1, ArrayList<ModelSlider> list2) {
        viewPager = findViewById(R.id.viewpager);
        if (list1.size()>0){
            slideShow_adapter = new SlideShow_adapter(this,list1);
        }else{
            slideShow_adapter = new SlideShow_adapter(list2,this);

        }
        viewPager.setAdapter(slideShow_adapter);
        circleIndicator = findViewById(R.id.indicator);
        circleIndicator.setViewPager(viewPager);
        AutoSwipingImg();
    }
    Handler handler;
    Runnable runnable;
    Timer timer;
    private void AutoSwipingImg() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                SwipImg();

            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 0, 3000);
    }
    private void SwipImg() {
        int i = viewPager.getCurrentItem();

        if (i == slideShow_adapter.urls.size() - 1) {
            i = 0;
        } else {
            i++;
        }
        viewPager.setCurrentItem(i, true);
    }



    private void initAdapter( ArrayList<ModelProducts> list) {
        recyclerView = findViewById(R.id.recyclerProduct);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapterProductsWithRate = new AdapterProductsWithRate(this,list);
        recyclerView.setAdapter(adapterProductsWithRate);


    }

    LinearLayout progressLay;
    private void getProducts() {
        ArrayList<String> arrayListImg  = new ArrayList<>();
        final ArrayList<ModelProducts> arrayList = new ArrayList<>();
        progressLay.setVisibility(View.VISIBLE);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Request.Builder ongoing = chain.request().newBuilder();
                        ongoing.addHeader("Content-Type", "application/json;");
                        ongoing.addHeader("Accept", "application/json");
//                        ongoing.addHeader("lang", SharedPrefManager.getInstance(getApplicationContext()).GetAppLanguage());
//                        String token = SharedPrefManager.getInstance(getApplicationContext()).getAppToken();
//                        ongoing.addHeader("Authorization", token);
                        return chain.proceed(ongoing.build());
                    }
                })
                .readTimeout(60*5, TimeUnit.SECONDS)
                .connectTimeout(60*5, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.ROOT_URL)
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api.RetrofitGetProduct service = retrofit.create(Api.RetrofitGetProduct.class);
        HashMap<String,String> hashMap =new HashMap<>();
        hashMap.put("category",getIntent().getExtras().getString("category"));
        hashMap.put("subCategory",getIntent().getExtras().getString("subCategory"));
        //hashMap.put("subCategory","Boys");
        Call<String> call = service.putParam(hashMap);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                try {
                    JSONObject object = new JSONObject(response.body());
                    String success = object.getString("success");
                    switch (success) {
                        case "true": {
//                            JSONObject data = object.getJSONObject("data");
                            JSONArray resultData = object.getJSONArray("data");
                            for (int i = 0; i < resultData.length(); i++) {
                                JSONObject itemData = resultData.getJSONObject(i);

                                ModelProducts departmentModel = new ModelProducts();
                                departmentModel.setId(itemData.getString("id"));
                                departmentModel.setName(itemData.getString("name"));
                                departmentModel.setNamemain(itemData.getString("description"));
                                departmentModel.setImage(itemData.getString("photo"));
                                departmentModel.setPrice(itemData.getString("price"));
                              //  Toast.makeText(SubDeptproduct.this,itemData.getString("subCategory"), LENGTH_LONG).show();

                                JSONArray rateArray = itemData.getJSONArray("rate");
                                if (rateArray.length()>0){
                                    JSONObject rateObj = rateArray.getJSONObject(0);
                                    departmentModel.setRate(rateObj.getString("rate"));
                                }

                                arrayList.add(departmentModel);
                            }

                            if (arrayList.size()>0){
                                initAdapter(arrayList);
                                noData.setVisibility(View.GONE);
                            }else{
                                noData.setVisibility(View.VISIBLE);
                            }


                            break;
                        }

                        default: {
                            Toast.makeText(getApplicationContext(), "حدث خطأ حاول مجددا", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    progressLay.setVisibility(View.GONE);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressLay.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                progressLay.setVisibility(View.GONE);
            }
        });
    }

}