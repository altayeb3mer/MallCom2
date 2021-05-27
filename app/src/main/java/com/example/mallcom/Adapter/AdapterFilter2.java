package com.example.mallcom.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mallcom.Models.ModelFilter1;
import com.example.mallcom.Models.ModelFilter2;
import com.example.mallcom.R;

import java.util.ArrayList;


public class AdapterFilter2 extends RecyclerView.Adapter<AdapterFilter2.ViewHolder> {


    private ArrayList<ModelFilter2> arrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;
    String type;


    public AdapterFilter2(Activity activity, ArrayList<ModelFilter2> r,String type) {
        this.mInflater = LayoutInflater.from(activity);
        this.arrayList = r;
        this.activity = activity;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.filter_item2, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ModelFilter2 item = arrayList.get(position);

//        holder.textViewOldPrice.setPaintFlags( holder.textViewOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

//        try {
//            Glide.with(activity).load(Api.ROOT_URL+item.getImage())
//                    .into(holder.imageView);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("color")){
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("price1","");
                    returnIntent.putExtra("price2","");
                    returnIntent.putExtra("color",item.getValue());
                    returnIntent.putExtra("rate","");
                    activity.setResult(Activity.RESULT_OK,returnIntent);
                    activity.finish();
                }else{
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("price1","");
                    returnIntent.putExtra("price2","");
                    returnIntent.putExtra("color","");
                    returnIntent.putExtra("rate",item.getValue());
                    activity.setResult(Activity.RESULT_OK,returnIntent);
                    activity.finish();
                }
            }
        });
        holder.textViewValue.setText(item.getValue());



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

        RelativeLayout container;
        TextView textViewValue;

        ViewHolder(View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.container);
            textViewValue = itemView.findViewById(R.id.value);


        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }



}
