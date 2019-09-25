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
import com.mind.memory.Model.NourritureOffer;
import com.squareup.picasso.Picasso;

public class RecupererNourritureActivity extends AppCompatActivity {
    private ImageView imageRecup;
    private TextView nomRecup,adresseRecup,jourRestantRecup,provenanceRecup;
    private String nourritureId;
    private Button btnRecupe,btnCancelRecup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperer_nourriture);

        nourritureId = getIntent().getStringExtra("nourritureId");

        imageRecup = findViewById(R.id.imageRecueperer);
        nomRecup = findViewById(R.id.typeRecuperer);
        adresseRecup = findViewById(R.id.adresseRecupere);
        provenanceRecup = findViewById(R.id.provenaceRecuperer);
        jourRestantRecup = findViewById(R.id.tempsRestantRecuperer);
        btnRecupe = findViewById(R.id.btn_recup);
        btnCancelRecup = findViewById(R.id.btn_cancel_recup);

        btnCancelRecup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecupererNourritureActivity.this,NourritureOffertActivity.class);
                startActivity(intent);
            }
        });
        btnRecupe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecupererNourritureActivity.this,ResponsableActivity.class);
                startActivity(intent);
            }
        });
       getNourritureDetails(nourritureId);
    }

    private void getNourritureDetails(String nourritureId) {
        DatabaseReference nourriturRef = FirebaseDatabase.getInstance().getReference().child("Nourriture");

        nourriturRef.child(nourritureId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    NourritureOffer nourritureOffer = dataSnapshot.getValue(NourritureOffer.class);
                    nomRecup.setText(nourritureOffer.getDescription());
                    provenanceRecup.setText(nourritureOffer.getProvenance());
                    adresseRecup.setText(nourritureOffer.getLieu());
                    Picasso.get().load(nourritureOffer.getImage()).into(imageRecup);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
