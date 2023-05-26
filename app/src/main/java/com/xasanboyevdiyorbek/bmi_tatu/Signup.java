package com.xasanboyevdiyorbek.bmi_tatu;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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

import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signup extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    TextInputEditText ismi, login, email, parol,phone;
    TextView t1, t2;
    Uri filepath;
    Bitmap bitmap;
    Button singup, qaytish;
    SignInButton googlebtn;
    ProgressBar progressBar;
    ImageView img;
    private FirebaseAuth mAuth;



    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://reklama-uchun-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ismi = findViewById(R.id.ismmm);
        login = findViewById(R.id.login);
        email = findViewById(R.id.emailR);
        parol = findViewById(R.id.parolR);
        phone = findViewById(R.id.PhoneR);
        img = findViewById(R.id.iconloginuchun);
        t1 = findViewById(R.id.logoname);
        t2 = findViewById(R.id.kirishuchun);

        singup = findViewById(R.id.signup);

        qaytish = findViewById(R.id.qaytish);

        progressBar = findViewById(R.id.proreesbar);


        mAuth = FirebaseAuth.getInstance();



        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String fullname = ismi.getText().toString();
                final String loginni = login.getText().toString();
                final String emaili = email.getText().toString();
                final String phonei = phone.getText().toString();
                final String paroli = parol.getText().toString();
                try {
                    if (emaili.isEmpty()) {
                        email.setError("Iltimos pochtangizni kiriting");

                        email.requestFocus();
                    } else if (paroli.isEmpty()) {
                        parol.setError("Iltimos parolingizni kiriting");
                        parol.requestFocus();
                    } else if (fullname.isEmpty()) {
                        ismi.setError("Iltimos ismingizni kiriting");
                        ismi.requestFocus();

                    } else if (loginni.isEmpty()) {
                        login.setError("Iltimos loginingizni kiriting");
                        login.requestFocus();

                    }else if (phonei.isEmpty()) {
                        login.setError("Iltimos telingizni kiriting");
                        login.requestFocus();

                    } else

                        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {


                                if (snapshot.hasChild(loginni)) {
                                    Toast.makeText(Signup.this, "Bu login oldin ro`yhatdan o`tgan", Toast.LENGTH_SHORT).show();

                                } else {
                                    ProgressDialog dialog=new ProgressDialog(Signup.this);
                                    dialog.setTitle("Malumotlar serverga yuklanyapdi!");
                                    dialog.show();

                                    dialog.dismiss();
                                    databaseReference.child("users").child(loginni).child("ismi").setValue(fullname);
                                    databaseReference.child("users").child(loginni).child("email").setValue(emaili);
                                    databaseReference.child("users").child(loginni).child("phone").setValue(phonei);
                                    databaseReference.child("users").child(loginni).child("login").setValue(loginni);
                                    databaseReference.child("users").child(loginni).child("parol").setValue(paroli);
//                                    databaseReference.child("users").child(loginni).child("image").setValue(uri.toString());

                                    Toast.makeText(Signup.this, "Ro`yhatdan o`tish muaffaqiyatli yakunlandi", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(Signup.this, Signin.class);
                                    startActivity(intent);
                                    finish();
//
//                                    FirebaseStorage storage=FirebaseStorage.getInstance();
//                                    StorageReference uploder=storage.getReference("Image1"+new Random().nextInt(500));


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                } catch (Exception exception) {
                    Toast.makeText(Signup.this, "Xatolik yuzberdi!" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        });
//        googlebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signIn();
//            }
//        });


        qaytish.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, Signin.class);
                Pair[] pairs = new Pair[3];
                pairs[0] = new Pair<View, String>(img, "logo_image");
                pairs[1] = new Pair<View, String>(t1, "logo_text");
                pairs[2] = new Pair<View, String>(t2, "logotext2");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Signup.this, pairs);
                startActivity(intent, options.toBundle());


            }
        });





    }


}