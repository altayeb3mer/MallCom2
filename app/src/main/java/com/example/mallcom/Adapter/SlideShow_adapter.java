package com.example.mallcom.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;


import com.bumptech.glide.Glide;
import com.example.mallcom.Activity.ProductDetails;
import com.example.mallcom.Models.ModelSlider;
import com.example.mallcom.R;

import java.util.ArrayList;


public class SlideShow_adapter extends PagerAdapter {

    LayoutInflater inflater;



    public ArrayList<String> urls;
    public ArrayList<ModelSlider> urls2=new ArrayList<>();
    int detail;

    Activity activity;


    public SlideShow_adapter(ArrayList<ModelSlider> urls2, Activity activity) {
        this.urls2 = urls2;
        this.activity = activity;
    }

    public SlideShow_adapter(Activity activity, ArrayList<String> images) {
        this.activity = activity;

        this.urls=images;
        //this.detail=det;
      }

//    public SlideShow_adapter(Activity activity, ArrayList<ModelSlider> urls2) {
//        this.activity = activity;
//
//        this.urls2=urls2;
//        //this.detail=det;
//      }


    @Override
    public int getCount() {
        if (!urls2.isEmpty()){
            return urls2.size();
        }
            return urls.size();
        }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(ConstraintLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view=inflater.inflate(R.layout.slide_show_layout,container,false);
//        TextView textView_trade_det_img_note = view.findViewById(R.id.trade_det_img_note);
        ImageView imgView=view.findViewById(R.id.img);

        if(!urls2.isEmpty()){
            Glide.with(activity).load(urls2.get(position).getImage())
                    .into(imgView);
        }else {
            Glide.with(activity).load(urls.get(position))
                    .into(imgView);
        }

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (urls2.size()>0)
                if (!urls2.get(position).getId().equals("")){
                    Intent intent = new Intent(activity, ProductDetails.class);
                    intent.putExtra("id",urls2.get(position).getId());
                    activity.startActivity(intent);
                }
            }
        });


        container.addView(view);


        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
