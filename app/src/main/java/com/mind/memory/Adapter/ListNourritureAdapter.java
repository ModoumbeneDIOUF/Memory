package com.mind.memory.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mind.memory.Model.ListNourritureOffert;
import com.mind.memory.R;

import java.util.ArrayList;

public class ListNourritureAdapter extends ArrayAdapter<ListNourritureOffert> {
    private  Context context;
    private  int resource;

    public ListNourritureAdapter(Context context, int resource, ArrayList<ListNourritureOffert> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String t =getItem(position).getListType();
        String d = getItem(position).getListDesc();

        ListNourritureOffert listN = new ListNourritureOffert(t,d);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource,parent,false);

        TextView tt = convertView.findViewById(R.id.listTypeNourriture);
        TextView dd = convertView.findViewById(R.id.listDescription);


        return  convertView;
    }
}
