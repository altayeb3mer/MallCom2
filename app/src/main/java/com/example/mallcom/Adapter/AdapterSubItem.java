package com.example.mallcom.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mallcom.Activity.MainActivity;
import com.example.mallcom.Activity.ProductDetails;
import com.example.mallcom.Activity.Products;
import com.example.mallcom.Activity.SubDept;
import com.example.mallcom.Activity.SubDeptproduct;
import com.example.mallcom.Models.ModelProducts;
import com.example.mallcom.R;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;

public class AdapterSubItem extends RecyclerView.Adapter<AdapterSubItem.ViewHolder> {

        //    Typeface tf;
        int current_page, last_page;
        private ArrayList<ModelProducts> arrayList;
        private LayoutInflater mInflater;
        private com.example.mallcom.Adapter.AdapterProductsWithRate.ItemClickListener mClickListener;
        private Activity activity;
        //    RelativeLayout container;
        public AdapterSubItem(Activity activity, ArrayList<ModelProducts> r) {
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
                   // Intent intent = new Intent(activity, ProductDetails.class);
                    //intent.putExtra("id",item.getId());
                    //activity.startActivity(intent);
                    Toast.makeText(activity,item.getName()+"\n"+item.getNamemain(), LENGTH_LONG).show();

                    Intent intent =new Intent(activity, SubDeptproduct.class);
                    intent.putExtra("category",item.getNamemain());
                    intent.putExtra("subCategory",item.getName());

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
        public void setClickListener(com.example.mallcom.Adapter.AdapterProductsWithRate.ItemClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }


        // parent activity will implement this method to respond to click events
        public interface ItemClickListener {
            void onItemClick(View view, int position);
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            ImageView imageView;
            LinearLayout container;
            TextView textViewPrice,textViewName,
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
