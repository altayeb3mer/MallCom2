package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mallcom.Adapter.MainDepartmentAdapter;
import com.example.mallcom.Adapter.NotoficationAdapter;
import com.example.mallcom.Database.SharedPrefManager;
import com.example.mallcom.Models.DepartmentModel;
import com.example.mallcom.Models.NotificationModel;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Api;
import com.example.mallcom.Utils.ToolbarClass;

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

public class MainDepartment extends ToolbarClass {
    RecyclerView recyclerView;
    ArrayList<DepartmentModel> arrayList;
    MainDepartmentAdapter adapterMyOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_department);
        super.onCreate(R.layout.activity_main_department, "الاقسام الرئيسية");

    }
    private void init(ArrayList<DepartmentModel> list) {
        recyclerView = findViewById(R.id.recyclermaindepartment);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getBaseContext(), 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

//        arrayList = new ArrayList<>();
//        for (int i = 0; i < 9; i++) {
//            DepartmentModel modelMyOrder = new DepartmentModel();
//            modelMyOrder.setId(""+i);
//            arrayList.add(modelMyOrder);
//        }
        adapterMyOrder = new MainDepartmentAdapter(this,arrayList);
        recyclerView.setAdapter(adapterMyOrder);
    }
    LinearLayout progressLay;
    private void addJob() {
        arrayList = new ArrayList<>();
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
                .readTimeout(60*5, TimeUnit.SECONDS)
                .connectTimeout(60*5, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.ROOT_URL)
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api.RetrofitCategory service = retrofit.create(Api.RetrofitCategory.class);

        Call<String> call = service.putParam();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                try {
                    JSONObject object = new JSONObject(response.body());
                    String statusCode = object.getString("code");
                    switch (statusCode) {
                        case "200": {

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