package com.example.datn.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResultApartment implements Serializable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("sqrt")
    @Expose
    private String sqrt;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("photos")
    @Expose
    private List<String> photos = null;
    @SerializedName("priority")
    @Expose
    private Integer priority;
    @SerializedName("lng")
    @Expose
    private String longitude;
    @SerializedName("lat")
    @Expose
    private String latitude;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("isApprove")
    @Expose
    private Boolean isApprove;
    @SerializedName("contactPhoneNumber")
    @Expose
    private String contactPhoneNumber;
    @SerializedName("sumBedroom")
    @Expose
    private String sumBedroom;

    public String getSumBedroom() {
        return sumBedroom;
    }

    public void setSumBedroom(String sumBedroom) {
        this.sumBedroom = sumBedroom;
    }

    @SerializedName("sumToilet")
    @Expose
    private String sumToilet;
    @SerializedName("createBy")
    @Expose
    private String createBy;
    @SerializedName("idOwner")
    @Expose
    private String idOwner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSqrt() {
        return sqrt;
    }

    public void setSqrt(String sqrt) {
        this.sqrt = sqrt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Boolean getIsApprove() {
        return isApprove;
    }

    public void setIsApprove(Boolean isApprove) {
        this.isApprove = isApprove;
    }

    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }

    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }

    public String getSumToilet() {
        return sumToilet;
    }

    public void setSumToilet(String sumToilet) {
        this.sumToilet = sumToilet;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(String idOwner) {
        this.idOwner = idOwner;
    }

}