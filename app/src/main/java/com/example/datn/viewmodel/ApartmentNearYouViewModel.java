package com.example.datn.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.datn.model.ApartmentPopular;
import com.example.datn.repository.ApartmentRepository;

public class ApartmentNearYouViewModel extends AndroidViewModel {
    public LiveData<ApartmentPopular> liveDataPopulate;
    public ApartmentRepository apartmentRepository;

    public ApartmentNearYouViewModel(@NonNull Application application) {
        super(application);
        apartmentRepository = new ApartmentRepository();
        this.liveDataPopulate = apartmentRepository.getApartmentNearYouData();
    }

    public LiveData<ApartmentPopular> getliveDataNearYou(Double longditude,Double latitude) {
        apartmentRepository.sendLocation(longditude,latitude);
        return liveDataPopulate;
    }
}
