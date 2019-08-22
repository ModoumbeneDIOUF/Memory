package com.mind.memory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mind.memory.Adapter.AdapterListAVendre;
import com.mind.memory.Model.DetailVente;
import com.mind.memory.Model.ListVente;

import java.util.ArrayList;

public class ProduitVenduActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<ListVente> models;
    private AdapterListAVendre adapterListAVendre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit_vendu);

        listView = findViewById(R.id.listProduitAVendre);
        models = DetailVente.getListVente();

        adapterListAVendre = new AdapterListAVendre(ProduitVenduActivity.this,models);
        listView.setAdapter(adapterListAVendre);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListVente listVente = models.get(position);
                Toast.makeText(ProduitVenduActivity.this, listVente.getPrixVente(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
