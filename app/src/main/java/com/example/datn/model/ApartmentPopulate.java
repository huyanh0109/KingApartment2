package com.example.datn.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApartmentPopulate {
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
    private PagePopulate pagePopulate;

    public PagePopulate getListApartmentPopulate() {
        return pagePopulate;
    }

    public void setListApartmentPopulate(PagePopulate pagePopulate) {
        this.pagePopulate = pagePopulate;
    }
}
