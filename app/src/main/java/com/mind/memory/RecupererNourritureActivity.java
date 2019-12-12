package com.mind.memory;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.mind.memory.Model.ListNourritureOffert;
import com.mind.memory.Model.Url;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecupererNourritureActivity extends AppCompatActivity {
    private String randomKey,numeroVolontaire;
    private Button btnRecup,btnCancelRecp;


    //Adater
    public class RecupListView extends BaseAdapter {
        Context c;
        ArrayList<ListNourritureOffert> nourritures;
        String numVol;

        public RecupListView(Context c, ArrayList<ListNourritureOffert> nourritures,String numVol) {
            this.c = c;
            this.nourritures = nourritures;
            this.numVol = numVol;
        }

        @Override
        public int getCount() {
            return nourritures.size();
        }

        @Override
        public Object getItem(int position) {
            return nourritures.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null){
                view = LayoutInflater.from(c).inflate(R.layout.list_recupe,parent,false);
            }

            TextView descriptionOffert = view.findViewById(R.id.descRecupeper);
            TextView provenanceOffert = view.findViewById(R.id.provenaceRecuperer);
            TextView adresseNourritureOffert = view.findViewById(R.id.adresseRecupere);
            TextView numeroOffert = view.findViewById(R.id.numRecupere);
            TextView jourRestantOffert = view.findViewById(R.id.tempsRestantRecuperer);
            ImageView imageOffert = view.findViewById(R.id.imageRecueperer);
            Button btnR = view.findViewById(R.id.btn_recup);
            Button btnC = view.findViewById(R.id.btn_cancel_recup);

            final ListNourritureOffert listNourritureOffert = (ListNourritureOffert)this.getItem(position);
            descriptionOffert.setText("Libellé: "+listNourritureOffert.getDes());
            provenanceOffert.setText("Provenance: "+listNourritureOffert.getPro());
            adresseNourritureOffert.setText("Adresse: "+listNourritureOffert.getAdr());
            numeroOffert.setText("Numéro :" +listNourritureOffert.getNum());
            jourRestantOffert.setText("Jour restant: "+listNourritureOffert.getJj());

            btnR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RecupererNourritureActivity.this,ResponsableActivity.class);
                    intent.putExtra("numero",numVol);
                    startActivity(intent);
                    //Toast.makeText(c, "Bien", Toast.LENGTH_SHORT).show();
                }
            });
            btnC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(c, "Cancel", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RecupererNourritureActivity.this,NourritureOffertActivity.class);
                    intent.putExtra("typeDon",listNourritureOffert.getType());
                    startActivity(intent);
                }
            });

            if (listNourritureOffert.getImg() != null && listNourritureOffert.getImg().length() > 0){
                Picasso.get().load(listNourritureOffert.getImg()).into(imageOffert);
                //  Picasso.get().load(listNourritureOffert.getImg()).placeholder(R.drawable.load1).into(imageOffert);

            }
            else {
                Toast.makeText(c, "URL de l'mage vide", Toast.LENGTH_SHORT).show();

            }

            return view;

        }
    }

    //http client
    public class RetrofitRecup {

        private static final String data_dow_url = Url.url+"nourritureARecup";
        private final Context c;
        private RecupListView adapter;
        private String random,numVol;

        public RetrofitRecup(Context c,String random,String numVol){this.c=c;this.random=random;this.numVol=numVol;}

        /*retrieve refresh*/
        public void retrieve(final ListView listView){
            final ArrayList<ListNourritureOffert> offres = new ArrayList<>();
            AndroidNetworking.get(data_dow_url+"/"+random)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {
                            JSONObject jo;
                            ListNourritureOffert ofe;
                            try {
                                for (int i=0;i<response.length();i++){
                                    jo = response.getJSONObject(i);

                                    String d = jo.getString("descriptionNourritureOffert");
                                    String t = jo.getString("typeNourritureOffert");
                                    String p = jo.getString("provenanceNourritureOffert");
                                    String l = jo.getString("lieuNourritureOffert");
                                    String n = jo.getString("numero");
                                    String imgUrl = jo.getString("imageNourritureOffert");
                                    String j = jo.getString("jourRestant");

                                    ofe = new ListNourritureOffert(t,d,p,l,n,j,Url.uri+"back/public/images/nourritureOffert/"+imgUrl);
                                    offres.add(ofe);
                                }
                                adapter = new RecupererNourritureActivity.RecupListView(c,offres,numVol);
                                listView.setAdapter(adapter);

                            }catch (JSONException E){

                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(c, "Une erreur", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperer_nourriture);

        ListView listView = findViewById(R.id.recupLis);

        Intent intent = getIntent();
        randomKey = intent.getStringExtra("randomKey");
        numeroVolontaire = intent.getStringExtra("numero");

        new RetrofitRecup(RecupererNourritureActivity.this,randomKey,numeroVolontaire).retrieve(listView);

    }

}
