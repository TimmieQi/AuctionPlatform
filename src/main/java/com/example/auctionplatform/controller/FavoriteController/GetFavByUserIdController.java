package com.example.auctionplatform.controller.FavoriteController;

import com.example.auctionplatform.dto.FavoriteDTO;
import com.example.auctionplatform.service.FavoriteService;
import com.example.auctionplatform.service.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能：获取一个用户的所有收藏
 * 作者：万礼阳
 * 日期：2024/7/21 下午2:21
 */
@RestController
@RequestMapping("/api/Favorite")
public class GetFavByUserIdController {
    @Autowired
    public GetFavByUserIdController(FavoriteService favoriteService) {
        this.favoriteService=favoriteService;
    }
    private final FavoriteService  favoriteService;
    @GetMapping("/fromId/Userid/{Userid}")
    public Response<List<FavoriteDTO>> getFavById(@PathVariable("Userid") int Userid) {
        return favoriteService.getFavoritesByUserId(Userid);
    }
}