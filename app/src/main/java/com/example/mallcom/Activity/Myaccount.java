package com.example.mallcom.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.mallcom.R;
import com.example.mallcom.Utils.ToolbarClass;

public class Myaccount extends AppCompatActivity{
    RelativeLayout canedite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaccount);
        canedite=findViewById(R.id.canedite);
        canedite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialoag();

            }
        });
    }
    public void showDialoag()
    {
        Toast.makeText(Myaccount.this,"more", Toast.LENGTH_LONG).show();
        LayoutInflater factory = LayoutInflater.from(getApplicationContext());
        final View deleteDialogView = factory.inflate(R.layout.dialog_deliev, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(getApplicationContext()).create();
        deleteDialog.setView(deleteDialogView);
        deleteDialog.show();


    }

}