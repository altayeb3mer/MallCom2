package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.mallcom.Adapter.AdapterProductsWithRate;
import com.example.mallcom.Adapter.SlideShow_adapter;
import com.example.mallcom.Models.ModelProducts;
import com.example.mallcom.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class SubDept extends AppCompatActivity {

    SlideShow_adapter slideShow_adapter;
    ArrayList<String> arrayListImg;
    ViewPager viewPager;
    CircleIndicator circleIndicator;

    //rec

    View view;
    AdapterProductsWithRate adapterProductsWithRate;
    ArrayList<ModelProducts> arrayList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_dept);
        initSlider();
        initAdapter();
    }


    private void initSlider() {
        arrayListImg = new ArrayList<>();
        viewPager = findViewById(R.id.viewpager);
        for (int i = 0; i < 5; i++) {
            arrayListImg.add(""+i);
        }
        slideShow_adapter = new SlideShow_adapter(this,arrayListImg);
        viewPager.setAdapter(slideShow_adapter);
        circleIndicator = findViewById(R.id.indicator);
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



    private void initAdapter() {
        recyclerView = findViewById(R.id.recyclerProduct);
        arrayList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        for (int i = 0; i < 5; i++) {
            ModelProducts modelProducts = new ModelProducts();
            modelProducts.setId(i+"");
            arrayList.add(modelProducts);
        }

        adapterProductsWithRate = new AdapterProductsWithRate(this,arrayList);
        recyclerView.setAdapter(adapterProductsWithRate);


    }

}