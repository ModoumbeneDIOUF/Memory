package com.mind.memory.Model;

import com.google.gson.annotations.SerializedName;

public class NourritureOffer {
    @SerializedName("typeNourritureOffert")
    private String type;
    @SerializedName("descriptionNourritureOffert")
    private String descriptionNourritureOffert;
    @SerializedName("lieuNourritureOffert")
    private String lieu;
    @SerializedName("provenanceNourritureOffert")
    private String provenance;
    @SerializedName("quantiteNourritureOffer")
    private  String quantite;
    @SerializedName("imageNorritureOffert")
    private String imageNourritureOffert;
    @SerializedName("numero")
    private String numero;
    @SerializedName("jourRestant")
    private String jourRestant;
    @SerializedName("dateAjoutNourritureOffert")
    private String dateAjoutNourritureOffert;
    @SerializedName("response")
    private String response;

    public String getResponse() {
        return response;
    }
}
