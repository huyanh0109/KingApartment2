package com.example.datn.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    public static final String BASE_URL = "https://kingapartment.herokuapp.com/api/";
    public static final String BASE_URL_v1 = "https://api.mocki.io/v2/3046575b/";
    public static final String BASE_URL_v2 = "https://63ecfe62be929df00cb6cc8b.mockapi.io/api/v1/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL_v1).addConverterFactory
                    (GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
    public static Retrofit getClientv2() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL_v2).addConverterFactory
                    (GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
