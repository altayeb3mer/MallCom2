package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mallcom.Database.SqlLiteDataBase;
import com.example.mallcom.Models.ModelCart;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Global;

public class ProductDetails extends AppCompatActivity implements View.OnClickListener {

    TextView addToCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        new Global().changeStatusBarColor(this,getResources().getColor(R.color.colorPrimary));
        init();
    }

    private void init() {
        addToCart = findViewById(R.id.addToCart);
        addToCart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addToCart:{

                ModelCart modelCart = null;
                try {
                    modelCart = new ModelCart();
                    modelCart.setId("1");
                    modelCart.setName("الاسم");
                    modelCart.setPrice1("500");
                    modelCart.setPrice2("600");
                    modelCart.setDescription("رام 1 ");
                    modelCart.setQty("1");
                    modelCart.setRate("5");
                    modelCart.setImage("");//todo image
                    new SqlLiteDataBase(getApplicationContext()).addToCart(modelCart);
                    Toast.makeText(this, "تمت الاضافة", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(this, "حدث خطأ", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}