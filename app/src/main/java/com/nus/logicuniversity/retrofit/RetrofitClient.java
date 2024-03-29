package com.nus.logicuniversity.retrofit;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nus.logicuniversity.utility.JsonDateDeserializer;

import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://172.23.201.81/SSISTeam9/rest/";

    private RetrofitClient() {
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDateDeserializer()).create();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
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
