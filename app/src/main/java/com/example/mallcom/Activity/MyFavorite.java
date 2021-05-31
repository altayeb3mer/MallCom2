package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mallcom.Adapter.AdapterMyOrder;
import com.example.mallcom.Adapter.FavoriteAdapter;
import com.example.mallcom.Adapter.NotoficationAdapter;
import com.example.mallcom.Data.Favoritedata;
import com.example.mallcom.Data.Notification;
import com.example.mallcom.Database.SharedPrefManager;
import com.example.mallcom.Models.FavoriteModel;
import com.example.mallcom.Models.ModelMyOrder;
import com.example.mallcom.Models.NotificationModel;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Api;
import com.example.mallcom.Utils.ToolbarClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.widget.Toast.LENGTH_LONG;

public class MyFavorite extends ToolbarClass  {
RecyclerView recyclerView;
    LinearLayout progressLay;
     String token;
     TextView nodatasound;
    ArrayList<FavoriteModel> arrayList;
    FavoriteAdapter adapterMyOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite);
        super.onCreate(R.layout.activity_my_favorite, "المفضلة");
        progressLay = findViewById(R.id.progressLay);
        recyclerView = findViewById(R.id.recyclerfavorite);
        token = SharedPrefManager.getInstance(getApplicationContext()).getAppToken();
        nodatasound = findViewById(R.id.nodatasound);

        favoritedata();
    }
    private void init() {
        arrayList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            FavoriteModel modelMyOrder = new FavoriteModel();
            modelMyOrder.setId(""+i);
            arrayList.add(modelMyOrder);
        }
        adapterMyOrder = new FavoriteAdapter(this,arrayList);
        recyclerView.setAdapter(adapterMyOrder);
    }
    private void favoritedata() {
        arrayList = new ArrayList<>();
        progressLay.setVisibility(View.VISIBLE);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Request.Builder ongoing = chain.request().newBuilder();
                        // ongoing.addHeader("Content-Type", "application/json;");
                        ongoing.addHeader("Accept", "application/json");
                        //.addHeader("Authorization", "Bearer " + Setting.Token)

//                        ongoing.addHeader("lang", SharedPrefManager.getInstance(getApplicationContext()).GetAppLanguage());
                       // String token = SharedPrefManager.getInstance(getApplicationContext()).getAppToken();
                        //String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xMjcuMC4wLjE6ODAwMFwvYXBpXC92MVwvdXNlclwvbG9naW4iLCJpYXQiOjE2MTYzNzQzMTQsIm5iZiI6MTYxNjM3NDMxNCwianRpIjoiVjY2bXVxM2FpSHJwenFBayIsInN1YiI6MSwicHJ2IjoiMjNiZDVjODk0OWY2MDBhZGIzOWU3MDFjNDAwODcyZGI3YTU5NzZmNyJ9.TF70v29HuwEQCb9ySR--bbY1pRivGv2831d0M1k_Wt0";
                        ongoing.addHeader("Authorization", token);
                        return chain.proceed(ongoing.build());
                    }
                })
                .readTimeout(60*5, TimeUnit.SECONDS)
                .connectTimeout(60*5, TimeUnit.SECONDS)
                .build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.ROOT_URL)
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api.Retrofitfavorite service = retrofit.create(Api.Retrofitfavorite.class);

        Call<Favoritedata.Favoriteresponse> call = service.putParam();
        call.enqueue(new Callback<Favoritedata.Favoriteresponse>() {
            @Override
            public void onResponse(Call<Favoritedata.Favoriteresponse> call,
                                   Response<Favoritedata.Favoriteresponse> response) {
                if (response.code()==200) {
                    if(response.body().getSuccess()){
                        //  ArrayList<NotificationModel> arrayList = new ArrayList<>();

                        for (Favoritedata.Datum data : response.body().getData())
                        {
                            FavoriteModel modelMyOrder = new FavoriteModel();
                            modelMyOrder.setId(data.getId()+"");
                            modelMyOrder.setUpdated_at(data.getUpdatedAt());
                            modelMyOrder.setName(data.getName());
                            modelMyOrder.setPrice(data.getPrice()+"");
                            modelMyOrder.setPhoto(data.getPhoto()+"");
                            if(data.getRate().size()>0)
                            modelMyOrder.setRate(data.getRate().get(0).getRate()+" ");
                            else
                                modelMyOrder.setRate("");

                            arrayList.add(modelMyOrder);
                        }
                        //Toast.makeText(Registration.this,arrayList.size()+"", Toast.LENGTH_LONG).show();
                   if(arrayList.size()>0){
                        adapterMyOrder = new FavoriteAdapter(MyFavorite.this,arrayList);
                        recyclerView.setAdapter(adapterMyOrder);
                        progressLay.setVisibility(View.GONE);}
                   else
                   {
                       nodatasound.setText("عذرا لا يوجد مفضلة بعد");
                       nodatasound.setVisibility(View.VISIBLE);
                       recyclerView.setVisibility(View.GONE);
                       //Toast.makeText(Notifications.this,response.code()+"\n"+response.headers(), LENGTH_LONG).show();
                       progressLay.setVisibility(View.GONE);

                   }
                    }
                }
                else
                {
                    nodatasound.setText("عذرا لا يوجد اشعارات");
                    nodatasound.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    //Toast.makeText(Notifications.this,response.code()+"\n"+response.headers(), LENGTH_LONG).show();
                    progressLay.setVisibility(View.GONE);

                }

            }
            @Override
            public void onFailure(Call<Favoritedata.Favoriteresponse> call, Throwable t) {
                Toast.makeText(MyFavorite.this,t.toString()+"", LENGTH_LONG).show();
                progressLay.setVisibility(View.GONE);

            }
        });
    }

}