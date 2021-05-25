package com.example.mallcom.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.mallcom.Adapter.SlideShow_adapter;
import com.example.mallcom.Database.SqlLiteDataBase;
import com.example.mallcom.Models.ModelCart;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Api;
import com.example.mallcom.Utils.Global;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ProductDetails extends AppCompatActivity implements View.OnClickListener {

    TextView addToCart, textViewName, textViewPrice, textViewDescription, textViewRate;
    LinearLayout progressLay;
    SlideShow_adapter slideShow_adapter;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    ConstraintLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        new Global().changeStatusBarColor(this, getResources().getColor(R.color.colorPrimary));
        init();
        Bundle args = getIntent().getExtras();
        if (args != null) {
            String id = args.getString("id");
            getDetails(id);
        }
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

    private void init() {
        container = findViewById(R.id.container);
        container.setVisibility(View.INVISIBLE);
        progressLay = findViewById(R.id.progressLay);
        textViewName = findViewById(R.id.name);
        textViewPrice = findViewById(R.id.price);
        textViewDescription = findViewById(R.id.description);
        textViewRate = findViewById(R.id.rate);

        addToCart = findViewById(R.id.addToCart);
        addToCart.setOnClickListener(this);
    }

    ModelCart modelCart = null;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addToCart: {

                addToCart(modelCart);
                break;
            }
        }
    }

    private void addToCart(ModelCart modelCart){

        try {
            new SqlLiteDataBase(getApplicationContext()).addToCart(modelCart);
            Toast.makeText(this, "تمت الاضافة", Toast.LENGTH_SHORT).show();
            setBadgeCount();

        } catch (Exception e) {
            Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void getDetails(String id) {
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
                .readTimeout(60 * 5, TimeUnit.SECONDS)
                .connectTimeout(60 * 5, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.ROOT_URL)
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api.RetrofitProductDetails service = retrofit.create(Api.RetrofitProductDetails.class);
        Call<String> call = service.putParam(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                try {
                    ArrayList<String> arrayListImages = new ArrayList<>();
                    JSONObject object = new JSONObject(response.body());
                    String success = object.getString("success");
                    switch (success) {
                        case "true": {

                            String id="",name="",price1="",price2="",desc="",qty="1",rate="",image="",
                            discount="";

                            JSONObject dataObj = object.getJSONObject("data");
                            id = dataObj.getString("id");
                            image = dataObj.getString("photo");
                            name = dataObj.getString("name");
                            textViewName.setText(name);
                            discount = dataObj.getString("discount");
                            price1 = dataObj.getString("price");
                            price2 =String.valueOf(Integer.parseInt(price1)-Integer.parseInt(discount));
                            textViewPrice.setText(price1);
                            desc = dataObj.getString("description");
                            textViewDescription.setText(desc);
                            JSONArray rateArray = dataObj.getJSONArray("rate");
                            JSONObject rateObj = null;
                            try {
                                rateObj = rateArray.getJSONObject(0);
                                rate = rateObj.getString("rate");
                                textViewRate.setText(rate);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            JSONArray arrayImages = dataObj.getJSONArray("product_photos");

                            if (arrayImages.length()>0){
                                for (int i = 0; i < arrayImages.length(); i++) {
                                    JSONObject imgObj = arrayImages.getJSONObject(i);
                                    arrayListImages.add(imgObj.getString("photo"));
                                }
                            }else{
                                arrayListImages.add(dataObj.getString("photo"));
                                initSlider(arrayListImages);
                            }

                            if (arrayListImages.size() > 0) {
                                initSlider(arrayListImages);
                            }


                            modelCart = new ModelCart();
                            modelCart.setId(id);
                            modelCart.setName(name);
                            modelCart.setPrice1(price1);
                            modelCart.setPrice2(price2);
                            modelCart.setDescription(desc);
                            modelCart.setQty(qty);
                            modelCart.setRate(rate);
                            modelCart.setImage(image);


                            container.setVisibility(View.VISIBLE);

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

    private void initSlider(ArrayList<String> list1) {
        viewPager = findViewById(R.id.viewpager);
        slideShow_adapter = new SlideShow_adapter(this,list1);
        viewPager.setAdapter(slideShow_adapter);
        circleIndicator = findViewById(R.id.indicator);
        circleIndicator.setViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBadgeCount();
    }
}