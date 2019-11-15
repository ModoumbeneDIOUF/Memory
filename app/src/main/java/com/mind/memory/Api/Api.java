package com.mind.memory.Api;

import com.mind.memory.Model.LoginResponse;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
    String REGIURL = "http://192.168.43.216/back/public/api";

    @FormUrlEncoded
    @POST("utilisateurs")
    Call<String> createUser(
            @Field("prenom")String prenom,
            @Field("nom")String nom,
            @Field("adresse")String adresse,
            @Field("profil")String profil,
            @Field("numero")String numero,
            @Field("password")String password

            );

    @FormUrlEncoded
    @POST("user")
    Call<LoginResponse> userLogin(
            @Field("numero") String numero,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("renew")
    Call<LoginResponse> userReenw(
            @Field("numero") String numero,
            @Field("password") String password

    );
}
