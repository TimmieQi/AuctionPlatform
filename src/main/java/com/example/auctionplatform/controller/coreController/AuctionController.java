package com.example.auctionplatform.controller.coreController;


import com.example.auctionplatform.dto.AuctionItemDTO;
import com.example.auctionplatform.service.AuctionItemService;
import com.example.auctionplatform.service.JWTService;
import com.example.auctionplatform.service.Response;
import com.example.auctionplatform.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api/auction")
public class AuctionController {
    @Autowired
    public AuctionController(AuctionItemService auctionItemService, UserService userService) {
        this.auctionItemService = auctionItemService;
        this.userService = userService;
    }

    private final AuctionItemService auctionItemService;
    private final UserService userService;
    @PostMapping("/OfferingPrice")
    public Response<Void> OfferingPrice(@RequestHeader(name ="Authorization") String token,
                                        HttpServletResponse httpServletResponse,
                                        @RequestBody AuctionItemDTO auctionItemDTO){
        try{
            JWTService.parseToken(token,userService.getSecret());

            AuctionItemDTO offeringPriceDTO  = new AuctionItemDTO();
            offeringPriceDTO.setId(auctionItemDTO.getId());
            offeringPriceDTO.setCurrPrice(auctionItemDTO.getCurrPrice());
            offeringPriceDTO.setUserId(auctionItemDTO.getUserId());
            return auctionItemService.OfferingPrice(offeringPriceDTO);
        }catch (Exception e) {
            httpServletResponse.setStatus(401);
            return Response.newErrorWithEmptyReturn("Not Logged In");
        }


    }
}
