package com.example.datn.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Account {
    @SerializedName("account")
    @Expose
    private AccountUser account;

    public AccountUser getAccount() {
        return account;
    }

    public void setAccount(AccountUser account) {
        this.account = account;
    }
}
