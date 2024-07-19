package com.example.auctionplatform.converter;

import com.example.auctionplatform.dao.Favorite;
import com.example.auctionplatform.dto.FavoriteDTO;

import java.util.ArrayList;
import java.util.List;

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
    public static List<Favorite> convertFavoriteDTOs(List<FavoriteDTO> FavoriteDTOS) {
        List<Favorite> FavoriteList = new ArrayList<>();
        for (FavoriteDTO FavoriteDTO : FavoriteDTOS) {
            FavoriteList.add(convertFavoriteDTO(FavoriteDTO));
        }
        return FavoriteList;
    }
    public static List<FavoriteDTO> convertFavorites(List<Favorite> FavoriteS) {
        List<FavoriteDTO> FavoriteDTOList = new ArrayList<>();
        for (Favorite Favorite : FavoriteS) {
            FavoriteDTOList.add(convertFavorite(Favorite));
        }
        return FavoriteDTOList;
    }
}
