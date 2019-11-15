package com.mind.memory.Retrof;

import com.mind.memory.Api.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitNourritureOffert {
    private static final String BASE_URL = "http://192.168.43.216/back/public/api/";
    private static Retrofit retrofit;

    public static Retrofit getApiNourritureOffert(){
        if (retrofit == null){
            retrofit = new  Retrofit.Builder().baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return  retrofit;
    }
}
