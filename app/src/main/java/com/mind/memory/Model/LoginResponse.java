package com.mind.memory.Model;

public class LoginResponse {
    private  boolean error;
    private String message;
    private  String profil,prenom,nom,numero;

    public LoginResponse(boolean error, String message, String profil,String prenom,String nom,String numero) {
        this.error = error;
        this.message = message;
        this.profil = profil;
        this.prenom = prenom;
        this.nom = nom;
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
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
