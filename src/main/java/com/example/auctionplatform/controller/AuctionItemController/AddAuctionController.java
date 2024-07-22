package com.example.auctionplatform.controller.AuctionItemController;

import com.example.auctionplatform.dto.AuctionItemDTO;
import com.example.auctionplatform.service.AuctionItemService;
import com.example.auctionplatform.service.JWTService;
import com.example.auctionplatform.service.Response;
import com.example.auctionplatform.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：上传商品
 * 作者：万礼阳
 * 日期：2024/7/21 上午10:23
 */
@RestController
@RequestMapping("/api/auction")
public class AddAuctionController {
    @Autowired
    public AddAuctionController(AuctionItemService auctionItemService, UserService userService) {
        this.userService=userService;
        this.auctionItemService = auctionItemService;
    }
    private final AuctionItemService auctionItemService;
    private final UserService userService;
    @PostMapping("/add/Auction")
    public Response<Void> addAuctionItem(@RequestBody AuctionItemDTO auctionItemDTO, @RequestHeader(name ="Authorization") String token,
                                         HttpServletResponse httpServletResponse) {
        try{
            JWTService.parseToken(token,userService.getSecret());
            return auctionItemService.addAuctionItem(auctionItemDTO);
        }catch (Exception e) {
            httpServletResponse.setStatus(401);
            return Response.newErrorWithEmptyReturn("获取用户地址失败");
        }

    }
}