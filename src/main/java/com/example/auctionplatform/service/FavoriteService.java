package com.example.auctionplatform.service;

import com.example.auctionplatform.dao.Favorite;
import com.example.auctionplatform.dto.FavoriteDTO;

import java.util.List;

public interface FavoriteService {
    /**
     * 这个地方判断ADD的对象是否存在
     */
    public String addFavorite(FavoriteDTO newfavoriteDTO);
    public Favorite getFavoriteById(int id);
    public List<Favorite> getAllFavorites();
    public String deleteFavoriteById(int id);
    public String updateFavoriteById(int id);
}
