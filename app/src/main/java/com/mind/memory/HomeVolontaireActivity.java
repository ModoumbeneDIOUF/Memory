package com.mind.memory;

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
                Toast.makeText(HomeVolontaireActivity.this, "Repas", Toast.LENGTH_SHORT).show();
            }
        });
        cardViewFruitLegume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeVolontaireActivity.this, "Fruits et legumes", Toast.LENGTH_SHORT).show();
            }
        });

        cardViewCereale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeVolontaireActivity.this, "Cereale", Toast.LENGTH_SHORT).show();
            }
        });

        cardViewVtmChess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeVolontaireActivity.this, "Vetement chessures", Toast.LENGTH_SHORT).show();
            }
        });

        cardViewArgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeVolontaireActivity.this, "Argent", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
