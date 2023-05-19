package com.xasanboyevdiyorbek.bmi_tatu;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;

public class Signup extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    TextInputEditText ismi, login, email, parol,phone;
    TextView t1, t2;
    Uri filepath;
    Bitmap bitmap;
    Button singup, qaytish;
    SignInButton googlebtn;
    ProgressBar progressBar;
    ImageView img, ProfileImg;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    private void RequrestGoogleSignin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }



    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 1 && resultCode == RESULT_OK) {

            filepath = data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                ProfileImg.setImageBitmap(bitmap);



            } catch (Exception e){
                Toast.makeText(Signup.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }


        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                //   Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());

                //create sharedPreference to store user data when user signs in successfully
                SharedPreferences.Editor editor = getApplicationContext()
                        .getSharedPreferences("MyPrefs", MODE_PRIVATE)
                        .edit();

                editor.putString("username", account.getDisplayName());
                editor.putString("useremail", account.getEmail());
                editor.putString("userPhoto", account.getPhotoUrl().toString());
                editor.apply();


            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                //  Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(Signup.this, "Xatolik yuz berdi" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
    }
// Agar oldin ro`yxatdan o`tgan bo`lsa signup oynasi chiqmasdan kirib ketadi
//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser user = mAuth.getCurrentUser();
//        if (user != null) {
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//        }
//    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(Signup.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Signup.this, "Xatolik yuz berdi", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://bmi-tatu-default-rtdb.firebaseio.com/");

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

//        googlebtn = findViewById(R.id.signinwithgoogle);

        mAuth = FirebaseAuth.getInstance();
        RequrestGoogleSignin();



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