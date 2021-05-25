package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.mallcom.Adapter.AdapterCart;
import com.example.mallcom.Database.SqlLiteDataBase;
import com.example.mallcom.Models.ModelCart;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Global;
import com.example.mallcom.Utils.ToolbarClass;

import java.util.ArrayList;

public class CartActivity extends ToolbarClass {

    RecyclerView recyclerView;
    AdapterCart adapterCart;
    ArrayList<ModelCart> arrayList;
    AppCompatButton button;
    public static TextView textViewTotal;
    public static LinearLayout layNoData,layBtn;

    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_cart, "السلة");
        new Global().changeStatusBarColor(this,getResources().getColor(R.color.productActivityColor));
        init();
        getCart();
    }

    private void getCart() {
        arrayList = new SqlLiteDataBase(getApplicationContext()).GetAllCart();
        if (arrayList.size()>0){
            getTotal(arrayList);
            adapterCart = new AdapterCart(this,arrayList);
            recyclerView.setAdapter(adapterCart);

            layBtn.setVisibility(View.VISIBLE);
            layNoData.setVisibility(View.INVISIBLE);
        }else{
            layBtn.setVisibility(View.INVISIBLE);
            layNoData.setVisibility(View.VISIBLE);
        }
    }

    public static double getTotal(ArrayList<ModelCart> list){
        double total = 0;
        for (int i = 0; i < list.size(); i++) {
            total = total + (Double.parseDouble(list.get(i).getPrice1())*Double.parseDouble(list.get(i).getQty()));

            textViewTotal.setText(new Global().formatNumber(total));
        }
        if (list.size()==0){
            layBtn.setVisibility(View.INVISIBLE);
            layNoData.setVisibility(View.VISIBLE);
        }
        double a=0d;
        return a;
    }

    private void init() {
        layNoData = findViewById(R.id.layNoData);
        layBtn = findViewById(R.id.btnLay);
        textViewTotal = findViewById(R.id.total);
        button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),DeliveryDetails.class));
            }
        });
        recyclerView = findViewById(R.id.recycler);
    }
}