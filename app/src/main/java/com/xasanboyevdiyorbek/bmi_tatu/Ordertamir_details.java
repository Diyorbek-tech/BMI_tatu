package com.xasanboyevdiyorbek.bmi_tatu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.URL;

public class Ordertamir_details extends AppCompatActivity {
    TextView product, phone, addition;
    ImageView img;
    Bitmap mIcon_val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordertamir_details);


        Intent intent = getIntent();
        String productt = intent.getStringExtra("product");
        String phonett = intent.getStringExtra("phone");
        String imgtt = intent.getStringExtra("img");
        String additiont = intent.getStringExtra("addition");


        product = findViewById(R.id.ddproduct);
        phone = findViewById(R.id.dphone);
        img = findViewById(R.id.imgid);
        addition = findViewById(R.id.ddaddition);

        Glide.with(this).load(imgtt).into(img);
        product.setText(productt);
        phone.setText(phonett);
        addition.setText(additiont);



    }
}