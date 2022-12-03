package com.example.datn.api;

import com.example.datn.model.Account;
import com.example.datn.model.AccountUser;
import com.example.datn.model.ApartmentPopular;
import com.example.datn.model.Message;
import com.example.datn.model.ResultApartment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIservice {
    @GET("apartment/popular")
    Call<ApartmentPopular> getServerDataApartmentPopular();

    @GET("apartment/near-you")
    Call<ApartmentPopular> getServerDataApartmentNearYou(@Query("longitude") Double longitude,
                                                         @Query("latitude") Double latitude);

    @Headers({"Content-Type: application/json"})
    @POST("login")
    Call<Account> postAccountData(@Body AccountUser account);
    @FormUrlEncoded
    @POST("apartment/favorite/add")
    Call<List<Message>> postToAddWishListData(@Field("email") String email, @Field("idApartment") String idApartment);
    @FormUrlEncoded
    @POST("apartment/favorite")
    Call<List<ResultApartment>> postToCallWishListData(@Field("email") String email);
}
