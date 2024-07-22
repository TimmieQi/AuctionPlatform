package com.example.auctionplatform.controller.coreController;

import com.example.auctionplatform.dto.AuctionItemDTO;
import com.example.auctionplatform.service.AuctionItemService;
import com.example.auctionplatform.service.JWTService;
import com.example.auctionplatform.service.Response;
import com.example.auctionplatform.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController()
@RequestMapping("/api")
public class ViewDetailController {
    @Autowired
    public ViewDetailController(AuctionItemService auctionItemService, UserService userService) {
        this.auctionItemService = auctionItemService;
        this.userService = userService;
    }
    private final AuctionItemService auctionItemService;
    private final UserService userService;
    @GetMapping("/viewDetail/auctionId/{auctionId}")
    public Response<AuctionItemDTO> viewDetailById(@RequestHeader(name ="Authorization") String token,
                                                   HttpServletResponse httpServletResponse,
                                                   @PathVariable("auctionId") int auctionId) {

        try{
            JWTService.parseToken(token,userService.getSecret());
            return auctionItemService.getAuctionItem(auctionId);
        }catch (Exception e) {
            httpServletResponse.setStatus(401);
            return Response.newErrorWithEmptyReturn("Not Logged In");
        }
    }
}
