package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mallcom.Adapter.MainDepartmentAdapter;
import com.example.mallcom.Adapter.NotoficationAdapter;
import com.example.mallcom.Models.DepartmentModel;
import com.example.mallcom.Models.NotificationModel;
import com.example.mallcom.R;
import com.example.mallcom.Utils.ToolbarClass;

import java.util.ArrayList;

public class MainDepartment extends ToolbarClass {
    RecyclerView recyclerView;
    ArrayList<DepartmentModel> arrayList;
    MainDepartmentAdapter adapterMyOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_department);
        super.onCreate(R.layout.activity_main_department, "الاقسام الرئيسية");

        init();
    }
    private void init() {
        recyclerView = findViewById(R.id.recyclermaindepartment);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getBaseContext(), 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        arrayList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            DepartmentModel modelMyOrder = new DepartmentModel();
            modelMyOrder.setId(""+i);
            arrayList.add(modelMyOrder);
        }
        adapterMyOrder = new MainDepartmentAdapter(this,arrayList);
        recyclerView.setAdapter(adapterMyOrder);
    }

}