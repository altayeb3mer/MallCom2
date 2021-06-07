package com.example.mallcom.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.mallcom.Database.SharedPrefManager;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Api;
import com.example.mallcom.Utils.Global;
import com.example.mallcom.Utils.ToolbarClass;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class DeliveryDetails extends ToolbarClass implements View.OnClickListener {

    AppCompatButton button;
    EditText phone, state, city, region;
    LinearLayout progressLay;
    HashMap<String, String> hashMap = new HashMap<>();
    String state_id = "";
    JSONArray orders = new JSONArray();

    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_delievary_details, "معلومات التوصيل");
        try {
            hashMap = (HashMap<String, String>) getIntent().getSerializableExtra("hashMap");

            String scamDatas = getIntent().getStringExtra("orders");
            orders = new JSONArray(scamDatas);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Global().changeStatusBarColor(this, getResources().getColor(R.color.colorPrimary));
        init();
        getProfile();
    }

    private void init() {
        phone = findViewById(R.id.phone);
        state = findViewById(R.id.state);
        city = findViewById(R.id.city);
        region = findViewById(R.id.region);
        progressLay = findViewById(R.id.progressLay);

        button = findViewById(R.id.btn);
        button.setOnClickListener(this);
    }

    private void getProfile() {
        progressLay.setVisibility(View.VISIBLE);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Request.Builder ongoing = chain.request().newBuilder();
                        ongoing.addHeader("Content-Type", "application/json;");
                        ongoing.addHeader("Accept", "application/json");
//                        ongoing.addHeader("lang", SharedPrefManager.getInstance(getApplicationContext()).GetAppLanguage());
                        String token = SharedPrefManager.getInstance(DeliveryDetails.this).getAppToken();
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
                            state_id = dataObj.getString("state_id");


                            JSONObject stateObj = dataObj.getJSONObject("state");
                            state.setText(stateObj.getString("name"));
                            city.setText(stateObj.getString("city"));

                            region.setText(dataObj.getString("address"));
                            phone.setText(dataObj.getString("phone"));

//                            hashMap.put("orders[0][state_id]",state_id);
                            hashMap.put("account_id",dataObj.getString("id"));

                            hashMap.put("state",stateObj.getString("name"));
                            hashMap.put("city",stateObj.getString("city"));
                            hashMap.put("region",dataObj.getString("address"));

                            break;
                        }

                        default: {
                            Toast.makeText(DeliveryDetails.this, "حدث خطأ حاول مجددا", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    progressLay.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(DeliveryDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressLay.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Toast.makeText(DeliveryDetails.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                progressLay.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn: {

                Intent intent = new Intent(getApplicationContext(),Payment1.class);
                intent.putExtra("hashMap",hashMap);
                startActivity(intent);
//                startActivity(new Intent(getApplicationContext(), Payment1.class));
                break;
            }
        }
    }
}