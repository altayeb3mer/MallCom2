package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mallcom.Adapter.AdapterFilter1;
import com.example.mallcom.Adapter.AdapterFilter2;
import com.example.mallcom.Models.ModelFilter1;
import com.example.mallcom.Models.ModelFilter2;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Global;
import com.example.mallcom.Utils.ToolbarClass;

import java.util.ArrayList;

public class FilterActivity extends ToolbarClass {

    RecyclerView recyclerView1;
    AdapterFilter1 adapterFilter1;
    ArrayList<ModelFilter1> arrayList1;

    RecyclerView recyclerView2;
    AdapterFilter2 adapterFilter2;
    ArrayList<ModelFilter2> arrayList2;


    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_filter, "تنقية");
        new Global().changeStatusBarColor(this,getResources().getColor(R.color.colorPrimary));
        init();
    }

    private void init() {
        arrayList1 = new ArrayList<>();
        arrayList2 = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            ModelFilter1 modelFilter1 = new ModelFilter1();
            modelFilter1.setId(i+"");
            arrayList1.add(modelFilter1);
        }

        for (int i = 0; i <10 ; i++) {
            ModelFilter2 modelFilter2 = new ModelFilter2();
            modelFilter2.setId(i+"");
            arrayList2.add(modelFilter2);
        }
        recyclerView1 = findViewById(R.id.recycler1);
        recyclerView2 = findViewById(R.id.recycler2);
        initAdapter1(arrayList1);
        initAdapter2(arrayList2);
    }

    private void initAdapter1(ArrayList<ModelFilter1> arrayList1) {
        adapterFilter1 = new AdapterFilter1(this,arrayList1);
        recyclerView1.setAdapter(adapterFilter1);
    }

    private void initAdapter2(ArrayList<ModelFilter2> arrayList2) {
        adapterFilter2 = new AdapterFilter2(this,arrayList2);
        recyclerView2.setAdapter(adapterFilter2);
    }

}