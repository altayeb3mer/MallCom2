package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mallcom.R;

public class Registration extends AppCompatActivity  implements View.OnClickListener {
    AppCompatButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registraton);
        init();
    }
    private void init() {
        button = findViewById(R.id.btn);
        button.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn: {
//                startActivity(new Intent(getApplicationContext(),ConfirmPhone.class));
                break;
            }
        }
    }
}