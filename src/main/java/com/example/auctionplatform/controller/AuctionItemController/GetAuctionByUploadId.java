package com.example.auctionplatform.controller.AuctionItemController;

import com.example.auctionplatform.dto.AuctionItemDTO;
import com.example.auctionplatform.service.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能：
 * 作者：万礼阳
 * 日期：2024/7/24 下午1:57
 */
@RestController
@RequestMapping("/api/auction")
@CrossOrigin
public class GetAuctionByUploadId {
    @Autowired
    public GetAuctionByUploadId(AuctionItemService auctionItemService, UserService userService) {
        this.auctionItemService = auctionItemService;
        this.userService = userService;
    }
    private final AuctionItemService auctionItemService;
    private final UserService userService;
    @GetMapping("/fromId/uploaderId/{uploaderId}")
    public Response<List<AuctionItemDTO>> getAuctionByUserIdControl(@PathVariable("uploaderId") int uploaderId, @RequestHeader(name ="Authorization") String token,
                                                                    HttpServletResponse httpServletResponse) {
        try{
            JWTService.parseToken(token,userService.getSecret());
            return auctionItemService.getAuctionItemsByUploaderId(uploaderId);
        }catch (Exception e) {
            httpServletResponse.setStatus(401);
            return Response.newErrorWithEmptyReturn("获取用户地址失败");
        }

    }
}