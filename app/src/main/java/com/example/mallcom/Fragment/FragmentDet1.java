package com.example.mallcom.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mallcom.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDet1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDet1 extends Fragment {


    public FragmentDet1() {
        // Required empty public constructor
    }
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_det1, container, false);
        init();
        return view;
    }

    private void init() {

    }
    Context context;
    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }

}