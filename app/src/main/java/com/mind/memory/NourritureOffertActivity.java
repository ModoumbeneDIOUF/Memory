package com.mind.memory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.mind.memory.Adapter.ListNourritureAdapter;
import com.mind.memory.Model.ListNourritureOffert;

public class NourritureOffertActivity extends AppCompatActivity {
    private ListView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nourriture_offert);

        recyclerView = findViewById(R.id.listNourritureRecycleview);

        ListNourritureOffert listNourritureOffert =  new ListNourritureOffert("plat","riz au poisson");

        ListNourritureAdapter adapter = new ListNourritureAdapter(getApplicationContext(),R.layout.list_nourriture_added,listNourritureOffert);

        recyclerView.setAdapter(adapter);
    }
}
