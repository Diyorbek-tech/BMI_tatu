package com.xasanboyevdiyorbek.bmi_tatu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class Splash_Screen extends AppCompatActivity {
    TextView txt1, txt2;
    LottieAnimationView lottie1;
    Animation a1, a2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        //Animations
        a1 = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        a2 = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        //elon

        lottie1 = findViewById(R.id.lottieanim1);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);

        //animm berish

        lottie1.setAnimation(a1);
        txt1.setAnimation(a2);
        txt2.setAnimation(a2);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash_Screen.this, Signin.class);
                startActivity(i);
                finish();
            }
        }, 5000);

    }
}