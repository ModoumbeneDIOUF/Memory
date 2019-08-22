package com.mind.memory.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mind.memory.Model.ListVente;
import com.mind.memory.R;

import java.util.ArrayList;

public class AdapterListAVendre extends BaseAdapter {
    private Context context;
    private ArrayList<ListVente> models;

    public AdapterListAVendre(Context context, ArrayList<ListVente> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(context,R.layout.list_produit_a_vendre,null);
        }
        ImageView imageVente1 = convertView.findViewById(R.id.list_vente_image);
        TextView quantiteAvendre1 = convertView.findViewById(R.id.listVenteQuantite);
        TextView prixDeVente1 = convertView.findViewById(R.id.listVentePrix);
        TextView date1 = convertView.findViewById(R.id.listVenteDate);

        ListVente listVente = models.get(position);

        imageVente1.setImageResource(listVente.getImageVented());
        quantiteAvendre1.setText(listVente.getQuantiteVendu());
        prixDeVente1.setText(listVente.getPrixVente());
        date1.setText(listVente.getJourRestant());


        return convertView;
    }
}
