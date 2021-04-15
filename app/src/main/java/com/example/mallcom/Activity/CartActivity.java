package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_cart, "السلة");
        new Global().changeStatusBarColor(this,getResources().getColor(R.color.productActivityColor));
        init();
    }

    private void init() {
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