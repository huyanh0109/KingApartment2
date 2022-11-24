package com.example.datn.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.datn.api.APIClient;
import com.example.datn.api.APIservice;
import com.example.datn.model.Account;
import com.example.datn.model.AccountUser;
import com.example.datn.model.ApartmentPopular;

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
        apIservice.getServerData()
                .enqueue(new Callback<ApartmentPopular>() {

                    @Override
                    public void onResponse(@NonNull Call<ApartmentPopular> call,
                                           @NonNull Response<ApartmentPopular> response) {
                        Log.i("TAG", "onResponse:" + response);
                        populateData.postValue(response.body());
                        Log.i("TAG", "datapost: " + populateData);
                    }

                    @Override
                    public void onFailure(Call<ApartmentPopular> call, Throwable t) {
                        populateData.postValue(null);
                    }
                });
        return populateData;
    }

}
