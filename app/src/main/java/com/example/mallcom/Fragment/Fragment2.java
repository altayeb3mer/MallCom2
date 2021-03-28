package com.example.mallcom.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.mallcom.Adapter.AdapterDepts;
import com.example.mallcom.Adapter.AdapterDepts1;
import com.example.mallcom.Adapter.AdapterDepts2;
import com.example.mallcom.Adapter.AdapterDet1;
import com.example.mallcom.Adapter.AdapterDet2;
import com.example.mallcom.Models.ModelDept;
import com.example.mallcom.Models.ModelDetails;
import com.example.mallcom.R;

import java.util.ArrayList;


public class Fragment2 extends Fragment {

    RecyclerView recyclerViewDept1,recyclerViewDept2;

    AdapterDepts1 adapterDepts1;
    AdapterDepts2 adapterDepts2;
    ArrayList<ModelDept> arrayList;

    View view;
    Spinner spinnerDept;
    ArrayList<String> arrayListSpinner;
    ArrayAdapter<String> arrayAdapterSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_2, container, false);
        init();
        return view;
    }

    private void init() {
        arrayList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            ModelDept modelDept = new ModelDept();
            modelDept.setId(i+"");
            arrayList.add(modelDept);
        }
        recyclerViewDept1 = view.findViewById(R.id.recyclerDept1);
        recyclerViewDept2 = view.findViewById(R.id.recyclerDept2);

        initAdapter();
        spinnerDept = view.findViewById(R.id.spinner);
        initSpinner();

    }

    private void initSpinner() {
        spinnerDept = view.findViewById(R.id.spinner);
        arrayListSpinner = new ArrayList<>();
        arrayListSpinner.add("قسم1");
        arrayListSpinner.add("قسم2");
        arrayListSpinner.add("قسم3");
        arrayListSpinner.add("قسم4");
        arrayListSpinner.add("قسم5");

        arrayAdapterSpinner = new ArrayAdapter<String>(context,R.layout.spinner_item,arrayListSpinner){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                return super.getDropDownView(position, convertView, parent);
            }
        };
        arrayAdapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDept.setAdapter(arrayAdapterSpinner);
        spinnerDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
//                    s_month = "";
                } else {
//                    s_month = array_month[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



    }

    private void initAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false);
        recyclerViewDept1.setLayoutManager(gridLayoutManager);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false);
        recyclerViewDept2.setLayoutManager(gridLayoutManager2);

        adapterDepts1 = new AdapterDepts1(getActivity(),arrayList);
        adapterDepts2 = new AdapterDepts2(getActivity(),arrayList);
        recyclerViewDept1.setAdapter(adapterDepts1);
        recyclerViewDept2.setAdapter(adapterDepts2);


    }

    Context context;
    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }


}