package com.example.datn.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.datn.model.Message;
import com.example.datn.repository.ApartmentRepository;

import java.util.List;

public class AddWishListViewModel extends AndroidViewModel {
    LiveData<String> addwishlistLiveData;
    public ApartmentRepository apartmentRepository;
    public AddWishListViewModel(@NonNull Application application) {
        super(application);
        apartmentRepository = new ApartmentRepository();
        this.addwishlistLiveData = apartmentRepository.postToAddWishListData();
    }
    public LiveData<String> postAddWishListLiveData(String email, String idApartment){
        apartmentRepository.addWishListData(email,idApartment);
        return addwishlistLiveData;
    }
}
