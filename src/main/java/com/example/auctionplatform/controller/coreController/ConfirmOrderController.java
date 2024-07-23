package com.example.auctionplatform.controller.coreController;

import com.example.auctionplatform.dto.AddressDTO;
import com.example.auctionplatform.dto.AuctionItemDTO;
import com.example.auctionplatform.dto.OrderDTO;
import com.example.auctionplatform.dto.UserDTO;
import com.example.auctionplatform.service.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ConfirmOrderController {
    @Autowired
    public ConfirmOrderController(OrderService orderService, UserService userService,
                                  AuctionItemService auctionItemService,AddressService addressService) {
        this.orderService = orderService;
        this.userService = userService;
        this.auctionItemService = auctionItemService;
        this.addressService = addressService;
    }
    private final OrderService orderService;
    private final UserService userService;
    private final AuctionItemService auctionItemService;
    private final AddressService addressService;
    @PostMapping("/Order/confirmOrder/OrderId/{OrderId}/BuyerAddId/{BuyerAddId}/SellerAddId/{SellerAddId}")
    public Response<Void> confirmOrder(@RequestHeader(name ="Authorization") String token,
                                           HttpServletResponse httpServletResponse,
                                           @PathVariable(name = "OrderId")int orderId,
                                       @PathVariable(name = "BuyerAddId")int buyerAddId,
                                       @PathVariable(name ="SellerAddId")int sellerAddId)  {
        try{
            Map<String,Object> map = JWTService.parseToken(token,userService.getSecret());
            int userId = (int)map.get("userId");
            Response<AuctionItemDTO> auctionItemDTOResponse = auctionItemService.getAuctionItem(orderId);
            if(!auctionItemDTOResponse.isSuccess()){
                return Response.newErrorWithEmptyReturn("no such auction item found");
            }
            AuctionItemDTO auctionItemDTO = auctionItemDTOResponse.getData();
            Response<UserDTO> userDTOResponse =  userService.getUserById(userId);
            if(!userDTOResponse.isSuccess()){
                return Response.newErrorWithEmptyReturn("no user found");
            }
            UserDTO userDTO = userDTOResponse.getData();

            Response<OrderDTO> orderDTOResponse = orderService.getOrderById(orderId);
            if(!orderDTOResponse.isSuccess()){
                return Response.newErrorWithEmptyReturn("no order found");
            }
            OrderDTO orderDTO = orderDTOResponse.getData();
            if(userDTO.getMoney() < orderDTO.getPrice()){
                return Response.newErrorWithEmptyReturn("not enough money");
            }
            Response<AddressDTO> buyerAddDTOResponse = addressService.getAddress(buyerAddId);
            if(!buyerAddDTOResponse.isSuccess()){
                return Response.newErrorWithEmptyReturn("no buyer address found");
            }
            Response<AddressDTO> sellerAddDTOResponse = addressService.getAddress(sellerAddId);
            if(!sellerAddDTOResponse.isSuccess()){
                return Response.newErrorWithEmptyReturn("no seller address found");
            }
            AddressDTO buyerAddressDTO = buyerAddDTOResponse.getData();
            AddressDTO sellerAddressDTO = sellerAddDTOResponse.getData();
            if(buyerAddressDTO.getAddress().isBlank()){
                return Response.newErrorWithEmptyReturn("no buyer address found");
            }
           if(sellerAddressDTO.getAddress().isBlank()){
               return Response.newErrorWithEmptyReturn("no seller address found");
           }
            orderDTO.setBuyAdd(buyerAddressDTO.getAddress());
            orderDTO.setSaleAdd(sellerAddressDTO.getAddress());

            orderService.updateOrderById(orderDTO);

            auctionItemDTO.setState(AuctionItemService.AUCTION_SOLD);

            boolean isSuccess =(userService.decreaseMoney(userId,orderDTO.getPrice()).isSuccess() &&
            orderService.changeOrderPayStateById(orderId,true).isSuccess() &&
            auctionItemService.updateAuctionItem(auctionItemDTO).isSuccess());
            if(isSuccess){
                return Response.newSuccess(null,"Successfully confirmed order");
            }
            return Response.newErrorWithEmptyReturn("order not confirmed");

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
