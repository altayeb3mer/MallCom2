package com.example.mallcom.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mallcom.Activity.SubDept;
import com.example.mallcom.Models.ModelStagger;
import com.example.mallcom.R;

import java.util.ArrayList;


public class AdapterStagger extends RecyclerView.Adapter<AdapterStagger.ViewHolder> {

//    Typeface tf;
    int current_page, last_page;
    private ArrayList<ModelStagger> arrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;
    Spinner spinner;
//    RelativeLayout container;
    public AdapterStagger(Activity activity, ArrayList<ModelStagger> r) {
        this.mInflater = LayoutInflater.from(activity);
        this.arrayList = r;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.stagger_item, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ModelStagger item = arrayList.get(position);


        holder.cardShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, SubDept.class));
            }
        });

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
//                Intent intent = new Intent(activity, DeptsActivity.class);
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

    private void initSpinner() {
        spinner.setDropDownWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        ArrayList<String> arrayListSpinner = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            arrayListSpinner.add(String.valueOf(i+1));
        }

        ArrayAdapter<String> arrayAdapterSpinner = new ArrayAdapter<String>(activity, R.layout.spinner_item, arrayListSpinner) {
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                return super.getDropDownView(position, convertView, parent);
            }
        };
        arrayAdapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapterSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
//                    s_month = "";
                } else {
//                    s_month = array_month[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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

        ImageView imageView;
        ConstraintLayout container;
        TextView textViewTitle, textViewPrice;
//        Spinner spinner;
        CardView cardShowMore;

        ViewHolder(View itemView) {
            super(itemView);
//            layDel = itemView.findViewById(R.id.layDel);
//            imageView = itemView.findViewById(R.id.img);
//            container = itemView.findViewById(R.id.container);
//            textViewTitle = itemView.findViewById(R.id.title);
//            textViewPrice = itemView.findViewById(R.id.price);
            cardShowMore = itemView.findViewById(R.id.cardShowMore);
//            initSpinner();

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }



}
