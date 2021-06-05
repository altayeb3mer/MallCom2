package com.example.mallcom.Utils;

import com.example.mallcom.Data.Favoritedata;
import com.example.mallcom.Data.Notification;
import com.example.mallcom.Data.Stateadata;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public class Api {
    public static String ROOT_URL = "https://mallcom-asw4i.ondigitalocean.app/mall/";
//    public static String ROOT_URL = "https://mallcom-asw4i.ondigitalocean.app/";
                                 //  https://mallcom-asw4i.ondigitalocean.app/
                                 //  api/v1/user/myNotifications
    //registration
    public interface RetrofitRegister {
        @POST("api/v1/auth/signUp")
        Call<String> putParam(@Body HashMap<String, String> param);
    }
    public interface RetrofitNotificatio {
        @GET("api/v1/user/myNotifications")
        Call<Notification.Notificationresponse> putParam();
    }public interface Retrofitfavorite {
        @GET("api/v1/user/favourit")
        Call<Favoritedata.Favoriteresponse> putParam();
    }
    //search
    public interface RetrofitSearch {
        @POST("api/v1/public/search")
        Call<String> putParam(@Body HashMap<String, String> param);
    }
    //checkAccount
    public interface RetrofitCheckAccount {
        @POST("api/v1/public/accountCheck")
        Call<String> putParam(@Body HashMap<String, String> param);
    }
    //verifyAccount
    public interface RetrofitVerifyAccount {
        @POST("api/v1/public/verifyAccount")
        Call<String> putParam(@Body HashMap<String, String> param);
    }
    //getCat
    public interface RetrofitCategory {
        @GET("api/v1/public/getCategories")
        Call<String> putParam();
    }
    //getProducts
    public interface RetrofitGetProduct {
        @GET("api/v1/public/productsWith")
        Call<String> putParam(@QueryMap HashMap<String, String> param);
    }
    public interface RetrofitgetSubCategories {
        @GET("api/v1/public/getSubCategories")
        Call<String> putParam(@Query("categoryName") String state);
    }
    //get my orders
    public interface RetrofitGetMyOrders {
        @GET("api/v1/user/ordersDetails")
        Call<String> putParam();
    }
    public interface RetrofitGetstate {
        @GET("api/v1/public/states")
        Call<Stateadata.Stateresponse> putParam();
    }
    //get my order product
    public interface RetrofitGetMyOrderProduct {
        @GET("api/v1/user/getMyOrderByNumber")
        Call<String> putParam(@QueryMap HashMap<String, String> param);
    }

    //product details
    public interface RetrofitProductDetails {
        @GET("api/v1/public/search_products/{id}")
        Call<String> putParam(@Path("id") String id);
    }

    //suggestions
    public interface RetrofitSuggestions {
        @GET("api/v1/public/suggestions")
        Call<String> putParam();
    }
    //top suggestions
    public interface RetrofitTopSuggestions {
        @GET("api/v1/public/topSuggestions")
        Call<String> putParam();
    }
    //my profile
    public interface RetrofitGetMyProfile {
        @GET("api/v1/user/profile")
        Call<String> putParam();
    }

    //update profile
    public interface RetrofitUpdateProfile {
        @PUT("api/v1/user/updateProfile")
        Call<String> putParam( @Body HashMap<String, String> param);
    }
    //update profile image
    public interface RetrofitUpdateProfileImage {
        @Multipart
        @PUT("api/v1/user/updateProfile")
        Call<String> putParam(@Part MultipartBody.Part file);
    }


}
