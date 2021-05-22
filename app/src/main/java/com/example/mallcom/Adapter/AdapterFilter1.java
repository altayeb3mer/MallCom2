package com.example.mallcom.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mallcom.Activity.ProductDetails;
import com.example.mallcom.Models.ModelFilter1;
import com.example.mallcom.Models.ModelItems;
import com.example.mallcom.R;

import java.util.ArrayList;


public class AdapterFilter1 extends RecyclerView.Adapter<AdapterFilter1.ViewHolder> {

//    Typeface tf;
    int current_page, last_page;
    private ArrayList<ModelFilter1> arrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;
    LinearLayout priceLay;
    RecyclerView recyclerView;
//    RelativeLayout container;
    public AdapterFilter1(Activity activity, ArrayList<ModelFilter1> r,LinearLayout priceLay, RecyclerView recyclerView) {
        this.mInflater = LayoutInflater.from(activity);
        this.arrayList = r;
        this.activity = activity;
        this.priceLay = priceLay;
        this.recyclerView = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.filter_item1, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ModelFilter1 item = arrayList.get(position);




        holder.textViewTitle.setText(item.getTitle());
//        holder.textViewPrice.setText(item.getPrice()+" "+"جنيه سوداني");
//
//
//
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position==0){
                    priceLay.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }else{
                    priceLay.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });


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

        ImageView imageView;
        ConstraintLayout container;
        TextView textViewTitle, textViewPrice;
//        Spinner spinner;

        ViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.title);
//            imageView = itemView.findViewById(R.id.img);
            container = itemView.findViewById(R.id.container);
//            textViewTitle = itemView.findViewById(R.id.title);
//            textViewOldPrice = itemView.findViewById(R.id.oldPrice);
//            spinner = itemView.findViewById(R.id.spinner);
//            initSpinner();

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }



}
