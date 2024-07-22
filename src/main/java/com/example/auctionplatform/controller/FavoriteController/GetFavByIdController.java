package com.example.auctionplatform.controller.FavoriteController;

import com.example.auctionplatform.dto.FavoriteDTO;
import com.example.auctionplatform.service.FavoriteService;
import com.example.auctionplatform.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：获取收藏一个商品的信息
 * 作者：万礼阳
 * 日期：2024/7/21 下午2:13
 */
@RestController
@RequestMapping("/api/Favorite")
public class GetFavByIdController {
    @Autowired
    public GetFavByIdController(FavoriteService favoriteService) {
        this.favoriteService=favoriteService;
    }
    private final FavoriteService  favoriteService;
    @GetMapping("/fromId/id/{id}")
    public Response<FavoriteDTO> getFavById(@PathVariable("id") int id) {
    return favoriteService.getFavoriteById(id);
}
}