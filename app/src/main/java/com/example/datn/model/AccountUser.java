package com.example.datn.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountUser {
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("fullname")
    @Expose
    private String fullname;


    public AccountUser(String avatar, String fullname, String email) {
        this.avatar = avatar;
        this.fullname = fullname;
        this.email = email;
    }

    @SerializedName("email")
    @Expose
    private String email;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString(){
        return "User("+ getFullname() + getEmail();
    }
}
