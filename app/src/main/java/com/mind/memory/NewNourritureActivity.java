package com.mind.memory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class NewNourritureActivity extends AppCompatActivity {
    private ImageView nouritureImage;
    private EditText type,provenance,lieu,quantite;
    private static final int galleryPick=1;
    private Button btnValider;
    String typen,provenancen,lieun,quantiten;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_nourriture);

        nouritureImage = findViewById(R.id.select_food_pic);
        type = findViewById(R.id.typeAliment);
        provenance = findViewById(R.id.provenance);
        lieu = findViewById( R.id.lieu);
        quantite = findViewById(R.id.quantiteNoutiture);
        btnValider = findViewById(R.id.btnAddNourriture);

        type.addTextChangedListener(obligatoire);
        provenance.addTextChangedListener(obligatoire);

        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewNourritureActivity.this,NourritureOffertActivity.class);
                startActivity(intent);
              //  addNewFood();
            }
        });


        nouritureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

    }

    private TextWatcher obligatoire = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
           String t = type.getText().toString().trim();
           String p = provenance.getText().toString();
           String l = lieu.getText().toString();
           String q = quantite.getText().toString();

            btnValider.setEnabled(!t.isEmpty() && !p.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private void addNewFood() {
        typen = type.getText().toString();
        provenancen = provenance.getText().toString();
        lieun = lieu.getText().toString();
        quantiten = quantite.getText().toString();
    }

    private void openGallery(){
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,galleryPick);
    }
}
