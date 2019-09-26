package com.mind.memory;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mind.memory.Model.ListVente;
import com.squareup.picasso.Picasso;

public class AcheterUnProduitActivity extends AppCompatActivity {
    private ImageView imageAchat;
    private TextView quantiteAchat,prixAchat,expirationAchat;
    private String produitVenduId;
    private Button btnConfirm,btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acheter_un_produit);

        produitVenduId = getIntent().getStringExtra("produitVenduId");


        imageAchat = findViewById(R.id.imageAcheter);
        quantiteAchat = findViewById(R.id.quantiteAAcheter);
        prixAchat = findViewById(R.id.prixAAcheter);
        expirationAchat = findViewById(R.id.dateExpirationAcheter);
        btnConfirm = findViewById(R.id.achat_btn_confirm);
        btnCancel = findViewById(R.id.achat_btn_cancel);

        getProduitDetails(produitVenduId);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcheterUnProduitActivity.this,ResponsableActivity.class);
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcheterUnProduitActivity.this,ProduitVenduActivity.class);
                startActivity(intent);
            }
        });
    }


    private void getProduitDetails(String produitVenduId) {
        DatabaseReference produitRef = FirebaseDatabase.getInstance().getReference().child("ProduitVendu");
        produitRef.child(produitVenduId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    ListVente listVente = dataSnapshot.getValue(ListVente.class);
                    quantiteAchat.setText(listVente.getQuantiteVendu());
                    prixAchat.setText(listVente.getPrixVente());
                    expirationAchat.setText(listVente.getDateExpiration());
                    Picasso.get().load(listVente.getImageVented()).into(imageAchat);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
