package com.mind.memory;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.mind.memory.Model.ListNourritureOffert;
import com.mind.memory.Model.Responsable;
import com.mind.memory.Model.Url;
import com.mind.memory.ViewHolder.ResponsableHelper;
import com.mind.memory.ViewHolder.ResponsableViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResponsableActivity extends AppCompatActivity {
    String numeroVolontaire;

    /*adapter*/
    public  class ResponsableAdapter  extends BaseAdapter{
        Context c;
        ArrayList<Responsable> responsables;
        public ResponsableAdapter(Context c, ArrayList<Responsable> responsables){
            this.c = c;
            this.responsables = responsables;
        }
        @Override
        public int getCount() {
            return responsables.size();
        }

        @Override
        public Object getItem(int position) {
            return responsables.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                    convertView = LayoutInflater.from(c).inflate(R.layout.list_reponsable,parent,false);
            }
            TextView nomRsponsable = convertView.findViewById(R.id.nom_responsable);
            TextView adresseResponsable = convertView.findViewById(R.id.adresse_responsable);
            TextView numeroRespon = convertView.findViewById(R.id.numero_responsable);

            final Responsable responsableList = (Responsable)this.getItem(position);

            nomRsponsable.setText("Nom :"+responsableList.getPrenom()+" "+responsableList.getNom());
            adresseResponsable.setText("Adresse : "+responsableList.getAdresse());
            numeroRespon.setText("Numero : "+responsableList.getPhone());

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent dial = new Intent (Intent.ACTION_CALL, Uri.parse(responsableList.getPhone().toString()));
                    startActivity(dial);
                }
            });
            return convertView;
        }
    }

    /*Retrofit Responsable*/
    public  class RetrofitResponsable{
        private static final String data_dow_url = Url.url+"getChef";
        private Context c;
        private ResponsableAdapter adapter;

        public RetrofitResponsable(Context c){this.c=c;}

        /*retrieve refresh*/
        public void retrieve(final ListView listView){
            final ArrayList<Responsable> responsables = new ArrayList<>();
            AndroidNetworking.get(data_dow_url)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {
                            JSONObject jo;
                            Responsable resp;
                            try {
                                for (int i=0;i<response.length();i++){
                                    jo = response.getJSONObject(i);
                                    String p = jo.getString("prenom");
                                    String n = jo.getString("nom");
                                    String a = jo.getString("adresse");
                                    String num = jo.getString("numero");

                                    resp = new Responsable(p,n,a,num);
                                    responsables.add(resp);
                                }
                                adapter = new ResponsableActivity.ResponsableAdapter(c,responsables);
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
        setContentView(R.layout.activity_responsable);
        Intent intent = getIntent();
        numeroVolontaire = intent.getStringExtra("numero");

        ListView listView = findViewById(R.id.responsable_listV);
        new RetrofitResponsable(ResponsableActivity.this).retrieve(listView);

       // Toast.makeText(this, numeroVolontaire, Toast.LENGTH_SHORT).show();

         }


}
