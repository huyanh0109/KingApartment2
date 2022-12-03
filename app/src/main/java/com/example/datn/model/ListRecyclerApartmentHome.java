package com.example.datn.model;

import java.util.List;

public class ListRecyclerApartmentHome {
    private String listTitle;
    private List<ResultApartment> listresult;
    private Integer viewType;

    public ListRecyclerApartmentHome(String listTitle, List<ResultApartment> listresult, Integer viewType) {
        this.listTitle = listTitle;
        this.listresult = listresult;
        this.viewType = viewType;
    }

    public Integer getViewType() {
        return viewType;
    }

    public void setViewType(Integer viewType) {
        this.viewType = viewType;
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public List<ResultApartment> getListresult() {
        return listresult;
    }

    public void setListresult(List<ResultApartment> listresult) {
        this.listresult = listresult;
    }
}
