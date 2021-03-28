package com.example.mallcom.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mallcom.Adapter.AdapterDepts;
import com.example.mallcom.Adapter.AdapterProducts;
import com.example.mallcom.Models.ModelDept;
import com.example.mallcom.Models.ModelProducts;
import com.example.mallcom.R;

import java.util.ArrayList;


public class Fragment1 extends Fragment {

    View view;
    AdapterDepts adapterDepts;
    ArrayList<ModelDept> arrayList;
    RecyclerView recyclerView;

    AdapterProducts adapterProducts;
    ArrayList<ModelProducts> productsArrayList;
    RecyclerView recyclerViewWomenDept,recyclerViewMenProduct;

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_1, container, false);
        init();
        return view;
    }

    private void init() {
        recyclerView = view.findViewById(R.id.recyclerDept);
        recyclerViewWomenDept = view.findViewById(R.id.recyclerProductWomen);
        recyclerViewMenProduct = view.findViewById(R.id.recyclerMen);


        initAdapterDept();
        initAdapterWomen();
    }

    private void initAdapterWomen() {
        productsArrayList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, true);
        recyclerView.setLayoutManager(gridLayoutManager);
        for (int i = 0; i < 5; i++) {
            ModelProducts  modelProducts = new ModelProducts();
            modelProducts.setId(i+"");
            productsArrayList.add(modelProducts);
        }

        adapterProducts = new AdapterProducts(getActivity(),productsArrayList);
        recyclerViewWomenDept.setAdapter(adapterProducts);
        recyclerViewMenProduct.setAdapter(adapterProducts);

    }

    private void initAdapterDept() {
        arrayList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, true);
        recyclerView.setLayoutManager(gridLayoutManager);
        for (int i = 0; i < 5; i++) {
            ModelDept  modelDept = new ModelDept();
            modelDept.setId(i+"");
            arrayList.add(modelDept);
        }

        adapterDepts = new AdapterDepts(getActivity(),arrayList);
        recyclerView.setAdapter(adapterDepts);


    }

    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }


}