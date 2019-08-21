package com.mind.memory.Model;

import com.mind.memory.R;

import java.util.ArrayList;

public class DetailNourritureOffert {

    public static ArrayList<ListNourritureOffert> getListOffert(){
        ArrayList<ListNourritureOffert> nourriture = new ArrayList<>();
        nourriture.add(new ListNourritureOffert(R.drawable.repas,"Yassa au poulet","3 jours"));
        nourriture.add(new ListNourritureOffert(R.drawable.repas,"Yassa au poulet","3 jours"));
        nourriture.add(new ListNourritureOffert(R.drawable.repas,"Yassa au poulet","3 jours"));
        nourriture.add(new ListNourritureOffert(R.drawable.repas,"Yassa au poulet","3 jours"));

        return nourriture;
    }
}
