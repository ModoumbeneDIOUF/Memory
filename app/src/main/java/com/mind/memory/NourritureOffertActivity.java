package com.mind.memory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mind.memory.Adapter.AdapterListNourriture;
import com.mind.memory.Model.DetailNourritureOffert;
import com.mind.memory.Model.ListNourritureOffert;

import java.util.ArrayList;

public class NourritureOffertActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<ListNourritureOffert> models;
    private AdapterListNourriture adapterListNourriture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nourriture_offert);

        listView = findViewById(R.id.listNourritureOffert);
        models = DetailNourritureOffert.getListOffert();

        adapterListNourriture = new AdapterListNourriture(NourritureOffertActivity.this,models);
        listView.setAdapter(adapterListNourriture);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListNourritureOffert listNourritureOffert = models.get(position);

                Toast.makeText(NourritureOffertActivity.this, "Position"+listNourritureOffert.getListDesc(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
