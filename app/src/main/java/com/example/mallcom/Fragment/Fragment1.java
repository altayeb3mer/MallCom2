package com.example.mallcom.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.mallcom.Adapter.AdapterDepts;
import com.example.mallcom.Adapter.AdapterOffer;
import com.example.mallcom.Adapter.AdapterStagger;
import com.example.mallcom.Adapter.MainDepartmentAdapter;
import com.example.mallcom.Adapter.SlideShow_adapter;
import com.example.mallcom.Models.DepartmentModel;
import com.example.mallcom.Models.ModelDept;
import com.example.mallcom.Models.ModelProducts;
import com.example.mallcom.Models.ModelSlider;
import com.example.mallcom.Models.ModelStagger;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class Fragment1 extends Fragment {

    View view;
    AdapterDepts adapterDepts;
    ArrayList<DepartmentModel> arrayList;
    RecyclerView recyclerView;

    AdapterStagger adapterStagger;

    AdapterOffer adapterProducts;
    RecyclerView recyclerProduct, recyclerStagger;

    Context context;
    SlideShow_adapter slideShow_adapter;
    ArrayList<String> arrayListImg;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    AppCompatButton tryAgainBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_1, container, false);
        init();
        getTopSuggestions();
        getCategory();
        getSuggestions();

        return view;
    }

    private void initSlider(ArrayList<String> list) {
//        arrayListImg = new ArrayList<>();
        viewPager = view.findViewById(R.id.viewpager);
//        for (int i = 0; i < 5; i++) {
//            arrayListImg.add(""+i);
//        }
        slideShow_adapter = new SlideShow_adapter(getActivity(),list);
        viewPager.setAdapter(slideShow_adapter);
        circleIndicator = view.findViewById(R.id.indicator);
        circleIndicator.setViewPager(viewPager);
        AutoSwipingImg();
    }

    Handler handler;
    Runnable runnable;
    Timer timer;
    private void AutoSwipingImg() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                SwipImg();

            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 0, 3000);
    }
    private void SwipImg() {
        int i = viewPager.getCurrentItem();

        if (i == slideShow_adapter.urls.size() - 1) {
            i = 0;
        } else {
            i++;
        }
        viewPager.setCurrentItem(i, true);
    }

    private void init() {
        tryAgainBtn = view.findViewById(R.id.tryAgainBtn);
        progressLay = view.findViewById(R.id.progressLay);
        tryAgainLay = view.findViewById(R.id.tryAgainLay);
        recyclerView = view.findViewById(R.id.recyclerDept);
        recyclerProduct = view.findViewById(R.id.recyclerProduct);
        recyclerStagger = view.findViewById(R.id.recyclerStagger);

//        tryAgainLay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getTopSuggestions();
//                getCategory();
//                getSuggestions();
//            }
//        });
        tryAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTopSuggestions();
                getCategory();
                getSuggestions();
            }
        });
    }

    private void initAdapterStagger(ArrayList<ModelStagger> list) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false);
        recyclerStagger.setLayoutManager(gridLayoutManager);
        adapterStagger = new AdapterStagger(getActivity(), list);
        recyclerStagger.setAdapter(adapterStagger);

    }
    private void initAdapterProduct(ArrayList<ModelProducts> list) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false);
        recyclerStagger.setLayoutManager(gridLayoutManager);

        adapterProducts = new AdapterOffer(getActivity(), list);
        recyclerProduct.setAdapter(adapterProducts);

    }

    private void initAdapterDept(ArrayList<DepartmentModel> list) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapterDepts = new AdapterDepts(getActivity(),list);
        recyclerView.setAdapter(adapterDepts);

    }


    LinearLayout progressLay,tryAgainLay;
    private void getCategory() {
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

        Api.RetrofitCategory service = retrofit.create(Api.RetrofitCategory.class);

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
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject itemData = data.getJSONObject(i);

                                DepartmentModel departmentModel = new DepartmentModel();
                                departmentModel.setName(itemData.getString("name"));
                                departmentModel.setImage(itemData.getString("img"));
                                departmentModel.setHasChild(itemData.getBoolean("hasSub"));
                                arrayList.add(departmentModel);
                            }

                            if (arrayList.size()>0){
                                initAdapterDept(arrayList);
                            }


                            break;
                        }

                        default: {
                            Toast.makeText(context, "حدث خطأ حاول مجددا", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    progressLay.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressLay.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                progressLay.setVisibility(View.GONE);
            }
        });
    }
    private void getSuggestions() {
        tryAgainLay.setVisibility(View.GONE);
        final ArrayList<ModelStagger>  staggerArrayList = new ArrayList<>();
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

        Api.RetrofitSuggestions service = retrofit.create(Api.RetrofitSuggestions.class);

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
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject itemData = data.getJSONObject(i);

                                ModelStagger modelStagger = new ModelStagger();
                                modelStagger.setName(itemData.getString("watchAll"));
                                //3 search_products
                                JSONArray productsImg = itemData.getJSONArray("imgs");
                                if (productsImg.length() > 0) {
                                    modelStagger.setProduct1(productsImg.getString(0));
                                    modelStagger.setProduct2(productsImg.getString(1));
                                    modelStagger.setProduct3(productsImg.getString(2));
                                }
                                //slider
                                JSONArray sliderImg = itemData.getJSONArray("topProduct");
                                ArrayList<ModelSlider> modelSliders = new ArrayList<>();
                                if (sliderImg.length() > 0) {
                                    for (int j = 0; j < sliderImg.length(); j++) {
                                        JSONObject jsonObjectSlider = sliderImg.getJSONObject(j);
                                        ModelSlider modelSlider = new ModelSlider();
                                        modelSlider.setId(jsonObjectSlider.getString("id"));
                                        modelSlider.setImage(jsonObjectSlider.getString("photo"));
                                        modelSliders.add(modelSlider);
                                    }
                                }

                                modelStagger.setModelSliderArrayList(modelSliders);


                                ArrayList<ModelProducts> discountedProduct = new ArrayList<>();
                                JSONArray discountedArray = itemData.getJSONArray("product");
                                for (int x = 0; x <discountedArray.length() ; x++) {
                                    ModelProducts modelProducts = new ModelProducts();
                                    JSONObject item = discountedArray.getJSONObject(x);
                                    modelProducts.setId(item.getString("id"));
                                    modelProducts.setImage(item.getString("photo"));
                                    modelProducts.setPrice(item.getString("discounted_price"));
                                    modelProducts.setOldPrice(item.getString("price"));
                                    modelProducts.setName(item.getString("name"));
                                    discountedProduct.add(modelProducts);
                                }

                                modelStagger.setModelProducts(discountedProduct);


                                staggerArrayList.add(modelStagger);

                            }

                            if (staggerArrayList.size()>0){
                                initAdapterStagger(staggerArrayList);
                                tryAgainLay.setVisibility(View.GONE);
                            }

                            tryAgainLay.setVisibility(View.GONE);
                            break;
                        }

                        default: {
                            Toast.makeText(context, "حدث خطأ حاول مجددا", Toast.LENGTH_SHORT).show();
                            tryAgainLay.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                    progressLay.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    tryAgainLay.setVisibility(View.VISIBLE);
                }
                progressLay.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                progressLay.setVisibility(View.GONE);
                tryAgainLay.setVisibility(View.VISIBLE);
            }
        });
    }

    String category="";
    private void getTopSuggestions() {
        final ArrayList<ModelStagger>  staggerArrayList = new ArrayList<>();
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

        Api.RetrofitTopSuggestions service = retrofit.create(Api.RetrofitTopSuggestions.class);

        Call<String> call = service.putParam();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                try {
                    JSONObject object = new JSONObject(response.body());
                    String success = object.getString("success");
                    switch (success) {
                        case "true": {
                            ArrayList<String> stringArrayList = new ArrayList<>();
                            JSONArray data = object.getJSONArray("data");

                            JSONObject dataObj = data.getJSONObject(0);
                            category = dataObj.getString("category");
                            JSONArray sliderArr = dataObj.getJSONArray("slider");
                            for (int i = 0; i < sliderArr.length(); i++) {
                                stringArrayList.add(sliderArr.getString(i));
                            }


                            if (stringArrayList.size()>0){
                                initSlider(stringArrayList);
                                view.findViewById(R.id.pagerLay).setVisibility(View.VISIBLE);
                            }else {
                                view.findViewById(R.id.pagerLay).setVisibility(View.GONE);
                            }



                            ArrayList<ModelProducts> productsArrayList = new ArrayList<>();
                            JSONArray productsArray = dataObj.getJSONArray("products");
                            for (int i = 0; i < productsArray.length(); i++) {
                                JSONObject itemData = productsArray.getJSONObject(i);
                                ModelProducts modelProducts=new ModelProducts();
                                modelProducts.setId(itemData.getString("id"));
                                modelProducts.setOldPrice(itemData.getString("price"));
                                modelProducts.setPrice(itemData.getString("discounted_price"));
                                modelProducts.setImage(itemData.getString("photo"));

                                productsArrayList . add(modelProducts);
                            }


                            if (productsArrayList.size()>0){
                                initAdapterProduct(productsArrayList);
                            }else {
                                recyclerProduct.setVisibility(View.GONE);
                            }

                            break;
                        }

                        default: {
                            Toast.makeText(context, "حدث خطأ حاول مجددا", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    progressLay.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressLay.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                progressLay.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }


}