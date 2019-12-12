package com.mind.memory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class HomeVenduVolontaireActivity extends AppCompatActivity {
    private CardView cardViewCosmetique,cardViewFruitLegume,cardViewCereale,cardViewVtmChess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_vendu_volontaire);

        cardViewCosmetique = findViewById(R.id.cardViewVenteCosmetique);
        cardViewCereale = findViewById(R.id.cardViewVenteFruit);
        cardViewFruitLegume = findViewById(R.id.cardViewVenteCereales);
        cardViewVtmChess = findViewById(R.id.cardViewVenteVetement);

        cardViewCosmetique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeVenduVolontaireActivity.this,NourritureOffertActivity.class);
                intent.putExtra("typeVente","Cosmétiques");
                startActivity(intent);
            }
        });

        cardViewCereale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeVenduVolontaireActivity.this,NourritureOffertActivity.class);
                intent.putExtra("typeVente","Céréales");
                startActivity(intent);
            }
        });

        cardViewFruitLegume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeVenduVolontaireActivity.this,NourritureOffertActivity.class);
                intent.putExtra("typeVente","Fruits ou légumes ");
                startActivity(intent);
            }
        });

        cardViewCosmetique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeVenduVolontaireActivity.this,NourritureOffertActivity.class);
                intent.putExtra("typeVente","Vêtements ou chessures");
                startActivity(intent);
            }
        });
    }
}
