package com.example.mallcom.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mallcom.Activity.CartActivity;
import com.example.mallcom.Database.SharedPrefManager;
import com.example.mallcom.Database.SqlLiteDataBase;
import com.example.mallcom.Models.ModelCart;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Api;
import com.example.mallcom.Utils.Global;

import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class AdapterCart extends RecyclerView.Adapter<AdapterCart.ViewHolder> {


    private ArrayList<ModelCart> arrayList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Activity activity;
    LinearLayout progressLay;

    public AdapterCart(Activity activity, ArrayList<ModelCart> r,LinearLayout progressLay) {
        this.mInflater = LayoutInflater.from(activity);
        this.arrayList = r;
        this.activity = activity;
        this.progressLay = progressLay;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cart_item, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ModelCart item = arrayList.get(position);

//
        try {
            Glide.with(activity).load(item.getImage())
                    .into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.textViewName.setText(item.getName());
        holder.textViewDesc.setText(item.getDescription());
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        if (item.getPrice1()!=null) {

            holder.textViewPrice.setText(new Global().formatNumber(item.getPrice1()));

        }
//        holder.textViewOldPrice.setText(item.getPrice2());
        if (item.getPrice2()!=null)
        holder.textViewOldPrice.setText(new Global().formatNumber(item.getPrice2()));
        holder.textViewOldPrice.setPaintFlags( holder.textViewOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.textViewRate.setText(item.getRate());
        holder.textViewQty.setText(item.getQty());



//        holder.container.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(activity, DeptsActivity.class);
//                activity.startActivity(intent);
//            }
//        });
        holder.layDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SqlLiteDataBase(activity).deleteCartItem(item.getId());
                arrayList.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
                CartActivity.getTotal(arrayList);
            }
        });
        holder.addToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SqlLiteDataBase(activity).deleteCartItem(item.getId());
                arrayList.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
                CartActivity.getTotal(arrayList);
              addFavorite(item.getId());
            }
        });
        holder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int qty = Integer.parseInt(item.getQty())+1;
               holder.textViewQty.setText(qty+"");
               item.setQty(String.valueOf(qty));
               updateQty(item);
                CartActivity.getTotal(arrayList);
            }
        });

        holder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(item.getQty())>1){
                    int qty = Integer.parseInt(item.getQty())-1;
                    holder.textViewQty.setText(qty+"");
                    item.setQty(String.valueOf(qty));
                    updateQty(item);
                    CartActivity.getTotal(arrayList);
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

        ImageView imageView;
        LinearLayout container;
        TextView textViewOldPrice, textViewPrice,textViewName,textViewDesc,textViewRate,textViewQty;
        LinearLayout layDel,addToFavorite;
        ImageButton increase,decrease;

        ViewHolder(View itemView) {
            super(itemView);
            textViewPrice = itemView.findViewById(R.id.price1);
            textViewName = itemView.findViewById(R.id.name);
            container = itemView.findViewById(R.id.container);
            textViewDesc = itemView.findViewById(R.id.description);
            textViewOldPrice = itemView.findViewById(R.id.oldPrice);
            textViewRate = itemView.findViewById(R.id.rate);
            textViewQty = itemView.findViewById(R.id.qty);
            imageView = itemView.findViewById(R.id.imageView);
            layDel = itemView.findViewById(R.id.delete);
            increase = itemView.findViewById(R.id.increase);
            decrease = itemView.findViewById(R.id.decrease);
            addToFavorite = itemView.findViewById(R.id.addToFavorite);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    private void updateQty(ModelCart modelCart){
        if (new SqlLiteDataBase(activity).updateToCart(modelCart)){
//            Toast.makeText(activity, "done", Toast.LENGTH_SHORT).show();
        }else{
//            Toast.makeText(activity, "error", Toast.LENGTH_SHORT).show();
        }

    }


    private void addFavorite(final String id) {
        progressLay.setVisibility(View.VISIBLE);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Request.Builder ongoing = chain.request().newBuilder();
                        ongoing.addHeader("Content-Type", "application/json;");
                        ongoing.addHeader("Accept", "application/json");
//                        ongoing.addHeader("lang", SharedPrefManager.getInstance(getApplicationContext()).GetAppLanguage());
                        String token = SharedPrefManager.getInstance(activity).getAppToken();
                        ongoing.addHeader("Authorization", token);
                        return chain.proceed(ongoing.build());
                    }
                })
                .readTimeout(60 * 5, TimeUnit.SECONDS)
                .connectTimeout(60 * 5, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.ROOT_URL)
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api.RetrofitAddFavorite service = retrofit.create(Api.RetrofitAddFavorite.class);
        Call<String> call = service.putParam(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                try {
                    JSONObject object = new JSONObject(response.body());
                    String success = object.getString("success");
                    switch (success) {
                        case "true": {

                            dialogMsg("تمت الاضافة للمفضلة يمكنك الرجوع له لاحقا","اضافة للمفضلة");


                            break;
                        }
                        case "false": {

                            dialogMsg(object.getString("errors"),"تنبيه!");
                            break;
                        }

                        default: {
                            Toast.makeText(activity, "حدث خطأ الرجاء المحاوله مرة اخرى", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    progressLay.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
//                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressLay.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                progressLay.setVisibility(View.GONE);
            }
        });
    }


    private void dialogMsg(final String msg,String title_) {
//        final BottomSheetDialog di`alog = new BottomSheetDialog(this);
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_msg);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        final TextView editText = dialog.findViewById(R.id.edt);
        final TextView title = dialog.findViewById(R.id.title);
        title.setText(title_);
        editText.setText(msg);
        AppCompatButton button = dialog.findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

            }
        });


        dialog.show();

    }

}
