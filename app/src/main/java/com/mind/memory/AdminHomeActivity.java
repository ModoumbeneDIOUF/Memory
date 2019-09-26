package com.mind.memory;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdminHomeActivity extends AppCompatActivity {
    private Button btnOffert,btnVendu,btnUsers,btnLogout;
    boolean doubleBackToExitPressedOnce = false;
    Toast toast;

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        btnOffert = findViewById(R.id.admin_btn_newNourriture);
        btnVendu = findViewById(R.id.admin_btn_vendu);
        btnLogout = findViewById(R.id.admin_btn_logout);
        btnUsers = findViewById(R.id.admin_btn_users);

        btnOffert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this,AdminOffertActivity.class);
                startActivity(intent);
            }
        });

        btnVendu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this,AdminProduitVenduActivity.class);
                startActivity(intent);
            }
        });

        btnUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        toast = Toast.makeText(this, "Tapotez de nouveau pour deconnecter", Toast.LENGTH_SHORT);
        toast.show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }
}
