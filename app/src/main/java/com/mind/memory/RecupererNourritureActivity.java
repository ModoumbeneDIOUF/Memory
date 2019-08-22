package com.mind.memory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class RecupererNourritureActivity extends AppCompatActivity {
    private ImageView imageRecup;
    private TextView nomRecup,adresseRecup,jourRestantRecup,provenanceRecup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperer_nourriture);

        imageRecup = findViewById(R.id.imageRecueperer);
        nomRecup = findViewById(R.id.typeRecuperer);
        adresseRecup = findViewById(R.id.adresseRecupere);
        provenanceRecup = findViewById(R.id.provenaceRecuperer);
        jourRestantRecup = findViewById(R.id.tempsRestantRecuperer);

        Intent intent = getIntent();
        int idImage = intent.getIntExtra("idImage",1);
        String nomNourriture = intent.getStringExtra("nomNourriture");
        String provenace = intent.getStringExtra("pronance");
        String adresse = intent.getStringExtra("adresse");
        String jourRestant = intent.getStringExtra("jourRestant");


        imageRecup.setImageResource(idImage);
        nomRecup.setText(nomNourriture);
        provenanceRecup.setText(provenace);
        adresseRecup.setText(adresse);
        jourRestantRecup.setText(jourRestant);

    }
}
