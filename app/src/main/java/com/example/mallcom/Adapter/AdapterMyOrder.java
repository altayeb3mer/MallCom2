package com.example.mallcom.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mallcom.Activity.MyOrderDetails;
import com.example.mallcom.Models.ModelCart;
import com.example.mallcom.Models.ModelMyOrder;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Global;

import java.util.ArrayList;


public class AdapterMyOrder extends RecyclerView.Adapter<AdapterMyOrder.ViewHolder> {

//    Typeface tf;
    int current_page, last_page;
    private ArrayList<ModelMyOrder> arrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;
    Spinner spinner;
//    RelativeLayout container;
    public AdapterMyOrder(Activity activity, ArrayList<ModelMyOrder> r) {
        this.mInflater = LayoutInflater.from(activity);
        this.arrayList = r;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.my_order, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ModelMyOrder item = arrayList.get(position);

        holder.textViewDate.setText(item.getDate().substring(0,10));
        holder.textViewCount.setText(item.getItemCount());
        holder.textViewNumber.setText(item.getNumber());
        holder.textViewTotal.setText(new Global().formatNumber(item.getTotal())+" "+"ج س");
        holder.textViewStatus.setText(item.getStatus());

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, MyOrderDetails.class);
                intent.putExtra("orderNumber",item.getNumber());
                activity.startActivity(intent);
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

        ConstraintLayout container;
        TextView textViewDate, textViewTotal,textViewCount,textViewNumber,textViewStatus;


        ViewHolder(View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.date);
            textViewTotal = itemView.findViewById(R.id.total);
            textViewCount = itemView.findViewById(R.id.count);
            textViewNumber = itemView.findViewById(R.id.number);
            textViewStatus = itemView.findViewById(R.id.status);

            container = itemView.findViewById(R.id.container);


        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }



}
