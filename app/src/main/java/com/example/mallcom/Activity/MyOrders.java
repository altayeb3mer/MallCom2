package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mallcom.Adapter.AdapterMyOrder;
import com.example.mallcom.Models.ModelMyOrder;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Global;
import com.example.mallcom.Utils.ToolbarClass;

import java.util.ArrayList;

public class MyOrders extends ToolbarClass {

    AdapterMyOrder adapterMyOrder;
    RecyclerView recyclerView;
    ArrayList<ModelMyOrder> arrayList;

    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_my_orders, "معلومات التوصيل");
        new Global().changeStatusBarColor(this,getResources().getColor(R.color.colorPrimary));
        init();
    }

    private void init() {
        recyclerView = findViewById(R.id.recycler);
        for (int i = 0; i < 5; i++) {
            ModelMyOrder item = new ModelMyOrder();
            item.setId(i+"");
            arrayList.add(item);
        }
        adapterMyOrder = new AdapterMyOrder(this,arrayList);
        recyclerView.setAdapter(adapterMyOrder);
    }
}