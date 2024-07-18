package com.example.auctionplatform.converter;

import com.example.auctionplatform.dao.Favorite;
import com.example.auctionplatform.dto.FavoriteDTO;

public class FavoriteConverter {
    public static Favorite convertFavoriteDTO(FavoriteDTO favoriteDTO) {
        Favorite favorite = new Favorite();
        favorite.setId(favoriteDTO.getId());
        favorite.setItemId(favoriteDTO.getItemId());
        favorite.setUserId(favoriteDTO.getUserId());
        return favorite;
    }
    public static FavoriteDTO convertFavorite(Favorite favorite) {
        FavoriteDTO favoriteDTO = new FavoriteDTO();
        favoriteDTO.setId(favorite.getId());
        favoriteDTO.setItemId(favorite.getItemId());
        favoriteDTO.setUserId(favorite.getUserId());
        return favoriteDTO;
    }
}
