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
    private List<ResultPopular> resultPopulates;

    public List<ResultPopular> getApartmentResult() {
        return resultPopulates;
    }


    public List<ResultPopular> getApartmentPopulatesList() {
        return resultPopulates;
    }


    public void setApartmentPopulates(List<ResultPopular> resultPopulates) {
        this.resultPopulates = resultPopulates;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }


}