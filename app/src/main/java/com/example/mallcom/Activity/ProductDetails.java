package com.example.mallcom.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.mallcom.Adapter.SlideShow_adapter;
import com.example.mallcom.Database.SharedPrefManager;
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

    TextView addToCart, textViewName, textViewPrice, textViewDescription, textViewRate,
            buyNow;
    LinearLayout progressLay;
    SlideShow_adapter slideShow_adapter;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    ConstraintLayout container;
    ImageView imgSearch, addToFavorite;

    String id = "";
    RelativeLayout imgCart;

    ArrayList<String> listColor = new ArrayList<>();
    ArrayList<String> listSize = new ArrayList<>();
    ImageView imgBack;
    ModelCart modelCart = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        new Global().changeStatusBarColor(this, getResources().getColor(R.color.colorPrimary));
        init();
        Bundle args = getIntent().getExtras();
        if (args != null) {
            id = args.getString("id");
            getDetails(id);
        }
        setBadgeCount();
    }

    private void setBadgeCount() {
        ArrayList<ModelCart> arrayList = new SqlLiteDataBase(getApplicationContext()).GetAllCart();
        TextView badges = findViewById(R.id.txtBadge);
        if (arrayList.size() > 0) {
            badges.setText(arrayList.size() + "");
            badges.setVisibility(View.VISIBLE);
        } else {
            badges.setVisibility(View.GONE);
        }
    }

    private void init() {
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        imgCart = findViewById(R.id.imgCart);
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }
        });

        addToFavorite = findViewById(R.id.addToFavorite);
        addToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFavorite(id);
            }
        });
        buyNow = findViewById(R.id.buyNow);
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listColor.size() > 0) {
                    final ArrayAdapter<String> arrayAdapter =
                            new ArrayAdapter<String>(ProductDetails.this, android.R.layout.select_dialog_singlechoice,listColor);
                    chooseColor(arrayAdapter,false);
                } else {
                    if (listSize.size()>0){
                        final ArrayAdapter<String> arrayAdapter =
                                new ArrayAdapter<String>(ProductDetails.this, android.R.layout.select_dialog_singlechoice,listSize);
                        chooseSize(arrayAdapter,false);
                    }else{
                        modelCart.setColor("");
                        addToCart(modelCart);
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                    }

                }
            }
        });
        imgSearch = findViewById(R.id.imgSearch);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SearchProducts.class));
            }
        });
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addToCart: {

                if (listColor.size() > 0) {
                    final ArrayAdapter<String> arrayAdapter =
                            new ArrayAdapter<String>(ProductDetails.this, android.R.layout.select_dialog_singlechoice,listColor);
                    chooseColor(arrayAdapter,true);
                } else {
                    if (listSize.size()>0){
                        final ArrayAdapter<String> arrayAdapter =
                                new ArrayAdapter<String>(ProductDetails.this, android.R.layout.select_dialog_singlechoice,listSize);
                        chooseSize(arrayAdapter,true);
                    }else{
                        modelCart.setColor("");
                        addToCart(modelCart);
                    }
                }
                break;
            }
        }
    }

    private void addToCart(ModelCart modelCart) {
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
//        Call<String> call = service.putParam("9");
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

                            String id = "", name = "", price1 = "", price2 = "", desc = "", qty = "1", rate = "", image = "",
                                    discount = "";

                            JSONObject dataObj = object.getJSONObject("data");
                            id = dataObj.getString("id");
                            image = dataObj.getString("photo");
                            name = dataObj.getString("name");
                            textViewName.setText(name);
                            discount = dataObj.getString("discount");
                            price1 = dataObj.getString("price");
                            price2 = String.valueOf(Integer.parseInt(price1) - Integer.parseInt(discount));
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
                            arrayListImages.add(dataObj.getString("photo"));
                            if (arrayImages.length() > 0) {
                                for (int i = 0; i < arrayImages.length(); i++) {
                                    JSONObject imgObj = arrayImages.getJSONObject(i);
                                    arrayListImages.add(imgObj.getString("photo"));
                                }
                            }
//                            else{
//                                arrayListImages.add(dataObj.getString("photo"));
//                                initSlider(arrayListImages);
//                            }

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

//                            additional_description
                            String additional_description1 = dataObj.getString("additional_description");
//                            JSONObject additional_description = dataObj.getJSONObject("additional_description");
                            if (!additional_description1.equals("null")) {
                                JSONObject additional_description = new JSONObject(additional_description1);
                                String coloString = additional_description.getString("color");
                                if (!coloString.equals("null")){
                                    JSONArray colorAr = new JSONArray(coloString);
                                    String color = "";

                                    for (int i = 0; i < colorAr.length(); i++) {
                                        color = color + " , " + colorAr.get(i);
                                        listColor.add(colorAr.getString(i));
                                    }
                                    textViewDescription.append("\n" + "\n" + "اللون : " + " " + color);
                                    textViewDescription.append("\n" + "الحجم : " + " " + additional_description.getString("weight"));
                                    textViewDescription.append("\n" + "مخصص ل  : " + " " + additional_description.getString("for"));
                                    textViewDescription.append("\n" + "الشركة المصنعة : " + " " + additional_description.getString("company"));
                                    textViewDescription.append("\n" + "تاريخ انتهاء الصلاحية : " + " " + additional_description.getString("expireDate"));

                                }

                            }
//                            additional_description
                            String product_sizes = dataObj.getString("product_sizes");
//                            JSONObject additional_description = dataObj.getJSONObject("additional_description");
                            if (!product_sizes.equals("[]")&&!product_sizes.equals("null")) {
                                JSONArray product_sizesAr = new JSONArray(product_sizes);
                                JSONObject object0 = product_sizesAr.getJSONObject(0);
                                JSONArray sizeAr = object0.getJSONArray("sizes_array");
                                String size = "";

                                for (int i = 0; i < sizeAr.length(); i++) {
                                    JSONObject item = sizeAr.getJSONObject(i);
                                    size = item.getString("size") + " " + item.getString("unit") + " , ";
                                    String sizeToArr = item.getString("size") + " " + item.getString("unit");

                                    listSize.add(sizeToArr);
                                }
                                textViewDescription.append("\n" + "\n" + "الاحجام المتوفرة : " + " " + size);

                            }
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

    private void addFavorite(String id) {
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

        Api.RetrofitAddFavorite service = retrofit.create(Api.RetrofitAddFavorite.class);
        Call<String> call = service.putParam(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                try {
                    JSONObject object = new JSONObject(response.body());
                    String success = object.getString("success");
                    switch (success) {
                        case "true": {

                            dialogMsg("تمت الاضافة للمفضلة", "اضافة للمفضلة");


                            break;
                        }
                        case "false": {
                            dialogMsg(object.getString("errors"), "تنبيه!");
                            break;
                        }

                        default: {
                            Toast.makeText(getApplicationContext(), "حدث خطأ الرجاء المحاوله مرة اخرى", Toast.LENGTH_SHORT).show();
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

    private void dialogMsg(final String msg, String title_) {
//        final BottomSheetDialog di`alog = new BottomSheetDialog(this);
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_msg);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        final TextView editText = dialog.findViewById(R.id.edt);
        final TextView title = dialog.findViewById(R.id.title);
        title.setText(title_);
        editText.setText(msg);
        AppCompatButton button = dialog.findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });

        dialog.show();

    }

    private void initSlider(ArrayList<String> list1) {
        viewPager = findViewById(R.id.viewpager);
        slideShow_adapter = new SlideShow_adapter(this, list1);
        viewPager.setAdapter(slideShow_adapter);
        circleIndicator = findViewById(R.id.indicator);
        circleIndicator.setViewPager(viewPager);
    }


    private void chooseColor(final ArrayAdapter<String> arrayAdapter, final boolean add) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("اختيار اللون");

//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DialogActivity.this, android.R.layout.select_dialog_singlechoice);
//        arrayAdapter.add("Hardik");
//        arrayAdapter.add("Archit");
//        arrayAdapter.add("Jignesh");
//        arrayAdapter.add("Umang");
//        arrayAdapter.add("Gatti");

        builderSingle.setNegativeButton("رجوع", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(ProductDetails.this);
                builderInner.setMessage(strName);
                builderInner.setTitle("قمت باختيار اللون : ");
                builderInner.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        modelCart.setColor(strName);
                        addToCart(modelCart);
                        if (listSize.size()>0){
                            final ArrayAdapter<String> arrayAdapter =
                                    new ArrayAdapter<String>(ProductDetails.this, android.R.layout.select_dialog_singlechoice,listColor);
                            chooseSize(arrayAdapter,add);
                        }else {
                            if (!add){
                                startActivity(new Intent(getApplicationContext(), CartActivity.class));
                            }
                        }



                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }

    private void chooseSize(final ArrayAdapter<String> arrayAdapter, final boolean add) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("اختيار الحجم");

//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(DialogActivity.this, android.R.layout.select_dialog_singlechoice);
//        arrayAdapter.add("Hardik");
//        arrayAdapter.add("Archit");
//        arrayAdapter.add("Jignesh");
//        arrayAdapter.add("Umang");
//        arrayAdapter.add("Gatti");

        builderSingle.setNegativeButton("رجوع", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(ProductDetails.this);
                builderInner.setMessage(strName);
                builderInner.setTitle("قمت باختيار الحجم : ");
                builderInner.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        modelCart.setSize(strName);
                        addToCart(modelCart);


                        if (!add){
                            startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        }


                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        setBadgeCount();
    }
}