package com.xasanboyevdiyorbek.bmi_tatu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class Make_Order extends AppCompatActivity {
    Button addorder;
    TextInputEditText product, height, weight, count, addition;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://bmi-tatu-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);

        addorder = findViewById(R.id.addorder);
        product = findViewById(R.id.producttxt);
        height = findViewById(R.id.heighttxt);
        weight = findViewById(R.id.weighttxt);
        count = findViewById(R.id.counttxt);
        addition = findViewById(R.id.additiontxt);

        User_info user_info = new User_info();
        String logini = user_info.getName();

        Toast.makeText(this, logini, Toast.LENGTH_SHORT).show();

        addorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String producttxt = product.getText().toString();
                String weighttxt = weight.getText().toString();
                String heighttxt = height.getText().toString();
                String counttxt = count.getText().toString();
                String additiontxt = addition.getText().toString();

                databaseReference.child("Orders").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String uniqueID = UUID.randomUUID().toString();

                        databaseReference.child("Orders").child(logini).child(uniqueID).child("product").setValue(producttxt);
                        databaseReference.child("Orders").child(logini).child(uniqueID).child("weight").setValue(weighttxt);
                        databaseReference.child("Orders").child(logini).child(uniqueID).child("hieght").setValue(heighttxt);
                        databaseReference.child("Orders").child(logini).child(uniqueID).child("count").setValue(counttxt);
                        databaseReference.child("Orders").child(logini).child(uniqueID).child("addition").setValue(additiontxt);

                        Toast.makeText(Make_Order.this, "Buyurma berish yakunlandi!!!", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });


            }
        });

    }
}