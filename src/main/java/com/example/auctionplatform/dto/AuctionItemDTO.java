package com.example.auctionplatform.dto;


import java.util.Date;

public class AuctionItemDTO {
    private Integer id;
    private String description;
    private Object Image;
    private Integer userId;
    private Integer uploaderId;
    private Double initialPrice;

    private Double currPrice;

    private Short state;

    private Date uploadTime;

    private Date auctionTime;
    private Integer favoriteSum;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getImage() {
        return Image;
    }

    public void setImage(Object image) {
        Image = image;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(Integer uploaderId) {
        this.uploaderId = uploaderId;
    }

    public Double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Double getCurrPrice() {
        return currPrice;
    }

    public void setCurrPrice(Double currPrice) {
        this.currPrice = currPrice;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Date getAuctionTime() {
        return auctionTime;
    }

    public void setAuctionTime(Date auctionTime) {
        this.auctionTime = auctionTime;
    }

    public Integer getFavoriteSum() {
        return favoriteSum;
    }

    public void setFavoriteSum(Integer favoriteSum) {
        this.favoriteSum = favoriteSum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
