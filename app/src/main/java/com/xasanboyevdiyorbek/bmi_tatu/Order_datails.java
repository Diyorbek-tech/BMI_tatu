package com.xasanboyevdiyorbek.bmi_tatu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Order_datails extends AppCompatActivity {
    TextView product, height, weight, count, addition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_datails);

        Intent intent = getIntent();
        String productt = intent.getStringExtra("product");
        String heightt = intent.getStringExtra("height");
        String weightt = intent.getStringExtra("weight");
        String countt = intent.getStringExtra("count");
        String additiont = intent.getStringExtra("addition");

        product = findViewById(R.id.dproduct);
        height = findViewById(R.id.dheight);
        weight = findViewById(R.id.dweight);
        count = findViewById(R.id.dcount);
        addition = findViewById(R.id.daddition);


        product.setText(productt);
        height.setText(heightt);
        weight.setText(weightt);
        count.setText(countt);
        addition.setText(additiont);


    }
}