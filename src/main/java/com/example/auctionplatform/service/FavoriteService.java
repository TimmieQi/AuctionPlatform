package com.example.auctionplatform.service;


import com.example.auctionplatform.dto.FavoriteDTO;

import java.util.List;

public interface FavoriteService {

    /**
     * 这个地方判断ADD的对象是否存在
     */
    Response<FavoriteDTO> addFavorite(FavoriteDTO newfavoriteDTO);

    /**
     * 根据ID获取收藏夹
     */
    Response<FavoriteDTO> getFavoriteById(int id);

    /**
     * 获取所有收藏
     */
    Response<List<FavoriteDTO>> getAllFavorites();

    /**
     * 根据id删除收藏
     */
    Response<Void> deleteFavoriteById(int id);
    /**
     * 根据id获取其所有收藏
     */
    Response<List<FavoriteDTO>> getFavoritesByUserId(int userId);

    /**
     * 按照降序排列收藏数
     * @return
     */

}
