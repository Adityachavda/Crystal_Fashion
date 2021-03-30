package com.example.crystalfashion.API_Client;

import com.example.crystalfashion.API_Interface.API;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API_Client {

    public static final String BASE_URL = "https://incomparable-vector.000webhostapp.com/Crystal_Fashion/";
    private static Retrofit retrofit;
    public static API_Client mInstance;


    private API_Client() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized API_Client getInstance() {
        if (mInstance == null) {
            mInstance = new API_Client();
        }
        return mInstance;
    }

    public API getApi() {
        return retrofit.create(API.class);
    }
}
