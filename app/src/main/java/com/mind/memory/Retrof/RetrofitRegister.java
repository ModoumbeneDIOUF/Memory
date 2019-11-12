package com.mind.memory.Retrof;

import com.mind.memory.Api.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRegister {
    private static final String BASE_URL = "http://192.168.43.216/back/public/api/";
    private static RetrofitRegister mInstance;
    private Retrofit retrofit;

    private RetrofitRegister(){
        retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
    }

    public static synchronized RetrofitRegister getInstance(){
        if (mInstance == null){
            mInstance = new RetrofitRegister();
        }
        return mInstance;
    }
    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
