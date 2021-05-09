package com.example.mallcom.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mallcom.Models.DepartmentModel;
import com.example.mallcom.R;

import java.util.ArrayList;

public class MainDepartmentAdapter extends RecyclerView.Adapter<MainDepartmentAdapter.ViewHolder> {
    private static LayoutInflater inflater = null;
    ArrayList<DepartmentModel> newsPaperArrayList;
    private LayoutInflater mInflater;
    Context mContext;

    public MainDepartmentAdapter(Context mContext, ArrayList<DepartmentModel> newsPaperArrayList) {
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

        mHolder.textViewName.setText(item.getName());
        Glide.with(mContext).load(item.getImage()).into(mHolder.imageView);
        mHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getHasChild()){
                    Toast.makeText(mContext, "has child", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "has no child", Toast.LENGTH_SHORT).show();
                }
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
