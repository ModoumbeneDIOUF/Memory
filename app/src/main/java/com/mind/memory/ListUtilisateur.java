package com.mind.memory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class ListUtilisateur extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_utilisateur);

        listeUtilisateur();
    }

    private void listeUtilisateur() {
        String url = "http://10.156.144.206/back/public/api/utilisateurs";
        Ion.with(ListUtilisateur.this)
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        try {
                                for (int i = 0;i<result.size();i++){
                                    JsonObject user = result.get(i).getAsJsonObject();
                                    Log.d("users",user.get("prenom").getAsString());

                                }
                        }catch (Exception error){
                            Toast.makeText(ListUtilisateur.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
