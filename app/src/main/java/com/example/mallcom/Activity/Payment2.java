package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mallcom.Database.SharedPrefManager;
import com.example.mallcom.Database.SqlLiteDataBase;
import com.example.mallcom.Models.CreateOrderAll;
import com.example.mallcom.Models.CreateOrderRequest;
import com.example.mallcom.Models.ModelCart;
import com.example.mallcom.Models.OrderData;
import com.example.mallcom.Models.OrderItemRequest;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Api;
import com.example.mallcom.Utils.Global;
import com.example.mallcom.Utils.ToolbarClass;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Payment2 extends ToolbarClass  implements View.OnClickListener {

    AppCompatButton button;
    TextView address,method;
    HashMap<String, String> hashMap = new HashMap<>();
    LinearLayout progressLay;
    JSONArray orders = new JSONArray();

    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.payment2, "بيانات الدفع");
        new Global().changeStatusBarColor(this,getResources().getColor(R.color.colorPrimary));
        try {
            hashMap = (HashMap<String, String>) getIntent().getSerializableExtra("hashMap");

//            String scamDatas = getIntent().getStringExtra("orders");
//            orders = new JSONArray(scamDatas);

        } catch (Exception e) {
            e.printStackTrace();
        }
        init();
    }

    private void init() {
        progressLay = findViewById(R.id.progressLay);
        address = findViewById(R.id.address);
        method = findViewById(R.id.method);
        address.setText(hashMap.get("state") +" "+ hashMap.get("city")+" "+hashMap.get("region"));
        method.setText(hashMap.get("payment_method"));
        button = findViewById(R.id.btn);
        button.setOnClickListener(this);

    }


    private void createOrder() {
        progressLay.setVisibility(View.VISIBLE);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Request.Builder ongoing = chain.request().newBuilder();
//                        ongoing.addHeader("Content-Type", "application/json;");
//                        ongoing.addHeader("Content-Type", "text/plain");
                        ongoing.addHeader("Accept", "application/json;");
                        ongoing.addHeader("Content-Type", "multipart/form-data; boundary=<calculated when request is sent>");
//                        ongoing.addHeader("Accept", "application/json");
//                        ongoing.addHeader("lang", SharedPrefManager.getInstance(getApplicationContext()).GetAppLanguage());
                        String token = SharedPrefManager.getInstance(Payment2.this).getAppToken();
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

        Api.RetrofitCreateOrder5 service = retrofit.create(Api.RetrofitCreateOrder5.class);


        ArrayList<ModelCart> arrayList = new SqlLiteDataBase(getApplicationContext()).GetAllCart();
        List<OrderItemRequest> list = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            OrderData orderData = new OrderData();
            JSONObject cart_data = new JSONObject();
            try {
                cart_data.put("state_id",hashMap.get("state_id"));
                cart_data.put("product_id",arrayList.get(i).getId());
                cart_data.put("amount",arrayList.get(i).getQty());

                OrderItemRequest orderItemRequest = new OrderItemRequest();
                orderItemRequest.setState_id(Integer.parseInt(hashMap.get("state_id")));
                orderItemRequest.setProduct_id(Integer.parseInt(arrayList.get(i).getId()));
                orderItemRequest.setAmount(Integer.parseInt(arrayList.get(i).getQty()));
                list.add(orderItemRequest);


            } catch (JSONException e) {
                e.printStackTrace();
            }
//            orderData.setState_id("1");
//            orderData.setProduct_id(arrayList.get(i).getId());
//            orderData.setAmount(arrayList.get(i).getQty());

//            Iterator x = cart_data.keys();
//            JSONArray jsonArray = new JSONArray();
//
//            while (x.hasNext()){
//                String key = (String) x.next();
//                try {
//                    jsonArray.put(cart_data.get(key));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }

            orders.put(cart_data);
//            orders.put(jsonArray);

//                        hashMap.put("orders["+i+"][product_id]",arrayList.get(i).getId());
//                        hashMap.put("orders["+i+"][amount]",arrayList.get(i).getQty());

        }


