package com.example.datn.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.datn.model.ResultApartment;
import com.example.datn.repository.ApartmentRepository;

import java.util.List;

public class WishListViewModel extends AndroidViewModel {
    public LiveData<List<ResultApartment>> wishListLiveData;
    public ApartmentRepository apartmentRepository;
    public WishListViewModel(@NonNull Application application) {
        super(application);
        apartmentRepository = new ApartmentRepository();
        this.wishListLiveData = apartmentRepository.postToCallWishListData();
    }
    public LiveData<List<ResultApartment>> postToCallWishListLiveData(String email){
        apartmentRepository.callWishListData(email);
        return wishListLiveData;
    }
}
