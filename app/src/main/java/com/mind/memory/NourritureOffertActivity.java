package com.mind.memory;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.mind.memory.Adapter.GriidViewdapter;
import com.mind.memory.Model.ListNourritureOffert;
import com.mind.memory.Model.Url;
import com.mind.memory.Retrof.RetrofitNourritureOffert;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NourritureOffertActivity extends AppCompatActivity {
    String typeDon;


    /*adapter*/
    public class GriidViewdapter extends BaseAdapter {
        Context c;
        ArrayList<ListNourritureOffert> nourritures ;
        public GriidViewdapter(Context c, ArrayList<ListNourritureOffert> nourritures){
            this.c = c;
            this.nourritures = nourritures;
        }

        @Override
        public int getCount(){return nourritures.size();}
        @Override
        public Object getItem(int i){return nourritures.get(i);}
        @Override
        public long getItemId(int i){return i;}
        @Override
        public View getView(int i, View view, ViewGroup viewGroup){
            if (view == null){
                view = LayoutInflater.from(c).inflate(R.layout.list_nourriture_added,viewGroup,false);
            }

            TextView descriptionOffert = view.findViewById(R.id.listDescNourritureOf);
            TextView provenanceOffert = view.findViewById(R.id.provenanceNourriture);
            TextView adresseNourritureOffert = view.findViewById(R.id.listAdresse);
            TextView numeroOffert = view.findViewById(R.id.listContact);
            TextView jourRestantOffert = view.findViewById(R.id.tempsRestant);
            ImageView imageOffert = view.findViewById(R.id.list_nourriture_pic);

            final ListNourritureOffert listNourritureOffert = (ListNourritureOffert)this.getItem(i);

            descriptionOffert.setText("Libellé: "+listNourritureOffert.getDes());
            provenanceOffert.setText("Provenance: "+listNourritureOffert.getPro());
            adresseNourritureOffert.setText("Adresse: "+listNourritureOffert.getAdr());
            numeroOffert.setText("Numéro :" +listNourritureOffert.getNum());
            jourRestantOffert.setText("Jour restant: "+listNourritureOffert.getJj());

            if (listNourritureOffert.getImg() != null && listNourritureOffert.getImg().length() > 0){
                Picasso.get().load(listNourritureOffert.getImg()).into(imageOffert);
                //  Picasso.get().load(listNourritureOffert.getImg()).placeholder(R.drawable.load1).into(imageOffert);

            }
            else {
                Toast.makeText(c, "URL de l'mage vide", Toast.LENGTH_SHORT).show();

            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(NourritureOffertActivity.this,RecupererNourritureActivity.class);
                    intent.putExtra("randomKey",listNourritureOffert.getRanomKey());
                    startActivity(intent);
                  //  Toast.makeText(c, listNourritureOffert.getImg(), Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }
    }



    /*http client*/
    public class RetrofitNourritureOffert {

        private static final String data_dow_url = Url.url+"nourritureList";
        private final Context c;
        private GriidViewdapter adapter;
        private String typeDon;

        public RetrofitNourritureOffert(Context c,String typeDon){this.c=c;this.typeDon=typeDon;}

        /*retrieve refresh*/
        public void retrieve(final ListView listView){
            final ArrayList<ListNourritureOffert> offres = new ArrayList<>();
            AndroidNetworking.get(data_dow_url+"/"+typeDon)
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
                                    String rand = jo.getString("donRandomKey");
                                    String d = jo.getString("descriptionNourritureOffert");
                                    String p = jo.getString("provenanceNourritureOffert");
                                    String l = jo.getString("lieuNourritureOffert");
                                    String q = jo.getString("quantiteNourritureOffert");
                                    String n = jo.getString("numero");
                                    String imgUrl = jo.getString("imageNourritureOffert");
                                    String j = jo.getString("jourRestant");

                                    ofe = new ListNourritureOffert(rand,d,p,l,n,Url.uri+"back/public/images/nourritureOffert/"+imgUrl,j);
                                    offres.add(ofe);
                                }
                                adapter = new GriidViewdapter(c,offres);
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
        setContentView(R.layout.activity_nourriture_offert);

        ListView listView = findViewById(R.id.gridNourritureOffert);
        Intent donnees = getIntent();
        typeDon = donnees.getStringExtra("typeDon");

        new RetrofitNourritureOffert(NourritureOffertActivity.this,typeDon).retrieve(listView);
    }

}
