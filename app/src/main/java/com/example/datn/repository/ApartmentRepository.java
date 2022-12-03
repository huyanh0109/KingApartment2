package com.example.datn.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.datn.api.APIClient;
import com.example.datn.api.APIservice;
import com.example.datn.model.Account;
import com.example.datn.model.ApartmentPopular;
import com.example.datn.model.Message;
import com.example.datn.model.ResultApartment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApartmentRepository {

    private final APIservice apIservice;

    public ApartmentRepository() {
        apIservice = APIClient.getClient().create(APIservice.class);
    }

    public LiveData<ApartmentPopular> getApartmentPopulateData() {
        final MutableLiveData<ApartmentPopular> populateData = new MutableLiveData<>();
        apIservice.getServerDataApartmentPopular()
                .enqueue(new Callback<ApartmentPopular>() {

                    @Override
                    public void onResponse(@NonNull Call<ApartmentPopular> call,
                                           @NonNull Response<ApartmentPopular> response) {
                        populateData.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<ApartmentPopular> call, Throwable t) {
                        populateData.postValue(null);
                    }
                });
        return populateData;
    }
    final MutableLiveData<ApartmentPopular> populateData = new MutableLiveData<>();
    public LiveData<ApartmentPopular> getApartmentNearYouData() {
        return populateData;
    }
    public void sendLocation(Double longditude,Double latitude){
        apIservice.getServerDataApartmentNearYou(longditude,latitude)
                .enqueue(new Callback<ApartmentPopular>() {

                    @Override
                    public void onResponse(@NonNull Call<ApartmentPopular> call,
                                           @NonNull Response<ApartmentPopular> response) {
                        populateData.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<ApartmentPopular> call, Throwable t) {
                        populateData.postValue(null);
                    }
                });
    }
    final MutableLiveData<Account> accountUserData = new MutableLiveData<>();
    public LiveData<Account> getAccountUserData() {
        return accountUserData;
    }
    public void PostAccountUser(Account user){
        apIservice.postAccountData(user.getAccountUser())
                .enqueue(new Callback<Account>() {
                    @Override
                    public void onResponse(Call<Account> call, Response<Account> response) {
                        Account account = response.body();
                        accountUserData.postValue(account);
                    }

                    @Override
                    public void onFailure(Call<Account> call, Throwable t) {
                        accountUserData.postValue(null);
                    }
    });
    }
    final MutableLiveData<List<ResultApartment>> wishListData  = new MutableLiveData<>();
    public LiveData<List<ResultApartment>> postToCallWishListData(){
        return wishListData;
    }
    public void callWishListData(String email){
        apIservice.postToCallWishListData(email).enqueue(new Callback<List<ResultApartment>>() {
            @Override
            public void onResponse(Call<List<ResultApartment>> call, Response<List<ResultApartment>> response) {
                wishListData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<ResultApartment>> call, Throwable t) {
                wishListData.postValue(null);
            }
        });
    }
    final MutableLiveData<List<Message>> addWishListData  = new MutableLiveData<>();
    public LiveData<List<Message>> postToAddWishListData(){
        return addWishListData;
    }
    public void addWishListData(String email,String idApartment){
        apIservice.postToAddWishListData(email,idApartment).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                addWishListData.postValue(response.body());
                Log.i("TAG", "onResponse: " + addWishListData);
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                wishListData.postValue(null);
            }
        });
    }

}
