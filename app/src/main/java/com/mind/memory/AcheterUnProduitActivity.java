package com.mind.memory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class AcheterUnProduitActivity extends AppCompatActivity {
    private ImageView imageAchat;
    private TextView quantiteAchat,prixAchat,expirationAchat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acheter_un_produit);

        imageAchat = findViewById(R.id.imageAcheter);
        quantiteAchat = findViewById(R.id.quantiteAAcheter);
        prixAchat = findViewById(R.id.prixAAcheter);
        expirationAchat = findViewById(R.id.dateExpirationAcheter);

        Intent intent = getIntent();
        int idImageAchat = intent.getIntExtra("imageAchat",1);
        String quantite = intent.getStringExtra("quantiteAchat");
        String prix = intent.getStringExtra("prixAchat");
        String expiration = intent.getStringExtra("dateExpiration");

        imageAchat.setImageResource(idImageAchat);
        quantiteAchat.setText(quantite);
        prixAchat.setText(prix);
        expirationAchat.setText(expiration);
    }
}
