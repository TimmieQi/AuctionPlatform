package com.example.auctionplatform.controller.FavoriteController;

import com.example.auctionplatform.service.FavoriteService;
import com.example.auctionplatform.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：删除一个收藏
 * 作者：万礼阳
 * 日期：2024/7/21 下午2:05
 */
@RequestMapping("/api/Favorite")
@RestController
public class DeleteFavoriteByIdController {
    @Autowired
    public DeleteFavoriteByIdController(FavoriteService favoriteService) {
        this.favoriteService=favoriteService;
    }
    private final FavoriteService favoriteService;
    @DeleteMapping("/delete/id/{id}")
    public Response<Void> delete(@PathVariable("id") int id) {
        return favoriteService.deleteFavoriteById(id);
    }
}