package com.example.mallcom.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mallcom.Models.ModelFilter1;
import com.example.mallcom.Models.ModelFilter2;
import com.example.mallcom.R;

import java.util.ArrayList;


public class AdapterFilter2 extends RecyclerView.Adapter<AdapterFilter2.ViewHolder> {

//    Typeface tf;
    int current_page, last_page;
    private ArrayList<ModelFilter2> arrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;
    Spinner spinner;
//    RelativeLayout container;
    public AdapterFilter2(Activity activity, ArrayList<ModelFilter2> r) {
        this.mInflater = LayoutInflater.from(activity);
        this.arrayList = r;
        this.activity = activity;
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
//        holder.container.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(activity, ProductDetails.class);
//                activity.startActivity(intent);
//            }
//        });
//        holder.textViewTitle.setText(item.getTitle());
//        holder.textViewPrice.setText(item.getPrice()+" "+"جنيه سوداني");
//
//
//
//        holder.layDel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                deleteAds(item.getType(),item.getId(),position);
//            }
//        });


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
        TextView textViewOldPrice, textViewPrice;
//        Spinner spinner;

        ViewHolder(View itemView) {
            super(itemView);
//            layDel = itemView.findViewById(R.id.layDel);
//            imageView = itemView.findViewById(R.id.img);
//            container = itemView.findViewById(R.id.container);
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
