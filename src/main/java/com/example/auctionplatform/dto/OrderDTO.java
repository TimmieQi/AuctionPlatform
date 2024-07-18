package com.example.auctionplatform.dto;

import jakarta.persistence.Column;

public class OrderDTO {
    private int id;

    private int saleId;

    private int buyId;

    private int itemId;

    private double price;

    private String saleAdd;

    private String buyAdd;

    private boolean isReceived;

    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public int getBuyId() {
        return buyId;
    }

    public void setBuyId(int buyId) {
        this.buyId = buyId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSaleAdd() {
        return saleAdd;
    }

    public void setSaleAdd(String saleAdd) {
        this.saleAdd = saleAdd;
    }

    public String getBuyAdd() {
        return buyAdd;
    }

    public void setBuyAdd(String buyAdd) {
        this.buyAdd = buyAdd;
    }

    public boolean isReceived() {
        return isReceived;
    }

    public void setReceived(boolean received) {
        isReceived = received;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
