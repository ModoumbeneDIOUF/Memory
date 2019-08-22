package com.mind.memory.Model;

public class ListVente {

    private int imageVented;
    private String quantiteVendu,prixVente,jourRestant;

    public ListVente() {
    }

    public ListVente(int imageVented, String quantiteVendu, String prixVente, String jourRestant) {
        this.imageVented = imageVented;
        this.quantiteVendu = quantiteVendu;
        this.prixVente = prixVente;
        this.jourRestant = jourRestant;
    }

    public int getImageVented() {
        return imageVented;
    }

    public void setImageVented(int imageVented) {
        this.imageVented = imageVented;
    }

    public String getQuantiteVendu() {
        return quantiteVendu;
    }

    public void setQuantiteVendu(String quantiteVendu) {
        this.quantiteVendu = quantiteVendu;
    }

    public String getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(String prixVente) {
        this.prixVente = prixVente;
    }

    public String getJourRestant() {
        return jourRestant;
    }

    public void setJourRestant(String jourRestant) {
        this.jourRestant = jourRestant;
    }
}
