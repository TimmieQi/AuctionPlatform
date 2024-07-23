package com.example.auctionplatform.dao;

import jakarta.persistence.*;

@Entity
@Table(name ="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "sale_id")
    private int saleId;
    @Column(name ="buy_id")
    private int buyId;
    @Column(name ="item_id")
    private int itemId;
    @Column(name ="price")
    private double price;
    @Column(name ="sale_add")
    private String saleAdd;
    @Column(name ="buy_add")
    private String buyAdd;
    @Column(name ="isReceived")
    private boolean isReceived;
    /**
     * 物流信息
     */
    @Column(name ="message")
    private String message;

    @Column(name ="isPaid")
    private boolean isPayed;

    public boolean isPayed() {
        return isPayed;
    }

    public void setPayed(boolean payed) {
        isPayed = payed;
    }

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
