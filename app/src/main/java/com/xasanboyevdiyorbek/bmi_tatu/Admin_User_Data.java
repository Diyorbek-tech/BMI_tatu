package com.xasanboyevdiyorbek.bmi_tatu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Admin_User_Data extends AppCompatActivity {

    TextView name, loginn, email, phoneii;
    Button tamirlashbtn, yangisibtn;
    RecyclerView recyclerView;

    private ArrayList<Orders> orders_list;
    private ArrayList<Ordertamir> orderstamir_list;
    private Admin_Orders_Adapter admin_orders_adapter;
    private Admin_Orderstamir_adapter admin_orderstamir_adapter;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://bmi-tatu-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_data);


        try {
            Intent intent = getIntent();
            String next = intent.getStringExtra("name");
            String login = intent.getStringExtra("login");
            String emails = intent.getStringExtra("email");
            String phone = intent.getStringExtra("phone");

            name = findViewById(R.id.namebaho);
            loginn = findViewById(R.id.loginii);
            email = findViewById(R.id.emailuserr);
            phoneii = findViewById(R.id.phoneii);

            tamirlashbtn = findViewById(R.id.tamirlashgabtn);
            yangisibtn = findViewById(R.id.yangisiuchunbtn);


            name.setText(next);
            loginn.setText(login);
            email.setText(emails);
            phoneii.setText(phone);


            User_info user = new User_info();

            RecyclerView recyclerView = findViewById(R.id.userorderlist);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            tamirlashbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    orderstamir_list = new ArrayList<>();

                    admin_orderstamir_adapter = new Admin_Orderstamir_adapter(Admin_User_Data.this, orderstamir_list);

                    recyclerView.setAdapter(admin_orderstamir_adapter);

                    databaseReference = FirebaseDatabase.getInstance().getReference("Orderstamir/" + login);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Ordertamir ordertamir = dataSnapshot.getValue(Ordertamir.class);
                                orderstamir_list.add(ordertamir);

                            }
                            admin_orderstamir_adapter.notifyDataSetChanged();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });
            yangisibtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    orders_list = new ArrayList<>();

                    admin_orders_adapter = new Admin_Orders_Adapter(Admin_User_Data.this, orders_list);

                    recyclerView.setAdapter(admin_orders_adapter);

                    databaseReference = FirebaseDatabase.getInstance().getReference("Orders/" + login);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Orders orders = dataSnapshot.getValue(Orders.class);
                                orders_list.add(orders);

                            }
                            admin_orders_adapter.notifyDataSetChanged();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            });


        } catch (Exception eee) {
            Toast.makeText(Admin_User_Data.this, "Admin" + eee.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
