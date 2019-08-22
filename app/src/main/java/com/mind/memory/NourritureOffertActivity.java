package com.mind.memory;

import android.app.ProgressDialog;
import android.content.Intent;
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
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nourriture_offert);
        progressDialog = new ProgressDialog(NourritureOffertActivity.this);

        listView = findViewById(R.id.listNourritureOffert);
        models = DetailNourritureOffert.getListOffert();

        adapterListNourriture = new AdapterListNourriture(NourritureOffertActivity.this,models);
        listView.setAdapter(adapterListNourriture);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListNourritureOffert listNourritureOffert = models.get(position);
                progressDialog.setTitle("Chargement en cours");
                progressDialog.setMessage("Veillez patienter un instant !!!");
                Intent intent = new Intent(NourritureOffertActivity.this,RecupererNourritureActivity.class);

                int idImage = listNourritureOffert.getImageId();
                String nomNourriture = listNourritureOffert.getListType().toString();
                String adresse = listNourritureOffert.getListLieu().toString();
                String provenance = listNourritureOffert.getListProvenance().toString();
                String jourRestant = listNourritureOffert.getListDesc().toString();

                intent.putExtra("idImage",idImage);
                intent.putExtra("nomNourriture",nomNourriture);
                intent.putExtra("pronance",provenance);
                intent.putExtra("adresse",adresse);
                intent.putExtra("jourRestant",jourRestant);

                startActivity(intent);

                Toast.makeText(NourritureOffertActivity.this,listNourritureOffert.getListType(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
