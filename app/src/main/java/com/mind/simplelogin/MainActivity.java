package com.mind.simplelogin;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btnOffrire,btnVendre,btndispo,btnAcheter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOffrire = findViewById(R.id.main_btn_newNourriture);
        btnVendre = findViewById(R.id.main_btn_vendreProduit);
        btndispo = findViewById(R.id.main_btn_nourritureDispo);
        btnAcheter = findViewById(R.id.main_btn_acheterProduit);

        //On gére les actions des buttons ici

        btnOffrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this,NewNourritureActivity.class);
                startActivity(intent);
            }
        });

        btnVendre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this,NewNourritureActivity.class);
                startActivity(intent);
            }
        });

        btndispo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this,NewNourritureActivity.class);
                startActivity(intent);
            }
        });

        btnAcheter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this,NewNourritureActivity.class);
                startActivity(intent);
            }
        });
    }

}
