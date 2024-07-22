package com.example.auctionplatform.controller.FavoriteController;

import com.example.auctionplatform.dto.FavoriteDTO;
import com.example.auctionplatform.service.FavoriteService;
import com.example.auctionplatform.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：添加一个收藏
 * 作者：万礼阳
 * 日期：2024/7/21 下午2:05
 */
@RestController
@RequestMapping("/api/favorite")
public class addFavoriteController {

    private final FavoriteService favoriteService;

    @Autowired
    public addFavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/add")
    public Response<Void> addFavorite(@RequestBody FavoriteDTO favoriteDTO) {
        return favoriteService.addFavorite(favoriteDTO);
    }
}
