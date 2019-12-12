package com.mind.memory;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.mind.memory.Model.Responsable;
import com.mind.memory.ViewHolder.ResponsableHelper;
import com.mind.memory.ViewHolder.ResponsableViewHolder;

import java.util.List;

public class ResponsableActivity extends AppCompatActivity {
    String numeroVolontaire;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsable);
        Intent intent = getIntent();
        numeroVolontaire = intent.getStringExtra("numero");
        Toast.makeText(this, numeroVolontaire, Toast.LENGTH_SHORT).show();


         }


}
