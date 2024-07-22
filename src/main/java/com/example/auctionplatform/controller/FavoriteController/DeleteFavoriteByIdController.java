package com.example.auctionplatform.controller.FavoriteController;

import com.example.auctionplatform.service.FavoriteService;
import com.example.auctionplatform.service.JWTService;
import com.example.auctionplatform.service.Response;
import com.example.auctionplatform.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
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
    private final FavoriteService favoriteService;
    private final UserService userService;
    @Autowired
    public DeleteFavoriteByIdController(FavoriteService favoriteService, UserService userService) {
        this.favoriteService = favoriteService;
        this.userService = userService;
    }

    @DeleteMapping("/delete/id/{id}")
    public Response<Void> delete(@PathVariable("id") int id, @RequestHeader(name ="Authorization") String token,
                                 HttpServletResponse httpServletResponse) {
        try{
            JWTService.parseToken(token,userService.getSecret());
            return favoriteService.deleteFavoriteById(id);
        }catch (Exception e) {
            httpServletResponse.setStatus(401);
            return Response.newErrorWithEmptyReturn("删除收藏夹失败");
        }

    }
}