//        hashMap.remove("account_id");
//        hashMap.remove("state");
//        hashMap.remove("region");
//        hashMap.remove("city");
//        hashMap.remove("total");
        JSONObject params = new JSONObject();
        try {
            params.put("payment_method","cash");
            params.put("orders",orders);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String , Object> hashMap2 = new HashMap<>();
        hashMap2.put("orders",orders);
        hashMap2.put("payment_method",hashMap.get("payment_method"));

        CreateOrderAll createOrderAll = new CreateOrderAll();
//        createOrderAll.setAccount_id(1);
        createOrderAll.setPayment_method(hashMap.get("payment_method"));
        createOrderAll.setOrders(list);


        Call<String> call = service.putParam(createOrderAll);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                try {
                    JSONObject object = new JSONObject(response.body());
                    String success = object.getString("success");
                    switch (success) {
                        case "true": {
//                            JSONArray data = object.getJSONArray("data");
                            Intent intent = new Intent(getApplicationContext(),PaymentDone.class);
                            intent.putExtra("hashMap",hashMap);
                            startActivity(intent);
                            finish();
                            break;
                        }

                        default: {

                            JSONArray errors = object.getJSONArray("errors");
                            String msg = errors.getString(0);
                            dialogWarning(msg);
                            break;
                        }
                    }
                    progressLay.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Payment2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressLay.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Toast.makeText(Payment2.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                progressLay.setVisibility(View.GONE);
            }
        });
    }


    private void sendOrder(){
        progressLay.setVisibility(View.VISIBLE);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Request.Builder ongoing = chain.request().newBuilder();
//                        ongoing.addHeader("Content-Type", "application/json;");
//                        ongoing.addHeader("Content-Type", "text/plain");
                        ongoing.addHeader("Accept", "application/json;");
                        ongoing.addHeader("Content-Type", "multipart/form-data; boundary=<calculated when request is sent>");
//                        ongoing.addHeader("Accept", "application/json");
//                        ongoing.addHeader("lang", SharedPrefManager.getInstance(getApplicationContext()).GetAppLanguage());
                        String token = SharedPrefManager.getInstance(Payment2.this).getAppToken();
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

        Api.RetrofitCreateOrder3 service = retrofit.create(Api.RetrofitCreateOrder3.class);

        ArrayList<ModelCart> arrayList = new SqlLiteDataBase(getApplicationContext()).GetAllCart();

        for (int i = 0; i < arrayList.size(); i++) {
            OrderData orderData = new OrderData();
            JSONObject cart_data = new JSONObject();
            try {
                cart_data.put("state_id","");
                cart_data.put("product_id",arrayList.get(i).getId());
                cart_data.put("amount",arrayList.get(i).getQty());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            orders.put(cart_data);

        }
        JSONObject params = new JSONObject();
        try {
            params.put("payment_method","cash");
            params.put("orders",orders);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String ,String> hashMap2 = new HashMap<>();
        hashMap2.put("orders",orders.toString());
        hashMap2.put("payment_method",hashMap.get("payment_method"));
        Call<JSONObject> call = service.putParam(params);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                Log.d("response", "Getting response from server: "+response);
                try {
                    JSONObject object = response.body();
                    String success = object.getString("success");
                    switch (success) {
                        case "true": {
//                            JSONArray data = object.getJSONArray("data");
                            Intent intent = new Intent(getApplicationContext(),PaymentDone.class);
                            intent.putExtra("hashMap",hashMap);
                            startActivity(intent);
                            finish();
                            break;
                        }

                        default: {

                            Toast.makeText(Payment2.this, "حدث خطأ", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(Payment2.this, object.getString("error"), Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    progressLay.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Payment2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressLay.setVisibility(View.GONE);



            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.d("response", "Getting response from server: " + t);
                progressLay.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn:{
               createOrder();
//               sendOrder();
                break;
            }
        }
    }



    private void dialogWarning(String msg) {
//        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_warning);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        AppCompatButton buttonYes = dialog.findViewById(R.id.btnYes);
        buttonYes.setText("موافق");
        AppCompatButton buttonNo = dialog.findViewById(R.id.btnNo);
        buttonNo.setText("ليس الان");
        buttonNo.setVisibility(View.GONE);
        TextView message = dialog.findViewById(R.id.msg);
        message.setText(msg);


        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });


        dialog.show();

    }




}