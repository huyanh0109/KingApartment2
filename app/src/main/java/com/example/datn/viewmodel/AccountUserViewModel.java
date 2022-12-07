package com.example.datn.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.datn.model.Account;
import com.example.datn.model.AccountUser;
import com.example.datn.repository.ApartmentRepository;

public class AccountUserViewModel extends AndroidViewModel {
    public LiveData<Account> liveDataAccount;
    public ApartmentRepository apartmentRepository;

    public AccountUserViewModel(@NonNull Application application) {
        super(application);
        apartmentRepository = new ApartmentRepository();
        this.liveDataAccount = apartmentRepository.getAccountUserData();
    }

    public LiveData<Account> setAccountUserLiveData(Account account) {
        apartmentRepository.PostAccountUser(account);
        return liveDataAccount;
    }
}
