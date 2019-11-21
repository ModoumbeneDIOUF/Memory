package com.mind.memory;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
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
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import java.util.ArrayList;
import java.util.List;

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

        if (TextUtils.isEmpty(prenom)){

            registerFirstName.setError("requis");
            Toast.makeText(this, "Tous les champs sont requis", Toast.LENGTH_LONG).show();

        }
        else if(TextUtils.isEmpty(nom)){
            registerName.setError("requis");
            Toast.makeText(this, "Tous les champs sont requis", Toast.LENGTH_LONG).show();

        }
        else if( TextUtils.isEmpty(adresse)){
            registerAdress.setError("requis");
            Toast.makeText(this, "Tous les champs sont requis", Toast.LENGTH_LONG).show();

        }
        else if( TextUtils.isEmpty(phone)){
            registerPhone.setError("requis");
            Toast.makeText(this, "Tous les champs sont requis", Toast.LENGTH_LONG).show();

        }
        else if (TextUtils.isEmpty(password)){
            registerPassword.setError("requis");
            Toast.makeText(this, "Tous les champs sont requis", Toast.LENGTH_LONG).show();
        }
        else if( TextUtils.isEmpty(confirm)){
            registerPasswordConfirm.setError("requis");
            Toast.makeText(this, "Tous les champs sont requis", Toast.LENGTH_LONG).show();
        }
        else if(!(password.equals(confirm))){
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER,0,0);
            TextView tv = new TextView(RegisterActivity.this);
            tv.setBackgroundColor(Color.WHITE);
            tv.setTextColor(Color.RED);
            tv.setTextSize(15);

            Typeface t = Typeface.create("serif",Typeface.BOLD_ITALIC);
            tv.setTypeface(t);
            tv.setPadding(10,10,10,10);
            tv.setText("Les deux mots de passe ne sont pas les mêmes");
            toast.setView(tv);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();

        }
        else if (profil.equals("vide")){
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER,0,0);
            TextView tv = new TextView(RegisterActivity.this);
            tv.setBackgroundColor(Color.WHITE);
            tv.setTextColor(Color.RED);
            tv.setTextSize(15);

            Typeface t = Typeface.create("serif",Typeface.BOLD_ITALIC);
            tv.setTypeface(t);
            tv.setPadding(10,10,10,10);
            tv.setText("Veillez choisir un profil");
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(tv);
            toast.show();

            //Toast.makeText(this, "Veillez choisir un profil svp!", Toast.LENGTH_SHORT).show();
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


            save(prenom,nom,adresse,profil,phone,password);
            loadingBar.dismiss();
            Clean();
        }
    }

    private void save(String d1,String d2,String d3,String d4,String d5,String d6) {
        String url = "http://192.168.43.216/back/public/api/utilisateurs";
        Ion.with(RegisterActivity.this)
                .load("POST",url)
                .setBodyParameter("prenom",d1)
                .setBodyParameter("nom",d2)
                .setBodyParameter("adresse",d3)
                .setBodyParameter("profil",d4)
                .setBodyParameter("numero",d5)
                .setBodyParameter("password",d6)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try{
                            String mess =result.get("inscription").getAsString();
                            if (mess.equals("ok")){
                                Toast.makeText(RegisterActivity.this, "Bien", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "Mal", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception er){
                            Toast.makeText(RegisterActivity.this, "Erreur", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void Clean() {
        registerFirstName.setText("");
        registerName.setText("");
        registerAdress.setText("");
        registerPhone.setText("");
        registerPassword.setText("");
        registerPasswordConfirm.setText("");
    }
}
