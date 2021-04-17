package com.example.mallcom.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.mallcom.Adapter.AdapterProducts;
import com.example.mallcom.Models.ModelItems;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Global;
import com.example.mallcom.Utils.ToolbarClass;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class Products extends AppCompatActivity {

    AdapterProducts adapterProducts;
    RecyclerView recyclerView;
    ArrayList<ModelItems> arrayList ;

    ImageView imgBack;
    LinearLayout laySort,layFilter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Global().changeStatusBarColor(this,getResources().getColor(R.color.colorPrimary));
        setContentView(R.layout.products);
        init();
    }



    private void dialogSort() {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
//      final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_sort);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RadioGroup radioGroup = dialog.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                dialog.dismiss();
            }
        });


        dialog.show();

    }

    private void init() {
        layFilter = findViewById(R.id.layFilter);
        layFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(),FilterActivity.class));
            }
        });
        laySort = findViewById(R.id.laySort);
        laySort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialogSort();
            }
        });
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        arrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ModelItems modelItems = new ModelItems();
            modelItems.setId(i+"");
            arrayList.add(modelItems);
        }
        recyclerView = findViewById(R.id.recycler);
        initAdapter(arrayList);
    }

    private void initAdapter(ArrayList<ModelItems> arrayList) {
        adapterProducts = new AdapterProducts(this,arrayList);
        recyclerView.setAdapter(adapterProducts);
    }

}