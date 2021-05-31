package com.example.mallcom.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mallcom.Models.FavoriteModel;
import com.example.mallcom.R;

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
        if(item.getRate().equals(""))
            mHolder.itemratef.setVisibility(View.GONE);
        else
            mHolder.itemratef.setText(item.getRate());
        mHolder.itempricef.setText(item.getPrice());
        Glide.with(mContext).load(item.getPhoto()).into(mHolder.itemimgf);

    }

    @Override
    public int getItemCount() {
        return newsPaperArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dayn,daten,itemnamef,itempricef,itemratef;
                    ImageView itemimgf;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           // dayn=itemView.findViewById(R.id.datef);
            itemimgf=itemView.findViewById(R.id.itemimgf);
            daten=itemView.findViewById(R.id.datef);
            itemnamef=itemView.findViewById(R.id.itemnamef);
            itempricef=itemView.findViewById(R.id.itempricef);
            itemratef=itemView.findViewById(R.id.itemratef);
        }
    }

}
