package com.mind.memory.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mind.memory.Model.ListNourritureOffert;
import com.mind.memory.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

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

        descriptionOffert.setText(listNourritureOffert.getDes());
        provenanceOffert.setText(listNourritureOffert.getPro());
        adresseNourritureOffert.setText(listNourritureOffert.getAdr());
        numeroOffert.setText(listNourritureOffert.getNum());
        jourRestantOffert.setText(listNourritureOffert.getJj());

        if (listNourritureOffert.getImg() != null && listNourritureOffert.getImg().length() > 0){
            Picasso.get().load(listNourritureOffert.getImg()).into(imageOffert);

        }
        else {
            Toast.makeText(c, "URL de l'mage vide", Toast.LENGTH_SHORT).show();

        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, listNourritureOffert.getImg(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}

