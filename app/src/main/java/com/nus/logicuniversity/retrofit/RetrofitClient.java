package com.nus.logicuniversity.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://10.0.2.2:60747/";

    private RetrofitClient() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static RetrofitClient getInstance() {
        if(instance == null)
            instance = new RetrofitClient();
        return instance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }

}
