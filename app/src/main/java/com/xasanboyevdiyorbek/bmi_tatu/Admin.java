package com.xasanboyevdiyorbek.bmi_tatu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Admin extends AppCompatActivity {

Button addbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        addbook=findViewById(R.id.kitobqoshadmin);

        addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Admin.this,Addbook.class);
                startActivity(i);
            }
        });



    }


}