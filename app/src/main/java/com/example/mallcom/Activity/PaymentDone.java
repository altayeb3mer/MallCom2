package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mallcom.Database.SqlLiteDataBase;
import com.example.mallcom.R;

import java.util.HashMap;

public class PaymentDone extends AppCompatActivity {

    AppCompatButton btn2;
    TextView total;
    HashMap<String, String> hashMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_done);
        try {
            hashMap = (HashMap<String, String>) getIntent().getSerializableExtra("hashMap");
        } catch (Exception e) {
            e.printStackTrace();
        }
        total = findViewById(R.id.total);
        total.setText(hashMap.get("total"));
        btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new SqlLiteDataBase(getApplicationContext()).deleteAllTableData("cart_order"))
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}