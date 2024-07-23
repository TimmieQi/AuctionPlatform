package com.example.auctionplatform.controller.AuctionItemController;

import com.example.auctionplatform.dto.AddressDTO;
import com.example.auctionplatform.dto.AuctionItemDTO;
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
 * 日期：2024/7/23 下午10:02
 */
@RestController
@RequestMapping("/api/auction")
@CrossOrigin
public class UpdateAuctionController {

    @Autowired
    public UpdateAuctionController(AuctionItemService auctionItemService, UserService userService) {
        this.userService=userService;
        this.auctionItemService = auctionItemService;
    }
    private final AuctionItemService auctionItemService;
    private final UserService userService;
    @PostMapping("/update/auctionId")
    public Response<Void> update(@RequestBody AuctionItemDTO auctionItemDTO, @RequestHeader(name ="Authorization") String token,
                                 HttpServletResponse httpServletResponse) {
        try{
            JWTService.parseToken(token,userService.getSecret());
            if(auctionItemDTO.getId()!=null){
            return auctionItemService.updateAuctionItem(auctionItemDTO);}
            return null;
        }catch (Exception e) {
            httpServletResponse.setStatus(401);
            return Response.newErrorWithEmptyReturn("更新商品失败");
        }

    }
}