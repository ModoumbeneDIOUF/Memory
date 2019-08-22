package com.mind.memory.Model;

import com.mind.memory.R;

import java.util.ArrayList;

public class DetailVente {

    public static ArrayList<ListVente> getListVente(){
        ArrayList<ListVente> produitVendu  = new ArrayList<>();
        produitVendu.add(new ListVente(R.drawable.repas,"Quantite:2kg","Prix:2500fr","Expiration:31/09/2019"));
        produitVendu.add(new ListVente(R.drawable.repas,"Quantite:2kg","Prix:2500fr","Expiration:31/09/2019"));
        produitVendu.add(new ListVente(R.drawable.repas,"Quantite:2kg","Prix:2500fr","Expiration:31/09/2019"));
        produitVendu.add(new ListVente(R.drawable.repas,"Quantite:2kg","Prix:2500fr","Expiration:31/09/2019"));
        produitVendu.add(new ListVente(R.drawable.repas,"Quantite:2kg","Prix:3000fr","Expiration:31/09/2019"));

        return  produitVendu;
    }
}
