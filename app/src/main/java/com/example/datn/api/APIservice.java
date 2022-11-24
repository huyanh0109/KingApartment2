package com.example.datn.api;

import com.example.datn.model.Account;
import com.example.datn.model.AccountUser;
import com.example.datn.model.ApartmentPopular;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIservice {
    @GET("apartment/popular")
    Call<ApartmentPopular> getServerData();

    @POST("login")
    Call<Account> postAccountData(@Body Account account);
}
