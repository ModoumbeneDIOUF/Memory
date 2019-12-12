package com.mind.memory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
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

import com.mind.memory.Model.LoginResponse;
import com.mind.memory.Retrof.RetrofitRegister;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private ImageButton btnGoToRegister;
    private EditText phoneLogin,passwordLogin;
    private TextView forgetPassword;
    private ProgressDialog loadingBar;
    boolean doubleBackToExitPressedOnce = false;
    Toast toast;


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

                LoginUser();

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

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            toast.cancel();
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        toast = Toast.makeText(this, "Tapotez de nouveau pour quitter", Toast.LENGTH_SHORT);
        toast.show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }



    private void LoginUser() {

        String phone = phoneLogin.getText().toString();
        String password = passwordLogin.getText().toString();
        if (TextUtils.isEmpty(phone)){

           phoneLogin.setError("requis");

            Toast.makeText(this, "Veillez remplir tous les champs", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(password)){
            passwordLogin.setError("requis");
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
            verif(phoneLogin.getText().toString(),passwordLogin.getText().toString());
            Clean();
        }
    }


    private void verif(String d1,String d2) {
        Call<LoginResponse> call = RetrofitRegister
                                    .getInstance()
                                    .getApi().userLogin(d1,d2);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                loadingBar.dismiss();
                LoginResponse loginResponse = response.body();
                if (!loginResponse.isError()){
                    //on test si on a un Donneur on un vendeur
                    if (loginResponse.getProfil().equals("Donneur") || loginResponse.getProfil().equals("Vendeur")){
                        //Toast.makeText(LoginActivity.this, "Vendeur ou Donneur", Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(LoginActivity.this,HomeActivity.class);
                        in.putExtra("prenom",loginResponse.getPrenom());
                        in.putExtra("nom",loginResponse.getNom());
                        startActivity(in);
                    }
                    else{
                        Intent in = new Intent(LoginActivity.this,AcceuilVontaireActivity.class);
                        in.putExtra("numero",loginResponse.getNumero());
                        startActivity(in);                    }

                }else {
                    Toast.makeText(LoginActivity.this, loginResponse.getMessage()+":Login et/ou mot de passe incorrect", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable throwable) {

            }
        });
    }

    private void Clean() {
        phoneLogin.setText("");
        passwordLogin.setText("");
    }

}
