package com.example.datn.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApartmentPopular {
    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("apartments")
    @Expose
    private PagePopular pagePopulate;

    public PagePopular getListApartmentPopulate() {
        return pagePopulate;
    }

    public void setListApartmentPopulate(PagePopular pagePopulate) {
        this.pagePopulate = pagePopulate;
    }
}
