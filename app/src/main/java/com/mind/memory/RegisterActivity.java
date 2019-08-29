package com.mind.memory;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
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


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    private RelativeLayout rlayout;
    private Animation animation;
    private Spinner spinner;
    private Button btnRegister;
    String profilChoisit,prenom,nom,adresse,profil,phone,password,confirm;
    private EditText registerFirstName,registerName,registerAdress,registerPhone,registerPassword,registerPasswordConfirm;
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
        registerPasswordConfirm = findViewById(R.id.register_password_confirm);

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
         prenom = registerFirstName.getText().toString();
         nom = registerName.getText().toString();
         adresse = registerAdress.getText().toString();
         profil = profilChoisit;
         phone = registerPhone.getText().toString();
         password = registerPassword.getText().toString();
         confirm = registerPasswordConfirm.getText().toString();

        if (TextUtils.isEmpty(prenom)|| TextUtils.isEmpty(nom) || TextUtils.isEmpty(adresse) || TextUtils.isEmpty(phone) ||TextUtils.isEmpty(password) ||TextUtils.isEmpty(confirm)){
            Toast.makeText(this, "Veillez remplire tous les champs svp!", Toast.LENGTH_SHORT).show();
        }
        else if(!(password.equals(confirm))){
            Toast.makeText(this, "Les deux mots de passe ne pas sont pas les même", Toast.LENGTH_SHORT).show();
        }
        else if (profil.equals("vide")){
            Toast.makeText(this, "Veillez choisir un profil svp!", Toast.LENGTH_SHORT).show();
        }

        else {
            loadingBar.setTitle("Inscription");
            loadingBar.setMessage("Veillez patienter un instant nous traitons votre demande");
            loadingBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            loadingBar.setMax(100);
            loadingBar.getMax();
            loadingBar.getProgress();
            loadingBar.incrementProgressBy(10);
            loadingBar.setCancelable(false);
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            haveInternetConnection();
            Clean();
        }
    }


    private void haveInternetConnection() {
        NetworkInfo network =((ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (network==null || !network.isConnected())
        {
            loadingBar.dismiss();
            Toast.makeText(this, "Vous n'avez pas accées à internet", Toast.LENGTH_SHORT).show();
        }
        else
        {


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

                    RootRef.child("Users").child(phone).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                  if (task.isSuccessful()){
                                      Toast.makeText(RegisterActivity.this, "Votre demande a été bien traité merci..", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                      Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                      startActivity(intent);
                                  }
                                  else {
                                      Toast.makeText(RegisterActivity.this, "Erreur de connection veillez recommencer svp!!", Toast.LENGTH_SHORT).show();

                                  }
                                }
                            });
                }
                else {
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Numero déja utilise veillez choisir au autre numéro", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void Clean() {
        registerFirstName.setText("");
        registerName.setText("");
        registerAdress.setText("");
        registerPhone.setText("");
        registerPassword.setText("");
    }
}
