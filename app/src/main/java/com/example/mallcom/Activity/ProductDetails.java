package com.example.mallcom.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mallcom.Database.SqlLiteDataBase;
import com.example.mallcom.Models.ModelCart;
import com.example.mallcom.R;
import com.example.mallcom.Utils.Global;

import static android.widget.Toast.LENGTH_LONG;

public class ProductDetails extends AppCompatActivity implements View.OnClickListener {

    TextView addToCart,nameofitem,priceh,rate;
    ImageView image,imagerate;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        new Global().changeStatusBarColor(this,getResources().getColor(R.color.colorPrimary));
        init();
    }

    private void init() {
        addToCart = findViewById(R.id.addToCart);
        imagerate = findViewById(R.id.imagerate);
        nameofitem = findViewById(R.id.nameofitem);
        nameofitem.setText(getIntent().getExtras().getString("name"));
        priceh = findViewById(R.id.priceh);
        priceh.setText(getIntent().getExtras().getString("price"));
        rate = findViewById(R.id.rate);
        if(getIntent().getExtras().getString("rate").equals("")){
            rate.setText(getIntent().getExtras().getString("rate"));
            imagerate.setVisibility(View.GONE);
        }
            else{
        rate.setText(getIntent().getExtras().getString("rate"));
            imagerate.setVisibility(View.VISIBLE);
            }
        image = findViewById(R.id.image);
        Glide.with(this).load(getIntent().getExtras().getString("image"))
                .into(image);
       // Toast.makeText(ProductDetails.this,getIntent().getExtras().getString("rate"), LENGTH_LONG).show();

        //image.setBackground(Drawable.createFromPath(getIntent().getExtras().getString("image")));
        addToCart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addToCart:{

                ModelCart modelCart = null;
                try {
                    modelCart = new ModelCart();
                    modelCart.setId(getIntent().getExtras().getString("id"));
                    modelCart.setName(getIntent().getExtras().getString("name"));
                    modelCart.setPrice1(getIntent().getExtras().getString("price"));
                    modelCart.setPrice2("600");
                    modelCart.setDescription("رام 1 ");
                    modelCart.setQty(i+++"");
                    modelCart.setRate(getIntent().getExtras().getString("rate"));
                    modelCart.setImage(getIntent().getExtras().getString("image"));//todo image
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