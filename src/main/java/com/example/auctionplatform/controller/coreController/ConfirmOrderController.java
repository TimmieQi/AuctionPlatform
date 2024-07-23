package com.example.auctionplatform.controller.coreController;

import com.example.auctionplatform.dto.AuctionItemDTO;
import com.example.auctionplatform.dto.OrderDTO;
import com.example.auctionplatform.service.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ConfirmOrderController {
    @Autowired
    public ConfirmOrderController(OrderService orderService, UserService userService, AuctionItemService auctionItemService) {
        this.orderService = orderService;
        this.userService = userService;
        this.auctionItemService = auctionItemService;
    }
    private final OrderService orderService;
    private final UserService userService;
    private final AuctionItemService auctionItemService;
    @PostMapping("/Order/confirmOrder")
    public Response<Void> confirmOrder(@RequestHeader(name ="Authorization") String token,
                                           HttpServletResponse httpServletResponse,
                                           @RequestBody OrderDTO orderDTO) {

        try{
            JWTService.parseToken(token,userService.getSecret());
            Response<AuctionItemDTO> auctionItemDTOResponse = auctionItemService.getAuctionItem(orderDTO.getItemId());
            if(!auctionItemDTOResponse.isSuccess()){
                return Response.newErrorWithEmptyReturn("no such auction item found");
            }
            AuctionItemDTO auctionItemDTO = auctionItemDTOResponse.getData();
            auctionItemDTO.setState(AuctionItemService.AUCTION_SOLD);
            auctionItemService.updateAuctionItem(auctionItemDTO);
            orderDTO.setReceived(false);
            return orderService.addNewOrder(orderDTO);
        }catch (Exception e) {
            httpServletResponse.setStatus(401);
            return Response.newErrorWithEmptyReturn("Not Logged In");
        }
    }
    @PostMapping("/Order/update/Order")
    public Response<Void> updateOrder(@RequestHeader(name ="Authorization") String token,
                                      HttpServletResponse httpServletResponse,
                                      @RequestBody OrderDTO orderDTO) {
        try{
            JWTService.parseToken(token,userService.getSecret());
            return orderService.updateOrderById(orderDTO);
        }catch (Exception e) {
            httpServletResponse.setStatus(401);
            return Response.newErrorWithEmptyReturn("Not Logged In");
        }

    }
}
