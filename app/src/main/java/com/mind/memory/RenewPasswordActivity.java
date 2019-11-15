package com.mind.memory;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mind.memory.Model.LoginResponse;
import com.mind.memory.Retrof.RetrofitRegister;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RenewPasswordActivity extends AppCompatActivity {
    private EditText forgetPhone,forgetPassword,forgetPasswordConf;
    private Button forgetBtn;
    String phone,password,passwordConf;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renew_password);

        forgetPhone = findViewById(R.id.forget_txt_phone_login);
        forgetPassword = findViewById(R.id.forget_password_login);
        forgetPasswordConf = findViewById(R.id.forget_password_login_conf);
        forgetBtn = findViewById(R.id.forget_btnLogin);
        loadingBar =  new ProgressDialog(this);

        forgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RenewPassword();
            }
        });

    }

    private void RenewPassword() {
        phone = forgetPhone.getText().toString();
        password = forgetPassword.getText().toString();
        passwordConf = forgetPasswordConf.getText().toString();
        
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password) ||TextUtils.isEmpty(passwordConf)){
            Toast.makeText(this, "Tous les champs sont requis", Toast.LENGTH_SHORT).show();
        }
        else if(!(password.equals(passwordConf))){
            Toast.makeText(this, "Les deux mots de passe ne sont pas les mêmes", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setMessage("Veillez patienter un instant nous traitons votre demande");
            loadingBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            loadingBar.setMax(100);
            loadingBar.getMax();
            loadingBar.getProgress();
            loadingBar.incrementProgressBy(2);
            loadingBar.setCancelable(false);
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            SaveNewPassword(phone,password);
            Clean();
        }
    }


    private void SaveNewPassword(final String phone, final String password) {
        Call<LoginResponse> call = RetrofitRegister
                                    .getInstance()
                                    .getApi().userReenw(phone,password);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginResponse loginResponse = response.body();
                    if (!loginResponse.isError()){
                        loadingBar.dismiss();
                        Toast.makeText(RenewPasswordActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        loadingBar.dismiss();
                        Toast.makeText(RenewPasswordActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable throwable) {

                }
            });
           }

    private void Clean() {
        forgetPassword.setText("");
        forgetPasswordConf.setText("");
        forgetPhone.setText("");
    }
}
