package com.mind.memory.Model;

public class NourritureOffer {
    private String description,lieu,numero,quantite,image,nourritureId,provenance,time,date;


    public NourritureOffer() {
    }

    public NourritureOffer(String description, String lieu, String numero, String quantite, String image, String nourritureId, String provenance, String time, String date) {
        this.description = description;
        this.lieu = lieu;
        this.numero = numero;
        this.quantite = quantite;
        this.image = image;
        this.nourritureId = nourritureId;
        this.provenance = provenance;
        this.time = time;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getQuantite() {
        return quantite;
    }

    public void setQuantite(String quantite) {
        this.quantite = quantite;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNourritureId() {
        return nourritureId;
    }

    public void setNourritureId(String nourritureId) {
        this.nourritureId = nourritureId;
    }

    public String getProvenance() {
        return provenance;
    }

    public void setProvenance(String provenance) {
        this.provenance = provenance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
