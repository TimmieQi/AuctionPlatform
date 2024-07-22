package com.example.auctionplatform.dto;

import jakarta.persistence.Column;

public class FavoriteDTO {
    private Integer id;
    private Integer userId;
    private Integer itemId;
    private long count;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
