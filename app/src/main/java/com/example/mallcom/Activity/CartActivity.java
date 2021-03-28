package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mallcom.Adapter.AdapterCart;
import com.example.mallcom.Models.ModelCart;
import com.example.mallcom.R;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterCart adapterCart;
    ArrayList<ModelCart> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
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