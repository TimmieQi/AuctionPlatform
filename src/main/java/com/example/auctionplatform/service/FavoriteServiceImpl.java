package com.example.auctionplatform.service;

import com.example.auctionplatform.converter.FavoriteConverter;
import com.example.auctionplatform.dao.Favorite;
import com.example.auctionplatform.dao.FavoriteRepository;
import com.example.auctionplatform.dto.FavoriteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 功能：
 * 作者：万礼阳
 * 日期：2024/7/19 上午8:37
 */
@Service
public class FavoriteServiceImpl {
    @Autowired
    public FavoriteServiceImpl(FavoriteRepository favoriteRepository) {
        this.favoriteRepository=favoriteRepository;
    }
    private final FavoriteRepository favoriteRepository;

    public FavoriteRepository getFavoriteRepositoryById(int id) {
        Optional<Favorite> favorite=favoriteRepository.findById(id);
        if(favorite.isPresent()) {
            return favoriteRepository;
        }
        return null;
    }
    public List<Favorite> getAllFavorites() {
        return favoriteRepository.findAll();
    }
    public String deleteFavoriteById(int id){
        Optional<Favorite> favorite=favoriteRepository.findById(id);
        if(favorite.isPresent()) {
            favoriteRepository.deleteById(id);
            return "Successfully Favorite deleted";
        }
        return "No such address";
    }
    public String addFavorite(FavoriteDTO newfavoriteDTO){
        List<Favorite> favorite_uid=favoriteRepository.findByUserId(newfavoriteDTO.getUserId());
        if(!favorite_uid.isEmpty()) {
            for(var favorite:favorite_uid) {
            if(favorite.getId()==newfavoriteDTO.getItemId()) {
                return "Favorite already exists";
            }
        }
        }
        Favorite favorite= FavoriteConverter.convertFavoriteDTO(newfavoriteDTO);
        favoriteRepository.save(favorite);
        return "Successfully Added Favorite";
    }
}