package com.example.mallcom.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.mallcom.Activity.SubDept;
import com.example.mallcom.Models.ModelStagger;
import com.example.mallcom.R;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;


public class AdapterStagger extends RecyclerView.Adapter<AdapterStagger.ViewHolder> {

//    Typeface tf;
    int current_page, last_page;
    private ArrayList<ModelStagger> arrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;
    Spinner spinner;
//    RelativeLayout container;
    public AdapterStagger(Activity activity, ArrayList<ModelStagger> r) {
        this.mInflater = LayoutInflater.from(activity);
        this.arrayList = r;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.stagger_item, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ModelStagger item = arrayList.get(position);


        if (item.getModelSliderArrayList().size()>0){
            SlideShow_adapter slideShow_adapter = new SlideShow_adapter(item.getModelSliderArrayList(),activity);
            holder.viewPager.setAdapter(slideShow_adapter);
            holder.circleIndicator.setViewPager(holder.viewPager);
        }else{
            holder.laySlider.setVisibility(View.GONE);
        }


        holder.cardShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //categoryName
                Intent intent = new Intent(activity, SubDept.class);
                intent.putExtra("categoryName",item.getName());
                activity.startActivity(intent);
            }
        });

        try {
            Glide.with(activity).load(item.getProduct1())
                    .into(holder.imageView1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Glide.with(activity).load(item.getProduct2())
                    .into(holder.imageView2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Glide.with(activity).load(item.getProduct3())
                    .into(holder.imageView3);
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.textViewName.setText(item.getName());


    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView1,imageView2,imageView3;
        ConstraintLayout container;
        TextView textViewName;
        CardView cardShowMore;
        ViewPager viewPager;
        CircleIndicator circleIndicator;
        RelativeLayout laySlider;

        ViewHolder(View itemView) {
            super(itemView);
            circleIndicator = itemView.findViewById(R.id.indicator);
            viewPager = itemView.findViewById(R.id.viewpager);
            imageView1 = itemView.findViewById(R.id.image1);
            imageView2 = itemView.findViewById(R.id.image2);
            imageView3 = itemView.findViewById(R.id.image3);
            textViewName = itemView.findViewById(R.id.name);
            cardShowMore = itemView.findViewById(R.id.cardShowMore);
            laySlider = itemView.findViewById(R.id.laySlider);


        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }



}
