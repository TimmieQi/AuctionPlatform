package com.example.auctionplatform.controller.FavoriteController;

import com.example.auctionplatform.dto.FavoriteDTO;
import com.example.auctionplatform.service.FavoriteService;
import com.example.auctionplatform.service.JWTService;
import com.example.auctionplatform.service.Response;
import com.example.auctionplatform.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：添加一个收藏
 * 作者：万礼阳
 * 日期：2024/7/21 下午2:05
 */
@RestController
@RequestMapping("/api/favorite")
public class AddFavoriteController {

    private final FavoriteService favoriteService;
    private final UserService userService;
    @Autowired
    public AddFavoriteController(FavoriteService favoriteService, UserService userService) {
        this.favoriteService = favoriteService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public Response<Void> addFavorite(@RequestBody FavoriteDTO favoriteDTO, @RequestHeader(name ="Authorization") String token,
                                      HttpServletResponse httpServletResponse) {
        try{
            JWTService.parseToken(token,userService.getSecret());
            return favoriteService.addFavorite(favoriteDTO);
        }catch (Exception e) {
            httpServletResponse.setStatus(401);
            return Response.newErrorWithEmptyReturn("添加收藏失败");
        }

    }
}
