package com.example.mallcom.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mallcom.Activity.SubDept;
import com.example.mallcom.Activity.ProductActivity;
import com.example.mallcom.Models.DepartmentModel;
import com.example.mallcom.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterDepts extends RecyclerView.Adapter<AdapterDepts.ViewHolder> {

//    Typeface tf;
    int current_page, last_page;
    private ArrayList<DepartmentModel> arrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;
//    RelativeLayout container;
    public AdapterDepts(Activity activity, ArrayList<DepartmentModel> r) {
        this.mInflater = LayoutInflater.from(activity);
        this.arrayList = r;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.main_cat, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final DepartmentModel item = arrayList.get(position);

        holder.textViewName.setText(item.getName());
        Glide.with(activity).load(item.getImage()).into(holder.imageView);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getHasChild()){
                  //  Toast.makeText(activity, "has child", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(activity, SubDept.class);
                     intent.putExtra("categoryName",item.getName());

                    activity.startActivity(intent);
                } else {
                    Toast.makeText(activity, "has no child", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(activity, ProductActivity.class);
                    intent.putExtra("category",item.getName());
                    intent.putExtra("subCategory","");
                    activity.startActivity(intent);
                }
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

        LinearLayout container;
        TextView textViewName;
        CircleImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.img);
            container = itemView.findViewById(R.id.container);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


}
