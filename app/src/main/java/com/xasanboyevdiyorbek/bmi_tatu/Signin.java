package com.xasanboyevdiyorbek.bmi_tatu;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Signin extends AppCompatActivity {
    Button hisobyarat, go;
    ImageView image;
    TextView textView, tv2;
    TextInputEditText login, parol;

    ProgressBar progressBar;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://bmi-tatu-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signin);

        //bu yerda layout oynasidan id bo`yicha kerakli elementlar topib olingan


        login = findViewById(R.id.login1);
        parol = findViewById(R.id.parol);

        go = findViewById(R.id.go_one);

        image = findViewById(R.id.iconloginuchun);
        textView = findViewById(R.id.logoname);
        tv2 = findViewById(R.id.kirishuchun);
        hisobyarat = findViewById(R.id.hisob_yaratish);

        progressBar = findViewById(R.id.proreesbar);


        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                final String Slogini, Sparoli;

                Slogini = String.valueOf(login.getText());
                Sparoli = String.valueOf(parol.getText());

                if (Slogini.isEmpty()) {
                    login.setError("Iltimos loginingizni kiriting");
                    login.requestFocus();
                    progressBar.setVisibility(View.GONE);

                } else if (Sparoli.isEmpty()) {
                    parol.setError("Iltimos parolingizni kiriting");
                    parol.requestFocus();
                    progressBar.setVisibility(View.GONE);
                } else if (!(Slogini.isEmpty() && Sparoli.isEmpty())) {

                    if (Slogini.equals("Admin") && Sparoli.equals("Admin")) {
                        Intent intent = new Intent(Signin.this, Admin.class);
                        startActivity(intent);
                        finish();

                    } else {


                        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChild(Slogini)) {

                                    final String getpassword = snapshot.child(Slogini).child("parol").getValue(String.class);

                                    if (getpassword.equals(Sparoli)) {
                                        Toast.makeText(Signin.this, "Muaffaqiyatli amalga oshirildi", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Signin.this, MainActivity.class);
                                        User_info user_info=new User_info();
                                        user_info.setName(Slogini);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(Signin.this, "Xato parol kiritildi", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                } else {
                                    Toast.makeText(Signin.this, "Xato login kiritildi", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }


                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                } else {
                    Toast.makeText(Signin.this,
                            "Xato yuz berdi! :-( ",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        hisobyarat.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signin.this, Signup.class);
                Pair[] pairs = new Pair[3];
                pairs[0] = new Pair<View, String>(image, "logo_image");
                pairs[1] = new Pair<View, String>(textView, "logo_text");
                pairs[2] = new Pair<View, String>(tv2, "logotext2");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Signin.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });
    }
}