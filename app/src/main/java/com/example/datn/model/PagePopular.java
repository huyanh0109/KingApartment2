package com.example.datn.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PagePopular {
    @SerializedName("totalPage")
    @Expose
    private Integer totalPage;
    @SerializedName("result")
    @Expose
    private List<ResultApartment> resultPopulates;

    public List<ResultApartment> getApartmentResult() {
        return resultPopulates;
    }


    public List<ResultApartment> getApartmentPopulatesList() {
        return resultPopulates;
    }


    public void setApartmentPopulates(List<ResultApartment> resultPopulates) {
        this.resultPopulates = resultPopulates;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }


}