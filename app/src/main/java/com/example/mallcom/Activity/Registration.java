package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mallcom.Data.Stateadata;
import com.example.mallcom.Database.SharedPrefManager;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Api;

import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
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

public class Registration extends AppCompatActivity{
    AppCompatButton button;
    Spinner gender,state;
    EditText editTextFirstName,editTextSecondName,
    editTextThirdName,editTextRegion,editTextPhone
    ,agech;
    LinearLayout progressLay;
    ArrayList<String> arrayListid = new ArrayList<>();
    HashMap<String,String> hashMap =new HashMap<>();

    String phone="";
    TextView editTextBirth;

    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registraton);

        Bundle args = getIntent().getExtras();
        if (args!=null){
            phone = args.getString("phone");
        }

        init();
        editTextPhone.setText(phone);

        button = findViewById(R.id.btnregestration);
        gender=findViewById(R.id.gender);
        state=findViewById(R.id.state);
        progressLay = findViewById(R.id.progressLay);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("اختر المدينة");
        arrayList.add("الخرطوم");
        arrayList.add("مدني");
        arrayList.add("بورتسودان");
        arrayList.add("الابيض");
        arrayList.add("الفاشر");
        arrayList.add("سنار");
        arrayList.add("عطبرة");
        arrayList.add("ربك");
        arrayList.add("الدمازين");
        arrayList.add("كسلا");
        arrayList.add("كوستي");
        arrayList.add("كادقلي");
        arrayList.add("الجنينة");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Registration.this,
                android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(arrayAdapter);
        gender.setAdapter(arrayAdapter);
        Statedata();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preReg();
//                hashMap.put("firstName",fullname.getText().toString());
//                hashMap.put("username",fullname.getText().toString());
//                hashMap.put("phone",getIntent().getExtras().getString("userphone"));
//                hashMap.put("password",agech.getText().toString());
//                hashMap.put("state_id",arrayListid.get(state.getSelectedItemPosition()));
//                hashMap.put("gender",gender.getSelectedItem().toString());

            }
        });


        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editTextBirth.setText(sdf.format(myCalendar.getTime()));
    }
    String gender_string="ذكر";
    RadioGroup radioGroupType;
    private void init() {
        editTextPhone = findViewById(R.id.phone);
        editTextFirstName = findViewById(R.id.firstName);
        editTextSecondName=findViewById(R.id.secondName);
        editTextThirdName=findViewById(R.id.thirdName);
        editTextBirth = findViewById(R.id.birth);
        editTextBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new DatePickerDialog(Registration.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                new DatePickerDialog(Registration.this, date, 1990, 1,
                        1).show();
            }
        });
        editTextRegion = findViewById(R.id.region);

        radioGroupType = findViewById(R.id.radioGroupType);
        radioGroupType.check(R.id.radioBtnType1);
        radioGroupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.radioBtnType1: {
                        gender_string = "ذكر";
                        break;
                    }
                    case R.id.radioBtnType2: {
                        gender_string = "انثى";
                        break;
                    }
                }
            }
        });
    }

    String firstName="",secondName="",lastName="",birthDate="",region="";
    private void preReg() {
        firstName=editTextFirstName.getText().toString();
        secondName=editTextSecondName.getText().toString();
        lastName=editTextThirdName.getText().toString();
        birthDate=editTextBirth.getText().toString();
        region=editTextRegion.getText().toString();
        phone=editTextPhone.getText().toString();

        if (phone.isEmpty()){
            editTextPhone.setError("رقم الهاتف مطلوب");
            editTextPhone.requestFocus();
            return;
        }
        if (firstName.isEmpty()){
            editTextFirstName.setError("هذا الحقل مطلوب");
            editTextFirstName.requestFocus();
            return;
        }
        if (secondName.isEmpty()){
            editTextSecondName.setError("هذا الحقل مطلوب");
            editTextSecondName.requestFocus();
            return;
        }
        if (lastName.isEmpty()){
            editTextThirdName.setError("هذا الحقل مطلوب");
            editTextThirdName.requestFocus();
            return;
        }
//        if (birthDate.isEmpty()||!isValidDate(birthDate)){
//            editTextBirth.setError("هذا الحقل مطلوب ،الرجاء التاكد من الصيغة");
//            editTextBirth.requestFocus();
//            return;
//        }

        if (region.isEmpty()){
            editTextRegion.setError("هذا الحقل مطلوب");
            editTextRegion.requestFocus();
            return;
        }


        registration();
    }

    private void registration() {
        hashMap.put("firstName",firstName);
        hashMap.put("middleName",secondName);
        hashMap.put("lastName",lastName);
        hashMap.put("username",phone);
        hashMap.put("phone",phone);
        hashMap.put("birthDate",birthDate);
        hashMap.put("gender",gender_string);
        hashMap.put("password",birthDate);// TODO: 05/06/21 pass
        hashMap.put("state_id",arrayListid.get(state.getSelectedItemPosition()));
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

            Api.RetrofitRegister service = retrofit.create(Api.RetrofitRegister.class);

            Call<String> call = service.putParam(hashMap);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    try {
                        JSONObject object = new JSONObject(response.body());
                       // Toast.makeText(getApplicationContext(), "حدث خطأ حاول مجددا", Toast.LENGTH_SHORT).show();

                        String success = object.getString("success");

                        switch (success) {
                            case "true": {
                                JSONObject dataObj = object.getJSONObject("data");
                                Intent intent = new Intent(getApplicationContext(),ConfirmPhone.class);
                                intent.putExtra("userId",dataObj.getString("id"));
                                startActivity(intent);

                                break;
                            }
                            case "false": {
                              //  Intent intent =new Intent(getApplicationContext(),Registration.class);
                              //  intent.putExtra("phone",phone);
                              //  startActivity(intent);
                              //  finish();
                                Toast.makeText(getApplicationContext(), "حدث خطأ حاول مجددا", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), object.getJSONObject("errors").toString(), Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(getApplicationContext(), e.getMessage()+"\n ", Toast.LENGTH_SHORT).show();
                    }
                    progressLay.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {
                    progressLay.setVisibility(View.GONE);
                }
            });
        }

    private void Statedata() {
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

        Api.RetrofitGetstate service = retrofit.create(Api.RetrofitGetstate.class);

        Call<Stateadata.Stateresponse> call = service.putParam();
        call.enqueue(new Callback<Stateadata.Stateresponse>() {
            @Override
            public void onResponse(Call<Stateadata.Stateresponse> call, Response<Stateadata.Stateresponse> response) {
                if (response.code()==200) {
                    if(response.body().getSuccess()){
                        ArrayList<String> arrayList = new ArrayList<>();

                        for (Stateadata.Datum data : response.body().getData())
                        {
                            arrayList.add(data.getCity());
                            arrayListid.add(data.getId());
                        }
                        //Toast.makeText(Registration.this,arrayList.size()+"", Toast.LENGTH_LONG).show();

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Registration.this,
                                android.R.layout.simple_spinner_item, arrayList);
                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        state.setAdapter(arrayAdapter);
                        progressLay.setVisibility(View.GONE);
                    }
                }

            }
            @Override
            public void onFailure(Call<Stateadata.Stateresponse> call, Throwable t) {
                Toast.makeText(Registration.this,t.toString()+"", LENGTH_LONG).show();
                progressLay.setVisibility(View.GONE);

            }
        });
    }


    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

}