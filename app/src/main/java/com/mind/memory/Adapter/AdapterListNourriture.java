package com.mind.memory.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mind.memory.Model.ListNourritureOffert;
import com.mind.memory.R;

import java.util.ArrayList;

public class AdapterListNourriture extends BaseAdapter {
    private Context context;
    private ArrayList<ListNourritureOffert> models;

    public AdapterListNourriture(Context context, ArrayList<ListNourritureOffert> models) {
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
            convertView = View.inflate(context,R.layout.list_nourriture_added,null);
        }

        ImageView images = convertView.findViewById(R.id.list_nourriture_pic);
        TextView listTypeNourriture = convertView.findViewById(R.id.listTypeNourriture);
        TextView provenance = convertView.findViewById(R.id.provenanceNourriture);
        TextView tempsRestant = convertView.findViewById(R.id.tempsRestant);
        TextView lieu = convertView.findViewById(R.id.listAdresse);
        ListNourritureOffert listNourritureOffert = models.get(position);

        images.setImageResource(listNourritureOffert.getImageId());
        listTypeNourriture.setText(listNourritureOffert.getListType());
        provenance.setText(listNourritureOffert.getListProvenance());
        tempsRestant.setText(listNourritureOffert.getListDesc());
        lieu.setText(listNourritureOffert.getListLieu());

        return convertView;
    }
}
