package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mallcom.Adapter.AdapterMyOrder;
import com.example.mallcom.Database.SharedPrefManager;
import com.example.mallcom.Models.ModelMyOrder;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Api;
import com.example.mallcom.Utils.Global;
import com.example.mallcom.Utils.ToolbarClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MyOrders extends ToolbarClass {

    AdapterMyOrder adapterMyOrder;
    RecyclerView recyclerView;


    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_my_orders, "طلباتي");
        new Global().changeStatusBarColor(this,getResources().getColor(R.color.colorPrimary));
        init();
        if (SharedPrefManager.getInstance(this).getAppToken().isEmpty()){
            findViewById(R.id.notRegister).setVisibility(View.VISIBLE);
        }else{
            getMyOrders();
        }
    }

    private void init() {
        progressLay = findViewById(R.id.progressLay);
    }

    private void initAdapter(ArrayList<ModelMyOrder> arrayList) {
        recyclerView = findViewById(R.id.recycler);
        adapterMyOrder = new AdapterMyOrder(this,arrayList);
        recyclerView.setAdapter(adapterMyOrder);
    }



    LinearLayout progressLay;
    private void getMyOrders() {
        final ArrayList<ModelMyOrder> arrayList = new ArrayList<>();
        progressLay.setVisibility(View.VISIBLE);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Request.Builder ongoing = chain.request().newBuilder();
                        ongoing.addHeader("Content-Type", "application/json;");
                        ongoing.addHeader("Accept", "application/json");
//                        ongoing.addHeader("lang", SharedPrefManager.getInstance(getApplicationContext()).GetAppLanguage());
                        String token = SharedPrefManager.getInstance(getApplicationContext()).getAppToken();
                        ongoing.addHeader("Authorization", token);
//                        ongoing.addHeader("Authorization", "Bearer"+" "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xMjcuMC4wLjE6ODAwMFwvYXBpXC92MVwvdXNlclwvbG9naW4iLCJpYXQiOjE2MTYzNzQzMTQsIm5iZiI6MTYxNjM3NDMxNCwianRpIjoiVjY2bXVxM2FpSHJwenFBayIsInN1YiI6MSwicHJ2IjoiMjNiZDVjODk0OWY2MDBhZGIzOWU3MDFjNDAwODcyZGI3YTU5NzZmNyJ9.TF70v29HuwEQCb9ySR--bbY1pRivGv2831d0M1k_Wt0");
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

        Api.RetrofitGetMyOrders service = retrofit.create(Api.RetrofitGetMyOrders.class);
        Call<String> call = service.putParam();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                try {
                    JSONObject object = new JSONObject(response.body());
                    String success = object.getString("success");
                    switch (success) {
                        case "true": {

                            JSONArray dataArray = object.getJSONArray("data");
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject jsonObject = dataArray.getJSONObject(i);
                                ModelMyOrder modelMyOrder = new ModelMyOrder();
                                modelMyOrder.setNumber(jsonObject.getString("orderNumber"));
                                modelMyOrder.setDate(jsonObject.getString("date"));
                                modelMyOrder.setItemCount(jsonObject.getString("products"));
                                modelMyOrder.setTotal(jsonObject.getString("total"));
                                modelMyOrder.setStatus(jsonObject.getString("status"));

                                arrayList.add(modelMyOrder);
                            }

                            if (arrayList.size()>0){
                                initAdapter(arrayList);
                                findViewById(R.id.noData).setVisibility(View.GONE);
                            }else{
                                findViewById(R.id.noData).setVisibility(View.VISIBLE);
//                                Toast.makeText(MyOrders.this, "لم تقم باضافة طلبات حتى الان", Toast.LENGTH_SHORT).show();
                            }


                            break;
                        }
                        case "false": {
//                            Toast.makeText(MyOrders.this, "حدث خطأ الرجاء المحاولة مرة اخرى", Toast.LENGTH_SHORT).show();
                            findViewById(R.id.noData).setVisibility(View.VISIBLE);
                            break;
                        }

                        default: {
//                            Toast.makeText(getApplicationContext(), "حدث خطأ حاول مجددا", Toast.LENGTH_SHORT).show();

                            findViewById(R.id.noData).setVisibility(View.VISIBLE);
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