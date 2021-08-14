package com.example.mallcom.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mallcom.Adapter.AdapterFilter1;
import com.example.mallcom.Adapter.AdapterFilter2;
import com.example.mallcom.Models.ModelFilter1;
import com.example.mallcom.Models.ModelFilter2;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Global;
import com.example.mallcom.Utils.ToolbarClass;

import java.util.ArrayList;

public class FilterActivity extends ToolbarClass {

    public static LinearLayout priceLay;
    RecyclerView recyclerView1;
    AdapterFilter1 adapterFilter1;
    ArrayList<ModelFilter1> arrayList1;
    public static RecyclerView recyclerView2;
    public static AdapterFilter2 adapterFilter2;
    ArrayList<ModelFilter2> arrayList2;
    String priceFrom = "", priceTo = "", rate = "", color = "";
    EditText editTextPriceFrom, editTextPriceTo;
    AppCompatButton buttonOkPrice;

    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_filter, "تنقية");
        new Global().changeStatusBarColor(this, getResources().getColor(R.color.colorPrimary));
        init();
    }

    private void init() {
        editTextPriceFrom = findViewById(R.id.edtPriceFrom);
        editTextPriceFrom = findViewById(R.id.edtPriceFrom);
        editTextPriceTo = findViewById(R.id.edtPriceTo);
        buttonOkPrice = findViewById(R.id.btnPrice);
        buttonOkPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("price1",editTextPriceFrom.getText().toString().trim());
                returnIntent.putExtra("price2",editTextPriceTo.getText().toString().trim());
                returnIntent.putExtra("color","");
                returnIntent.putExtra("rate","");
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
        priceLay = findViewById(R.id.priceLay);


        arrayList1 = new ArrayList<>();
        arrayList2 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                ModelFilter1 modelFilter1 = new ModelFilter1();
                modelFilter1.setId(i + "");
                modelFilter1.setTitle("السعر");
                arrayList1.add(modelFilter1);
            }
//            if (i == 1) {
//                ModelFilter1 modelFilter1 = new ModelFilter1();
//                modelFilter1.setId(i + "");
//                modelFilter1.setTitle("اللون");
//                arrayList1.add(modelFilter1);
//            }
            if (i == 2) {
                ModelFilter1 modelFilter1 = new ModelFilter1();
                modelFilter1.setId(i + "");
                modelFilter1.setTitle("التقييم");
                arrayList1.add(modelFilter1);
            }

        }


        recyclerView1 = findViewById(R.id.recycler1);
        recyclerView2 = findViewById(R.id.recycler2);
        initAdapter1(arrayList1);
//        initAdapter2(this,arrayList2);
    }

    private void initAdapter1(ArrayList<ModelFilter1> arrayList1) {
        adapterFilter1 = new AdapterFilter1(this, arrayList1, priceLay, recyclerView2);
        recyclerView1.setAdapter(adapterFilter1);
    }

    public static void initAdapter2(Activity activity,ArrayList<ModelFilter2> arrayList2,String type) {
        adapterFilter2 = new AdapterFilter2(activity, arrayList2,type);
        recyclerView2.setAdapter(adapterFilter2);
    }

}