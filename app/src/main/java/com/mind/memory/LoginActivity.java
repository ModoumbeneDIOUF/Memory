package com.mind.memory;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mind.memory.Model.Users;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private ImageButton btnGoToRegister;
    private EditText phoneLogin,passwordLogin;
    private TextView forgetPassword;
    private ProgressDialog loadingBar;
    public String sendName,sendFirstName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoToRegister = findViewById(R.id.btnRegister);
        phoneLogin = findViewById(R.id.txt_phone_login);
        passwordLogin = findViewById(R.id.password_login);
        forgetPassword = findViewById(R.id.tvForgot);
        loadingBar =  new ProgressDialog(this);


        btnGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                haveInternetConnection();

            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RenewPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void haveInternetConnection() {
        NetworkInfo network =((ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (network==null || !network.isConnected())
        {
            Toast.makeText(this, "Vous n'avez pas accées à internet", Toast.LENGTH_SHORT).show();
        }
        else
        {
            LoginUser();
        }

    }


    private void LoginUser() {
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER,0,0);
        TextView tv = new TextView(LoginActivity.this);
        tv.setBackgroundColor(Color.WHITE);
        tv.setTextColor(Color.RED);
        tv.setTextSize(15);

        Typeface t = Typeface.create("serif",Typeface.BOLD_ITALIC);
        tv.setTypeface(t);
        tv.setPadding(10,10,10,10);
        tv.setText("Tous les champs sont requis");
        toast.setView(tv);
        toast.setDuration(Toast.LENGTH_LONG);

        String phone = phoneLogin.getText().toString();
        String password = passwordLogin.getText().toString();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)){

            toast.show();
        }
        else {
            loadingBar.setTitle("Connection");
            loadingBar.setMessage("Veillez patienter un instant nous traitons votre demande");
            loadingBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            loadingBar.setMax(100);
            loadingBar.getMax();
            loadingBar.getProgress();
            loadingBar.incrementProgressBy(2);
            loadingBar.setCancelable(false);
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            AccessToAccout(phone,password);
            Clean();
        }
    }


    private void AccessToAccout(final String phone,final String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(phone).exists()){
                    Users usersData = dataSnapshot.child("Users").child(phone).getValue(Users.class);
                    if (usersData.getPassword().equals(password)){
                        loadingBar.dismiss();
                        if (dataSnapshot.child("Users").child(phone).child("profil").getValue().toString().equals("Volontaire"))
                        {
                            Intent intent = new Intent(LoginActivity.this,AdminHomeActivity.class);
                            sendFirstName = dataSnapshot.child("Users").child(phone).child("prenom").getValue().toString();
                            sendName = dataSnapshot.child("Users").child(phone).child("nom").getValue().toString();
                            intent.putExtra("prenom",sendFirstName);
                            intent.putExtra("nom",sendName);
                            startActivity(intent);
                            loadingBar.dismiss();

                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.CENTER,0,0);
                            TextView tv = new TextView(LoginActivity.this);
                            tv.setBackgroundColor(Color.WHITE);
                            tv.setTextColor(Color.BLUE);
                            tv.setTextSize(15);

                            Typeface t = Typeface.create("serif",Typeface.BOLD_ITALIC);
                            tv.setTypeface(t);
                            tv.setPadding(10,10,10,10);
                            tv.setText("Bienvenue "+ dataSnapshot.child("Users").child(phone).child("prenom").getValue().toString()+" "+dataSnapshot.child("Users").child(phone).child("nom").getValue().toString());
                            toast.setView(tv);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.show();

                        }
                        else{
                            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                            sendFirstName = dataSnapshot.child("Users").child(phone).child("prenom").getValue().toString();
                            sendName = dataSnapshot.child("Users").child(phone).child("nom").getValue().toString();
                            intent.putExtra("prenom",sendFirstName);
                            intent.putExtra("nom",sendName);
                            startActivity(intent);
                            loadingBar.dismiss();

                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.CENTER,0,0);
                            TextView tv = new TextView(LoginActivity.this);
                            tv.setBackgroundColor(Color.WHITE);
                            tv.setTextColor(Color.BLUE);
                            tv.setTextSize(15);

                            Typeface t = Typeface.create("serif",Typeface.BOLD_ITALIC);
                            tv.setTypeface(t);
                            tv.setPadding(10,10,10,10);
                            tv.setText("Bienvenue "+ dataSnapshot.child("Users").child(phone).child("prenom").getValue().toString()+" "+dataSnapshot.child("Users").child(phone).child("nom").getValue().toString());
                            toast.setView(tv);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.show();

                        }
                       }
                    else {
                        loadingBar.dismiss();

                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER,0,0);
                        TextView tv = new TextView(LoginActivity.this);
                        tv.setBackgroundColor(Color.WHITE);
                        tv.setTextColor(Color.RED);
                        tv.setTextSize(15);

                        Typeface t = Typeface.create("serif",Typeface.BOLD_ITALIC);
                        tv.setTypeface(t);
                        tv.setPadding(10,10,10,10);
                        tv.setText("Mot de passe est incorrecte");
                        toast.setView(tv);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else {
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER,0,0);
                    TextView tv = new TextView(LoginActivity.this);
                    tv.setBackgroundColor(Color.WHITE);
                    tv.setTextColor(Color.RED);
                    tv.setTextSize(15);

                    Typeface t = Typeface.create("serif",Typeface.BOLD_ITALIC);
                    tv.setTypeface(t);
                    tv.setPadding(10,10,10,10);
                    tv.setText("Compte inexistant");
                    toast.setView(tv);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();

                    loadingBar.dismiss();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void Clean() {
        phoneLogin.setText("");
        passwordLogin.setText("");
    }


}
