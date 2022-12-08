package com.example.datn.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.datn.model.ApartmentPopular;
import com.example.datn.model.ResultApartment;
import com.example.datn.repository.ApartmentRepository;

import java.util.List;

public class SearchViewModel extends AndroidViewModel {
    public LiveData<List<ResultApartment>> searchLiveData;
    public ApartmentRepository apartmentRepository;
    public SearchViewModel(@NonNull Application application) {
        super(application);
        apartmentRepository =  new ApartmentRepository();
        this.searchLiveData = apartmentRepository.getSearchData();
    }

    public void getSearchLiveData(String pattern){
        apartmentRepository.searchData(pattern);
    }
}
