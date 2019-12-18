package com.mind.memory.Model;

public class Responsable {
    String prenom,nom,adresse,phone;

    public Responsable(String prenom,String nom,String adresse,String phone){
        this.prenom = prenom;
        this.nom = nom;
        this.adresse = adresse;
        this.phone = phone;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }


    public String getPhone() {
        return phone;
    }

}
