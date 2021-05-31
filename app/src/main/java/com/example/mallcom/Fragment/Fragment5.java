package com.example.mallcom.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mallcom.R;



public class Fragment5 extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match


    public Fragment5() {
        // Required empty public constructor
    }


    TextView textViewName,textViewState,textViewCity,textViewRegion;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_5, container, false);
        init();
        return view;
    }

    private void init() {
        textViewName = view.findViewById(R.id.name);
        textViewState = view.findViewById(R.id.state);
        textViewCity = view.findViewById(R.id.city);
        textViewRegion = view.findViewById(R.id.region);

        textViewName.setOnClickListener(this);
        textViewState.setOnClickListener(this);
        textViewCity.setOnClickListener(this);
        textViewRegion.setOnClickListener(this);

    }


    private void dialogEdit(final String value, final String key) {
//        final BottomSheetDialog di`alog = new BottomSheetDialog(this);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_edit);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView ic_close = dialog.findViewById(R.id.ic_close);
        ic_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        final EditText editText = dialog.findViewById(R.id.edt);
        editText.setText(value);
        AppCompatButton button = dialog.findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().trim().equals(value))
                {
                    Toast.makeText(context, "لم تقم بالتعديل"+key, Toast.LENGTH_SHORT).show();
                }else{
                    dialog.dismiss();
                }
            }
        });


        dialog.show();

    }

    Context context;
    @Override
    public void onAttach(@NonNull Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.name:{
                dialogEdit(textViewName.getText().toString().trim(),"name");
                break;
            }
            case R.id.state:{
                dialogEdit(textViewName.getText().toString().trim(),"state");
                break;
            }
            case R.id.city:{
                dialogEdit(textViewName.getText().toString().trim(),"city");
                break;
            }
            case R.id.region:{
                dialogEdit(textViewName.getText().toString().trim(),"region");
                break;
            }
        }
    }
}