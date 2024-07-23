package com.example.auctionplatform.dto;



public class OrderDTO {
    private Integer id;

    private Integer saleId;

    private Integer buyId;

    private Integer itemId;

    private Double price;

    private String saleAdd;

    private String buyAdd;

    private Boolean isReceived;

    private String message;

    private Boolean isPayed;

    public Boolean getPayed() {
        return isPayed;
    }

    public void setPayed(Boolean payed) {
        isPayed = payed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public Integer getBuyId() {
        return buyId;
    }

    public void setBuyId(Integer buyId) {
        this.buyId = buyId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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

    public Boolean getReceived() {
        return isReceived;
    }

    public void setReceived(Boolean received) {
        isReceived = received;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
