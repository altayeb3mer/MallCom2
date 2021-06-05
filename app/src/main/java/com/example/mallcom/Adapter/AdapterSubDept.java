package com.example.mallcom.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mallcom.Activity.ProductActivity;
import com.example.mallcom.Models.DepartmentModel;
import com.example.mallcom.R;

import java.util.ArrayList;

public class AdapterSubDept extends RecyclerView.Adapter<AdapterSubDept.ViewHolder> {
    private static LayoutInflater inflater = null;
    ArrayList<DepartmentModel> newsPaperArrayList;
    private LayoutInflater mInflater;
    Context mContext;

    public AdapterSubDept(Context mContext, ArrayList<DepartmentModel> newsPaperArrayList) {
        this.newsPaperArrayList = newsPaperArrayList;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.deparment_item, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder mHolder, int position) {
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final DepartmentModel item = newsPaperArrayList.get(position);

        mHolder.textViewName.setText(item.getSubCat());
        Glide.with(mContext).load(item.getSubImage()).into(mHolder.imageView);
        mHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (item.getHasChild()){
                //    Toast.makeText(mContext, "has children", Toast.LENGTH_SHORT).show();
//                    Intent intent =new Intent(mContext, SubDept.class);
//                    intent.putExtra("categoryName",item.getSubCat());

                    Intent intent = new Intent(mContext, ProductActivity.class);
                    intent.putExtra("category", item.getName());
                    intent.putExtra("subCategory", item.getSubCat());
                    mContext.startActivity(intent);
//                } else {
//
//                    Toast.makeText(mContext, "has no child", Toast.LENGTH_SHORT).show();

//                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return newsPaperArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        ImageView imageView;
        LinearLayout container;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.img);
            container = itemView.findViewById(R.id.container);

        }
    }

}
