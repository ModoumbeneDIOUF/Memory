package com.mind.memory.Model;

public class LoginResponse {
    private  boolean error;
    private String message;
    private  String profil,prenom,nom;

    public LoginResponse(boolean error, String message, String profil,String prenom,String nom) {
        this.error = error;
        this.message = message;
        this.profil = profil;
        this.prenom = prenom;
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getProfil() {
        return profil;
    }
}
