package com.mind.memory.Api;

import com.mind.memory.Model.NourritureOffer;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiNourritureOffert {

    @FormUrlEncoded
    @POST("offrirNourriture")
    Call<NourritureOffer> ajouterNourritureOffert(
            @Field("typeNourritureOffert") String type,
            @Field("descriptionNourritureOffert") String desc,
            @Field("provenanceNourritureOffert") String provenance,
            @Field("lieuNourritureOffert") String lieu,
            @Field("quantiteNourritureOffert") String quantite,
            @Field("numero") String numero,
            @Field("imageNourritureOffert") String imageNourritureOffert,
            @Field("dateAjoutNourritureOffert") String date
    );
}
