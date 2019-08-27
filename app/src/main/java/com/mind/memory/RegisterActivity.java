package com.mind.memory;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import   android.widget.Spinner;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private RelativeLayout rlayout;
    private Animation animation;
    private Spinner spinner;
    private Button btnRegister;
    String profilChoisit;
    private EditText registerFirstName,registerName,registerAdress,registerPhone,registerPassword;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerFirstName = findViewById(R.id.register_name);
        registerName = findViewById(R.id.register_firstname);
        registerAdress = findViewById(R.id.register_adresse);
        registerPhone = findViewById(R.id.register_telephone);
        registerPassword = findViewById(R.id.register_password);

        btnRegister = findViewById(R.id.register_btn);
        loadingBar =  new ProgressDialog(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        rlayout = findViewById(R.id.rlayout);
        animation  = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        rlayout.setAnimation(animation);
        spinner =  findViewById(R.id.register_profile);

        final List<String> profil = new ArrayList<>();
        profil.add(0,"Choisir un Profil");
        profil.add("Donneur");
        profil.add("Volontaire");
        profil.add("Vendeur");
        profil.add("Délégué de quartier");
        profil.add("Chef de village");

        ArrayAdapter<String> dataProfil;
        dataProfil = new ArrayAdapter(this,android.R.layout.simple_spinner_item,profil);
        dataProfil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataProfil);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).equals("Choisir un Profil")){
                    profilChoisit = "vide";

                }
                else {
                    profilChoisit = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CrateAccount();
                }
            });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void CrateAccount() {
        String prenom = registerFirstName.getText().toString();
        String nom = registerName.getText().toString();
        String adresse = registerAdress.getText().toString();
        String profil = profilChoisit;
        String phone = registerPhone.getText().toString();
        String password = registerPassword.getText().toString();

        if (TextUtils.isEmpty(prenom)|| TextUtils.isEmpty(nom) || TextUtils.isEmpty(adresse) || TextUtils.isEmpty(phone) ||TextUtils.isEmpty(password)){
            Toast.makeText(this, "Veillez remplire tous les champs svp!", Toast.LENGTH_SHORT).show();
        }
        else if (profil.equals("vide")){
            Toast.makeText(this, "Veillez choisir un profil svp!", Toast.LENGTH_SHORT).show();
        }

        else {
            loadingBar.setTitle("Inscription");
            loadingBar.setMessage("Veillez patienter un instant nous traitons votre demande");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatePhoneNumber(prenom,nom,adresse,profil,phone,password);
        }
    }

    private void ValidatePhoneNumber(final String prenom, final String nom, final String adresse, final String profil, final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(phone).exists())){
                    HashMap<String,Object> userDataMap = new HashMap<>();
                    userDataMap.put("phone",phone);
                    userDataMap.put("prenom",prenom);
                    userDataMap.put("nom",nom);
                    userDataMap.put("adresse",adresse);
                    userDataMap.put("profil",profil);
                    userDataMap.put("password",password);
                    Toast.makeText(RegisterActivity.this, "Bien", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
