package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioGroup;

import com.example.mallcom.R;
import com.example.mallcom.Utils.Global;
import com.example.mallcom.Utils.ToolbarClass;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Payment1 extends ToolbarClass implements View.OnClickListener {

    AppCompatButton button;

    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.payment1, "بيانات الدفع");
        new Global().changeStatusBarColor(this,getResources().getColor(R.color.colorPrimary));
        init();
        dialogCash();
    }

    private void init() {
        button = findViewById(R.id.btn);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn:{
                startActivity(new Intent(getApplicationContext(),Payment2.class));
                break;
            }
        }
    }


    private void dialogCash() {
//        final BottomSheetDialog dialog = new BottomSheetDialog(this);
      final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_cash);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        AppCompatButton button = dialog.findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }

}