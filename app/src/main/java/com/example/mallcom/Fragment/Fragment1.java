package com.example.mallcom.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.example.mallcom.Models.ModelStagger;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
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
    ArrayList<ModelStagger> staggerArrayList;
    AdapterOffer adapterProducts;
    ArrayList<ModelProducts> productsArrayList;
    RecyclerView recyclerProduct, recyclerStagger;

    Context context;
    SlideShow_adapter slideShow_adapter;
    ArrayList<String> arrayListImg;
    ViewPager viewPager;
    CircleIndicator circleIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_1, container, false);
        init();
        initSlider();
        getCategory();
        return view;
    }

    private void initSlider() {
        arrayListImg = new ArrayList<>();
        viewPager = view.findViewById(R.id.viewpager);
        for (int i = 0; i < 5; i++) {
            arrayListImg.add(""+i);
        }
        slideShow_adapter = new SlideShow_adapter(getActivity(),arrayListImg);
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
        progressLay = view.findViewById(R.id.progressLay);
        recyclerView = view.findViewById(R.id.recyclerDept);
        recyclerProduct = view.findViewById(R.id.recyclerProduct);
        recyclerStagger = view.findViewById(R.id.recyclerStagger);

        initAdapterProduct();
        initAdapterSagger();
    }

    private void initAdapterSagger() {
        staggerArrayList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false);
        recyclerStagger.setLayoutManager(gridLayoutManager);
        for (int i = 0; i < 5; i++) {
            ModelStagger  modelProducts = new ModelStagger();
            modelProducts.setId(i+"");
            staggerArrayList.add(modelProducts);
        }

        adapterStagger = new AdapterStagger(getActivity(), staggerArrayList);
        recyclerStagger.setAdapter(adapterStagger);

    }
    private void initAdapterProduct() {
        productsArrayList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false);
        recyclerStagger.setLayoutManager(gridLayoutManager);
        for (int i = 0; i < 5; i++) {
            ModelProducts  modelProducts = new ModelProducts();
            modelProducts.setId(i+"");
            productsArrayList.add(modelProducts);
        }

        adapterProducts = new AdapterOffer(getActivity(), productsArrayList);
        recyclerProduct.setAdapter(adapterProducts);

    }

    private void initAdapterDept(ArrayList<DepartmentModel> list) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, true);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapterDepts = new AdapterDepts(getActivity(),list);
        recyclerView.setAdapter(adapterDepts);


    }



    LinearLayout progressLay;
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

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }


}