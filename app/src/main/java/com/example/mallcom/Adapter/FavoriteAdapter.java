package com.example.mallcom.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mallcom.Activity.ProductDetails;
import com.example.mallcom.Models.FavoriteModel;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Global;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private static LayoutInflater inflater = null;
    ArrayList<FavoriteModel> newsPaperArrayList;
    private LayoutInflater mInflater;
    Context mContext;

    public FavoriteAdapter(Context mContext, ArrayList<FavoriteModel> newsPaperArrayList) {
        this.newsPaperArrayList = newsPaperArrayList;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.favorite_item2, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder mHolder, int position) {
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final FavoriteModel item = newsPaperArrayList.get(position);
        mHolder.itemnamef.setText(item.getName());
        mHolder.daten.setText(item.getUpdated_at().substring(0,10));
        if(item.getRate().equals(""))
        mHolder.itemratef.setText("0.0");
        else
            mHolder.itemratef.setText(item.getRate());
        mHolder.itempricef.setText(new Global().formatNumber(item.getPrice()));
        Glide.with(mContext).load(item.getPhoto()).into(mHolder.itemimgf);

        mHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductDetails.class);
                intent.putExtra("id",item.getId());
                mContext.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return newsPaperArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dayn,daten,itemnamef,itempricef,itemratef;
                    ImageView itemimgf;
                    LinearLayout container;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container=itemView.findViewById(R.id.container);
            itemimgf=itemView.findViewById(R.id.itemimgf);
            daten=itemView.findViewById(R.id.datef);
            itemnamef=itemView.findViewById(R.id.itemnamef);
            itempricef=itemView.findViewById(R.id.itempricef);
            itemratef=itemView.findViewById(R.id.itemratef);
        }
    }

}
