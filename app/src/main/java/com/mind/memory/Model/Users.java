package com.mind.memory.Model;

public class Users {
    private String prenom,nom,adresse,profil,numero,password;

    public Users() {
    }

    public Users(String prenom, String nom, String adresse, String profil, String numero, String password) {
        this.prenom = prenom;
        this.nom = nom;
        this.adresse = adresse;
        this.profil = profil;
        this.numero = numero;
        this.password = password;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
