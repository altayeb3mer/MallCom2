package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mallcom.Data.Stateadata;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Api;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.widget.Toast.LENGTH_LONG;

public class Registration extends AppCompatActivity{
    AppCompatButton button;
    Spinner gender,state;
    EditText fullname,agech;
    LinearLayout progressLay;
    HashMap<String,String> hashMap =new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registraton);
        fullname = findViewById(R.id.fullname);
        agech = findViewById(R.id.agech);
        button = findViewById(R.id.btnregestration);
        gender=findViewById(R.id.gender);
        state=findViewById(R.id.state);
        progressLay = findViewById(R.id.progressLay);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("أنثى");
        arrayList.add("ذكر");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Registration.this,
                android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(arrayAdapter);
        gender.setAdapter(arrayAdapter);
        Statedata();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(fullname.getText().toString().equals("")||agech.getText().toString().equals(""))
            Toast.makeText(Registration.this,"الرجاء ملء كل الحقول", LENGTH_LONG).show();
                 else{
                     hashMap.put("firstName",fullname.getText().toString());
                     hashMap.put("username",fullname.getText().toString());
                     hashMap.put("phone",getIntent().getExtras().getString("userphone"));
                     hashMap.put("password",agech.getText().toString());
                     hashMap.put("state_id","1");
                     hashMap.put("gender",gender.getSelectedItem().toString());
                     registration();
                 }
            }
        });
    }

        private void registration() {
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

            Api.RetrofitRegister service = retrofit.create(Api.RetrofitRegister.class);

            Call<String> call = service.putParam(hashMap);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    try {
                        JSONObject object = new JSONObject(response.body());
                        Toast.makeText(getApplicationContext(), "حدث خطأ حاول مجددا", Toast.LENGTH_SHORT).show();

                        String success = object.getString("success");

                        switch (success) {
                            case "true": {

//                            JSONObject dataObj = object.getJSONObject("data");

                                Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                               // intent.putExtra("userId",object.getString("data"));
                               // intent.putExtra("userphone",phone);
                                startActivity(intent);
                                finish();



                                break;
                            }
                            case "false": {
                              //  Intent intent =new Intent(getApplicationContext(),Registration.class);
                              //  intent.putExtra("phone",phone);
                              //  startActivity(intent);
                              //  finish();
                                Toast.makeText(getApplicationContext(), "حدث خطأ حاول مجددا", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), object.getString("error")+":::", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(getApplicationContext(), e.getMessage()+"\n ", Toast.LENGTH_SHORT).show();
                    }
                    progressLay.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                    progressLay.setVisibility(View.GONE);
                }
            });
        }

    private void Statedata() {
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

        Api.RetrofitGetstate service = retrofit.create(Api.RetrofitGetstate.class);

        Call<Stateadata.Stateresponse> call = service.putParam();
        call.enqueue(new Callback<Stateadata.Stateresponse>() {
            @Override
            public void onResponse(Call<Stateadata.Stateresponse> call, Response<Stateadata.Stateresponse> response) {
                if (response.code()==200) {
                    if(response.body().getSuccess()){
                        ArrayList<String> arrayList = new ArrayList<>();

                        for (Stateadata.Datum data : response.body().getData())
                        {
                            arrayList.add("  "+data.getCity()+" ");
                        }
                        //Toast.makeText(Registration.this,arrayList.size()+"", Toast.LENGTH_LONG).show();

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Registration.this,
                                android.R.layout.simple_spinner_item, arrayList);
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        state.setAdapter(arrayAdapter);
                        progressLay.setVisibility(View.GONE);
                    }
                }

            }
            @Override
            public void onFailure(Call<Stateadata.Stateresponse> call, Throwable t) {
                Toast.makeText(Registration.this,t.toString()+"", LENGTH_LONG).show();
                progressLay.setVisibility(View.GONE);

            }
        });
    }


}