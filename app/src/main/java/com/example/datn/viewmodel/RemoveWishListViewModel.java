package com.example.datn.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.datn.model.Message;
import com.example.datn.repository.ApartmentRepository;

import java.util.List;

public class RemoveWishListViewModel extends AndroidViewModel {
    ApartmentRepository apartmentRepository;
    LiveData<String> messageLiveData;
    public RemoveWishListViewModel(@NonNull Application application) {
        super(application);
        apartmentRepository = new ApartmentRepository();
        this.messageLiveData = apartmentRepository.postToRemoveData();
    }
    public LiveData<String> postRemoveLiveData(String email , String idApartment){
        apartmentRepository.postToRemoveLiveData(email,idApartment);
        return messageLiveData;
    }
}
