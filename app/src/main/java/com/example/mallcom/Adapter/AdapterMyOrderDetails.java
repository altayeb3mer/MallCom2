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

import com.bumptech.glide.Glide;
import com.example.mallcom.Activity.ProductDetails;
import com.example.mallcom.Models.ModelItems;
import com.example.mallcom.Models.ModelMyOrder;
import com.example.mallcom.R;

import java.util.ArrayList;


public class AdapterMyOrderDetails extends RecyclerView.Adapter<AdapterMyOrderDetails.ViewHolder> {


    private ArrayList<ModelItems> arrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;

    public AdapterMyOrderDetails(Activity activity, ArrayList<ModelItems> r) {
        this.mInflater = LayoutInflater.from(activity);
        this.arrayList = r;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_order_item_details, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ModelItems item = arrayList.get(position);

        holder.textViewOldPrice.setPaintFlags( holder.textViewOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        try {
            Glide.with(activity).load(item.getImage())
                    .into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ProductDetails.class);
                intent.putExtra("id",item.getId());
                activity.startActivity(intent);
            }
        });
        holder.textViewName.setText(item.getName());
        holder.textViewDesc.setText("عدد"+" "+item.getDesc());
        holder.textViewRate.setText(item.getRate());
        holder.textViewPrice.setText(item.getFinalPrice());
        if (Integer.parseInt(item.getFinalPrice())>Integer.parseInt(item.getPrice1())){
            holder.textViewOldPrice.setText(item.getPrice1());
        }else{
            holder.textViewOldPrice.setVisibility(View.GONE);
        }

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
        TextView textViewOldPrice, textViewPrice,textViewName,textViewDesc,
                textViewRate;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            textViewName = itemView.findViewById(R.id.name);
            textViewDesc = itemView.findViewById(R.id.desc);
            container = itemView.findViewById(R.id.container);
            textViewPrice = itemView.findViewById(R.id.price1);
            textViewOldPrice = itemView.findViewById(R.id.oldPrice);
            textViewRate = itemView.findViewById(R.id.rate);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }



}
