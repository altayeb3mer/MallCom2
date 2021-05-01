package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mallcom.Adapter.AdapterMyOrder;
import com.example.mallcom.Adapter.FavoriteAdapter;
import com.example.mallcom.Models.FavoriteModel;
import com.example.mallcom.Models.ModelMyOrder;
import com.example.mallcom.R;
import com.example.mallcom.Utils.ToolbarClass;

import java.util.ArrayList;

public class MyFavorite extends ToolbarClass  {
RecyclerView recyclerView;
    ArrayList<FavoriteModel> arrayList;
    FavoriteAdapter adapterMyOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite);
        super.onCreate(R.layout.activity_my_favorite, "المفضلة");
        init();
    }
    private void init() {
        recyclerView = findViewById(R.id.recyclerfavorite);
        arrayList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            FavoriteModel modelMyOrder = new FavoriteModel();
            modelMyOrder.setId(""+i);
            arrayList.add(modelMyOrder);
        }
        adapterMyOrder = new FavoriteAdapter(this,arrayList);
        recyclerView.setAdapter(adapterMyOrder);
    }
}