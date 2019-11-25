package com.mind.memory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.mind.memory.Retrof.RetrofitNourritureOffert;

public class NourritureOffertActivity extends AppCompatActivity {
    String typeDon;


    /*adapter*/


    /*http client*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nourriture_offert);

        GridView listView = findViewById(R.id.gridNourritureOffert);
        Intent donnees = getIntent();
        typeDon = donnees.getStringExtra("typeDon");

        new RetrofitNourritureOffert(NourritureOffertActivity.this,typeDon).retrieve(listView);
    }

}
