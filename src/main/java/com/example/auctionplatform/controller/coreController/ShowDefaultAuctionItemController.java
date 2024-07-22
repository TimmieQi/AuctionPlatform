package com.example.auctionplatform.controller.coreController;


import com.auth0.jwt.JWT;
import com.example.auctionplatform.dao.AuctionItem;
import com.example.auctionplatform.dto.AuctionItemDTO;
import com.example.auctionplatform.service.AuctionItemService;
import com.example.auctionplatform.service.JWTService;
import com.example.auctionplatform.service.Response;
import com.example.auctionplatform.service.UserService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class ShowDefaultAuctionItemController {

    @Autowired
    public ShowDefaultAuctionItemController(AuctionItemService auctionItemService, UserService userService) {
        this.auctionItemService = auctionItemService;
        this.userService = userService;
    }
    private final AuctionItemService auctionItemService;
    private final UserService userService;
    @GetMapping("/default")
    public Response<List<AuctionItemDTO>> getDefaultAuctionItem(@RequestHeader(name ="Authorization") String token,
                                                                HttpServletResponse httpServletResponse) {
        try{
            Map<String,Object> str = JWTService.parseToken(token,userService.getSecret());
            return auctionItemService.getAllAuctionItems();
        }catch (Exception e) {
            httpServletResponse.setStatus(401);
            return Response.newErrorWithEmptyReturn("Not Logged In");
        }
    }
}
