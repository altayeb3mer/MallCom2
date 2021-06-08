package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mallcom.R;
import com.example.mallcom.Utils.Global;
import com.example.mallcom.Utils.ToolbarClass;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;

import java.util.HashMap;

public class Payment1 extends ToolbarClass implements View.OnClickListener {

    AppCompatButton button;
    HashMap<String, String> hashMap = new HashMap<>();

    JSONArray orders = new JSONArray();

    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.payment1, "بيانات الدفع");
        new Global().changeStatusBarColor(this,getResources().getColor(R.color.colorPrimary));
        try {
            hashMap = (HashMap<String, String>) getIntent().getSerializableExtra("hashMap");
            total = hashMap.get("total");
        } catch (Exception e) {
            e.printStackTrace();
        }
        init();
//        dialogCash();
        initRadios();
    }

    String payment_method ="";
    RadioGroup rGroup;
    String  total="";
    private void initRadios() {
        rGroup = findViewById(R.id.radio_sim_type);
//        pay_type_group = findViewById(R.id.radio_pay_type);
        // This will get the radiobutton in the radiogroup that is checked
        RadioButton checkedRadioButton = rGroup.findViewById(rGroup.getCheckedRadioButtonId());
        // This overrides the radiogroup onCheckListenerd
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio1: {
                        payment_method = "cash";
                        dialogCash("هل توافق على الدفع عند التسليم ؟",total);
                        break;
                    }
                    case R.id.radio2: {
                        payment_method = "credit";
                        dialogCash("سوف يتم الدفع عن طريق البطاقة المصرفية ؟",total);
                        break;
                    }
                    case R.id.radio3: {
                        payment_method = "bok";
                        dialogCash("هل توافق على الدفع عن طريق تطبيق بنكك ؟",total);
                        break;
                    }
                    default:{
                        Toast.makeText(Payment1.this, "الرجاء اختيار نوع دفع الهاتف المحول له", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
//                Toast.makeText(TransToPhone.this, "" + sim_type, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void init() {
        button = findViewById(R.id.btn);
        button.setOnClickListener(this);
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn:{
                if (!agree){
                    Toast.makeText(this, "الرجاء تحديد طريقة الدفع والموافقة عليها", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!payment_method.isEmpty()){
                    Intent intent = new Intent(getApplicationContext(),Payment2.class);
                    hashMap.put("payment_method",payment_method);
                    intent.putExtra("hashMap",hashMap);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "الرجاء تحديد طريقة الدفع", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }


    private boolean agree=false;
    private void dialogCash(String msg,String total) {
//        final BottomSheetDialog dialog = new BottomSheetDialog(this);
      final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_cash);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        AppCompatButton button = dialog.findViewById(R.id.btn);
        AppCompatButton button2 = dialog.findViewById(R.id.btn2);
        TextView message = dialog.findViewById(R.id.msg);
        TextView textViewTotal = dialog.findViewById(R.id.total);
        message.setText(msg);
        textViewTotal.setText(total);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agree = true;
                dialog.dismiss();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agree = false;
                dialog.dismiss();
            }
        });


        dialog.show();

    }

}