package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mallcom.Adapter.AdapterProductsWithRate;
import com.example.mallcom.Adapter.AdapterSubDept;
import com.example.mallcom.Adapter.AdapterSubItem;
import com.example.mallcom.Adapter.MainDepartmentAdapter;
import com.example.mallcom.Adapter.SlideShow_adapter;
import com.example.mallcom.Models.DepartmentModel;
import com.example.mallcom.Models.ModelItems;
import com.example.mallcom.Models.ModelProducts;
import com.example.mallcom.Models.ModelSlider;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Api;
import com.example.mallcom.Utils.Global;
import com.example.mallcom.Utils.ToolbarClass;

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

import static android.widget.Toast.LENGTH_LONG;

public class SubDept extends ToolbarClass {

    SlideShow_adapter slideShow_adapter;
    ViewPager viewPager;
    CircleIndicator circleIndicator;

    //rec
TextView subname;
ImageView imgBack;
    View view;
    AdapterSubItem adapterProductsWithRate;
    RecyclerView recyclerView;
    AdapterSubDept adapterSubDept;



    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getExtras().getString("categoryName");
        super.onCreate(R.layout.activity_main_department, title);
        new Global().changeStatusBarColor(this,getResources().getColor(R.color.colorPrimary));
        init();
        getSubDept();
    }


    private void init() {
        progressLay = findViewById(R.id.progressLay);
    }



//    private void initAdapter( ArrayList<ModelProducts> list) {
//        recyclerView = findViewById(R.id.recyclerProduct);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(gridLayoutManager);
//        adapterProductsWithRate = new AdapterSubItem(this,list);
//        recyclerView.setAdapter(adapterProductsWithRate);
//
//
//    }
    private void initAdapter(ArrayList<DepartmentModel> list) {
        recyclerView = findViewById(R.id.recyclermaindepartment);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getBaseContext(), 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapterSubDept = new AdapterSubDept(this,list);
        recyclerView.setAdapter(adapterSubDept);
    }

    LinearLayout progressLay;
    private void getSubDept() {
        final ArrayList<DepartmentModel> arrayList = new ArrayList<>();
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

        Api.RetrofitgetSubCategories service = retrofit.create(Api.RetrofitgetSubCategories.class);
        HashMap<String,String> hashMap =new HashMap<>();
        hashMap.put("categoryName",getIntent().getExtras().getString("categoryName"));
        //hashMap.put("subCategory","Boys");
        Call<String> call = service.putParam(getIntent().getExtras().getString("categoryName"));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                try {
                    JSONObject object = new JSONObject(response.body());
                    String success = object.getString("success");
                    switch (success) {
                        case "true": {
//                            JSONObject data = object.getJSONObject("data");
                            JSONArray data = object.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject itemData = data.getJSONObject(i);

                                DepartmentModel departmentModel = new DepartmentModel();
                                departmentModel.setName(itemData.getString("name"));
                                departmentModel.setSubImage(itemData.getString("sub_img"));
                                departmentModel.setSubCat(itemData.getString("subCategory"));
//                                departmentModel.setHasChild(itemData.getBoolean("hasSub"));
                                arrayList.add(departmentModel);
                            }

                            if (arrayList.size()>0){
                                initAdapter(arrayList);
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