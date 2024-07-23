package com.example.auctionplatform.controller.coreController;


import com.example.auctionplatform.dto.AuctionItemDTO;
import com.example.auctionplatform.service.AuctionItemService;
import com.example.auctionplatform.service.JWTService;
import com.example.auctionplatform.service.Response;
import com.example.auctionplatform.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class SearchItemsController {
    @Autowired
    public SearchItemsController(AuctionItemService auctionItemService, UserService userService) {
        this.auctionItemService = auctionItemService;
        this.userService = userService;
    }
    private final AuctionItemService auctionItemService;
    private final UserService userService;
    @GetMapping("/search/name/{name}")
    public Response<List<AuctionItemDTO>> searchItemsByName(@RequestHeader(name ="Authorization") String token,
                                                            HttpServletResponse httpServletResponse,
                                                            @PathVariable(name = "name") String name) {
        try{
            JWTService.parseToken(token,userService.getSecret());
            return auctionItemService.getAuctionItemsByName(name);
        }catch (Exception e) {
            httpServletResponse.setStatus(401);
            return Response.newErrorWithEmptyReturn("Not Logged In");
        }
    }
}
