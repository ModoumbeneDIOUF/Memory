package com.mind.memory.Retrof;

import com.mind.memory.Api.Api;
import com.mind.memory.Model.Url;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRegister {
    private static final String BASE_URL = Url.url;
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
