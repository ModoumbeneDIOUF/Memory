package com.mind.memory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                Intent intent =  new Intent(MainActivity.this,NourritureOffertActivity.class);
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
