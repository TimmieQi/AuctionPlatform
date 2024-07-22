package com.example.auctionplatform.controller.AuctionItemController;

import com.example.auctionplatform.service.AuctionItemService;
import com.example.auctionplatform.service.JWTService;
import com.example.auctionplatform.service.Response;
import com.example.auctionplatform.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：
 * 作者：万礼阳
 * 日期：2024/7/21 上午10:56
 */
@RestController
@RequestMapping("/api/auction")
public class DeleteAuctionController {

    @Autowired
    public DeleteAuctionController(AuctionItemService auctionItemService, UserService userService) {
        this.userService=userService;
        this.auctionItemService = auctionItemService;
    }
    private final AuctionItemService auctionItemService;
    private final UserService userService;
    @DeleteMapping("/delete/auctionId/{auctionId}")
    public Response<Void> delete(@PathVariable("auctionId") int auctionId, @RequestHeader(name ="Authorization") String token,
                                 HttpServletResponse httpServletResponse) {
        try{
            JWTService.parseToken(token,userService.getSecret());
            return auctionItemService.deleteAuctionItemById(auctionId);
        }catch (Exception e) {
            httpServletResponse.setStatus(401);
            return Response.newErrorWithEmptyReturn("添加商品失败");
        }

    }
}