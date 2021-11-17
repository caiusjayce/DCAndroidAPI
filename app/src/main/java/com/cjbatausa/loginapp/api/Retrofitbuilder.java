package com.cjbatausa.loginapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofitbuilder {
    public Retrofit retrofit;

    public Retrofitbuilder() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BaseURL.baseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();



    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }
}
