package com.example.mallcom.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mallcom.Adapter.AdapterDepts1;
import com.example.mallcom.Adapter.AdapterDepts2;
import com.example.mallcom.Adapter.AdapterDet1;
import com.example.mallcom.Adapter.AdapterDet2;
import com.example.mallcom.Models.ModelDetails;
import com.example.mallcom.R;

import java.util.ArrayList;


public class FragmentDet2 extends Fragment {


    public FragmentDet2() {
        // Required empty public constructor
    }


    View view;

    RecyclerView recyclerView1,recyclerView2;
    AdapterDet1 adapterDepts1;
    AdapterDet2 adapterDepts2;

    ArrayList<ModelDetails> arrayList1,arrayList2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_det2, container, false);
        initAdapters();
        return view;
    }

    private void initAdapters() {
        recyclerView1 = view.findViewById(R.id.recycler);
        recyclerView2 = view.findViewById(R.id.recycler2);
        arrayList1 = new ArrayList<>();
        arrayList2 = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            ModelDetails modelDetails = new ModelDetails();
            modelDetails.setId(""+i);
            arrayList1.add(modelDetails);
        }
        adapterDepts1 = new AdapterDet1(getActivity(),arrayList1);
        adapterDepts2 = new AdapterDet2(getActivity(),arrayList1);
        recyclerView1.setAdapter(adapterDepts1);
        recyclerView2.setAdapter(adapterDepts2);

    }


    Context context;
    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }

}