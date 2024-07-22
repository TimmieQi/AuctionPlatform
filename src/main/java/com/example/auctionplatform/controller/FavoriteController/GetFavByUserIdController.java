package com.example.auctionplatform.controller.FavoriteController;

import com.example.auctionplatform.dto.FavoriteDTO;
import com.example.auctionplatform.service.FavoriteService;
import com.example.auctionplatform.service.JWTService;
import com.example.auctionplatform.service.Response;
import com.example.auctionplatform.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
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
@CrossOrigin
public class GetFavByUserIdController {
    private final FavoriteService favoriteService;
    private final UserService userService;
    @Autowired
    public GetFavByUserIdController(FavoriteService favoriteService, UserService userService) {
        this.favoriteService = favoriteService;
        this.userService = userService;
    }
    @GetMapping("/fromId/Userid/{Userid}")
    public Response<List<FavoriteDTO>> getFavById(@PathVariable("Userid") int Userid, @RequestHeader(name ="Authorization") String token,
                                                  HttpServletResponse httpServletResponse) {
        try{
            JWTService.parseToken(token,userService.getSecret());
            return favoriteService.getFavoritesByUserId(Userid);
        }catch (Exception e) {
            httpServletResponse.setStatus(401);
            return Response.newErrorWithEmptyReturn("获取收藏夹失败");
        }
    }
}