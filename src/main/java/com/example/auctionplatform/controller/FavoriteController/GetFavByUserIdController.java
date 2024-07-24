package com.example.auctionplatform.controller.FavoriteController;

import com.example.auctionplatform.dto.AuctionItemDTO;
import com.example.auctionplatform.dto.FavoriteDTO;
import com.example.auctionplatform.service.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    private final AuctionItemService auctionItemService;
    @Autowired
    public GetFavByUserIdController(FavoriteService favoriteService, UserService userService, AuctionItemService auctionItemService) {
        this.favoriteService = favoriteService;
        this.userService = userService;
        this.auctionItemService = auctionItemService;

    }
    @GetMapping("/fromId/Userid/{Userid}")
    public Response<List<AuctionItemDTO>> getFavById(@PathVariable("Userid") int Userid, @RequestHeader(name ="Authorization") String token,
                                                  HttpServletResponse httpServletResponse) {
        try{
            JWTService.parseToken(token,userService.getSecret());
            Response<List<FavoriteDTO>>  favorites = favoriteService.getFavoritesByUserId(Userid);

            if(!favorites.isSuccess()){
                return Response.newErrorWithEmptyReturn("favorites not found");
            }
            List<AuctionItemDTO> auctionItemDTOList=new ArrayList<>();
            for(var favoriteDTO : favorites.getData()) {
                Response<AuctionItemDTO> response = auctionItemService.getAuctionItem(favoriteDTO.getItemId());
                if(response.isSuccess()){
                    auctionItemDTOList.add(response.getData());
                }
            }
            return Response.newSuccess(auctionItemDTOList,"favorites found");
        }catch (Exception e) {
            httpServletResponse.setStatus(401);
            return Response.newErrorWithEmptyReturn("获取收藏夹失败");
        }
    }
}