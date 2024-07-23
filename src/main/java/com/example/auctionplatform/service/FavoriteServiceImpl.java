package com.example.auctionplatform.service;

import com.example.auctionplatform.converter.FavoriteConverter;
import com.example.auctionplatform.dao.Favorite;
import com.example.auctionplatform.dao.FavoriteRepository;
import com.example.auctionplatform.dto.FavoriteDTO;
import com.example.auctionplatform.logger.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/*
 已重构
 YCX
 */

/**
 * 功能：
 * 作者：万礼阳
 * 日期：2024/7/19 上午8:37
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    public FavoriteServiceImpl(FavoriteRepository favoriteRepository) {
        this.favoriteRepository=favoriteRepository;
    }
    private final FavoriteRepository favoriteRepository;
    @Override
    public Response<List<FavoriteDTO>> getFavoritesByUserId(int userId) {
        try{
            List<Favorite> favorites = favoriteRepository.findByUserId(userId);
            if(favorites.isEmpty()){
                return Response.newErrorWithEmptyReturn("No favorites found");
            }
            return Response.newSuccess(FavoriteConverter.convertFavorites(favorites),"Favorites found");
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage()+"An error occurred while getting favorites.\n");
            return Response.newErrorWithEmptyReturn("An error occurred while getting favorites.\n");
        }
    }
    public Response<List<FavoriteDTO>> getAllFavorites() {
        try{
            List<Favorite> favorites = favoriteRepository.findAll();
            if(favorites.isEmpty()){
                return Response.newErrorWithEmptyReturn("No favorites found");
            }
            return Response.newSuccess(FavoriteConverter.convertFavorites(favorites),"Favorites found");
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage()+"An error occurred while getting favorites.\n");
            return Response.newErrorWithEmptyReturn("An error occurred while getting favorites.\n");
        }
    }
    public Response<Void> deleteFavoriteById(int id){
        try{
            Optional<Favorite> optionalFavorite=favoriteRepository.findById(id);
            if(optionalFavorite.isPresent()) {
                favoriteRepository.delete(optionalFavorite.get());
                return Response.newSuccess(null,"Favorite deleted.\n");
            }
            return Response.newError("No favorites found");
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage()+"An error occurred while deleting favorite with id "+id+".\n");
            return Response.newError("An error occurred while deleting favorite with id "+id+".\n");
        }
    }
    public Response<FavoriteDTO> addFavorite(FavoriteDTO newfavoriteDTO){
        try {
            List<Favorite> favorite_uid = favoriteRepository.findByUserId(newfavoriteDTO.getUserId());
            if (!favorite_uid.isEmpty()) {
                for (var favorite : favorite_uid) {
                    if (favorite.getItemId() == newfavoriteDTO.getItemId()) {
                        return Response.newErrorWithEmptyReturn("Favorite already exists");
                    }
                }
            }
            Favorite favorite = new Favorite();
            favorite.setUserId(newfavoriteDTO.getUserId());
            favorite.setItemId(newfavoriteDTO.getItemId());
            favorite = favoriteRepository.save(favorite);

            return Response.newSuccess(FavoriteConverter.convertFavorite(favorite),"Favorite added.");
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage() + "An error occurred while adding favorite.\n");
            return Response.newErrorWithEmptyReturn("An error occurred while adding favorite.\n");
        }
    }

    @Override
    public Response<FavoriteDTO> getFavoriteById(int id) {
        try {
            Optional<Favorite> optionalFavorite=favoriteRepository.findById(id);
            return optionalFavorite.map(favorite
                    -> Response.newSuccess(FavoriteConverter.convertFavorite(favorite), "Favorite found."))
                    .orElseGet(() -> Response.newErrorWithEmptyReturn("No favorites found"));
        }catch (Exception e){
            e.fillInStackTrace();
            LogManager.LogOtherError(e.getMessage()+"An error occurred while getting favorite with id "+id+".\n");
            return Response.newErrorWithEmptyReturn("An error occurred while getting favorite with id "+id+".\n");
        }
    }

}

