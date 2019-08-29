package com.mind.memory;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        final DatabaseReference RootRef,ChildRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        ChildRef = FirebaseDatabase.getInstance().getReference("Users").child(phone).child("password");

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Users").child(phone).exists()){
                    loadingBar.dismiss();
                    ChildRef.setValue(password);
                    Toast.makeText(RenewPasswordActivity.this, "bien ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(RenewPasswordActivity.this, "Compte inexistant ", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RenewPasswordActivity.this, "Veillez donner le bon numéro", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void Clean() {
        forgetPassword.setText("");
        forgetPasswordConf.setText("");
        forgetPhone.setText("");
    }
}
