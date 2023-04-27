package com.example.app_votacao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    private final Retrofit retrofit;

    public RetrofitConfig() {
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit=new Retrofit.Builder().baseUrl("http://192.168.56.1:8080/apis/").
                addConverterFactory(GsonConverterFactory.create()).
                build();
    }
    public CNService getCEPService() {
        return this.retrofit.create(CNService.class);
    }

}
