package com.example.mallcom.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.mallcom.Adapter.FavoriteAdapter;
import com.example.mallcom.Data.Favoritedata;
import com.example.mallcom.Data.Profiledata;
import com.example.mallcom.Database.SharedPrefManager;
import com.example.mallcom.Models.FavoriteModel;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Api;
import com.example.mallcom.Utils.ToolbarClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.widget.Toast.LENGTH_LONG;

public class Myaccount extends AppCompatActivity{
    RelativeLayout canedite;
    LinearLayout progressLay;
    CircleImageView profile_image;
      EditText profilename2,state,cityprofile,regionprofile;
      TextView profilename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaccount);
        canedite=findViewById(R.id.canedite);
        progressLay = findViewById(R.id.progressLay);
        profile_image=findViewById(R.id.profile_image);
        profilename2=findViewById(R.id.profilename2);
        profilename=findViewById(R.id.profilename);
        state=findViewById(R.id.state);
        cityprofile=findViewById(R.id.cityprofile);
        regionprofile=findViewById(R.id.regionprofile);
        profiledata();
        canedite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialoag();

            }
        });
    }
    public void showDialoag()
    {
        LayoutInflater factory = LayoutInflater.from(getApplicationContext());
        final View deleteDialogView = factory.inflate(R.layout.dialog_deliev, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(getApplicationContext()).create();
        deleteDialog.setView(deleteDialogView);
        deleteDialog.show();


    }
    private void profiledata() {
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
                        String token = SharedPrefManager.getInstance(getApplicationContext()).getAppToken();
                       // String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8xMjcuMC4wLjE6ODAwMFwvYXBpXC92MVwvdXNlclwvbG9naW4iLCJpYXQiOjE2MTYzNzQzMTQsIm5iZiI6MTYxNjM3NDMxNCwianRpIjoiVjY2bXVxM2FpSHJwenFBayIsInN1YiI6MSwicHJ2IjoiMjNiZDVjODk0OWY2MDBhZGIzOWU3MDFjNDAwODcyZGI3YTU5NzZmNyJ9.TF70v29HuwEQCb9ySR--bbY1pRivGv2831d0M1k_Wt0";
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

        Api.Retrofitmyaccount service = retrofit.create(Api.Retrofitmyaccount.class);

        Call<Profiledata.Profileresponse> call = service.putParam();
        call.enqueue(new Callback<Profiledata.Profileresponse>() {
            @Override
            public void onResponse(Call<Profiledata.Profileresponse> call,
                                   Response<Profiledata.Profileresponse> response) {
                if (response.code()==200) {
                    if(response.body().getSuccess()){
                        for (Profiledata.Datum data : response.body().getData()) {
                            profilename.setText(data.getFirstName()+" "+data.getMiddleName()+" "+data.getLastName());
                            profilename2.setText(data.getFirstName()+" "+data.getMiddleName()+" "+data.getLastName());
                            state.setText(data.getState().getName());
                            cityprofile.setText(data.getState().getCity());
                            regionprofile.setText(data.getState().getCity());
                            Glide.with(Myaccount.this).load(data.getThumbnail()).into(profile_image);
                            //Toast.makeText(Registration.this,arrayList.size()+"", Toast.LENGTH_LONG).show();
                        } progressLay.setVisibility(View.GONE);
                    }
                }
                else
                {
                    Toast.makeText(Myaccount.this,response.code()+"\n"+response.headers(), LENGTH_LONG).show();
                    progressLay.setVisibility(View.GONE);

                }

            }
            @Override
            public void onFailure(Call<Profiledata.Profileresponse> call, Throwable t) {
                Toast.makeText(Myaccount.this,t.toString()+"", LENGTH_LONG).show();
                progressLay.setVisibility(View.GONE);

            }
        });
    }

}