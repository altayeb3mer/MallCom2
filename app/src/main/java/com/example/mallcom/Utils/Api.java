package com.example.mallcom.Utils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class Api {
    public static String ROOT_URL = "https://mallcom-asw4i.ondigitalocean.app/";

    //registration
    public interface RetrofitRegister {
        @POST("api/v1/auth/signUp")
        Call<String> putParam(@Body HashMap<String, String> param);
    }
    //getCat
    public interface RetrofitCategory {
        @GET("api/v1/auth/signUp")
        Call<String> putParam();
    }


}
