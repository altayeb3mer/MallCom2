package com.example.mallcom.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mallcom.Activity.ItemDetails;
import com.example.mallcom.Models.ModelDept;
import com.example.mallcom.Models.ModelProducts;
import com.example.mallcom.R;

import java.util.ArrayList;


public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.ViewHolder> {

//    Typeface tf;
    int current_page, last_page;
    private ArrayList<ModelProducts> arrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;
//    RelativeLayout container;
    public AdapterProducts(Activity activity, ArrayList<ModelProducts> r) {
        this.mInflater = LayoutInflater.from(activity);
        this.arrayList = r;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.product_item, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ModelProducts item = arrayList.get(position);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, ItemDetails.class));
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
        LinearLayout container;
        TextView textViewTitle, textViewPrice;

        ViewHolder(View itemView) {
            super(itemView);
//            layDel = itemView.findViewById(R.id.layDel);
//            imageView = itemView.findViewById(R.id.img);
            container = itemView.findViewById(R.id.container);
//            textViewTitle = itemView.findViewById(R.id.title);
//            textViewPrice = itemView.findViewById(R.id.price);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


}
