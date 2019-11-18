package com.mind.memory.Retrof;

import android.content.Context;
import android.widget.GridView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.mind.memory.Adapter.GriidViewdapter;
import com.mind.memory.Model.ListNourritureOffert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataRetrieve{
    private static final String data_dow_url = "http://192.168.43.216/back/nourritureOffert";
    private final Context c;
    private GriidViewdapter adapter;

    public DataRetrieve(Context c){this.c=c;}

    /*retrieve refresh*/
    public void retrieve(final GridView listView){
        final ArrayList<ListNourritureOffert> offres = new ArrayList<>();
        AndroidNetworking.get(data_dow_url)
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
                                int id = jo.getInt("id");
                                String d = jo.getString("des");
                                String p = jo.getString("pro");
                                String l = jo.getString("adr");
                                String q = jo.getString("qu");
                                String n = jo.getString("num");
                                String imgUrl = jo.getString("img");
                                String j = jo.getString("jj");

                                ofe = new ListNourritureOffert(d,p,l,n,data_dow_url+"public/images/nourritureOffert/"+imgUrl,j);
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

