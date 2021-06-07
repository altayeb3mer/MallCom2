package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mallcom.Database.SharedPrefManager;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Api;
import com.goodiebag.pinview.Pinview;

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

public class ConfirmPhone extends AppCompatActivity  implements View.OnClickListener {
    AppCompatButton button;
    Pinview pinview;
    String otp="",userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_phone);
        Bundle args = getIntent().getExtras();
        if (args!=null) {
            userId = args.getString("userId");
        }
        userId = args.getString("userId");

//        Toast.makeText(getApplicationContext(), userId, Toast.LENGTH_SHORT).show();


        init();
    }
    private void init() {
        progressLay = findViewById(R.id.progressLay);
        pinview = findViewById(R.id.pinview);
        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                otp = pinview.getValue().trim();

            }
        });
        button = findViewById(R.id.btn);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn: {
                if (!otp.equals("")&&otp.length()==6){
                    verifyAccount();
                }else{
                    Toast.makeText(this, "الرجاء كتابة كود صحيح", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }


    LinearLayout progressLay;
    private void verifyAccount() {
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

        Api.RetrofitVerifyAccount service = retrofit.create(Api.RetrofitVerifyAccount.class);
        HashMap<String,String> hashMap =new HashMap<>();
        hashMap.put("id",userId);
        hashMap.put("verificationCode",otp);
        Call<String> call = service.putParam(hashMap);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                try {
                    JSONObject object = new JSONObject(response.body());
                    String success = object.getString("success");
                    switch (success) {
                        case "true": {
                            JSONObject dataObj = object.getJSONObject("data");
                            String token = object.getString("token");
                            SharedPrefManager.getInstance(getApplicationContext()).storeAppToken("Bearer"+" "+token);

                            Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);

                            finish();
                            break;
                        }
//                        case "false": {
//                            Intent intent =new Intent(getApplicationContext(),Registration.class);
//                            intent.putExtra("phone",phone);
//                            startActivity(intent);
//
//
//                            break;
//                        }

                        default: {
                            Toast.makeText(getApplicationContext(), "خطأ البيانات غير صحيحة", Toast.LENGTH_SHORT).show();
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