package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mallcom.R;
import com.example.mallcom.Utils.Global;
import com.example.mallcom.Utils.ToolbarClass;

public class Payment2 extends ToolbarClass  implements View.OnClickListener {

    AppCompatButton button;
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.payment2, "بيانات الدفع");
        new Global().changeStatusBarColor(this,getResources().getColor(R.color.colorPrimary));
        init();
    }

    private void init() {
        button = findViewById(R.id.btn);
        button.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn:{
                startActivity(new Intent(getApplicationContext(),PaymentDone.class));
                break;
            }
        }
    }
}