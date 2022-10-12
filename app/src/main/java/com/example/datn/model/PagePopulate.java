package com.example.datn.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PagePopulate {
    @SerializedName("totalPage")
    @Expose
    private Integer totalPage;
    @SerializedName("result")
    @Expose
    private List<ResultPopulate> resultPopulates;

    public List<ResultPopulate> getApartmentResult() {
        return resultPopulates;
    }


    public List<ResultPopulate> getApartmentPopulatesList() {
        return resultPopulates;
    }


    public void setApartmentPopulates(List<ResultPopulate> resultPopulates) {
        this.resultPopulates = resultPopulates;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }


}