package com.example.mallcom.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mallcom.Adapter.AdapterCart;
import com.example.mallcom.Models.ModelCart;
import com.example.mallcom.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment4#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment4 extends Fragment {


    public Fragment4() {
        // Required empty public constructor
    }

    View view;

    RecyclerView recyclerView;
    AdapterCart adapterCart;
    ArrayList<ModelCart> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_4, container, false);
        init();
        return view;
    }

    private void init() {
        recyclerView = view.findViewById(R.id.recycler);
        arrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ModelCart modelCart = new ModelCart();
            modelCart.setId(""+i);
            arrayList.add(modelCart);
        }
        adapterCart = new AdapterCart(getActivity(),arrayList);
        recyclerView.setAdapter(adapterCart);
    }
    Context context;
    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }

}