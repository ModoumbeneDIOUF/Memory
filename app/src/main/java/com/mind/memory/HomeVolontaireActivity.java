package com.mind.memory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

public class HomeVolontaireActivity extends AppCompatActivity {
    private CardView cardViewrepas,cardViewFruitLegume,cardViewCereale,cardViewVtmChess,cardViewArgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_volontaire);

        cardViewrepas = findViewById(R.id.cardViewrepasOffert);
        cardViewFruitLegume = findViewById(R.id.cardViewrfruitlegumeOffert);
        cardViewCereale = findViewById(R.id.cardViewcerealeOffert);
        cardViewVtmChess = findViewById(R.id.cardViewvetementchessuresOffert);
        cardViewArgent = findViewById(R.id.cardViewargentOffert);

        cardViewrepas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeVolontaireActivity.this,NourritureOffertActivity.class);
                intent.putExtra("typeDon","Plats");
                startActivity(intent);
            }
        });
        cardViewFruitLegume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeVolontaireActivity.this,NourritureOffertActivity.class);
                intent.putExtra("typeDon","Fruits ou légumes");
                startActivity(intent);

            }
        });

        cardViewCereale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeVolontaireActivity.this,NourritureOffertActivity.class);
                intent.putExtra("typeDon","Céréales");
                startActivity(intent);
            }
        });

        cardViewVtmChess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeVolontaireActivity.this,NourritureOffertActivity.class);
                intent.putExtra("typeDon","Vêtements ou chessures");
                startActivity(intent);
            }
        });

        cardViewArgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeVolontaireActivity.this,NourritureOffertActivity.class);
                intent.putExtra("typeDon","Argent");
                startActivity(intent);
            }
        });
    }
}
