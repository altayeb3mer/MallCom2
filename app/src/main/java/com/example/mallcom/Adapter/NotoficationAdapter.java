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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NotoficationAdapter extends RecyclerView.Adapter<NotoficationAdapter.ViewHolder> {
    private static LayoutInflater inflater = null;
    ArrayList<NotificationModel> newsPaperArrayList;
    private LayoutInflater mInflater;
    Context mContext;
    DateFormat date ;//= new SimpleDateFormat("MM/dd/yyyy");
    DateFormat time;
    DateFormat f;
    Date d;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final NotificationModel item = newsPaperArrayList.get(position);
        holder.titlen.setText(item.getTitle());
        holder.bodyn.setText(item.getContent());
        try {
            String string = item.getUpdated_at();
            String[] parts = string.split("T");
            String part1 = parts[0]; // 004
            String part2 = parts[1]; // 034556
            //2021-05-02T12:23:32.000000
            f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
          //  f = new SimpleDateFormat("yyyy-MM-ddThh:mm:ssssss");
            //d = f.parse(item.getUpdated_at());
            d = f.parse(part1+" "+part2);
            date = new SimpleDateFormat("dd-MM-yyyy");
            time = new SimpleDateFormat("hh:mm:ss");
            //  System.out.println("Date: " + date.format(d));
            //System.out.println("Time: " + time.format(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Toast.makeText(activity,date.format(d)+"   1", Toast.LENGTH_LONG).show();
        // Toast.makeText(activity,time.format(d)+"     z", Toast.LENGTH_LONG).show();
        holder.daten.setText(date.format(d)+"");
        holder.timen.setText(time.format(d)+"");
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        // String formattedDate2 = df.format(date);
        // Toast.makeText(activity,formattedDate+"    celender", Toast.LENGTH_LONG).show();
        // Toast.makeText(activity,formattedDate2+"   server", Toast.LENGTH_LONG).show();

        if (date.format(d).equals(formattedDate)) {
            holder.dayn.setText("اليوم");
        }else
            holder.dayn.setText(date.format(d)+"");

    }

    @Override
    public int getItemCount() {
        return newsPaperArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dayn,daten,titlen,timen,bodyn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
               dayn=itemView.findViewById(R.id.dayn);
            daten=itemView.findViewById(R.id.daten);
            titlen=itemView.findViewById(R.id.titlen);
            timen=itemView.findViewById(R.id.timen);
            bodyn=itemView.findViewById(R.id.bodyn);
        }
    }

}
