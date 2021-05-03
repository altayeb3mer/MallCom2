package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toolbar;

import com.example.mallcom.Adapter.FavoriteAdapter;
import com.example.mallcom.Adapter.NotoficationAdapter;
import com.example.mallcom.Models.FavoriteModel;
import com.example.mallcom.Models.NotificationModel;
import com.example.mallcom.R;
import com.example.mallcom.Utils.ToolbarClass;

import java.util.ArrayList;

public class Notifications extends ToolbarClass {
    RecyclerView recyclerView;
    ArrayList<NotificationModel> arrayList;
    NotoficationAdapter adapterMyOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        super.onCreate(R.layout.activity_notifications, "الإشعارات");

        init();
    }
    private void init() {
        recyclerView = findViewById(R.id.recyclernotification);
        arrayList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            NotificationModel modelMyOrder = new NotificationModel();
            modelMyOrder.setId(""+i);
            arrayList.add(modelMyOrder);
        }
        adapterMyOrder = new NotoficationAdapter(this,arrayList);
        recyclerView.setAdapter(adapterMyOrder);
    }
}