package com.example.datn.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.datn.api.APIClient;
import com.example.datn.api.APIservice;
import com.example.datn.model.ApartmentPopulate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApartmentRepository {

    private final APIservice apIservice;

    public ApartmentRepository() {
        apIservice = APIClient.getClient().create(APIservice.class);
    }

    public LiveData<ApartmentPopulate> getApartmentPopulateData() {
        final MutableLiveData<ApartmentPopulate> populateData = new MutableLiveData<>();
        apIservice.getServerData()
                .enqueue(new Callback<ApartmentPopulate>() {

                    @Override
                    public void onResponse(@NonNull Call<ApartmentPopulate> call,
                                           @NonNull Response<ApartmentPopulate> response) {
                        Log.i("TAG", "onResponse:" + response);
                        populateData.postValue(response.body());
                        Log.i("TAG", "datapost: " + populateData);
                    }

                    @Override
                    public void onFailure(Call<ApartmentPopulate> call, Throwable t) {
                        populateData.postValue(null);
                    }
                });
        return populateData;
    }
}
