package com.mind.memory.Model;

import com.mind.memory.R;

import java.util.ArrayList;

public class DetailNourritureOffert {

    public static ArrayList<ListNourritureOffert> getListOffert(){
        ArrayList<ListNourritureOffert> nourriture = new ArrayList<>();
        nourriture.add(new ListNourritureOffert(R.drawable.repas,"poulet cuit","magal de touba","Lieu:Diaxao thies","Jour restant:3 jours"));
        nourriture.add(new ListNourritureOffert(R.drawable.repas,"riz au poisson","réunion de famille","Lieu:Diaxao thies","Jour restant:3 jours"));
        nourriture.add(new ListNourritureOffert(R.drawable.repas,"fruits","Gamou","Lieu:Diaxao thies","Jour restant:3 jours"));
        nourriture.add(new ListNourritureOffert(R.drawable.repas,"baigner","mariage","Lieu:Diaxao thies","Jour restant:3 jours"));


        return nourriture;
    }
}
