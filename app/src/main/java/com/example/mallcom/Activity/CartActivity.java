package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.example.mallcom.Adapter.AdapterCart;
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

    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_cart, "السلة");
        new Global().changeStatusBarColor(this,getResources().getColor(R.color.productActivityColor));
        init();
    }

    private void init() {
        button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),DeliveryDetails.class));
            }
        });
        recyclerView = findViewById(R.id.recycler);
        arrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ModelCart modelCart = new ModelCart();
            modelCart.setId(""+i);
            arrayList.add(modelCart);
        }
        adapterCart = new AdapterCart(this,arrayList);
        recyclerView.setAdapter(adapterCart);
    }
}