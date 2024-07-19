package com.example.auctionplatform.dto;

public class AddressDTO {
    public static short AUCTION_ITEM_STATE_NOT_CHANGE = -2;
    private int userId;
    private String address;
    private int addressId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
}
