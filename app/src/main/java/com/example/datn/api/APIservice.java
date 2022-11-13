package com.example.datn.api;

import com.example.datn.model.ApartmentPopulate;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIservice {
    @GET("apartment/popular")
    Call<ApartmentPopulate> getServerData();
}
