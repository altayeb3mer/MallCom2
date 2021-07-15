package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.mallcom.Adapter.AdapterCart;
import com.example.mallcom.Database.SharedPrefManager;
import com.example.mallcom.Database.SqlLiteDataBase;
import com.example.mallcom.Models.ModelCart;
import com.example.mallcom.Models.ModelOrder_props;
import com.example.mallcom.Models.OrderData;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Api;
import com.example.mallcom.Utils.Global;
import com.example.mallcom.Utils.ToolbarClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CartActivity extends ToolbarClass {

    RecyclerView recyclerView;
    AdapterCart adapterCart;
    ArrayList<ModelCart> arrayList;
    AppCompatButton button;
    public static TextView textViewTotal;
    public static LinearLayout layNoData,layBtn;

    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_cart, "السلة");
        new Global().changeStatusBarColor(this,getResources().getColor(R.color.productActivityColor));
        init();
        getCart();
//        getStateId();
    }

    private void getCart() {
        arrayList = new SqlLiteDataBase(getApplicationContext()).GetAllCart();
        adapterCart = new AdapterCart(this,arrayList,progressLay);
        recyclerView.setAdapter(adapterCart);
        if (arrayList.size()>0){
            getTotal(arrayList);

            layBtn.setVisibility(View.VISIBLE);
            layNoData.setVisibility(View.INVISIBLE);
            getStateId();
        }else{
            layBtn.setVisibility(View.INVISIBLE);
            layNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCart();
    }

    public static double getTotal(ArrayList<ModelCart> list){
        double total = 0;
        for (int i = 0; i < list.size(); i++) {
            total = total + (Double.parseDouble(list.get(i).getPrice1())*Double.parseDouble(list.get(i).getQty()));

            textViewTotal.setText(new Global().formatNumber(total));
        }
        if (list.size()==0){
            layBtn.setVisibility(View.INVISIBLE);
            layNoData.setVisibility(View.VISIBLE);
        }
        double a=0d;
        return a;
    }

    public static JSONArray orders;
    LinearLayout progressLay;
    private void init() {
        progressLay = findViewById(R.id.progressLay);
        layNoData = findViewById(R.id.layNoData);
        layBtn = findViewById(R.id.btnLay);
        textViewTotal = findViewById(R.id.total);
        button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (new SharedPrefManager(CartActivity.this).getAppToken().isEmpty()){
                    dialogRegisterWarning("الرجاء التسجيل لارسال الطلب!");
                }else{

                    orders = new JSONArray();

//                String orders [][] =new String[3][arrayList.size()];


                    HashMap<String,String> hashMap = new HashMap<>();

                    for (int i = 0; i < arrayList.size(); i++) {
                        OrderData orderData = new OrderData();
                        orderData.setState_id(state_id);
                        orderData.setProduct_id(arrayList.get(i).getId());
                        orderData.setAmount(arrayList.get(i).getQty());
//                        color && size
                        ModelOrder_props modelOrder_props = new ModelOrder_props();
                        modelOrder_props.setId(arrayList.get(i).getId());
                        modelOrder_props.setColor(arrayList.get(i).getColor());
                        modelOrder_props.setSize(arrayList.get(i).getSize());

                        orderData.setOrder_props(modelOrder_props);

                        orders.put(orderData);

//                        hashMap.put("orders["+i+"][product_id]",arrayList.get(i).getId());
//                        hashMap.put("orders["+i+"][amount]",arrayList.get(i).getQty());

                    }





//                hashMap.put("orders", Arrays.toString(orders));

                    hashMap.put("total",textViewTotal.getText().toString());
                    hashMap.put("state_id",state_id);
                    Intent intent = new Intent(getApplicationContext(),DeliveryDetails.class);
                    intent.putExtra("hashMap",hashMap);
                    intent.putExtra("orders",orders.toString());
//                intent.putExtra("orders",orders);
                    startActivity(intent);
                }


            }
        });
        recyclerView = findViewById(R.id.recycler);
    }

    String state_id="";
    private void getStateId() {
        progressLay.setVisibility(View.VISIBLE);
//        button.setVisibility(View.GONE);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Request.Builder ongoing = chain.request().newBuilder();
                        ongoing.addHeader("Content-Type", "application/json;");
                        ongoing.addHeader("Accept", "application/json");
//                        ongoing.addHeader("lang", SharedPrefManager.getInstance(getApplicationContext()).GetAppLanguage());
                        String token = SharedPrefManager.getInstance(CartActivity.this).getAppToken();
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
                            button.setVisibility(View.VISIBLE);
                            break;
                        }

                        default: {
//                            Toast.makeText(DeliveryDetails.this, "حدث خطأ حاول مجددا", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    progressLay.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
//                    Toast.makeText(DeliveryDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressLay.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Toast.makeText(CartActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                progressLay.setVisibility(View.GONE);
            }
        });
    }


    private void dialogRegisterWarning(String msg) {
//        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_warning);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        AppCompatButton buttonYes = dialog.findViewById(R.id.btnYes);
        buttonYes.setText("تسجيل");
        AppCompatButton buttonNo = dialog.findViewById(R.id.btnNo);
        buttonNo.setText("ليس الان");
        TextView message = dialog.findViewById(R.id.msg);
        message.setText(msg);


        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
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