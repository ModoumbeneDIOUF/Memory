package com.mind.memory.Model;

public class    ListVente {

    private String quantiteVendu,prixVente,dateExpiration,produitVenduId,imageVented,produitRandomKey;

    public ListVente() {
    }

    public ListVente(String quantiteVendu, String prixVente, String dateExpiration,String produitVenduId,String imageVented,String produitRandomKey) {
        this.imageVented = imageVented;
        this.quantiteVendu = quantiteVendu;
        this.prixVente = prixVente;
        this.dateExpiration = dateExpiration;
        this.produitVenduId = produitVenduId;
        this.produitRandomKey = produitRandomKey;

    }

    public String getProduitRandomKey() {
        return produitRandomKey;
    }

    public void setProduitRandomKey(String produitRandomKey) {
        this.produitRandomKey = produitRandomKey;
    }

    public String getProduitVenduId() {
        return produitVenduId;
    }

    public void setProduitVenduId(String produitVenduId) {
        this.produitVenduId = produitVenduId;
    }

    public String getImageVented() {
        return imageVented;
    }

    public void setImageVented(String imageVented) {
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

    public String getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
    }
}
