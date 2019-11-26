package com.mind.memory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecupererNourritureActivity extends AppCompatActivity {
    private ImageView imageRecup;
    private TextView nomRecup,adresseRecup,jourRestantRecup,provenanceRecup;
    private String randomKey;
    private Button btnRecupe,btnCancelRecup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperer_nourriture);

        Intent intent = getIntent();
        randomKey = intent.getStringExtra("randomKey");
        imageRecup = findViewById(R.id.imageRecueperer);
        nomRecup = findViewById(R.id.typeRecuperer);
        adresseRecup = findViewById(R.id.adresseRecupere);
        provenanceRecup = findViewById(R.id.provenaceRecuperer);
        jourRestantRecup = findViewById(R.id.tempsRestantRecuperer);
        btnRecupe = findViewById(R.id.btn_recup);
        btnCancelRecup = findViewById(R.id.btn_cancel_recup);

        Toast.makeText(this, randomKey, Toast.LENGTH_SHORT).show();

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
    }

}
