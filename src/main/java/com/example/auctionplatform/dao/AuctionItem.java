package com.example.auctionplatform.dao;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "AuctionItem")
public class AuctionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private int id;
    @Column(name ="des")
    private String description;
    @Column(name ="user_id")
    private int userId;
    @Column(name = "uploader_id")
    private int uploaderId;
    @Column(name ="image")
    private Object Image;
    @Column(name ="initPrice")
    private  double initialPrice;
    @Column(name ="currPrice")
    private double currPrice;
    @Column(name ="state")
    private short state;
    @Column(name ="uploadTime")
    private Date uploadTime;
    @Column(name ="auctionTime")
    private Date auctionTime;
    @Column(name ="favorite_sum")
    private int favoriteSum;

    public int getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(int uploaderId) {
        this.uploaderId = uploaderId;
    }

    public int getFavoriteSum() {
        return favoriteSum;
    }

    public void setFavoriteSum(int favoriteSum) {
        this.favoriteSum = favoriteSum;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public double getCurrPrice() {
        return currPrice;
    }

    public void setCurrPrice(double currPrice) {
        this.currPrice = currPrice;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
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
}
