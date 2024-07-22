package com.example.auctionplatform.controller.FavoriteController;

import com.example.auctionplatform.service.*;
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
@CrossOrigin
public class DeleteFavoriteByIdController {
    private final FavoriteService favoriteService;
    private final UserService userService;
    private final AuctionItemService auctionItemService;
    @Autowired
    public DeleteFavoriteByIdController(FavoriteService favoriteService, UserService userService, AuctionItemService auctionItemService) {
        this.favoriteService = favoriteService;
        this.userService = userService;
        this.auctionItemService = auctionItemService;
    }

    @DeleteMapping("/delete/id/{id}")
    public Response<Void> delete(@PathVariable("id") int id, @RequestHeader(name ="Authorization") String token,
                                 HttpServletResponse httpServletResponse) {
        try{
            JWTService.parseToken(token,userService.getSecret());
            Response<Void> favoriteDTOResponse = favoriteService.deleteFavoriteById(id);
            if(favoriteDTOResponse.isSuccess()){
                return auctionItemService.decreaseFavourite(id);
            }
            else return favoriteDTOResponse;
        }catch (Exception e) {
            httpServletResponse.setStatus(401);
            return Response.newErrorWithEmptyReturn("删除该收藏商品失败");
        }

    }
}