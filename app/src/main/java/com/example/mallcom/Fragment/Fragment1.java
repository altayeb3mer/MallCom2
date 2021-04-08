package com.example.mallcom.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.mallcom.Adapter.AdapterDepts;
import com.example.mallcom.Adapter.AdapterProducts;
import com.example.mallcom.Adapter.AdapterStagger;
import com.example.mallcom.Adapter.SlideShow_adapter;
import com.example.mallcom.Models.ModelDept;
import com.example.mallcom.Models.ModelProducts;
import com.example.mallcom.Models.ModelStagger;
import com.example.mallcom.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


public class Fragment1 extends Fragment {

    View view;
    AdapterDepts adapterDepts;
    ArrayList<ModelDept> arrayList;
    RecyclerView recyclerView;

    AdapterStagger adapterStagger;
    ArrayList<ModelStagger> staggerArrayList;
    AdapterProducts adapterProducts;
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
        recyclerView = view.findViewById(R.id.recyclerDept);
        recyclerProduct = view.findViewById(R.id.recyclerProduct);
        recyclerStagger = view.findViewById(R.id.recyclerStagger);


        initAdapterDept();
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

        adapterProducts = new AdapterProducts(getActivity(), productsArrayList);
        recyclerProduct.setAdapter(adapterProducts);

    }

    private void initAdapterDept() {
        arrayList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, true);
        recyclerView.setLayoutManager(gridLayoutManager);
        for (int i = 0; i < 5; i++) {
            ModelDept  modelDept = new ModelDept();
            modelDept.setId(i+"");
            arrayList.add(modelDept);
        }

        adapterDepts = new AdapterDepts(getActivity(),arrayList);
        recyclerView.setAdapter(adapterDepts);


    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }


}