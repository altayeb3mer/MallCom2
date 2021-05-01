package com.example.mallcom.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mallcom.Models.NotificationModel;
import com.example.mallcom.R;

import java.util.ArrayList;

public class NotoficationAdapter extends RecyclerView.Adapter<NotoficationAdapter.ViewHolder> {
    private static LayoutInflater inflater = null;
    ArrayList<NotificationModel> newsPaperArrayList;
    private LayoutInflater mInflater;
    Context mContext;

    public NotoficationAdapter(Context mContext, ArrayList<NotificationModel> newsPaperArrayList) {
        this.newsPaperArrayList = newsPaperArrayList;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.notification_item, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder mHolder, int position) {
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final NotificationModel item = newsPaperArrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return newsPaperArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

}
