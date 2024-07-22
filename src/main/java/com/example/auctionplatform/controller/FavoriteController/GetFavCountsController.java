package com.example.auctionplatform.controller.FavoriteController;


import com.example.auctionplatform.dto.FavoriteDTO;
import com.example.auctionplatform.service.FavoriteService;
import com.example.auctionplatform.service.JWTService;
import com.example.auctionplatform.service.Response;
import com.example.auctionplatform.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 功能：
 * 作者：万礼阳
 * 日期：2024/7/22 下午4:01
 */


import java.util.List;

@RestController
@RequestMapping("/api/favorite")
public class GetFavCountsController {

    private final FavoriteService favoriteService;
    private final UserService userService;
    @Autowired
    public GetFavCountsController(FavoriteService favoriteService, UserService userService) {
        this.favoriteService = favoriteService;
        this.userService = userService;
    }
    @RestController
    @RequestMapping("/api/Favorite")
    public class GetFavByUserIdController {
        private final FavoriteService favoriteService;
        private final UserService userService;
        @Autowired
        public GetFavByUserIdController(FavoriteService favoriteService, UserService userService) {
            this.favoriteService = favoriteService;
            this.userService = userService;
        }
        @GetMapping("/counts")
        public Response<List<FavoriteDTO>> getFavoriteCounts(@RequestHeader(name = "Authorization") String token,
                                                             HttpServletResponse httpServletResponse) {
            try {
                JWTService.parseToken(token, userService.getSecret());
                return favoriteService.getFavoriteCounts();
            } catch (Exception e) {
                httpServletResponse.setStatus(401);
                return Response.newErrorWithEmptyReturn("获取收藏统计信息失败");
            }
        }
    }
}