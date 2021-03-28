package com.example.mallcom.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mallcom.Adapter.AdapterOurNew;
import com.example.mallcom.Models.ModelOurNew;
import com.example.mallcom.R;

import java.util.ArrayList;


public class Fragment3 extends Fragment {

    RecyclerView recyclerView;
    ArrayList<ModelOurNew> arrayList;
    AdapterOurNew adapterOurNew;


    public Fragment3() {
        // Required empty public constructor
    }



    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_3, container, false);
        init();
        return view;
    }

    private void init() {
        recyclerView = view.findViewById(R.id.recycler);
        arrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ModelOurNew modelOurNew = new ModelOurNew();
            modelOurNew.setId(""+i);
            arrayList.add(modelOurNew);
        }
        adapterOurNew = new AdapterOurNew(getActivity(),arrayList);
        recyclerView.setAdapter(adapterOurNew);

    }




    Context context;
    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }
}