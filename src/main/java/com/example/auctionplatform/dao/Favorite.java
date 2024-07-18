package com.example.auctionplatform.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Favorites")
public class Favorite {
    @Id
    @Column(name ="id")
    private int id;
    @Column(name ="user_id")
    private int userId;
    @Column(name ="item_id")
    private int itemId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int user_Id) {
        this.userId = user_Id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int item_Id) {
        this.itemId = item_Id;
    }
}
