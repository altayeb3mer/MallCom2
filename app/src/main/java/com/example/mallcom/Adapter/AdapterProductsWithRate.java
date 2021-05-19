package com.example.mallcom.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mallcom.Activity.ProductDetails;
import com.example.mallcom.Models.ModelProducts;
import com.example.mallcom.R;

import java.util.ArrayList;


public class AdapterProductsWithRate extends RecyclerView.Adapter<AdapterProductsWithRate.ViewHolder> {

//    Typeface tf;
    int current_page, last_page;
    private ArrayList<ModelProducts> arrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;
//    RelativeLayout container;
    public AdapterProductsWithRate(Activity activity, ArrayList<ModelProducts> r) {
        this.mInflater = LayoutInflater.from(activity);
        this.arrayList = r;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.product_item_with_rate, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ModelProducts item = arrayList.get(position);
//
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ProductDetails.class);
                intent.putExtra("id",item.getId());
                intent.putExtra("name",item.getName());
                if (item.getRate()!=null){
                intent.putExtra("rate",item.getRate());}
                else
                {
                    intent.putExtra("rate","");

            }
                intent.putExtra("price",item.getPrice());
                intent.putExtra("image",item.getImage());
                activity.startActivity(intent);
            }
        });

        try {
            Glide.with(activity).load(item.getImage())
                    .into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.textViewName.setText(item.getName());
        if (item.getRate()!=null){
            holder.textViewRate.setText(item.getRate());
        }else{
            holder.cardRate.setVisibility(View.GONE);
        }
        holder.textViewPrice.setText(item.getPrice());


//        holder.textViewPriceCross.setPaintFlags( holder.textViewPriceCross.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//

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
        LinearLayout container;
        TextView  textViewPrice,textViewName,
                textViewRate;
        CardView cardRate;

        ViewHolder(View itemView) {
            super(itemView);
            cardRate = itemView.findViewById(R.id.cardRate);
            imageView = itemView.findViewById(R.id.img);
            textViewName = itemView.findViewById(R.id.name);
            container = itemView.findViewById(R.id.container);
            textViewPrice = itemView.findViewById(R.id.price);
            textViewRate = itemView.findViewById(R.id.rate);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


}
