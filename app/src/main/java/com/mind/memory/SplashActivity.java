package com.mind.memory;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    private static int timeOut = 4000;
    TextView welcom;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        welcom = findViewById(R.id.bienvenuTxt);
        imageView = findViewById(R.id.splashImage);

        Animation animation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.myanim);
        imageView.startAnimation(animation);
        welcom.startAnimation(animation);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },timeOut);
    }
}
