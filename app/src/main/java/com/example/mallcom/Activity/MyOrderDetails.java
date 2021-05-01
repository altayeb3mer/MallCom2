package com.example.mallcom.Activity;


import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mallcom.Adapter.AdapterMyOrder;
import com.example.mallcom.Models.ModelMyOrder;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Global;
import com.example.mallcom.Utils.ToolbarClass;

import java.util.ArrayList;

public class MyOrderDetails extends ToolbarClass {


    RecyclerView recyclerView;
    ArrayList<ModelMyOrder> arrayList;
    AdapterMyOrder adapterMyOrder;

    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.my_order, "طلباتي");
        new Global().changeStatusBarColor(this,getResources().getColor(R.color.colorPrimary));
        init();
    }

    private void init() {
        recyclerView = findViewById(R.id.recycler);
        arrayList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            ModelMyOrder modelMyOrder = new ModelMyOrder();
            modelMyOrder.setId(""+i);
            arrayList.add(modelMyOrder);
        }
        adapterMyOrder = new AdapterMyOrder(this,arrayList);
        recyclerView.setAdapter(adapterMyOrder);
    }
}