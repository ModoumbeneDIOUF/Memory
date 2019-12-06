package com.mind.memory;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AcceuilVontaireActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    boolean doubleBackToExitPressedOnce = false;
    Toast toast;
    private Button btn_offre,btn_vendu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil_vontaire);

        btn_offre = findViewById(R.id.main_btn_offre);
        btn_vendu = findViewById(R.id.main_btn_vendu);

        btn_offre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcceuilVontaireActivity.this,HomeVolontaireActivity.class);
                startActivity(intent);
            }
        });

        btn_vendu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AcceuilVontaireActivity.this, "Vendu", Toast.LENGTH_SHORT).show();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbaracceuilvolontaire);
        toolbar.setTitle("Acceuil");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_acceuil_volontaire);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_accueuil_volontaire);

        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_acceuil_volontaire);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        else if (doubleBackToExitPressedOnce) {
            toast.cancel();
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        toast= Toast.makeText(this, "Tapotez de nouveau pour deconnecter", Toast.LENGTH_SHORT);
        toast.show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        // if (id == R.id.action_settings) {
        //       return true;
        //  }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_offrir) {
            Intent intent = new Intent(AcceuilVontaireActivity.this,NewNourritureActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_vendre) {
            Intent intent = new Intent(AcceuilVontaireActivity.this,VendreProduitActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_dispo) {
            Intent intent = new Intent(AcceuilVontaireActivity.this,NourritureOffertActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_achat) {
            Intent intent = new Intent(AcceuilVontaireActivity.this,ProduitVenduActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(AcceuilVontaireActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

}
