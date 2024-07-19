package com.example.auctionplatform.service;

import com.example.auctionplatform.dao.Favorite;
import com.example.auctionplatform.dto.FavoriteDTO;

import java.util.List;

public interface FavoriteService {
    /**
     * 这个地方判断ADD的对象是否存在
     */
    public String addFavorite(FavoriteDTO newfavoriteDTO);

    /**
     * 根据ID获取收藏夹
     */
    public Favorite getFavoriteById(int id);

    /**
     * 获取所有收藏
     */
    public List<Favorite> getAllFavorites();

    /**
     * 根据id删除收藏
     */
    public String deleteFavoriteById(int id);
    /**
     * 根据id获取其所有收藏
     */
}
