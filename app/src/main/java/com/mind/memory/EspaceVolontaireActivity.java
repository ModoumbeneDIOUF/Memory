package com.mind.memory;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.mind.memory.Model.ListDonRecupe;
import com.mind.memory.Model.ListNourritureOffert;
import com.mind.memory.Model.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EspaceVolontaireActivity extends AppCompatActivity {


    /*adapter*/
    public class RecycleEspace extends BaseAdapter{
        Context c;
        ArrayList<ListDonRecupe> donRecupes;
        String numV;
        public RecycleEspace(Context c,ArrayList<ListDonRecupe> donRecupes,String numV){
            this.c =c;
            this.donRecupes = donRecupes;
            this.numV = numV;
        }

        @Override
        public int getCount() {
            return donRecupes.size();
        }

        @Override
        public Object getItem(int position) {
            return donRecupes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = LayoutInflater.from(c).inflate(R.layout.list_espace_volontaire,parent,false);
            }
            TextView desc = convertView.findViewById(R.id.descriptionEspaceVolontaire);
            Button btnAnul = convertView.findViewById(R.id.btnCancelEspaceVolontaire);
            Button btnSup = convertView.findViewById(R.id.btndeleteEspaceVolontaire);

            final ListDonRecupe listDonRecupe = (ListDonRecupe)this.getItem(position);
            desc.setText(listDonRecupe.getDes()+" à "+listDonRecupe.getAdresse());

            btnAnul.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Mettre à jour le statut dans
                    String data_dow_url = Url.url+"updateStatusDonRecup";
                    AndroidNetworking.get(data_dow_url+"/"+listDonRecupe.getRandomKey())
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONArray(new JSONArrayRequestListener() {
                                @Override
                                public void onResponse(JSONArray response) {

                                }

                                @Override
                                public void onError(ANError anError) {

                                }
                            });
                    String data_dow_url2 = Url.url+"cancelDonRecup";
                    AndroidNetworking.delete(data_dow_url2+"/"+listDonRecupe.getRandomKey())
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONArray(new JSONArrayRequestListener() {
                                @Override
                                public void onResponse(JSONArray response) {

                                }

                                @Override
                                public void onError(ANError anError) {

                                }
                            });

                   // Toast.makeText(c, listDonRecupe.getRandomKey(), Toast.LENGTH_SHORT).show();
                }
            });
            btnSup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String data_dow_url = Url.url+"offDonRecup";
                    AndroidNetworking.get(data_dow_url+"/"+listDonRecupe.getRandomKey())
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONArray(new JSONArrayRequestListener() {
                                @Override
                                public void onResponse(JSONArray response) {

                                }

                                @Override
                                public void onError(ANError anError) {

                                }
                            });

                }
            });
            return convertView;
        }
    }

    /*http client*/
    public class NetworkingDonrecup{
        private static final String data_dow_url = Url.url+"getDonRecup";
        Context c;
        private RecycleEspace adapter;
        String numV;

        public NetworkingDonrecup(Context c,String numV){this.c=c;this.numV = numV;}

        /*retrieve refresh*/
        public void retrieve(final ListView listView){
            final ArrayList<ListDonRecupe> don = new ArrayList<>();
            AndroidNetworking.get(data_dow_url+"/"+numV)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {
                            JSONObject jo;
                            ListDonRecupe ofre;
                            try {
                                for (int i=0;i<response.length();i++){
                                    jo = response.getJSONObject(i);
                                    String d = jo.getString("description");
                                    String a = jo.getString("adresse");
                                    String rand = jo.getString("donRandomKey");

                                    ofre = new ListDonRecupe(d,a,rand);
                                    don.add(ofre);
                                }
                                    adapter = new RecycleEspace(c,don,numV);
                                listView.setAdapter(adapter);

                            }catch (JSONException E){

                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(c, "Une erreur s'est produit", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_volontaire);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarEspaceVolontaire);
        toolbar.setTitle("Espace Volontaire");


        ListView listView = findViewById(R.id.recycleViewEspaceVolontaire);


        Intent donnees = getIntent();
        String numeroVol = donnees.getStringExtra("numero");

        new NetworkingDonrecup(EspaceVolontaireActivity.this,numeroVol).retrieve(listView);


    }
}
