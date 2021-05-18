package com.example.mallcom.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mallcom.Adapter.AdapterProducts;
import com.example.mallcom.Models.ModelItems;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Api;
import com.example.mallcom.Utils.Global;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SearchProducts extends AppCompatActivity {

    AdapterProducts adapterProducts;
    RecyclerView recyclerView;
    ArrayList<ModelItems> arrayList ;

    ImageView imgBack;
    LinearLayout laySort,layFilter;
    //search
    EditText editTextSearch;
    ImageView ic_clear,ic_search2;
    String query="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Global().changeStatusBarColor(this,getResources().getColor(R.color.colorPrimary));
        setContentView(R.layout.products);
        init();
    }



    private void dialogSort() {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
//      final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_sort);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RadioGroup radioGroup = dialog.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                dialog.dismiss();
            }
        });


        dialog.show();

    }

    private void init() {
        arrayList = new ArrayList<>();

        ic_search2 = findViewById(R.id.ic_search2);
        ic_search2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preSearch();
            }
        });
        progressLay = findViewById(R.id.progressLay);
        editTextSearch = findViewById(R.id.edtSearch);
        editTextSearch.addTextChangedListener(editTextWatcher);

        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    preSearch();
                    return true;
                }
                return false;
            }
        });
        ic_clear = findViewById(R.id.ic_clear);
        ic_clear.setVisibility(View.INVISIBLE);

        ic_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextSearch.setText("");
                showKeyboard(SearchProducts.this);
                query = "";
            }
        });


        layFilter = findViewById(R.id.layFilter);
        layFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(),FilterActivity.class));
            }
        });
        laySort = findViewById(R.id.laySort);
        laySort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialogSort();
            }
        });
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        recyclerView = findViewById(R.id.recycler);

    }

    private void preSearch(){
        query = editTextSearch.getText().toString().trim();
        if (!query.equals("")) {
            hideKeyboard(SearchProducts.this);
            if (arrayList.size()>0){
                arrayList.clear();
                adapterProducts.notifyDataSetChanged();
            }
            getSearch();
        } else {
            Toast.makeText(SearchProducts.this, "الرجاء كتابة شي للبحث", Toast.LENGTH_SHORT).show();
        }
    }


    public void showKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.showSoftInput(editTextSearch, 0);
    }

    TextWatcher editTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //none
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //none
        }

        @Override
        public void afterTextChanged(Editable s) {

            String check = s.toString();

            if (check.length()>0){
                ic_clear.setVisibility(View.VISIBLE);
            }else{
                ic_clear.setVisibility(View.INVISIBLE);
            }

        }

    };
    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private void initAdapter(ArrayList<ModelItems> arrayList) {
        adapterProducts = new AdapterProducts(this,arrayList);
        recyclerView.setAdapter(adapterProducts);
    }


    LinearLayout progressLay;
    private void getSearch() {
        arrayList = new ArrayList<>();
        progressLay.setVisibility(View.VISIBLE);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Request.Builder ongoing = chain.request().newBuilder();
                        ongoing.addHeader("Content-Type", "application/json;");
                        ongoing.addHeader("Accept", "application/json");
//                        ongoing.addHeader("lang", SharedPrefManager.getInstance(getApplicationContext()).GetAppLanguage());
//                        String token = SharedPrefManager.getInstance(getApplicationContext()).getAppToken();
//                        ongoing.addHeader("Authorization", token);
                        return chain.proceed(ongoing.build());
                    }
                })
                .readTimeout(60*5, TimeUnit.SECONDS)
                .connectTimeout(60*5, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.ROOT_URL)
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api.RetrofitSearch service = retrofit.create(Api.RetrofitSearch.class);
        HashMap<String,String> hashMap =new HashMap<>();
        hashMap.put("search",query);
        Call<String> call = service.putParam(hashMap);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                try {
                    JSONObject object = new JSONObject(response.body());
                    String success = object.getString("success");
                    switch (success) {
                        case "true": {
                            JSONObject data = object.getJSONObject("data");
                            JSONArray resultData = data.getJSONArray("result");
                            for (int i = 0; i < resultData.length(); i++) {
                                JSONObject itemData = resultData.getJSONObject(i);

                                ModelItems departmentModel = new ModelItems();
                                departmentModel.setId(itemData.getString("id"));
                                departmentModel.setName(itemData.getString("name"));
                                departmentModel.setImage(itemData.getString("photo"));
                                departmentModel.setDesc(itemData.getString("description"));
                                departmentModel.setPrice1(itemData.getString("price"));
                                departmentModel.setFinalPrice(itemData.getString("final_price"));

                                JSONArray rateArray = itemData.getJSONArray("rate");
                                if (rateArray.length()>0){
                                    JSONObject rateObj = rateArray.getJSONObject(0);
                                    departmentModel.setRate(rateObj.getString("rate"));
                                }else {
                                    departmentModel.setRate("");
                                }

                                arrayList.add(departmentModel);
                            }

                            if (arrayList.size()>0){
                                initAdapter(arrayList);
                            }else{
                                Toast.makeText(SearchProducts.this, "لاتوجد نتائج للبحث", Toast.LENGTH_SHORT).show();
                            }


                            break;
                        }

                        default: {
                            Toast.makeText(getApplicationContext(), "حدث خطأ حاول مجددا", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    progressLay.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressLay.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                progressLay.setVisibility(View.GONE);
            }
        });
    }


}