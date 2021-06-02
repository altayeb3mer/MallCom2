package com.example.mallcom.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mallcom.Activity.Notifications;
import com.example.mallcom.Adapter.AdapterDepts;
import com.example.mallcom.Adapter.AdapterDepts1;
import com.example.mallcom.Adapter.AdapterDepts2;
import com.example.mallcom.Adapter.AdapterDet1;
import com.example.mallcom.Adapter.AdapterDet2;
import com.example.mallcom.Adapter.NotoficationAdapter;
import com.example.mallcom.Data.Notification;
import com.example.mallcom.Database.SharedPrefManager;
import com.example.mallcom.Models.ModelDept;
import com.example.mallcom.Models.ModelDetails;
import com.example.mallcom.Models.NotificationModel;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Api;

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


public class Fragment2 extends Fragment {

    RecyclerView recyclerView;
    ArrayList<NotificationModel> arrayList;
    NotoficationAdapter adapterMyOrder;
    LinearLayout progressLay;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_2, container, false);
        progressLay = view.findViewById(R.id.progressLay);
        recyclerView = view.findViewById(R.id.recyclernotification);
        notificationdata();
        return view;
    }


    private void notificationdata() {
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
//                        String token = SharedPrefManager.getInstance(context).getAppToken();
                        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xMjcuMC4wLjE6ODAwMFwvYXBpXC92MVwvdXNlclwvbG9naW4iLCJpYXQiOjE2MTYzNzQzMTQsIm5iZiI6MTYxNjM3NDMxNCwianRpIjoiVjY2bXVxM2FpSHJwenFBayIsInN1YiI6MSwicHJ2IjoiMjNiZDVjODk0OWY2MDBhZGIzOWU3MDFjNDAwODcyZGI3YTU5NzZmNyJ9.TF70v29HuwEQCb9ySR--bbY1pRivGv2831d0M1k_Wt0";
                        ongoing.addHeader("Authorization", "Bearer "+token);
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

        Api.RetrofitNotificatio service = retrofit.create(Api.RetrofitNotificatio.class);

        Call<Notification.Notificationresponse> call = service.putParam();
        call.enqueue(new Callback<Notification.Notificationresponse>() {
            @Override
            public void onResponse(Call<Notification.Notificationresponse> call,
                                   Response<Notification.Notificationresponse> response) {
                if (response.code()==200) {
                    if(response.body().getSuccess()){
                        //  ArrayList<NotificationModel> arrayList = new ArrayList<>();

                        for (Notification.Datum data : response.body().getData())
                        {
                            NotificationModel modelMyOrder = new NotificationModel();
                            modelMyOrder.setId(data.getId()+"");
                            modelMyOrder.setUpdated_at(data.getUpdatedAt());
                            modelMyOrder.setTitle(data.getTitle());
                            modelMyOrder.setContent(data.getContent());
                            arrayList.add(modelMyOrder);
                        }
                        //Toast.makeText(Registration.this,arrayList.size()+"", Toast.LENGTH_LONG).show();

                        adapterMyOrder = new NotoficationAdapter(context,arrayList);
                        recyclerView.setAdapter(adapterMyOrder);
                        progressLay.setVisibility(View.GONE);
                    }
                }
                else
                {
                    Toast.makeText(context,response.code()+"\n"+response.headers(), LENGTH_LONG).show();
                    progressLay.setVisibility(View.GONE);

                }

            }
            @Override
            public void onFailure(Call<Notification.Notificationresponse> call, Throwable t) {
                Toast.makeText(context,t.toString()+"", LENGTH_LONG).show();
                progressLay.setVisibility(View.GONE);

            }
        });
    }

    Context context;
    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }


}