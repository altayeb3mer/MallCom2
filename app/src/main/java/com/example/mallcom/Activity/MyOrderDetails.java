package com.example.mallcom.Activity;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mallcom.Adapter.AdapterMyOrderDetails;
import com.example.mallcom.Database.SharedPrefManager;
import com.example.mallcom.Models.ModelItems;
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

public class MyOrderDetails extends ToolbarClass {


    RecyclerView recyclerView;
    AdapterMyOrderDetails adapterMyOrder;


    LinearLayout progressLay;

    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.my_order_details, "تفاصيل طلب");
        new Global().changeStatusBarColor(this, getResources().getColor(R.color.colorPrimary));
        Bundle args = getIntent().getExtras();
        init();
        if (args != null) {
            String orderNumber = args.getString("orderNumber");
            getMyOrderProduct(orderNumber);
        }
//        getMyOrderProduct("161901960922");

    }

    private void init() {
        progressLay = findViewById(R.id.progressLay);

    }

    private void initAdapter(ArrayList<ModelItems> list) {
        recyclerView = findViewById(R.id.recycler);
        adapterMyOrder = new AdapterMyOrderDetails(this, list);
        recyclerView.setAdapter(adapterMyOrder);
    }


    private void getMyOrderProduct(String orderNumber) {
        final ArrayList<ModelItems> arrayList = new ArrayList<>();
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

        Api.RetrofitGetMyOrderProduct service = retrofit.create(Api.RetrofitGetMyOrderProduct.class);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("orderNumber", orderNumber);
        Call<String> call = service.putParam(hashMap);
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
                                JSONObject itemData = dataArray.getJSONObject(i);

                                ModelItems modelItems = new ModelItems();
                                modelItems.setId(itemData.getString("product_id"));

                                JSONObject product = itemData.getJSONObject("product");
                                modelItems.setName(product.getString("name"));
                                modelItems.setImage(product.getString("photo"));

                                modelItems.setDesc(itemData.getString("amount"));
                                modelItems.setPrice1(product.getString("price"));
                                modelItems.setFinalPrice(product.getString("final_price"));



                                JSONArray rateArray = product.getJSONArray("rate");
                                if (rateArray.length()>0){
                                    JSONObject rateObj = rateArray.getJSONObject(0);
                                    modelItems.setRate(rateObj.getString("rate"));
                                }


                                arrayList.add(modelItems);
                            }

                            if (arrayList.size() > 0) {
                                initAdapter(arrayList);
                            } else {
                                Toast.makeText(MyOrderDetails.this, "ليس لديك طلبات حتى الان", Toast.LENGTH_SHORT).show();
                            }


                            break;
                        }
                        case "false": {
                            Toast.makeText(MyOrderDetails.this, "حدث خطأ الرجاء المحاولة مرة اخرى", Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(MyOrderDetails.this, "ليس لديك طلبات حتى الان", Toast.LENGTH_SHORT).show();
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